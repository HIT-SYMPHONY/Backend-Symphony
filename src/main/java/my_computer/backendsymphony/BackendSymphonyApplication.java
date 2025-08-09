package my_computer.backendsymphony;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class BackendSymphonyApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackendSymphonyApplication.class, args);
        System.out.println("BackendSymphony Application Started");
    }
}
