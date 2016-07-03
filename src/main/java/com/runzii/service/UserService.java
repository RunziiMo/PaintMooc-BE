package com.runzii.service;

import com.runzii.model.entity.User;
import com.runzii.model.form.UserForm;
import com.runzii.model.support.Role;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Collection;

/**
 * Created by runzii on 16-4-17.
 */
public interface UserService extends UserDetailsService {

    void createUser(User user);

    void createVerify(String phone, String verifyCode);

    boolean verifyPhone(String phone, String VerifyCode);

    void updateUser(long id, UserForm user);

    Collection<User> findByRole(Role role);

    boolean changePassword(String phone, String newPassword);

}
