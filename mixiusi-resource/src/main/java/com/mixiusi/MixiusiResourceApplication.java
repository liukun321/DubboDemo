package com.mixiusi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.transaction.annotation.EnableTransactionManagement;
/**
 * 对外接口服务
 * @author liukun
 *
 */
@SpringBootApplication(exclude={DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
public class MixiusiResourceApplication extends SpringBootServletInitializer {//配置springmvc
	/**
	 * 日志句柄
	 */
	private static final Logger LOG = LoggerFactory.getLogger(MixiusiResourceApplication.class);
    public static void main(String[] args) {
        SpringApplication.run(MixiusiResourceApplication.class, args);
    }

    @Override  
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {  
        return application.sources(MixiusiResourceApplication.class);  
    }  

}
