package com.cbf.framework.config.properties.datasource;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author : Liuqijie
 * @Date: 2025/6/20 15:23
 */
@Configuration
@ConfigurationProperties("spring.datasource.druid.slave")
public class SlaveDruidProperties extends BaseDruidProperties {
}

