package com.platform.school.entity;


import com.platform.school.converter.FacilitiesConverter;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "school")
public class School {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne
    private SchoolLocation location;
    @Column(nullable = false)
    private String address;
    @Column(nullable = false, unique = true)
    private String phoneNumber;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String type;
    @Column(nullable = false)
    private Integer establishedYear;
    @Column(nullable = false)
    private Integer studentCapacity;
    @Column(nullable = false)
    private Integer studentCount;
    @Column(nullable = false)
    private Integer teacherCount;
    @Column(nullable = false)
    private Integer staffCount;
    @Column(nullable = false)
    private Integer classroomCount;
    @Column(columnDefinition = "jsonb")
    @Convert(converter = FacilitiesConverter.class)
    private List<String> facilities;
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "start", column = @Column(name = "work_start_time", nullable = false)),
            @AttributeOverride(name = "end", column = @Column(name = "work_end_time", nullable = false))
    })
    private TimeRange workTime;
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "start", column = @Column(name = "school_start_time", nullable = false)),
            @AttributeOverride(name = "end", column = @Column(name = "school_end_time", nullable = false))
})
    private TimeRange schoolHours;
}
