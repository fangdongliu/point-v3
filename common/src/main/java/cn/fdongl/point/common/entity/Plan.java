package cn.fdongl.point.common.entity;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;

@Data
@Accessors(chain = true)
@EntityListeners(AuditingEntityListener.class)
@Entity
public class Plan extends BaseEntity{

    Long planId;
    Long matrixId;
    Long grade;
    String planFilename;
    String matrixFilename;

}
