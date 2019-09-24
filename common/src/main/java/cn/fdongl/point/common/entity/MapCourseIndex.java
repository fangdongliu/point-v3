package cn.fdongl.point.common.entity;

import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;

@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
public class MapCourseIndex extends BaseEntityFromFile{

    Long indexId;
    Long courseId;
    Long grade;
    Double val;
    Long parentIndex;
    Long childIndex;

}
