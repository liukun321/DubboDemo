package com.mixiusi.protocol.response;

import java.awt.image.ShortLookupTable;

import com.mixiusi.protocol.ServiceID;
import com.mixiusi.protocol.enums.ILinkService;
import com.mixiusi.protocol.pack.Pack;


/**
 * 閸欐垼鎹ｉ幓鈩冨鐠囬攱鐪伴敍宀冨箯閸欐牕鐦戦柦锟�
 * User: jingege
 * Date: 1/13/12
 * Time: 10:14 AM
 */
public class Handshakeresponse extends Response{

	private byte[] Rc4Key;

    public Handshakeresponse(String uid) {
    	//short a = 1;
    	//Rc4Key = shortToByte(a);
    	super(uid);
    	Rc4Key = new byte[128];
    	byte a = 1;
    	for(int i =0;i<128;i++){
    		Rc4Key[i] = a;
    	}
    	
    }
   /* public static byte[] shortToByte(short number) { 
        int temp = number; 
        byte[] b = new byte[2]; 
        for (int i = 0; i < b.length; i++) { 
            b[i] = new Integer(temp & 0xff).byteValue();// 

            temp = temp >> 8; // 鍚戝彸绉�8浣� 
        } 
        return b; 
    } */
    
    public byte[] getRc4Key() {
		return Rc4Key;
	}

    @Override
    public Pack packResponse() {
        Pack pack = new Pack();
        
        pack.putVarbin(Rc4Key);
        
        return pack;
    }

    @Override
    public short getServiceId() {
        return ServiceID.SVID_LITE_LINK;
    }

    @Override
    public short getCommandId() {
        return ILinkService.CommandId.CID_EXCHANGE_KEY;
    }
}
