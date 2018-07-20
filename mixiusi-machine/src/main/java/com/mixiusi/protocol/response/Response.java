package com.mixiusi.protocol.response;

import java.rmi.server.UID;

import com.mixiusi.protocol.LinkFrame;
import com.mixiusi.protocol.pack.AutoPackHelper;
import com.mixiusi.protocol.pack.Pack;


/**
 * User: jingege
 * Date: 1/13/12
 * Time: 10:21 AM
 */
public abstract class Response extends AutoPackHelper{
    
    protected String uid;
    protected LinkFrame header = null;

    public Response(String uid){
        
        this.uid = uid;
    }

    public LinkFrame getLinkFrame(){
    	if (header == null) {
    		header = new LinkFrame(getServiceId(),getCommandId(),uid);
    	}
        return header;
    }
    
    public Pack packResponse() {
    	return pack();
    }

    public abstract short getServiceId();

    public abstract short getCommandId();
   
}
