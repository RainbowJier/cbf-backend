package com.cbf.framework.config.redis;


import com.cbf.common.utils.sign.Aes;
import lombok.Data;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;

import java.time.Duration;

@Data
@Configuration
@ConfigurationProperties(prefix = "spring.redis")
public class RedisProperties {

    private String host;
    private int port;
    private int database;
    private String password;
    private Duration timeout;
    private Lettuce lettuce;

    @Data
    public static class Lettuce {
        private Pool pool;

        @Data
        public static class Pool {
            private int minIdle;
            private int maxIdle;
            private int maxActive;
        }
    }

    @Bean
    public LettuceConnectionFactory redisConnectionFactory() throws Exception {
        // 基础配置
        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();
        config.setHostName(host);
        config.setPort(port);
        config.setDatabase(database);

        // 密码处理（支持加密）
        if (password.startsWith("ENC(") && password.endsWith(")")) {
            password = password.substring(4, password.length() - 1);
            password = Aes.decrypt(password);
        }
        config.setPassword(password);

        // 连接池配置
        GenericObjectPoolConfig<Object> poolConfig = new GenericObjectPoolConfig<>();
        if (lettuce != null && lettuce.pool != null) {
            poolConfig.setMinIdle(lettuce.pool.minIdle);
            poolConfig.setMaxIdle(lettuce.pool.maxIdle);
            poolConfig.setMaxTotal(lettuce.pool.maxActive);
        }

        LettuceClientConfiguration clientConfig = LettucePoolingClientConfiguration.builder()
                .poolConfig(poolConfig)
                .commandTimeout(timeout != null ? timeout : Duration.ofSeconds(10))
                .build();

        return new LettuceConnectionFactory(config, clientConfig);
    }
}
