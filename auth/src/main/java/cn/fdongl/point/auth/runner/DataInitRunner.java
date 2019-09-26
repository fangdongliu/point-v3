package cn.fdongl.point.auth.runner;

import cn.fdongl.point.auth.repository.UserRepository;
import cn.fdongl.point.common.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitRunner implements CommandLineRunner {

    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        User user = new User()
                .setUsername("admin")
                .setRealName("admin")
                .setRole("admin")
                .setPassword(passwordEncoder.encode("123456"));
        try {
            userRepository.save(user);
        }catch (Exception e){
            System.out.println("admin已存在");
        }
    }
}
