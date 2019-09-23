package cn.fdongl.point.common.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
@Getter
@Setter
public class BaseEntityFromFile extends BaseEntity{

    Long createFrom;

}
