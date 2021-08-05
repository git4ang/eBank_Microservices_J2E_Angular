package ang.neggaw.employees;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;


@EnableEurekaClient
@SpringBootApplication
public class M10_EmployeeMS_App {
    public static void main(String[] args) {
        SpringApplication.run(M10_EmployeeMS_App.class, args);
    }
}
