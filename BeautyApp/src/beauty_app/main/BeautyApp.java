package beauty_app.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;

@ComponentScan(basePackages = "beauty_app")
@EnableAutoConfiguration
@ImportResource({"classpath:context.xml"})
public class BeautyApp {
    public static void main(String[] args) {
        SpringApplication.run(BeautyApp.class, args);
    }
}
