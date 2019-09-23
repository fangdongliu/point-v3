package cn.fdongl.point.exec;

import cn.fdongl.point.auth.EnableAuthModule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableAuthModule
@ComponentScan("cn.fdongl.point")
public class ExecApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExecApplication.class, args);
    }

}
