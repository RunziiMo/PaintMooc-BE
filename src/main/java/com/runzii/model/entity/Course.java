package com.runzii.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.runzii.model.serializer.UserSetSerializer;
import com.runzii.model.support.CourseType;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.Set;

/**
 * Created by runzii on 16-4-10.
 */
@Entity
@Table(name = "COURSES", schema = "PaintMooc", catalog = "")
@DynamicInsert()
@Getter
@Setter
public class Course extends BaseModel {

    @NotEmpty
    private String coursename;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Enumerated(EnumType.STRING)
    @NotNull
    private CourseType courseType = CourseType.Chinese;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "course", cascade = CascadeType.REMOVE)
    private Set<Video> videos;

    private String thumbnail = "http://o76845j6w.bkt.clouddn.com/2.png";

    @JsonSerialize(using = UserSetSerializer.class)
    @ManyToMany(mappedBy = "likes")
    private Set<User> fans;

}
