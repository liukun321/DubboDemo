package com.mixiusi.protocol.request;

import com.mixiusi.protocol.LinkFrame;
import com.mixiusi.protocol.ResponseCode;
import com.mixiusi.protocol.pack.AutoPackHelper;
import com.mixiusi.protocol.pack.Unpack;

/**
 * 閸濆秴绨� User: jingege Date: 1/13/12 Time: 4:06 PM
 */
public abstract class Request extends AutoPackHelper{

	/**
	 * LinkFrame
	 */
	protected LinkFrame linkFrame;

	/**
	 * 鐟欙絽瀵�
	 * 
	 * @param unpack
	 *            瀵板懓袙
	 * @throws Exception
	 *             鐟欙絽瀵樻径杈Е
	 * @return 閸︺劑娓剁憰锟�2濞喡ば掗崠鍛,閹跺﹪娓剁憰浣叫掗崢瀣畱閺佺増宓佹担婊�璐熸潻鏂挎礀閸婏拷
	 */
	public abstract Unpack unpackBody(Unpack unpack) throws Exception;

	/**
	 * 閼惧嘲褰嘗inkFrame
	 * 
	 * @return
	 */
	public LinkFrame getLinkFrame() {
		return this.linkFrame;
	}

	/**
	 * 鐠佸墽鐤哃inkFrame
	 * 
	 * @param linkFrame
	 */
	public void setLinkFrame(LinkFrame linkFrame) {
		this.linkFrame = linkFrame;
	}

	/**
	 * code閺勵垰鎯佹稉绡燯CCESS
	 * @return
	 */
	public boolean isSuccess() {
		if (linkFrame != null) {
			return linkFrame.resCode == ResponseCode.RES_SUCCESS;
		}
		return false;
	}
	
	public boolean isOfflineMsg() {
		if (linkFrame != null) {
			return linkFrame.serialId == 0;
		}
		
		return false;
	}

}
