package com.ezen.network.http.server;


import com.ezen.network.ftp.server.FTPServer;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * HTTP 응용 프로토콜 기반 Web Server 기본 기능 구현
 */
public class WebServer {
	/** HTTP 사용하는 잘 알려진 포트 */
	private static final int PORT = 8282;
	
	/** 인터넷(웹)을 통해 서비스 하고자 하는 리소스 파일 저장 디렉터리
	 * 웹 디렉터리 또는 public 디렉터리라고 한다.
	 * 서비스 하고자 하는 리소스(파일) 예 : html, css, javascript, 이미지, 오디오, 동영상 파일 등
	 */
	public static String WEB_DIRECTORY = "WebContents";
//	public static String WEB_DIRECTORY = "C:/server/WebContents";

	private boolean stop;
	private ServerSocket serverSocket;

	/** 웹 서버 구동 */
	public void start() throws IOException{
		serverSocket = new ServerSocket(PORT);
		System.out.println("☆☆☆ Web(Http) Server["+PORT+"] Start ☆☆☆");
		while(!stop){
			Socket socket = serverSocket.accept();
			System.out.println("Web Client(Browser) Connected...");
			// 웹 클라이언트 http 요청 처리 스레드 생성 및 실행
			HttpResponseTask responseTask = new HttpResponseTask(socket);
			Thread thread = new Thread(responseTask);
			thread.start();
		}
		System.out.println("★★★ Http(Web) Server("+PORT+") Stop ★★★");
	}

	/** 웹 서버 종료 */
	public void stop() throws IOException{
		if(serverSocket != null) serverSocket.close();
	}

	public static void main(String[] args) {
		File webDirectory = new File(WEB_DIRECTORY);
		if(!webDirectory.exists()) {
			webDirectory.mkdir();
		}

		// 웹 서버 생성
		WebServer webServer = new WebServer();
		try {
			// 웹 서버 실행
			webServer.start();
		} catch (IOException e) {
			System.err.println("포트("+PORT+") 충돌로 Web Server를 구동할 수 없습니다..");
		}
	}

}
