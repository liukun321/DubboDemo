package com.mixiusi.handler.coffee;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Reference;
import com.mixiusi.bean.CoffeeMachine;
import com.mixiusi.bean.CoffeeMachineAddress;
import com.mixiusi.bean.CoffeeMaterial;
import com.mixiusi.bean.utils.MaterialEnum;
import com.mixiusi.bean.utils.StringUtils;
import com.mixiusi.handler.RequestHandler;
import com.mixiusi.protocol.request.Request;
import com.mixiusi.protocol.request.coffee.UpdateMachineAddressRequest;
import com.mixiusi.protocol.response.coffee.UpdateMachineAddressResponse;
import com.mixiusi.service.CoffeeMachineService;
import com.mixiusi.service.CoffeeMaterialService;
import com.mixiusi.service.MachineAddressService;

/**
 * 咖啡机地理位置信息更新
 * 同时更新咖啡机 和物料的信息
 * @author liukun
 *
 */
@Component
public class UpdateMachineAddressHandler extends RequestHandler {
	private final Logger log = LoggerFactory.getLogger(UpdateMachineAddressHandler.class);
	@Reference
	private MachineAddressService mervice;
	private static MachineAddressService machineAddressService;
	@Reference
	private CoffeeMaterialService service;
	private static CoffeeMaterialService coffeeMaterialService;
	@Reference
	private CoffeeMachineService cservice;
	private static CoffeeMachineService coffeeMachineService;
	@PostConstruct
	public void init() {
		machineAddressService = mervice;
		coffeeMaterialService = service;
		coffeeMachineService = cservice;
	}
	@Override
	public void processRequest(Request request, ChannelHandlerContext ctx) {
		log.info("------开始更新地理位置=====更新咖啡机的信息和料盒信息-------");
		List<CoffeeMaterial> list = new ArrayList<>();
		UpdateMachineAddressRequest updateMachineAddressRequest = (UpdateMachineAddressRequest) request;
		log.info("咖啡机用户=" + updateMachineAddressRequest.getLinkFrame().key);
		log.info(updateMachineAddressRequest.getMachineId());
		String machineCode = updateMachineAddressRequest.getLinkFrame().key;
		String machineId = updateMachineAddressRequest.getMachineId();
		//初始化咖啡机信息和物料信息
		
		CoffeeMachine cm = coffeeMachineService.getCoffeeMachineById(machineId);
		if(null == cm) {//新建咖啡机信息时初始化咖啡机的料盒信息
			cm = new CoffeeMachine();
			cm.setMachineId(machineId);
			cm.setMachineCode(machineCode);
			cm.setCreateTime(new Date());
			cm.setType(0);//初代机。。二代机
			//初始化物料信息
			for(int i = 0; i < 11; i++) {
				CoffeeMaterial cmaterial = new CoffeeMaterial();
				cmaterial.setCategory(MaterialEnum.getName(i + 1));
				cmaterial.setMachineId(machineId);
				cmaterial.setStackNumber(i + 1);
				cmaterial.setStatus(0);
				list.add(cmaterial);
			}
			coffeeMaterialService.batchInsertMaterial(list);
		}
		cm.setIs_running(true);
		cm.setStatus(0);
		cm.setUpdateTime(new Date());
		
		coffeeMachineService.addCoffeeMachine(cm);
		//初始化地理位置信息
		CoffeeMachineAddress machineAddress = null;
		if(!StringUtils.isNull(machineId)) {
			machineAddress = machineAddressService.queryMachineAddressById(machineId);
			if(null == machineAddress) {
				machineAddress = new CoffeeMachineAddress();
			}
			BeanUtils.copyProperties(updateMachineAddressRequest, machineAddress);
			machineAddress = machineAddressService.updateMachineAddress(machineAddress);
		}
		UpdateMachineAddressResponse updateStockresponse = new UpdateMachineAddressResponse(updateMachineAddressRequest.getLinkFrame().key);
		updateStockresponse.getLinkFrame().serialId = request.getLinkFrame().serialId;
		
		if (null == machineAddress)
			updateStockresponse.getLinkFrame().resCode = 0;
		ctx.getChannel().write(updateStockresponse);
	}

}
