package com.runzii.controller;

import com.runzii.exception.ParamErrorException;
import com.runzii.model.entity.Course;
import com.runzii.model.entity.User;
import com.runzii.model.entity.Video;
import com.runzii.model.qiniu.CallBackFetchKey;
import com.runzii.model.qiniu.CallBackInfo;
import com.runzii.model.qiniu.TransCallBackInfo;
import com.runzii.model.support.CourseType;
import com.runzii.model.support.VideoStatus;
import com.runzii.repository.CourseRepository;
import com.runzii.repository.UserRepository;
import com.runzii.repository.VideoRepository;
import com.runzii.service.QiNiu;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Collection;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * Created by runzii on 16-4-12.
 */
@RequestMapping("/api/courses")
@RestController
public class CourseController {

    private static final Logger logger = Logger.getLogger(CourseController.class);

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private VideoRepository videoRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private QiNiu qiNiu;

    @ApiOperation(value = "获取课程", notes = "所有参数可选，不带参数即为获取所有画家的所有课程")
    @RequestMapping(method = GET)
    public Collection<Course> getCourses(@ApiParam(value = "用户id，根据id获取课程", required = false)
                                         @RequestParam(required = false) Long userid
            , @RequestParam(required = false) CourseType type) {
        if (userid == null && type == null) {
            return courseRepository.findAll();
        } else if (userid == null)
            return courseRepository.findByCourseType(type);
        else if (type == null)
            return courseRepository.findByUser(userRepository.findOne(userid));
        else
            return courseRepository.findByUserAndCourseType(userRepository.findOne(userid), type);
    }

    @ApiOperation(value = "点赞某个课程", notes = "在url中加上被点赞的课程的id")
    @RequestMapping(value = "/{id:[0-9]+}/like", method = POST)
    public void like(Principal principal, @PathVariable Long id) {
        userRepository.findByPhone(principal.getName())
                .getLikes().add(courseRepository.findOne(id));
    }

    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_PAINTER') or hasRole('ROLE_ADMIN')")
    @ApiOperation(value = "获取视频下载的URL", notes = "需要带上视频的ID唯一标志符号")
    @RequestMapping(value = "/videos/{vid:[0-9]+}", method = GET)
    public String getVideo(@PathVariable Long vid) {
        Video video = videoRepository.findOne(vid);
        return qiNiu.generateURL(video.getKey());
    }

    @PreAuthorize("hasRole('ROLE_PAINTER') or hasRole('ROLE_ADMIN')")
    @ApiOperation(value = "删除视频", notes = "需要带上视频的ID唯一标志符号")
    @ResponseStatus(OK)
    @RequestMapping(value = "/videos/{vid:[0-9]+}", method = DELETE)
    public void deleteVideo(@PathVariable Long vid) {
        Video video = videoRepository.findOne(vid);
        qiNiu.deleteVideo(video.getBucket(), video.getKey());
        videoRepository.delete(video);
    }

    @PreAuthorize("hasRole('ROLE_PAINTER') or hasRole('ROLE_ADMIN')")
    @ApiOperation(value = "增加课程", notes = "必须带上认证")
    @ResponseStatus(CREATED)
    @RequestMapping(method = POST)
    public void addCourses(Principal principal,
                           @ApiParam(value = "post一个带课程名的类就行")
                           @Valid @RequestBody Course course, Errors errors) {
        if (errors.hasErrors()) {
            logger.debug("课程参数验证出错");
            throw new ParamErrorException(errors.getAllErrors().get(0).getDefaultMessage());
        }
        User user = userRepository.findByPhone(principal.getName());
        course.setUser(user);
        courseRepository.save(course);
    }

    @PreAuthorize("hasRole('ROLE_PAINTER') or hasRole('ROLE_ADMIN')")
    @ApiOperation(value = "增加视频章节", notes = "必须带上认证")
    @RequestMapping(value = "/{courseid:[0-9]+}", method = POST)
    @ResponseStatus(CREATED)
    public void addVideo(@PathVariable Long courseid,
                         @ApiParam(value = "post一个带课名的类")
                         @Valid @RequestBody Video video, Errors errors) {
        if (errors.hasErrors()) {
            throw new ParamErrorException(errors.getAllErrors().get(0).getDefaultMessage());
        }
        Course course = courseRepository.findOne(courseid);
        if (course == null) {
            throw new ParamErrorException("该课程不存在");
        }
        video.setCourse(course);
        videoRepository.save(video);
    }

    @ApiOperation(value = "获取上传视频的token", notes = "每次上传视频前必须先获取token")
    @PreAuthorize("hasRole('ROLE_PAINTER') or hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/gettoken", method = GET)
    public String getToken(Principal principal) {
        return qiNiu.getUpToken(principal.getName());
    }

    @ApiIgnore
    @RequestMapping(value = "/handleqiniu", method = POST)
    @ResponseStatus(OK)
    public CallBackFetchKey handleQiNiu(@RequestBody CallBackInfo info) {
        Course course = courseRepository.findOne(info.getCid());
        if (course != null) {
            Video video = new Video();
            video.setVideoname(info.getVideoname());
            video.setCourse(course);
            video.setStatus(VideoStatus.TRANSCODING);
            video.setBucket(info.getBucket());
            video.setKey(info.getKey());
            videoRepository.save(video);
        }
        CallBackFetchKey fetchKey = new CallBackFetchKey();
        fetchKey.setKey(info.getKey());
        fetchKey.setPayload(new CallBackFetchKey
                .PayloadBean(course == null ? "false" : "true", info.getVideoname()));
        return fetchKey;
    }

    @ApiIgnore
    @RequestMapping(value = "/handleready", method = POST)
    public void handleReady(@RequestBody TransCallBackInfo info) {
        logger.debug(info);
        if (info.getCode() == 0) {
            Video video = videoRepository.findByBucketAndKey(info.getInputBucket(), info.getInputKey());
            video.setKey(info.getItems().get(0).getKey());
            video.setStatus(VideoStatus.READY);
            videoRepository.save(video);
            logger.debug("视频 " + video.getVideoname() + " 转码完成");
        }
    }


    @RequestMapping(value = "/samplevideo", method = GET)
    @ResponseBody
    public String getVideo() {
        return "http://7xsxal.com2.z0.glb.clouddn.com/5PMdr_L6S8NfIEFRfuI49c2GhAs=/limFKu_k2JpZtqo3LdejPq5gTkAR";
    }

}
