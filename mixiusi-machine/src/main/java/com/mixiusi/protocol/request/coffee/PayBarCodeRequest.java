package com.mixiusi.protocol.request.coffee;

import com.mixiusi.protocol.ServiceID;
import com.mixiusi.protocol.enums.ICoffeeService;
import com.mixiusi.protocol.pack.PackIndex;
import com.mixiusi.protocol.pack.Unpack;
import com.mixiusi.protocol.request.RequestID;
import com.mixiusi.protocol.request.SingleRequest;

@RequestID(service = ServiceID.SVID_LITE_COFFEE, command = { ICoffeeService.CommandId.PAY_BAR_CODE
		+ "" })
public class PayBarCodeRequest extends SingleRequest {
	//咖啡产品信息
	@PackIndex(1)
	private String coffeeId;
	//购买者的条形码信息
	@PackIndex(2)
	private String authCode;
	//咖啡机编号
	@PackIndex(3)
	private String machineId;
//	//折扣  TODO 删除
//	@PackIndex(4)
//	private String discount;
	
	/**
     * 冷热饮
     */
	@PackIndex(4)
    private boolean isHot;

    /**
     * 糖份
     * 0 1 2 3 4 
     */
	@PackIndex(5)
    private int sugar;
	/**
	 * 机器IP
	 */
	@PackIndex(6)
    private String ip;
	//优惠券类型  TODO 删除
//	@PackIndex(8)
//	private int couponsType;
	/**
	 * 0 1 2 普通 低因 特浓
	 * 咖啡机浓度
	 */
	@PackIndex(7)
	private int coffeeType;
	
	@PackIndex(8)
	private String couponsCode;
	
	@Override
    public Unpack unpackBody(Unpack unpack) throws Exception {
		coffeeId = unpack.popVarstr();
		authCode = unpack.popVarstr();
		machineId = unpack.popVarstr();
//		discount = unpack.popVarstr();
		isHot = unpack.popBoolean();
		sugar = unpack.popInt();
		ip = unpack.popVarstr();
//		couponsType = unpack.popInt();
		coffeeType = unpack.popInt();
		couponsCode = unpack.popVarstr();
        return null;
    }


	public String getCoffeeId() {
		return coffeeId;
	}

//	public int getCouponsType() {
//		return couponsType;
//	}
//
//
//	public void setCouponsType(int couponsType) {
//		this.couponsType = couponsType;
//	}


	public String getAuthCode() {
		return authCode;
	}


	public String getMachineId() {
		return machineId;
	}


//	public String getDiscount() {
//		return discount;
//	}


	public boolean isHot() {
		return isHot;
	}


	public int getSugar() {
		return sugar;
	}


	public void setCoffeeId(String coffeeId) {
		this.coffeeId = coffeeId;
	}

	public void setAuthCode(String authCode) {
		this.authCode = authCode;
	}


	public int getCoffeeType() {
		return coffeeType;
	}


	public void setCoffeeType(int coffeeType) {
		this.coffeeType = coffeeType;
	}


	public String getIp() {
		return ip;
	}


	public void setIp(String ip) {
		this.ip = ip;
	}


	public void setMachineId(String machineId) {
		this.machineId = machineId;
	}

//
//	public void setDiscount(String discount) {
//		this.discount = discount;
//	}


	public void setHot(boolean isHot) {
		this.isHot = isHot;
	}


	public void setSugar(int sugar) {
		this.sugar = sugar;
	}


	public String getCouponsCode() {
		return couponsCode;
	}


	public void setCouponsCode(String couponsCode) {
		this.couponsCode = couponsCode;
	}
	

}
