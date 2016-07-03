package com.runzii.controller;

import com.runzii.exception.ParamErrorException;
import com.runzii.exception.VarifyErrorException;
import com.runzii.model.entity.User;
import com.runzii.model.form.UserForm;
import com.runzii.model.support.Role;
import com.runzii.repository.UserRepository;
import com.runzii.service.AliDayu;
import com.runzii.service.UserService;
import com.runzii.utils.DTOUtil;
import com.taobao.api.ApiException;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Collection;

import static org.springframework.web.bind.annotation.RequestMethod.*;

/**
 * Created by runzii on 16-4-17.
 */
@RequestMapping("/api/users")
@Slf4j
@RestController
@Transactional
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AliDayu dayu;

    @RequestMapping(method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "批量查找画家", notes = "画家的获取接口，无需认证")
    public Collection<User> queryUser(@ApiParam(value = "前几个(根据粉丝数量排名)，-1代表获取全部", required = true)
                                      @RequestParam int num) {
        if (num == -1)
            return userRepository.findByRole(Role.ROLE_PAINTER);
        else
            return userRepository.findAll(new PageRequest(0, num, Sort.Direction.DESC, "fans")).getContent();
    }

    @ApiOperation(value = "获取单个用户信息", notes = "需要带上用户id")
    @RequestMapping(value = "/{id:[0-9]+}", method = GET)
    public User queryOneUser(@PathVariable Long id) {
        return userRepository.findOne(id);
    }

    @ApiOperation(value = "关注某个用户", notes = "在url中加上被关注的用户的id")
    @RequestMapping(value = "/{id:[0-9]+}/follow", method = POST)
    public void follow(Principal principal, @PathVariable Long id) {
        userRepository.findByPhone(principal.getName())
                .getFollows().add(userRepository.findOne(id));
    }

    @ApiOperation(value = "获取自己的信息，包括id等", notes = "需要带上token认证")
    @RequestMapping(value = "/self", method = GET)
    public User querySelf(Principal principal) {
        return userRepository.findByPhone(principal.getName());
    }

    @RequestMapping(method = POST)
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "注册用户", notes = "body里加上验证码")
    @ApiResponses(value = {
            @ApiResponse(code = 403, message = "用户参数出错，具体看返回的String"),
            @ApiResponse(code = 201, message = "注册成功")})
    public void addUser(Principal principal, @RequestBody @Valid UserForm userForm, BindingResult errors) {
        if (errors.hasErrors()) {
            throw new ParamErrorException(errors.getAllErrors().get(0).getDefaultMessage());
        }
        User user = DTOUtil.map(userForm, User.class);
        userService.createUser(user);
//        if (userService.verifyPhone(user.getPhone(), userForm.getVerifyCode()))
//            userService.createUser(user);
//        else throw new VarifyErrorException("验证码不正确");
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/{id:[0-9]+}", method = DELETE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    @ApiOperation(value = "删除用户", notes = "只有管理员能够操作")
    public void deleteUser(@PathVariable Long id) {
        userRepository.delete(id);
    }

    @RequestMapping(value = "/{id:[0-9]+}", method = PATCH)
    @ResponseStatus(HttpStatus.ACCEPTED)
    @ApiOperation(value = "修改用户信息", notes = "直接把用户类传过来，不要带上密码")
    @ApiResponses(value = {
            @ApiResponse(code = 403, message = "用户参数出错，具体看返回的String"),
            @ApiResponse(code = 202, message = "修改成功")})
    public void updateUser(@PathVariable Long id, @RequestBody @Valid UserForm userForm, BindingResult errors) {
        if (errors.hasErrors()) {
            throw new ParamErrorException(errors.getAllErrors().get(0).getDefaultMessage());
        }
        userService.updateUser(id, userForm);
    }

    @RequestMapping(value = "/changepwd", method = RequestMethod.PATCH)
    @ResponseStatus(HttpStatus.ACCEPTED)
    @ApiOperation(value = "修改用户密码", notes = "要在RequestParam加上验证码")
    @ApiResponses(value = {
            @ApiResponse(code = 403, message = "用户参数出错，具体看返回的String"),
            @ApiResponse(code = 202, message = "修改密码成功")})
    public void changePassword(@RequestParam String verifyCode, @RequestParam String phone, @RequestParam String newpwd) {
        if (userService.verifyPhone(phone, verifyCode)) {
            if (!userService.changePassword(phone, newpwd))
                throw new ParamErrorException("新密码不能为空");
        } else throw new VarifyErrorException("验证码不正确");
    }

    @RequestMapping(value = "/sendmsg", method = RequestMethod.GET)
    @ApiOperation(value = "发送验证码", notes = "在注册用户前先发送验证码，用验证码注册")
    public String sendMsg(@RequestParam(value = "phone") String phone,
                          @ApiParam(value = "短信类型,1注册验证，2修改密码验证",
                                  allowableValues = "1,2", required = true)
                          @RequestParam(value = "type") int type) throws ApiException {
        String verifyCode = dayu.getRandNum(5);
        userService.createVerify(phone, verifyCode);
        return dayu.sendMessage(phone, verifyCode, type);
    }

}
