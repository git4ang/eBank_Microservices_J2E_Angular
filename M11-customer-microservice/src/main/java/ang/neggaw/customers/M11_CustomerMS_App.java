package ang.neggaw.customers;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class M11_CustomerMS_App {
    public static void main(String[] args) {
        SpringApplication.run(M11_CustomerMS_App.class, args);
    }
}
