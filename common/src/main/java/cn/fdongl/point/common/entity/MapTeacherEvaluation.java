package cn.fdongl.point.common.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;

@Data
@Entity
public class MapTeacherEvaluation extends BaseEntityFromFile{

    @Column(length = 100,nullable = false)
    String courseNumber;
    Long grade;
    Double val;
    Integer parentIndex;
    Integer childIndex;

}
