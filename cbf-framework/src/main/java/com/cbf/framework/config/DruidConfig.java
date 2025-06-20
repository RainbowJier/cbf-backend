package com.cbf.framework.config;

import com.alibaba.druid.spring.boot.autoconfigure.properties.DruidStatProperties;
import com.alibaba.druid.util.Utils;
import com.cbf.common.enums.DataSourceType;
import com.cbf.common.utils.spring.SpringUtils;
import com.cbf.framework.config.properties.datasource.MasterDruidProperties;
import com.cbf.framework.config.properties.datasource.SlaveDruidProperties;
import com.cbf.framework.datasource.DynamicDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.servlet.*;
import javax.sql.DataSource;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * druid 配置多数据源
 *
 * @author Frank
 */
@Slf4j
@Configuration
public class DruidConfig {
    /**
     * master data source
     *
     * @param properties master data source properties
     * @return DataSource
     */
    @Bean
    public DataSource masterDataSource(MasterDruidProperties properties) throws Exception {
        return properties.dataSource();
    }

    /**
     * slave data source
     *
     * @param properties slave data source properties
     * @return DataSource
     */
    @Bean
    @ConditionalOnProperty(prefix = "spring.datasource.druid.slave", name = "enabled", havingValue = "true")
    public DataSource slaveDataSource(SlaveDruidProperties properties) throws Exception {
        return properties.dataSource();
    }

    /**
     * dynamic data source.
     * Springboot will search the bean by type(DataSource),
     * if the type contains multi beans, it will choose by name.
     *
     * @param masterDataSource bean named masterDataSource.
     * @return DataSource
     */
    @Bean(name = "dynamicDataSource")
    @Primary
    public DynamicDataSource dataSource(DataSource masterDataSource) {
        Map<Object, Object> targetDataSources = new HashMap<>();

        targetDataSources.put(DataSourceType.MASTER.name(), masterDataSource);
        try {
            // if slaveDataSource is completely not registered , ignore
            DataSource dataSource = SpringUtils.getBean("slaveDataSource");
            targetDataSources.put(DataSourceType.SLAVE.name(), dataSource);
        } catch (Exception ignored) {
            log.info("the slave dataSource is unable.");
        }

        return new DynamicDataSource(masterDataSource, targetDataSources);
    }

    /**
     * 去除监控页面底部的广告
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    @Bean
    @ConditionalOnProperty(name = "spring.datasource.druid.statViewServlet.enabled", havingValue = "true")
    public FilterRegistrationBean removeDruidFilterRegistrationBean(DruidStatProperties properties) {
        // 获取web监控页面的参数
        DruidStatProperties.StatViewServlet config = properties.getStatViewServlet();
        // 提取common.js的配置路径
        String pattern = config.getUrlPattern() != null ? config.getUrlPattern() : "/druid/*";
        String commonJsPattern = pattern.replaceAll("\\*", "js/common.js");
        final String filePath = "support/http/resources/js/common.js";
        // 创建filter进行过滤
        Filter filter = new Filter() {
            @Override
            public void init(javax.servlet.FilterConfig filterConfig) throws ServletException {
            }

            @Override
            public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
                    throws IOException, ServletException {
                chain.doFilter(request, response);
                // 重置缓冲区，响应头不会被重置
                response.resetBuffer();
                // 获取common.js
                String text = Utils.readFromResource(filePath);
                // 正则替换banner, 除去底部的广告信息
                text = text.replaceAll("<a.*?banner\"></a><br/>", "");
                text = text.replaceAll("powered.*?shrek.wang</a>", "");
                response.getWriter().write(text);
            }

            @Override
            public void destroy() {
            }
        };
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(filter);
        registrationBean.addUrlPatterns(commonJsPattern);
        return registrationBean;
    }
}
