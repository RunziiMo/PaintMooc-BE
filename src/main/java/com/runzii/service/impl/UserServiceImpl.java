package com.runzii.service.impl;

import com.runzii.exception.UserExistException;
import com.runzii.exception.UserNotExistException;
import com.runzii.exception.VarifyErrorException;
import com.runzii.model.entity.User;
import com.runzii.model.entity.VerifyCode;
import com.runzii.model.form.UserForm;
import com.runzii.model.support.Role;
import com.runzii.repository.UserRepository;
import com.runzii.repository.VerifiesRepository;
import com.runzii.service.UserService;
import com.runzii.utils.DTOUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.Collection;
import java.util.Collections;

/**
 * Created by runzii on 16-4-17.
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VerifiesRepository verifiesRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public boolean verifyPhone(String phone, String VerifyCode) {
        VerifyCode code = verifiesRepository.findByPhone(phone);
        if (code == null)
            throw new VarifyErrorException("验证码不存在");
        if (code.getVerifyCode().equals(VerifyCode)) {
            verifiesRepository.delete(code);
            return true;
        }
        return false;
    }

    @Override
    public void updateUser(long id, UserForm user) {
        User userl = userRepository.findOne(id);
        if (userl == null) {
            throw new UserNotExistException("该用户id不存在");
        }
        DTOUtil.mapTo(user, userl);
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userRepository.findByPhone(s);
        if (user == null) {
            throw new UsernameNotFoundException("user " + s + " 不存在!");
        }
        return createSpringUser(user);
    }

    private org.springframework.security.core.userdetails.User createSpringUser(User user) {
        return new org.springframework.security.core.userdetails.User(
                user.getPhone(),
                user.getPassword(),
                Collections.singleton(createAuthority(user)));
    }

    private GrantedAuthority createAuthority(User user) {
        return new SimpleGrantedAuthority(user.getRole().toString());
    }

    @Override
    public void createUser(User user) {
        User check = userRepository.findByUsername(user.getUsername());
        if (check != null) {
            throw new UserExistException("用户名 " + user.getUsername() + " 已存在！");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.saveAndFlush(user);
        logger.debug("gotcha");
    }

    @Override
    public Collection<User> findByRole(Role role) {
        return userRepository.findByRole(role);
    }

    @Override
    public boolean changePassword(String phone, String newPassword) {
        if (newPassword == null || newPassword.isEmpty())
            return false;

        User user = userRepository.findByPhone(phone);
        if (user == null) {
            throw new UserNotExistException("手机号 " + phone + " 对应的用户不存在！");
        }
        user.setPassword(passwordEncoder.encode(newPassword));

        userRepository.save(user);

        logger.info("用户 " + user.getUsername() + " 密码更改。");

        return true;
    }

    @Override
    public void createVerify(String phone, String verifyCode) {
        VerifyCode code = verifiesRepository.findByPhone(phone);
        if (code != null)
            code.setVerifyCode(verifyCode);
        else
            code = new VerifyCode(phone, verifyCode);
        verifiesRepository.save(code);
    }
}
