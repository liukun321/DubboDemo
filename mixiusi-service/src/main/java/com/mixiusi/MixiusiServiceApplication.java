package com.mixiusi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
/**
 * 服务提供方
 * @author liukun
 *
 */
@SpringBootApplication(exclude={DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
@EnableAutoConfiguration
@ComponentScan(basePackages = { "com.mixiusi"})
@EntityScan(basePackages={"com.mixiusi.bean"})
@EnableTransactionManagement
public class MixiusiServiceApplication extends SpringBootServletInitializer{//配置springmvc
	/**
	 * 日志句柄
	 */
	private static final Logger LOG = LoggerFactory.getLogger(MixiusiServiceApplication.class);
	
	/**
	 * 查看PlatformTransactionManager接口实现类
	 * @param platformTransactionManager
	 * @return
	 */
	@Bean
	public Object testBean(PlatformTransactionManager platformTransactionManager) {

		LOG.error(">>>>>>>>>>"+ platformTransactionManager.getClass().getName());

		System.out.println(">>>>>>>>>>"+ platformTransactionManager.getClass().getName());
		return new Object();
	}
	public static void main(String[] args) {
		ConfigurableApplicationContext app = SpringApplication.run(MixiusiServiceApplication.class, args);
	}
	
	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(MixiusiServiceApplication.class);
    }

	/**  
     * 文件上传配置  
     * @return  
     */  
//    @Bean  
//    public MultipartConfigElement multipartConfigElement() {  
//        MultipartConfigFactory factory = new MultipartConfigFactory();  
//        //文件最大  
//        factory.setMaxFileSize("10240KB"); //KB,MB  
//        /// 设置总上传数据总大小  
//        factory.setMaxRequestSize("102400KB");  
//        return factory.createMultipartConfig();  
//    }
}
