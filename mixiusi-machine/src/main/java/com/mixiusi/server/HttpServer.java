package com.mixiusi.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

//获取支付宝或者微信返回的支付成功的消息，即异步通知结果，还未完成
public class HttpServer {
	private int port = 8888;

	public HttpServer() {
		ServerSocket ss;
		try {
			ss = new ServerSocket(port);

			while (true) {
				Socket socket = ss.accept();

				Serverthread sthread = new Serverthread(socket);
				sthread.start();
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}

class Serverthread extends Thread {
	private Socket socket;

	public Serverthread(Socket socket) {
		this.socket = socket;
	}

	public void run() {
		try {

			BufferedReader bd = new BufferedReader(new InputStreamReader(socket.getInputStream()));

			/**
			 * 接受HTTP请求
			 */
			String requestHeader;
			int contentLength = 0;
			while ((requestHeader = bd.readLine()) != null && !requestHeader.isEmpty()) {
				System.out.println(requestHeader);
				/**
				 * 获得GET参数
				 */
				if (requestHeader.startsWith("GET")) {
					int begin = requestHeader.indexOf("/?") + 2;
					int end = requestHeader.indexOf("HTTP/");
					String condition = requestHeader.substring(begin, end);
					System.out.println("GET参数是：" + condition);
				}
				/**
				 * 获得POST参数 1.获取请求内容长度
				 */
				if (requestHeader.startsWith("Content-Length")) {
					int begin = requestHeader.indexOf("Content-Lengh:") + "Content-Length:".length();
					String postParamterLength = requestHeader.substring(begin).trim();
					contentLength = Integer.parseInt(postParamterLength);
					System.out.println("POST参数长度是：" + Integer.parseInt(postParamterLength));
				}
			}
			StringBuffer sb = new StringBuffer();
			if (contentLength > 0) {
				for (int i = 0; i < contentLength; i++) {
					sb.append((char) bd.read());
				}
				System.out.println("POST参数是：" + sb.toString());
			}

			socket.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}