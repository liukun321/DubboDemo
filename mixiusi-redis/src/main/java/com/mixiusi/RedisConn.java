package com.mixiusi;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * 
 * @author liukun
 *
 */
@Component
@PropertySource("classpath:config/redis.properties")//读取*.properties配置文件的内容
public class RedisConn {
	@Value("${spring.redis.host}")
    private String host;
	@Value("${spring.redis.port}")
    private int port;
	@Value("${spring.redis.timeout}")
    private int timeout;
	@Value("${spring.redis.password}")
    private String password;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
    public String toString() {
        return "Redis [localhost=" + host + ", port=" + port + ", timeout=" + timeout + "]";
    }
}
