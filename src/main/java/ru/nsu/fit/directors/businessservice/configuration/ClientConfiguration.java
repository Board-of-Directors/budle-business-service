package ru.nsu.fit.directors.businessservice.configuration;

import feign.Logger;
import feign.codec.Encoder;
import feign.form.spring.SpringFormEncoder;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.AbstractFormWriter;
import org.springframework.cloud.openfeign.support.JsonFormWriter;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ClientConfiguration {
    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }

    @Bean
    public Encoder multipartFormEncoder() {
        return new SpringFormEncoder(
            new SpringEncoder(() -> new HttpMessageConverters(new RestTemplate().getMessageConverters()))
        );
    }

    @Bean
    public AbstractFormWriter jsonFormWriter() {
        return new JsonFormWriter();
    }

}
