package com.mixiusi.server;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mixiusi.handler.RequestHandler;
import com.mixiusi.protocol.LinkFrame;
import com.mixiusi.protocol.pack.Unpack;
import com.mixiusi.protocol.request.Request;

public class DealRequest {
	private final Logger log = LoggerFactory.getLogger(DealRequest.class);
	private ExecutorService pool = Executors.newFixedThreadPool(7); 
	public void dealRequest(LinkFrame header, Unpack body, ChannelHandlerContext ctx) {
		if (!RequestFactory.getInstance().existsRequest(header)) {
			log.info("Response not registered, SID: " + header.serviceId + " CID: " + header.commandId);
			return;
		}
		deal(header, body, RequestFactory.getInstance().queryRequestPriority(header), ctx);
	}

	private void deal(LinkFrame header, Unpack body, Integer queryRequestPriority, ChannelHandlerContext ctx) {
//		Future future = pool.submit(task);
		pool.execute(new Runnable() {
			@Override
			public void run() {
				handlePacket(header, body, queryRequestPriority, ctx);
			}
		});
	}
	private void handlePacket(LinkFrame header, Unpack body, Integer queryRequestPriority,
			ChannelHandlerContext ctx) {
		log.info("----handlePacket------");
		Request request = RequestFactory.getInstance().newRequest(header);
		// check
		if (request == null) {
			// TODO
			// trace
			 System.out.println("jj");
			return;
		}

		// set header
		request.setLinkFrame(header);

		/**
		 * status
		 * 
		 * false when need unpack body
		 */
		// boolean status = header.resCode != ResponseCode.RES_SUCCESS
		// || body == null;

		/**
		 * embedded
		 */
		boolean embedded = false;
		LinkFrame header2 = null;
		Unpack body2 = null;

		/**
		 * unpack
		 */
		if (true) {
			try {
				// unpack body
				body2 = request.unpackBody(body);

				// embedded
				embedded = (body2 != null);

				if (embedded) {
					// new header
					header2 = new LinkFrame();

					// pop header
					body2.popMarshallable(header2);
				}

				// OK here
				// status = true;
			} catch (Exception e) {
				log.error(e.getMessage(), e);

				// TODO
				// trace
			}
		}

		// validate
		if (true) {
			// trace
			log.info("handle packet: " + header);

			// execute response
			executeRequest(request, ctx);

			if (embedded) {
				// handle packet again
				handlePacket(header2, body2, null, ctx);
			}
		}
	}

	private void executeRequest(Request request, ChannelHandlerContext ctx) {
		log.info("-----------------执行请求------------");
		RequestHandler handler = RequestFactory.getInstance().queryRequestHandler(request);
		if (handler != null) {
			handler.processRequest(request, ctx);
		}
	}
}
