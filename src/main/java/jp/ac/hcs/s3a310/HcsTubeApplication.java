package jp.ac.hcs.s3a310;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
@SpringBootApplication
public class HcsTubeApplication extends SpringBootServletInitializer{
  public static void main(String[] args) {
    SpringApplication.run(HcsTubeApplication.class, args);
  }
  @Override
  protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
      return builder.sources(HcsTubeApplication.class);
    }
}