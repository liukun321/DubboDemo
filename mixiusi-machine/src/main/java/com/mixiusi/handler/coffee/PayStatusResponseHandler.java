package com.mixiusi.handler.coffee;
//package com.netease.vendor.service.handler.coffee;
//
//import com.netease.vendor.service.bean.result.PayStatusAskResult;
//import com.netease.vendor.service.core.ResendRequestTask;
//import com.netease.vendor.service.handler.ResponseHandler;
//import com.netease.vendor.service.protocol.request.coffee.PayStatusAskRequest;
//import com.netease.vendor.service.protocol.response.Response;
//import com.netease.vendor.util.log.LogUtil;
//
//public class PayStatusResponseHandler extends ResponseHandler {
//
//	@Override
//	public void processResponse(Response response) {
//        ResendRequestTask task = (ResendRequestTask) core.cancelRequestRetryTimer(
//                response.getLinkFrame().serialId);
//        String coffeeIndent = null;
//        if (task != null) {
//            PayStatusAskRequest request = (PayStatusAskRequest)task.getRequest();
//            coffeeIndent = request.getCoffeeindent();
//            LogUtil.vendor("PayStatusResponse-> retrive for PayStatusAskRequest, indent is " + coffeeIndent);
//        }
//
//		if (response.isSuccess() && coffeeIndent!= null) {
//            PayStatusAskResult result = new PayStatusAskResult();
//            result.setResCode(response.getLinkFrame().resCode);
//            result.setCoffeeIndent(coffeeIndent);
//			postToUI(result.toRemote());
//		}
//	}
//}
