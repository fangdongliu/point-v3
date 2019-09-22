package cn.fdongl.point.common.entity;

import lombok.Data;

import javax.persistence.Entity;

@Data
@Entity
public class MapCourseIndex extends BaseEntityFromFile{

    Long indexId;
    Long courseId;

}
