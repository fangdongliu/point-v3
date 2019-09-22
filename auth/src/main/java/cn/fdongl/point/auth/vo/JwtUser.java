package cn.fdongl.point.auth.vo;

import cn.fdongl.point.common.entity.User;
import cn.fdongl.point.common.util.Converter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

@Getter
@Setter
public class JwtUser implements UserDetails {

    public static JwtUser fromUser(User user){
        JwtUser jwtUser = Converter.convert(user, JwtUser.class);
        jwtUser.authorities = Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"),new SimpleGrantedAuthority("ROLE_"+user.getRole()));
        return jwtUser;
    }

    Long id;

    String username;

    String password;

    String realName;

    String role;

    String department;

    Long grade;

    Date createDate;

    Long createBy;

    Date modifyDate;

    Long modifiedBy;

    private Collection<? extends GrantedAuthority> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @JsonIgnore
    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return true;
    }
}

