package com.fortech;

import com.fortech.swagger.SwaggerConfig;
import com.fortech.security.WebSecurityConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Created by iosifvarga on 07.07.2017.
 */
@SpringBootApplication
@EntityScan("com.fortech.model")
@EnableJpaRepositories("com.fortech.repository")
@ComponentScan("com.fortech")
//@Import({WebSecurityConfig.class})
@Import({SwaggerConfig.class})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
