package com.mixiusi.handler.coffee;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Reference;
import com.mixiusi.bean.CoffeeMachine;
import com.mixiusi.bean.CoffeeMaterial;
import com.mixiusi.bean.utils.MaterialEnum;
import com.mixiusi.handler.RequestHandler;
import com.mixiusi.protocol.request.Request;
import com.mixiusi.protocol.request.coffee.CoffeeMachineInitRequest;
import com.mixiusi.protocol.response.coffee.CoffeeMachineInitResponse;
import com.mixiusi.service.CoffeeMachineService;
import com.mixiusi.service.CoffeeMaterialService;

/**
 * 咖啡机初始化信息
 * 初始化料盒信息 默认是正常状态
 * 初始化咖啡机自身的信息
 * @author liukun
 *
 */
@Component
public class CoffeeMachineInitHandler extends RequestHandler {
	private final Logger log = LoggerFactory.getLogger(CoffeeMachineInitHandler.class);
	@Reference
	private CoffeeMaterialService service;
	private static CoffeeMaterialService coffeeMaterialService;
	@Reference
	private CoffeeMachineService cservice;
	private static CoffeeMachineService coffeeMachineService;
	@PostConstruct
	public void init() {
		coffeeMaterialService = service;
		coffeeMachineService = cservice;
	}
	@Override
	public void processRequest(Request request, ChannelHandlerContext ctx) {
		CoffeeMachineInitRequest addStockRequest = (CoffeeMachineInitRequest) request;
		log.info("添加物料--" + addStockRequest.getInventory());
		List<CoffeeMaterial> list = new ArrayList<>();
		String machineCode = addStockRequest.getLinkFrame().key;
		
		Long oldmachine = coffeeMachineService.generateMachineCode();
		if(null != oldmachine) {
			machineCode = oldmachine.toString();
		}
		//初始化咖啡机信息
		CoffeeMachine cm = new CoffeeMachine();
//		cm.setMachineCode(machineId);
		cm.setCreateTime(new Date());
		cm.setIs_running(true);
		cm.setStatus(0);
		/**
		 * 1 + 4位
		 * 2 + 4位
		 */
		cm.setMachineCode(machineCode);
		cm.setUpdateTime(new Date());
		cm.setType(0);
		coffeeMachineService.addCoffeeMachine(cm);
		//初始化物料信息
		for(int i = 0; i < 11; i++) {
			CoffeeMaterial cmaterial = new CoffeeMaterial();
			cmaterial.setCategory(MaterialEnum.getName(i));
//			cmaterial.setMachineId(machineId);
			cmaterial.setStackNumber(i);
			cmaterial.setStatus(0);
			list.add(cmaterial);
		}
		//TODO 批量保存数据
		coffeeMaterialService.batchInsertMaterial(list);

		CoffeeMachineInitResponse addStockresponse = new CoffeeMachineInitResponse(addStockRequest.getLinkFrame().key);
		addStockresponse.getLinkFrame().serialId = addStockRequest.getLinkFrame().serialId;
		ctx.getChannel().write(addStockresponse);

	}
}
