package jp.ac.hcs.s3a310;

import javax.servlet.MultipartConfigElement;

import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.unit.DataSize;

@Configuration
public class MultiPartConfigure {
    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        DataSize size=DataSize.ofGigabytes(10);


        factory.setMaxFileSize(size);
        factory.setMaxRequestSize(size);
        return factory.createMultipartConfig();
    }
}