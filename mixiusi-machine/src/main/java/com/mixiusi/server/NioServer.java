/**
 * @author LuTao
 *
 */
package com.mixiusi.server;


import java.net.InetSocketAddress;
import java.nio.ByteOrder;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.buffer.HeapChannelBufferFactory;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mixiusi.nettyserver.PacketDecoder;
import com.mixiusi.nettyserver.PacketEncoder;
import com.mixiusi.protocol.pack.PackagePacker;

public class NioServer{
	private final Logger log = LoggerFactory.getLogger(NioServer.class);
	private PacketDecoder packetDecoder;
	private PacketEncoder packetEncoder;
	private PackagePacker packagePacker;
//	private DealRequest dealRequest;
	public static NioServer nioServer;
	
    private int port = 8060;

	public NioServer() {
		setupNetty();
	}

	private void setupNetty() {
		// 创建服务类对象
		packagePacker = new PackagePacker();
		packetDecoder = new PacketDecoder();
		packetEncoder = new PacketEncoder();
		packetDecoder.setUnpacker(packagePacker);
		packetEncoder.setPacker(packagePacker);

		ServerBootstrap serverBootstrap = new ServerBootstrap();

		ExecutorService boss = Executors.newCachedThreadPool();
		ExecutorService worker = Executors.newCachedThreadPool();
		
		serverBootstrap.setFactory(new NioServerSocketChannelFactory(boss, worker));

		serverBootstrap.setOption("tcpNoDelay", true);
		serverBootstrap.setOption("keepAlive", true);
		serverBootstrap.setOption("receiveBufferSize", 64 * 1024);
		serverBootstrap.setOption("bufferFactory", HeapChannelBufferFactory.getInstance(ByteOrder.LITTLE_ENDIAN));

		// 设置管道工厂
		serverBootstrap.setPipelineFactory(new ChannelPipelineFactory() {

			@Override
			public ChannelPipeline getPipeline() throws Exception {

				ChannelPipeline channelPipeline = Channels.pipeline();
				// 加上此行代码，在Handler中便可以直接获取字符串，而不用经过ChannelBuffer了
//				SSLEngine sslEngine = sslContext.newEngine(ch.alloc());
				// 看源码可得，decoder用来处理上行数据
//				channelPipeline.addLast("", new SslHandler(sslEngine));
				channelPipeline.addLast("decoder", packetDecoder);
				channelPipeline.addLast("Handler", new ServerHandler());
				// encoder用来数据下行数据
				channelPipeline.addLast("encoder", packetEncoder);
				/**
				 * 经测试可得，decoder和encoder不能省去，而下面的可以省掉
				 */

				// 返回
				return channelPipeline;
			}
		});

		// 注意此行代码，在绑定时，一定要在工厂之后，否则就会报错
		serverBootstrap.bind(new InetSocketAddress(port));
		
		log.info("start --> server-" + port);

	}
}
