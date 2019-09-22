package cn.fdongl.point.common.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@Data
public class Course extends BaseEntityFromFile{

    @Column(length = 100,nullable = false)
    String number;
    String name;
    Long grade;
    Long semester;
    Long subSemester;

}
