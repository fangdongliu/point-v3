package cn.fdongl.point.common.entity;

import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@Data
@EntityListeners(AuditingEntityListener.class)
@Table(
        uniqueConstraints = @UniqueConstraint(columnNames = {"courseNumber","workId","courseSemester"})
)
public class MapStudentCourse extends BaseEntityFromFile{

    @Column(length = 100,nullable = false)
    String workId;

    String realName;

    @Column(length = 100,nullable = false)
    String courseNumber;
    Long courseSemester;
    Long subCourseSemester;
    String courseName;
    Long statuz;

}
