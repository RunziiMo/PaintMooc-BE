package com.runzii.model.entity;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.runzii.model.serializer.CoursesSerializer;
import com.runzii.model.serializer.UserSetSerializer;
import com.runzii.model.support.Role;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Set;

import static com.runzii.model.support.Role.ROLE_USER;

/**
 * Created by runzii on 16-4-10.
 */
@Entity
@Table(name = "users")
@DynamicInsert()
@DynamicUpdate()
@Setter
@Getter
public class User extends BaseModel {

    @NotEmpty(message = "用户名不能为空")
    private String username;
    private String avatar;

    @NotEmpty(message = "密码不能为空")
    @Size(min = 6, max = 255, message = "密码必须在6到255位之间")
    private String password;

    @Column(unique = true)
    @NotEmpty(message = "手机号不能为空")
    @Pattern(regexp = "^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$", message = "手机号格式不正确")
    private String phone;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role = ROLE_USER;

    @JsonSerialize(using = CoursesSerializer.class)
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.REMOVE)
    private Set<Course> course;

    @JsonSerialize(using = UserSetSerializer.class)
    @ManyToMany(mappedBy = "follows")
    private Set<User> fans;

    @JsonSerialize(using = UserSetSerializer.class)
    @ManyToMany()
    @JoinTable(name = "user_relations",
            joinColumns = {@JoinColumn(name = "from_user_id", nullable = false, updatable = false)},
            inverseJoinColumns = {@JoinColumn(name = "to_user_id", nullable = false, updatable = false)}
    )
    private Set<User> follows;

    @JsonSerialize(using = CoursesSerializer.class)
    @ManyToMany()
    @JoinTable(name = "user_courses_like",
            joinColumns = {@JoinColumn(name = "from_user_id", nullable = false, updatable = false)},
            inverseJoinColumns = {@JoinColumn(name = "to_course_id", nullable = false, updatable = false)}
    )
    private Set<Course> likes;

}
