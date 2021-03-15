package com.avenga.fil.lt.config.property;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "db")
@Getter
@Setter
public class DBProperties {

    private String dbSecretName;
    private String driverClassName;
}
