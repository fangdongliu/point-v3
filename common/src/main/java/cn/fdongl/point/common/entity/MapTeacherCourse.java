package cn.fdongl.point.common.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;

@Data
@Entity
public class MapTeacherCourse extends BaseEntityFromFile{

    @Column(length = 100,nullable = false)
    String workId;

    @Column(length = 100,nullable = false)
    String courseNumber;
    Long grade;
    Long statuz;


}
