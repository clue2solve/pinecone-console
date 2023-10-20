package io.clue2solve.pineconeconsole;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;


@EnableConfigurationProperties(PineconeProperties.class)
@SpringBootApplication
public class PineconeConsoleApplication {

    public static void main(String[] args) {
        SpringApplication.run(PineconeConsoleApplication.class, args);
    }

}
