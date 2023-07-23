package io.hskim.learnspringcloudorderservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class LearnSpringCloudOrderServiceApplication {

  public static void main(String[] args) {
    SpringApplication.run(LearnSpringCloudOrderServiceApplication.class, args);
  }
}
