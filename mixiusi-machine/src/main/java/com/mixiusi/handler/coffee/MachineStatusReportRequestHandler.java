package com.mixiusi.handler.coffee;

import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.mixiusi.bean.CoffeeMachine;
import com.mixiusi.bean.ErrorRecord;
import com.mixiusi.bean.Material;
import com.mixiusi.bean.utils.DateUtils;
import com.mixiusi.handler.RequestHandler;
import com.mixiusi.protocol.request.Request;
import com.mixiusi.protocol.request.coffee.MachineStatusReportRequest;
import com.mixiusi.protocol.response.coffee.MachineStatusReportResponse;
import com.mixiusi.service.CoffeeMachineService;
import com.mixiusi.service.ErrorRecordService;
import com.mixiusi.service.MaterialService;

/**
 * 11 咖啡机状态报告
 * @author liukun
 * 初始化咖啡机的状态信息或者更新状态
 * 错误记录的创建和终止
 * 
 */
@Component
public class MachineStatusReportRequestHandler extends RequestHandler {
	private final Logger log = LoggerFactory.getLogger(MachineStatusReportRequestHandler.class);
	@Reference
	private CoffeeMachineService service;
	@Reference
	private MaterialService mservice;
	@Reference
	private ErrorRecordService errorservice;
	private static ErrorRecordService errorRecordService;
	private static CoffeeMachineService coffeeMachineService;
	private static MaterialService materialService;
	@PostConstruct
	public void init() {
		coffeeMachineService = service;
		materialService = mservice;
		errorRecordService = errorservice;
	}

	@Override
	public void processRequest(Request request, ChannelHandlerContext ctx) {
		log.info("----------------咖啡机状态报告信息---------------------");
		MachineStatusReportRequest machineStatusReportRequest = (MachineStatusReportRequest) request;
		log.info(machineStatusReportRequest.getMachineStatusJson());
		//是否是 咖啡机的ID
		String machineCode = machineStatusReportRequest.getLinkFrame().key;
		String venderName = "";
		CoffeeMachine cm = coffeeMachineService.getCoffeeMachineInfo(machineCode);
		if(null != cm)
			venderName = cm.getMachineId();
		int status = machineStatusReportRequest.getStatus();
		
		MachineStatusReportResponse machineStatusReportresponse = new MachineStatusReportResponse(
				request.getLinkFrame().key);
		//查询该咖啡机是否是新创建
		CoffeeMachine machineInfo = coffeeMachineService.getCoffeeMachineInfo(machineStatusReportRequest.getLinkFrame().key);
		if(null != machineInfo) {
			boolean isrun = machineStatusReportRequest.isIs_running();
			String workerId = machineInfo.getEmployeeCode();
			boolean is_running = machineInfo.getIs_running();
//			Integer status = machineInfo.getStatus();
			
			List<ErrorRecord> errors = null;
			ErrorRecord error = null;
			//处理之前缺料未解决，最终停机的情况
			if(!is_running && isrun) {//前一次是停机状态，当前是重启，则计算这次错误的持续的时间
				//查询上一次的错误记录
				errors = errorRecordService.queryErrorRecord(venderName);
				//将该咖啡机的所有错误记录中没有结束时间的记录添加结束时间，并计算错误持续的时长
				if(null != errors && !errors.isEmpty()) {
					errors.stream().forEach(err -> {
						Date end = new Date();
						if(null == err.getEndTime()) {
							err.setEndTime(end);
							err.setDurationTime(DateUtils.subtractForDate(err.getStartTime(), end, 1000*60));
						}
					});
					errorRecordService.batchUpdate(errors);
				}else {
					log.info("错误记录数据丢失，找不到前一次的停机记录");
				}
				//计算当前时间和上次停机时间的时间差
			}else if(is_running && !isrun) {//前一次正常，当前是停机，则新建错误记录
				machineInfo.setDownTime(new Date());
				error = new ErrorRecord();
				error.setMachineId(venderName);
				error.setStartTime(new Date());
				error.setType(2);//停机错误，当前无警报处理
				error.setWorkerId(workerId);
			}
			//缺料预警
			if(status == 1) {
				error = new ErrorRecord();
				error.setMachineId(venderName);
				error.setStartTime(new Date());
				error.setType(0);//缺料预警
				error.setWorkerId(workerId);
			}else if(status == 2) {//严重缺料
				error = new ErrorRecord();
				error.setMachineId(venderName);
				error.setStartTime(new Date());
				error.setType(1);//严重缺料
				error.setWorkerId(workerId);
			}
			
			if(error != null)
				errorRecordService.addErrorRecord(error);
			machineInfo.setStatus(status);
			machineInfo.setIs_running(isrun);
			machineInfo.setUpdateTime(new Date());
			// TODO 获取咖啡机物料状态信息
			log.info(venderName + "咖啡机物料信息：" + machineStatusReportRequest.getMachineStatusJson());
			Material material = materialService.queryMaterial(venderName);
			if(null == material) {
				material = new Material();
				material.setMachineId(venderName);
			}
			// ？？机器状态的json串是否与物料表的字段对应  相应料盒编号和当前状态
			material = JSON.parseObject(machineStatusReportRequest.getMachineStatusJson(), Material.class);
			//TODO　更新咖啡机物料信息
			materialService.updateMaterial(material);
			//更新咖啡机状态
			coffeeMachineService.addCoffeeMachine(machineInfo);
		}else {
			log.info("--------找不到指定的咖啡机信息------" + machineStatusReportRequest.getLinkFrame().key);
			machineStatusReportresponse.getLinkFrame().resCode = 0;
		}
		machineStatusReportresponse.getLinkFrame().serialId = request.getLinkFrame().serialId;
		ctx.getChannel().write(machineStatusReportresponse);
	}
	
}
