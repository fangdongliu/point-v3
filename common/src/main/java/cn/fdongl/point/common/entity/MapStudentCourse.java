package cn.fdongl.point.common.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@Data
public class MapStudentCourse extends BaseEntityFromFile{

    @Column(length = 100,nullable = false)
    String workId;

    @Column(length = 100,nullable = false)
    String courseNumber;
    Long courseSemester;
    Long subCourseSemester;
    String courseName;
    Long statuz;

}
