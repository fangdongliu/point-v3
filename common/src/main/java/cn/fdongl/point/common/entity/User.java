package cn.fdongl.point.common.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "sys_user")
@Accessors(chain = true)
@EntityListeners(AuditingEntityListener.class)
public class User extends BaseEntityFromFile{

    @Column(length = 100,unique = true)
    String username;

    @Column(length = 100)
    String password;

    @Column(length = 40)
    String realName;

    @Column(length = 30)
    String role;

    String department;


    Long grade;

}
