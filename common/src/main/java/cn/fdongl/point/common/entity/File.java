package cn.fdongl.point.common.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Entity;

@Data
@Entity
@Accessors(chain = true)
public class File extends BaseEntity{

    String filename;

    @Column(length = 40)
    String type;




}
