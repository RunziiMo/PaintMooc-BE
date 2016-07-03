package com.runzii.repository;


import com.runzii.model.entity.User;
import com.runzii.model.support.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;

/**
 * Created by runzii on 16-4-7.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Collection<User> findByRole(@Param("role") Role role);

    User findByUsername(@Param("username") String username);

    User findByPhone(@Param("phone") String phone);
}
