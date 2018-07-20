package com.mixiusi.handler.coffee;

import javax.annotation.PostConstruct;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.mixiusi.bean.CoffeeMachine;
import com.mixiusi.bean.Material;
import com.mixiusi.bean.utils.StringUtils;
import com.mixiusi.handler.RequestHandler;
import com.mixiusi.protocol.request.Request;
import com.mixiusi.protocol.request.coffee.UpdateStockRequest;
import com.mixiusi.protocol.response.coffee.UpdateStockResponse;
import com.mixiusi.service.CoffeeMachineService;
import com.mixiusi.service.MaterialService;
import com.mixiusi.vo.UpdateMaterialVo;
//咖啡机物料更新
@Component
public class UpdateStockHandler extends RequestHandler {
	private final Logger log = LoggerFactory.getLogger(UpdateStockHandler.class);
	@Reference
	private MaterialService service;
	private static MaterialService materialService;
	@Reference
	private CoffeeMachineService cservice;
	private static CoffeeMachineService coffeeMachineService;
	@PostConstruct
	public void init() {
		materialService = service;
		coffeeMachineService = cservice;
	}
	@Override
	public void processRequest(Request request, ChannelHandlerContext ctx) {
		UpdateStockRequest updateStockRequest = (UpdateStockRequest) request;
		String inventory = updateStockRequest.getInventory();
		String machineCode = updateStockRequest.getLinkFrame().key;
		log.info("更新咖啡机物料=" + updateStockRequest.getLinkFrame().key + "---" + inventory);
		Material m = updateMaterial(machineCode, inventory);
		UpdateStockResponse updateStockresponse = new UpdateStockResponse(updateStockRequest.getLinkFrame().key);
		updateStockresponse.getLinkFrame().serialId = request.getLinkFrame().serialId;
		if (null == m)
			updateStockresponse.getLinkFrame().resCode = 0;
		updateStockresponse.setInventory(inventory);
		ctx.getChannel().write(updateStockresponse);
	}
	/**
	 * 更新咖啡机的物料
	 * @param addStockRequest
	 * @return
	 */
	private Material updateMaterial(String machineCode, String inventory) {
		Material material = new Material();
		CoffeeMachine cm = coffeeMachineService.getCoffeeMachineInfo(machineCode);
		if(null != cm) {
			material.setMachineId(cm.getMachineId());
			UpdateMaterialVo map = JSON.parseObject(inventory, UpdateMaterialVo.class);
			//{"cupNum":"6","number1":"1","number2":"3","number3":"6","number4":"9","number9":"9","water":"9"}
			if (!StringUtils.isNull(map.getCoffeeNormal()))
				material.setCoffeebean(map.getCoffeeNormal());
			if (!StringUtils.isNull(map.getCoffeeLow()))
				material.setLcoffeebean(map.getCoffeeLow());
			if (!StringUtils.isNull(map.getMilk()))
				material.setMilk(map.getMilk());
			if (!StringUtils.isNull(map.getWater()))
				material.setWater(map.getWater());
			return materialService.updateMaterial(material);
		}else {
			return null;
		}
	}

}
