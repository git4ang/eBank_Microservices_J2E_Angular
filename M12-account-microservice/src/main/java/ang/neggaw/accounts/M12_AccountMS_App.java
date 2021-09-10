package ang.neggaw.accounts;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@EnableEurekaClient
@SpringBootApplication
public class M12_AccountMS_App {
    public static void main(String[] args) {
        SpringApplication.run(M12_AccountMS_App.class, args);
    }
}
