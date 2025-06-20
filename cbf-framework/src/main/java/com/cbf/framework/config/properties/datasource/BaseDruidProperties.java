package com.cbf.framework.config.properties.datasource;


import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.cbf.common.utils.sign.Aes;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

/**
 *
 * @author : Liuqijie
 * @Date: 2025/6/20 15:20
 */

@Data
public class BaseDruidProperties {
    private String url;
    private String username;
    private String password;

    @Value("${spring.datasource.druid.initialSize}")
    private int initialSize;

    @Value("${spring.datasource.druid.minIdle}")
    private int minIdle;

    @Value("${spring.datasource.druid.maxActive}")
    private int maxActive;

    @Value("${spring.datasource.druid.maxWait}")
    private int maxWait;

    @Value("${spring.datasource.druid.connectTimeout}")
    private int connectTimeout;

    @Value("${spring.datasource.druid.socketTimeout}")
    private int socketTimeout;

    @Value("${spring.datasource.druid.timeBetweenEvictionRunsMillis}")
    private int timeBetweenEvictionRunsMillis;

    @Value("${spring.datasource.druid.minEvictableIdleTimeMillis}")
    private int minEvictableIdleTimeMillis;

    @Value("${spring.datasource.druid.maxEvictableIdleTimeMillis}")
    private int maxEvictableIdleTimeMillis;

    public DruidDataSource dataSource() throws Exception {
        DruidDataSource dataSource = DruidDataSourceBuilder.create().build();
        // connection properties.
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        if (password.startsWith("ENC(") && password.endsWith(")")) {
            password = password.substring(4, password.length() - 1);
            password = Aes.decrypt(password);
        }
        dataSource.setPassword(password);

        // 配置初始化大小、最小、最大
        dataSource.setInitialSize(initialSize);
        dataSource.setMaxActive(maxActive);
        dataSource.setMinIdle(minIdle);

        // 配置获取连接等待超时的时间
        dataSource.setMaxWait(maxWait);

        // 配置驱动连接超时时间，检测数据库建立连接的超时时间，单位是毫秒
        dataSource.setConnectTimeout(connectTimeout);

        // 配置网络超时时间，等待数据库操作完成的网络超时时间，单位是毫秒 */
        dataSource.setSocketTimeout(socketTimeout);

        // 配置间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒 */
        dataSource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);

        // 配置一个连接在池中最小、最大生存的时间，单位是毫秒 */
        dataSource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
        dataSource.setMaxEvictableIdleTimeMillis(maxEvictableIdleTimeMillis);

        return dataSource;
    }
}
