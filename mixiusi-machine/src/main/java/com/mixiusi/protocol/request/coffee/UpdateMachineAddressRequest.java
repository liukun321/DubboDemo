package com.mixiusi.protocol.request.coffee;

import com.mixiusi.protocol.ServiceID;
import com.mixiusi.protocol.enums.ICoffeeService;
import com.mixiusi.protocol.pack.PackIndex;
import com.mixiusi.protocol.pack.Unpack;
import com.mixiusi.protocol.request.RequestID;
import com.mixiusi.protocol.request.SingleRequest;

@RequestID(service = ServiceID.SVID_LITE_COFFEE, command = { ICoffeeService.CommandId.UPDATE_MACHINE_ADDRESS
        + "" })
public class UpdateMachineAddressRequest extends SingleRequest {
	@PackIndex(1)
    private String machineId;
    @PackIndex(2)
    private long time;
    @PackIndex(3)
    private String address;
    @PackIndex(4)
    private double latitude;
    @PackIndex(5)
    private double longitude;
	
   @Override
    public Unpack unpackBody(Unpack unpack) throws Exception {
	   machineId = unpack.popVarstr();
	   time = unpack.popLong();
	   address = unpack.popVarstr();
	   latitude = unpack.popDouble();
	   longitude = unpack.popDouble();
//	   city = unpack.popVarstr();
	   return null;
    }

	public String getMachineId() {
		return machineId;
	}
	
	public long getTime() {
		return time;
	}
	
	public String getAddress() {
		return address;
	}
	
	public double getLatitude() {
		return latitude;
	}
	
	public double getLongitude() {
		return longitude;
	}
	
	public void setMachineId(String machineId) {
		this.machineId = machineId;
	}
	
	public void setTime(long time) {
		this.time = time;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

//	public String getCity() {
//		return city;
//	}
//
//	public void setCity(String city) {
//		this.city = city;
//	}

}
