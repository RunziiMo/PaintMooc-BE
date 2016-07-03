package com.runzii.model.form;

import com.runzii.model.support.Role;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import static com.runzii.model.support.Role.ROLE_USER;

/**
 * Created by runzii on 16-6-10.
 */
@Getter
@Setter
public class UserForm {

    private String verifyCode;

    @NotEmpty(message = "用户名不能为空")
    private String username;

    @NotEmpty(message = "密码不能为空")
    @Size(min = 6, max = 255, message = "密码必须在6到255位之间")
    private String password;

    @NotEmpty(message = "手机号不能为空")
    @Pattern(regexp = "^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$", message = "手机号格式不正确")
    private String phone;

    private Role role = ROLE_USER;
}
