package cn.fdongl.point.common.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.CreatedBy;

import javax.persistence.Column;
import javax.persistence.Entity;

@Data
@Entity
@Accessors(chain = true)
public class User extends BaseEntityFromFile{

    @Column(length = 100)
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
