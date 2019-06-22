package pl.sda.springfrontend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringFrontendApplication {

    public static void main(String[] args) {

        SpringApplication springApplication = new SpringApplication(SpringFrontendApplication.class);
        springApplication.setAdditionalProfiles("ssl");
        springApplication.run(args);
    }

}
