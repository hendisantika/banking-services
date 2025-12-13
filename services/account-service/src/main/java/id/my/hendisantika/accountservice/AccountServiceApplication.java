package id.my.hendisantika.accountservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class AccountServiceApplication {

    static void main(String[] args) {
        SpringApplication.run(AccountServiceApplication.class, args);
    }

}
