package cn.fdongl.point.auth.controller;

import cn.fdongl.point.auth.repository.UserRepository;
import cn.fdongl.point.auth.vo.JwtUser;
import cn.fdongl.point.common.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserRestController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping(value = "user")
    public User getAuthenticatedUser(JwtUser jwtUser) {
        return userRepository.findFirstByUsername(jwtUser.getUsername());
    }
}