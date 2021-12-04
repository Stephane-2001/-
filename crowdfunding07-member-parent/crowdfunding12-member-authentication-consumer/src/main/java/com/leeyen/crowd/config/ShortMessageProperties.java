package com.leeyen.crowd.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "short.message")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShortMessageProperties {
    private String host;

    private String path;

    private String method;

    private String appcode;

    private String template_id;

    private String phoneNum;
}
