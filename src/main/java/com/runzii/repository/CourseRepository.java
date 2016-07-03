package com.runzii.repository;


import com.runzii.model.entity.Course;
import com.runzii.model.entity.User;
import com.runzii.model.support.CourseType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;

/**
 * Created by runzii on 16-4-7.
 */
@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    Collection<Course> findByUser(@Param("user") User user);

    Collection<Course> findByUserAndCourseType(@Param("user") User user, @Param("courseType") CourseType courseType);

    Collection<Course> findByCourseType(@Param("courseType") CourseType courseType);

}
