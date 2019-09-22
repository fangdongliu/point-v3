package cn.fdongl.point.common.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@Data
public class CourseInfo extends BaseEntityFromFile{

    @Column(length = 100,nullable = false)
    String name;
    Double credit;
    @Column(length = 100,nullable = false)
    String number;
    @Column(length = 30)
    String type;
    Long grade;
    Long semester;
    Long subSemester;

}
