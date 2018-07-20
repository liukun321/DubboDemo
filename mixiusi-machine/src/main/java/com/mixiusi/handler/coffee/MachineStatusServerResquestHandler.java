package com.mixiusi.handler.coffee;

import javax.annotation.PostConstruct;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Reference;
import com.mixiusi.bean.MachineStatus;
import com.mixiusi.handler.RequestHandler;
import com.mixiusi.protocol.request.Request;
import com.mixiusi.protocol.request.coffee.MachineStatusServerRequest;
import com.mixiusi.protocol.response.coffee.MachineStatusServerResponse;
import com.mixiusi.service.MachineStatusService;

//更新咖啡机的状态
@Component
public class MachineStatusServerResquestHandler extends RequestHandler {

	@Reference
	private MachineStatusService service;
	private static MachineStatusService machineStatusService;
	@PostConstruct
	public void init() {
		machineStatusService = service;
	}
	@Override
	public void processRequest(Request request, ChannelHandlerContext ctx) {
		MachineStatusServerRequest machineStatusServerRequest = new MachineStatusServerRequest();
		MachineStatusServerResponse machineStatusServerresponse = new MachineStatusServerResponse(
				request.getLinkFrame().key);
		System.out.println(machineStatusServerRequest.getMachineInfo());
		System.out.println(machineStatusServerRequest.getTimestamp());

//		int flag = database.insertinfo(machineStatusServerRequest.getLinkFrame().key,
//				machineStatusServerRequest.getTimestamp(), machineStatusServerRequest.getMachineInfo());
		
		MachineStatus machineStatus = new MachineStatus();
		machineStatus.setMachineId(machineStatusServerRequest.getLinkFrame().key);
		machineStatus.setTimestamps(machineStatusServerRequest.getTimestamp());
		machineStatus.setStatusjson(machineStatusServerRequest.getMachineInfo());
		
		machineStatusService.addMachineStatus(machineStatus);
		
		machineStatusServerresponse.getLinkFrame().serialId = request.getLinkFrame().serialId;
		ctx.getChannel().write(machineStatusServerresponse);

	}

}
