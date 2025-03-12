--liquibase formatted sql

-- changeset sd:1
CREATE TABLE IF NOT EXISTS school_location
(
    id BIGSERIAL PRIMARY KEY,
    region VARCHAR(255) NOT NULL,
    locality VARCHAR(255) NOT NULL
);

-- changeset sd:2
    CREATE TABLE IF NOT EXISTS school
    (
        id BIGSERIAL PRIMARY KEY,
        name varchar(255) NOT NULL ,
        location_id BIGINT,
        address VARCHAR(255) NOT NULL,
        phone_number VARCHAR(50) UNIQUE NOT NULL,
        email VARCHAR(255) UNIQUE NOT NULL,
        type VARCHAR(50) NOT NULL,
        established_year INTEGER NOT NULL,
        student_capacity INTEGER NOT NULL,
        student_count INTEGER NOT NULL,
        teacher_count INTEGER NOT NULL,
        staff_count INTEGER NOT NULL,
        classroom_count INTEGER NOT NULL,
        facilities JSONB,
        work_start_time TIME NOT NULL,
        work_end_time TIME NOT NULL,
        school_start_time TIME NOT NULL,
        school_end_time TIME NOT NULL,
        FOREIGN KEY (location_id) REFERENCES school_location
    );