package com.runzii.repository;


import com.runzii.model.entity.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by runzii on 16-4-7.
 */
@Repository
@Transactional
public interface VideoRepository extends JpaRepository<Video, Long> {

    Video findByBucketAndKey(@Param("bucket") String bucket, @Param("key") String key);

}
