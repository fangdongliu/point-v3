package cn.fdongl.point.common.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;

@Data
@Entity
public class MapStudentEvaluation extends BaseEntityFromFile{

    @Column(length = 100,nullable = false)
    String courseNumber;
    Long grade;
    Long val;
    Integer parentIndex;
    Integer childIndex;

}
