package cn.fdongl.point.common.entity;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;

@Data
@Entity
@Accessors(chain = true)
@EntityListeners(AuditingEntityListener.class)
public class MapTeacherCourse extends BaseEntityFromFile{

    @Column(length = 100,nullable = false)
    String workId;

    @Column(length = 100,nullable = false)
    String courseNumber;
    Long statuz;

    Long semester;
    Long subSemester;


}
