package com.mixiusi.service.redis;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import com.alibaba.dubbo.config.annotation.Service;
import com.mixiusi.service.RedisService;

import redis.clients.jedis.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;

/**
 * Redis服务接口实现类
 * @author liukun
 *
 */
@Service(interfaceClass = RedisService.class, version = "1.0.0")
public class RedisServiceImpl implements RedisService {

    @Autowired
    private RedisTemplate redisTemplate;
    private final Logger log = LoggerFactory.getLogger(RedisServiceImpl.class);
    /**
     * 批量删除对应的value
     *
     * @param keys
     */
    @Override
    public void remove(final String... keys) {
        for (String key : keys) {
            remove(key);
        }
    }

    /**
     * 批量删除key
     *
     * @param pattern
     */
    @Override
    public void removePattern(final String pattern) {
        Set<Serializable> keys = redisTemplate.keys(pattern);
        if (keys.size() > 0) {
            redisTemplate.delete(keys);
        }
    }

    /**
     * 删除对应的value
     *
     * @param key
     */
    @Override
    public void remove(final String key) {
        if (exists(key)) {
            redisTemplate.delete(key);
        }
    }

    /**
     * 判断缓存中是否有对应的value
     *
     * @param key
     * @return
     */
    @Override
    public boolean exists(final String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 读取缓存
     *
     * @param key
     * @return
     */
    @Override
    public Object get(final String key) {
        Object result = null;
        ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
        System.out.println(redisTemplate + "--" + operations);
        result = operations.get(key);
        return result;
    }

    /**
     * 写入缓存
     *
     * @param key
     * @param value
     * @return
     */
    @Override
    public boolean set(final String key, Object value) {
        boolean result = false;
        try {
            ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
            operations.set(key, value);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 写入缓存
     *
     * @param key
     * @param value
     * @return
     */
    @Override
    public boolean set(final String key, Object value, Long expireTime) {
        boolean result = false;
        try {
            ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
            operations.set(key, value);
            redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

	@Override
	public <K, HK, HV> boolean setMap(K key, Map<HK, HV> map, Long expireTime) {
//		redisTemplate.multi();
//		
//		if(!redisTemplate.opsForValue().getBit(key, offset)){//新用户
//			redisTemplate.opsForValue().setBit(key, value, true);
//		}
//		redisTemplate.opsForValue().setBit(key, value, true);
//
//			Response<Long> total = redisTemplate.boundListOps(key).getKey();
//			redisTemplate.exec();//事务执行
//			total.get();
		
//		redisTemplate.opsForValue().getBit(key, offset);//获取bit值
//		redisTemplate.opsForValue().setBit(key, value);
//		redisTemplate.opsForGeo().
		return false;
	}

	@Override
	public <K, HK, HV> Map<HK, HV> getMap(K key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long generateId(String key) {
		Long result = null;
		try {
			ValueOperations<Serializable, Long> operations = redisTemplate.opsForValue();
			Long value = (Long) this.get(key);
			if(null != value) {
				result = operations.increment(key, value);
			}else {
				result = operations.increment(key, 10000L);
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return result;
	}
}
