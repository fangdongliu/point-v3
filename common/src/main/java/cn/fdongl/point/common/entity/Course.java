package cn.fdongl.point.common.entity;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;

@Entity
@Data
@Accessors(chain = true)
@EntityListeners(AuditingEntityListener.class)
public class Course extends BaseEntityFromFile{

    @Column(length = 100,nullable = false)
    String number;
    String name;
    String type;
    Long grade;
    Long semester;
    Long subSemester;

}
