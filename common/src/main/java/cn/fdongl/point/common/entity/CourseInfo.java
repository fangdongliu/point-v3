package cn.fdongl.point.common.entity;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import java.util.Date;

@Entity
@Data
@Accessors(chain = true)
@EntityListeners(AuditingEntityListener.class)
public class CourseInfo extends BaseEntityFromFile{

    @Column(length = 100,nullable = false)
    String name;
    Double credit;
    @Column(length = 100,nullable = false)
    String number;
    @Column(length = 30)
    String type;
    Long grade;

}
