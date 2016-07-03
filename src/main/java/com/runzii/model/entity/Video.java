package com.runzii.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.runzii.model.support.VideoStatus;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;

import static com.runzii.model.support.VideoStatus.UPLOADING;


/**
 * Created by runzii on 16-4-10.
 */
@Entity
@Table(name = "videos", schema = "PaintMooc", catalog = "")
@DynamicInsert(value = true)
public class Video extends BaseModel {

    @JsonIgnore
    @ManyToOne
    private Course course;
    @NotEmpty
    private String videoname;

    @Enumerated(EnumType.STRING)
    private VideoStatus status = UPLOADING;
    private String bucket;
    @Column(name = "`key`")
    private String key;

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public String getVideoname() {
        return videoname;
    }

    public void setVideoname(String videoname) {
        this.videoname = videoname;
    }

    public VideoStatus getStatus() {
        return status;
    }

    public void setStatus(VideoStatus status) {
        this.status = status;
    }

    public String getBucket() {
        return bucket;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
