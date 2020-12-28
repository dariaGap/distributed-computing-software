package beauty_app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource({"classpath:context.xml"})
public class BeautyApp {
    public static void main(String[] args) {
        SpringApplication.run(BeautyApp.class, args);
    }
}
