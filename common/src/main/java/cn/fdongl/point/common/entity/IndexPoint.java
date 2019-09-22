package cn.fdongl.point.common.entity;


import lombok.Data;

import javax.persistence.Entity;

@Data
@Entity
public class IndexPoint extends BaseEntityFromFile{

    Long parentIndex;
    Long childIndex;

    String content;
    String parent;


}
