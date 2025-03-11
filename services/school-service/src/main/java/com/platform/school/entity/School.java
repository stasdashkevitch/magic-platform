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
public class School {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne
    private SchoolLocation location;
    private String address;
    private String phoneNumber;
    private String email;
    private String type;
    private Integer establishedYear;
    private Integer studentCapacity;
    private Integer studentCount;
    private Integer teacherCount;
    private Integer staffCount;
    private Integer classroomCount;
    @Column(columnDefinition = "jsonb")
    @Convert(converter = FacilitiesConverter.class)
    private List<String> facilities;
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "start", column = @Column(name = "work_start_time")),
            @AttributeOverride(name = "end", column = @Column(name = "work_end_time"))
    })
    private TimeRange workTime;
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "start", column = @Column(name = "school_start_time")),
            @AttributeOverride(name = "end", column = @Column(name = "school_end_time"))
})
    private TimeRange schoolHours;
}
