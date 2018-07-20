package com.mixiusi.protocol.request.coffee;
//package com.netease.vendor.service.protocol.request.coffee;
//
//import com.netease.vendor.service.protocol.ServiceID;
//import com.netease.vendor.service.protocol.enums.ICoffeeService;
//import com.netease.vendor.service.protocol.pack.Pack;
//import com.netease.vendor.service.protocol.request.Request;
//
//public class PayAliSonicWaveRequest extends Request {
//	
//	private int coffeeId;
//	private String dosing;
//	private short provider;
//	private String dynamicID;
//	
//	public PayAliSonicWaveRequest(String uid, int coffeeId, String dosing, short provider,
//			String dynamicID) {
//		super(uid);
//		this.coffeeId = coffeeId;
//		this.dosing = dosing;
//		this.provider = provider;
//		this.dynamicID = dynamicID;
//	}
//	
//	@Override
//    public Pack packRequest() {
//        Pack pack = new Pack();
//        pack.putInt(coffeeId);
//        pack.putVarstr(dosing);
//        pack.putShort(provider);
//        pack.putVarstr(dynamicID);
//        return pack;
//    }
//
//	@Override
//	public short getServiceId() {
//		return ServiceID.SVID_LITE_COFFEE;
//	}
//
//	@Override
//	public short getCommandId() {
//		return ICoffeeService.CommandId.PAY_ALI_SONICWAVE;
//	}
//
//	public int getCoffeeId() {
//		return coffeeId;
//	}
//
//	public void setCoffeeId(int coffeeId) {
//		this.coffeeId = coffeeId;
//	}
//
//	public String getDosing() {
//		return dosing;
//	}
//
//	public void setDosing(String dosing) {
//		this.dosing = dosing;
//	}
//
//	public short getProvider() {
//		return provider;
//	}
//
//	public void setProvider(short provider) {
//		this.provider = provider;
//	}
//
//	public String getDynamicID() {
//		return dynamicID;
//	}
//
//	public void setDynamicID(String dynamicID) {
//		this.dynamicID = dynamicID;
//	}
//}
