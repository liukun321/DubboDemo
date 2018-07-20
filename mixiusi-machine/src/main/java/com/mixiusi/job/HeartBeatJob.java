package com.mixiusi.job;

import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.PostConstruct;

import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFuture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Reference;
import com.mixiusi.bean.CoffeeMachine;
import com.mixiusi.bean.ErrorRecord;
import com.mixiusi.protocol.response.KeepAliveresponse;
import com.mixiusi.server.ServerHandler;
import com.mixiusi.service.CoffeeMachineService;
import com.mixiusi.service.ErrorRecordService;
/**
 * 心跳检测，是通过想通道发送消息，若通道已经断开则收到的Future对象是异常，由此判断连接是否保持
 * @author liukun
 *
 */
@Component
public class HeartBeatJob {
	private final Logger log = LoggerFactory.getLogger(HeartBeatJob.class);
	
	@Reference
	private CoffeeMachineService service;
	private static CoffeeMachineService coffeeMachineService;
	@Reference
	private ErrorRecordService eservice;
	private static ErrorRecordService errorRecordService;
	
	@PostConstruct
	public void init() {
		coffeeMachineService = service;
		errorRecordService = eservice;
	}
	
	@Scheduled(cron = "0 0/5 * * * ?")//每5分钟执行一次
	public void sendHeartBeat() {
		log.info("--------开始检测客户端的心跳---------");
		Map<String, Channel> map = ServerHandler.channels;
		for(Entry<String, Channel> entry : map.entrySet()) {
			String machineCode = entry.getKey();
			log.info("咖啡机" + machineCode + "正在进行心跳检测");
			CoffeeMachine coffeeMachine = coffeeMachineService.getCoffeeMachineInfo(machineCode);
			if(null != coffeeMachine) {
				String machineId = coffeeMachine.getMachineId();
				KeepAliveresponse keepAliveresponse = new KeepAliveresponse(machineCode);
				ChannelFuture cf = entry.getValue().write(keepAliveresponse);
				//返回null则表示成功或者请求执行未完成
				Throwable error = cf.getCause();
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					log.error(e.getMessage());
				}
				if(null != error) {
					log.info("咖啡机" + machineCode + "心跳检测失败");
					//客户端停机,将连接从map中删除
					map.remove(entry.getKey());
					coffeeMachine.setDownTime(new Date());
					coffeeMachine.setIs_running(false);
					coffeeMachineService.addCoffeeMachine(coffeeMachine);
					
					//停机记录
					ErrorRecord er = new ErrorRecord();
					er.setMachineId(machineId);
					er.setStartTime(new Date());
					er.setType(2);
					er.setWorkerId(coffeeMachine.getEmployeeCode());
					errorRecordService.addErrorRecord(er);
				}
			}else {
				map.remove(entry.getKey());
			}
		}
		
	}
}
