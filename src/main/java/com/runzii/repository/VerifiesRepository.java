package com.runzii.repository;

import com.runzii.model.entity.VerifyCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by runzii on 16-4-20.
 */
@Repository
public interface VerifiesRepository extends JpaRepository<VerifyCode, Long> {

    VerifyCode findByPhone(@Param("phone") String phone);

}
