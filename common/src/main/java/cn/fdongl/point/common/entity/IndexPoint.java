package cn.fdongl.point.common.entity;


import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;

@Data
@Entity
@Accessors(chain = true)
@EntityListeners(AuditingEntityListener.class)
public class IndexPoint extends BaseEntityFromFile{

    Long parentIndex;
    Long childIndex;

    String content;
    String parent;

    Long grade;


}
