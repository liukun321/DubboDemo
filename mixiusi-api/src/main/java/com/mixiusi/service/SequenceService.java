package com.mixiusi.service;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public interface SequenceService {
	/**
     * @param key
     * @param value
     * @param expireTime
     * @Title: set
     * @Description: set cache.
     */
	 public void set(String key, int value, Date expireTime);
	 /**
	     * @param key
	     * @param value
	     * @param timeout
	     * @param unit
	     * @Title: set
	     * @Description: set cache.
	     */
	 public void set(String key, int value, long timeout, TimeUnit unit);
	 /**
     * @param key
     * @return
     * @Title: generate
     * @Description: Atomically increments by one the current value.
     */
	 public long generate(String key);
	 /**
     * @param key
     * @return
     * @Title: generate
     * @Description: Atomically increments by one the current value.
     */
	 public long generate(String key, Date expireTime);
	 
	 /**
     * @param key
     * @param increment
     * @return
     * @Title: generate
     * @Description: Atomically adds the given value to the current value.
     */
    public long generate(String key, int increment);
    
    /**
     * @param key
     * @param increment
     * @param expireTime
     * @return
     * @Title: generate
     * @Description: Atomically adds the given value to the current value.
     */
    public long generate(String key, int increment, Date expireTime);
}
