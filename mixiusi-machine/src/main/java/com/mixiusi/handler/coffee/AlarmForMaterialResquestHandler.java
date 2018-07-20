package com.mixiusi.handler.coffee;

import java.util.Date;

import javax.annotation.PostConstruct;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Reference;
import com.mixiusi.bean.CoffeeMachine;
import com.mixiusi.bean.CoffeeMaterial;
import com.mixiusi.bean.Employee;
import com.mixiusi.handler.RequestHandler;
import com.mixiusi.protocol.request.Request;
import com.mixiusi.protocol.request.coffee.AlarmForMaterialResquest;
import com.mixiusi.protocol.response.coffee.AlarmForMaterialResponse;
import com.mixiusi.service.CoffeeMachineService;
import com.mixiusi.service.CoffeeMaterialService;
import com.mixiusi.service.EmployeeService;
import com.mixiusi.service.JPushService;

/**
 * 咖啡机物料预警信息请求
 * @author liukun
 *咖啡机物料预警：
 *1 更新咖啡机的状态
 *2 更新咖啡机相应物料的状态
 *2 向运维人员推送报警信息
 */
@Component
public class AlarmForMaterialResquestHandler extends RequestHandler {
	private final Logger log = LoggerFactory.getLogger(AlarmForMaterialResquestHandler.class);
	@Reference
	private CoffeeMachineService service;
	private static CoffeeMachineService coffeeMachineService;
	
	@Reference
	private CoffeeMaterialService cervice;
	private static CoffeeMaterialService coffeeMaterialService;
	
	@Reference
	private EmployeeService eservice;
	private static EmployeeService employeeService;
	
	@Reference
	private JPushService jservice;
	private static JPushService jPushService;
	
	@PostConstruct
	public void init() {
		System.out.println("24243434343"+service);
		coffeeMachineService = service;
		employeeService = eservice;
		jPushService = jservice;
		coffeeMaterialService = cervice;
	}
	@Override
	public void processRequest(Request request, ChannelHandlerContext ctx) {
		log.info("--------------咖啡机预警请求------------------");
		AlarmForMaterialResquest alarmForMaterialRequest = (AlarmForMaterialResquest) request;
		
		String machineCode = alarmForMaterialRequest.getLinkFrame().key;
		AlarmForMaterialResponse alarmForMaterialResponse = new AlarmForMaterialResponse(machineCode);
		// 物料报警处理
		int num = alarmForMaterialRequest.getBoxNumber();
		double value = alarmForMaterialRequest.getValue();
		int type = alarmForMaterialRequest.getType();
		CoffeeMachine coffeeMachine = coffeeMachineService.getCoffeeMachineInfo(machineCode);
		String machineId = coffeeMachine.getMachineId();
		coffeeMachine.setStatus(type);
		coffeeMachineService.addCoffeeMachine(coffeeMachine);
		String employeeCode = coffeeMachine.getEmployeeCode();
		
		//TODO 添加物料报警信息
		CoffeeMaterial material = coffeeMaterialService.queryMaterialByIdAndNumber(machineId, num);
		material.setStatus(type);
		material.setDangerTime(new Date());
		coffeeMaterialService.addMaterial(material);
		
		//找到运营人员对应的设备标识,字段暂时还没有
		Employee employee = employeeService.queryEmployeeByPhone(employeeCode);
		String registrationId = employee.getWorkerId();
		//TODO　推送消息模板 哪台咖啡机/几号料盒，什么物料，缺料，剩余量是多少，或者百分比
		String notification_alert = "咖啡机" + machineId + "的" + num + "料盒物料缺料， 请及时处理(" + value + ")";
		String notification_title = "咖啡机物料警报";
		//推送该信息到该员工的APP，咖啡机ID和哪个物料盒缺料
		
		int result = jPushService.sendToRegistrationId(registrationId, notification_alert, notification_title, "");
		if(0 == result) {
			log.info(notification_alert + "推送失败");
		}else if(1 == result) {
			log.info(notification_alert + "推送成功");
			alarmForMaterialResponse.getLinkFrame().resCode = 0;
		}
		
		//推送消息后，添加error记录，更行咖啡机的当前状态
		
		alarmForMaterialResponse.getLinkFrame().serialId = request.getLinkFrame().serialId;
		ctx.getChannel().write(alarmForMaterialResponse);

	}

}
