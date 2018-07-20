package com.mixiusi.protocol.request.coffee;

import com.mixiusi.protocol.ServiceID;
import com.mixiusi.protocol.enums.ICoffeeService;
import com.mixiusi.protocol.pack.PackIndex;
import com.mixiusi.protocol.pack.Unpack;
import com.mixiusi.protocol.request.Request;
import com.mixiusi.protocol.request.RequestID;

@RequestID(service = ServiceID.SVID_LITE_COFFEE, command = { ICoffeeService.CommandId.ALARM_MATERIAL
        + "" })
public class AlarmForMaterialResquest extends Request {

	//报警值的物料盒编号
	@PackIndex(1)
	private int boxNumber;
	//物料盒中剩余量
	@PackIndex(2)
	private double value;
	//警报级别
	@PackIndex(3)
	private int type;
	
	@Override
	public Unpack unpackBody(Unpack unpack) throws Exception {
		boxNumber = unpack.popInt();
		value = unpack.popDouble();
		type = unpack.popInt();
		return null;
	}

	public int getBoxNumber() {
		return boxNumber;
	}

	public double getValue() {
		return value;
	}

	public void setBoxNumber(int boxNumber) {
		this.boxNumber = boxNumber;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

}
