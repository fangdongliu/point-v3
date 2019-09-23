package cn.fdongl.point.uploadapiimpl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@SpringBootApplication
public class UploadApiImplApplication {

    public static void main(String[] args) {
        SpringApplication.run(UploadApiImplApplication.class, args);
    }

}
