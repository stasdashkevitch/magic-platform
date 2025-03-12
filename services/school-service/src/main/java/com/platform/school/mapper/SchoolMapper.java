package com.platform.school.mapper;

import com.platform.school.dto.SchoolRequest;
import com.platform.school.dto.SchoolResponse;
import com.platform.school.entity.School;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SchoolMapper {

    SchoolResponse schoolToSchoolResponse(School school);

    School schoolRequestToSchool(SchoolRequest schoolRequest);
}