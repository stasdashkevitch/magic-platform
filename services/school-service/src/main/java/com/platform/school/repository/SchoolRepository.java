package com.platform.school.repository;

import com.platform.school.entity.School;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;



public interface SchoolRepository extends
        JpaRepository<School, Long>,
        QuerydslPredicateExecutor<School> {
}
