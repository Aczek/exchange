package pl.aczek.exchange;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class ExchangeApplication {

  public static void main(String[] args) {
    SpringApplication.run(ExchangeApplication.class, args);
  }

}
