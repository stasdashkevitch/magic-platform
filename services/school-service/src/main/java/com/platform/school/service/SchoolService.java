package com.platform.school.service;

import com.platform.school.dto.SchoolRequest;
import com.platform.school.dto.SchoolResponse;
import com.platform.school.exception.SchoolNotFoundException;
import com.platform.school.mapper.SchoolMapper;
import com.platform.school.repository.SchoolRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.function.Supplier;

@Slf4j
@Service
@AllArgsConstructor
public class SchoolService {

    private final SchoolRepository schoolRepository;
    private final SchoolMapper schoolMapper;

    public SchoolResponse getSchoolById(Long id) {
        log.info("Fetching school by ID: {}", id);

        var school = schoolRepository.findById(id).orElse(null);
        if (school == null) {
            log.error("School not found. ID: {}", id);
            throw new SchoolNotFoundException("School not found with id: " + id);
        }

        var schoolResponse = schoolMapper.schoolToSchoolResponse(school);

        log.info("Successfully fetched school. ID: {}", id);
        return schoolResponse;
    }

    public SchoolResponse createSchool(SchoolRequest schoolRequest) {
        log.info("Creating school: {}", schoolRequest);

        var school = schoolMapper.schoolRequestToSchool(schoolRequest);
        var savedSchool = schoolRepository.save(school);
        var resultSchoolResponse = schoolMapper.schoolToSchoolResponse(savedSchool);

        log.info("Successfully created school. ID: {}", school.getId());
        return resultSchoolResponse;
    }

    public SchoolResponse deleteSchoolById(Long id) {
        log.info("Deleting school by ID: {}", id);

        var school = schoolRepository.findById(id).orElse(null);
        if (school == null) {
            log.error("School not found. ID: {}", id);
            throw new SchoolNotFoundException("School not found. ID: " + id);
        }

        schoolRepository.deleteById(id);

        var resultSchoolResponse = schoolMapper.schoolToSchoolResponse(school);

        log.info("Successfully deleted school. ID: {}", id);
        return resultSchoolResponse;
    }

    public SchoolResponse updateSchoolById(Long id, SchoolRequest updateSchoolRequest) {
        log.info("Updating school by ID: {}", id);

        var school = schoolRepository.findById(id).orElse(null);

        if (school == null) {
            throw new SchoolNotFoundException("School not found. ID: " + id);
        }

        updatedField(updateSchoolRequest::name, school::setName);
        updatedField(updateSchoolRequest::address, school::setAddress);
        updatedField(updateSchoolRequest::region, school.getLocation()::setRegion);
        updatedField(updateSchoolRequest::locality, school.getLocation()::setLocality);
        updatedField(updateSchoolRequest::phoneNumber, school::setPhoneNumber);
        updatedField(updateSchoolRequest::email, school::setEmail);
        updatedField(updateSchoolRequest::type, school::setType);
        updatedField(updateSchoolRequest::studentCapacity, school::setStudentCapacity);
        updatedField(updateSchoolRequest::staffCount, school::setStaffCount);
        updatedField(updateSchoolRequest::teacherCount, school::setTeacherCount);
        updatedField(updateSchoolRequest::staffCount, school::setStaffCount);
        updatedField(updateSchoolRequest::classroomCount, school::setClassroomCount);

        if (updateSchoolRequest.facilities() != null) {
            school.setFacilities(new ArrayList<>(updateSchoolRequest.facilities()));
        }

        if (updateSchoolRequest.workTime() != null) {
            updatedField(updateSchoolRequest.workTime()::start, school.getWorkTime()::setStart);
            updatedField(updateSchoolRequest.workTime()::end, school.getWorkTime()::setEnd);
        }

        if (updateSchoolRequest.schoolHours() != null) {
            updatedField(updateSchoolRequest.schoolHours()::start, school.getSchoolHours()::setStart);
            updatedField(updateSchoolRequest.schoolHours()::end, school.getSchoolHours()::setEnd);
        }


        var updatedSchool = schoolRepository.save(school);

        var updatedSchoolResponse = schoolMapper.schoolToSchoolResponse(updatedSchool);

        log.info("Successfully(Patch) updated school. ID: {}", updatedSchool.getId());
        return updatedSchoolResponse;
    }

    private <T>void updatedField(Supplier<T> newValueSupplier, Consumer<T> newValueConsumer) {
        T newValue = newValueSupplier.get();

        if (newValue != null) {
            newValueConsumer.accept(newValue);
        }
    }
}
