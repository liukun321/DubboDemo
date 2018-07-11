package com.mixiusi.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
/**
 * 多数据源配置
 * @author liukun
 *
 */
@Configuration
public class DataSourceConfig {
	@Bean(name = "writeDataSource")
    @Qualifier("writeDataSource")
    @Primary
    @ConfigurationProperties(prefix="spring.datasource.write")
    public DataSource wdxhDataSource() {
        return DataSourceBuilder.create().build();
    }
    @Bean(name = "readDataSource")
    @Qualifier("readDataSource")
    @ConfigurationProperties(prefix="spring.datasource.read")
    public DataSource fileUploadDataSource() {
        return DataSourceBuilder.create().build();
    }
}
