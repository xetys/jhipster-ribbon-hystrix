package de.stytex.foobar.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

/**
 * Created by on 26.03.16.
 *
 * @author David Steiman
 */
@Configuration
public class RestTemplateConfiguration {
    @Bean
    public RestOperations restTemplate() {
        return new RestTemplate();
    }
}
