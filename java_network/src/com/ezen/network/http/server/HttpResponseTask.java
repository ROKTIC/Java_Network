package com.ezen.network.http.server;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.Charset;
import java.util.StringTokenizer;

public class HttpResponseTask implements Runnable {
	private Socket socket;
	private BufferedReader fileReader;
	private BufferedReader in;
	private PrintWriter out;
	
	public HttpResponseTask(Socket socket) throws IOException {
		this.socket = socket;
		in = new BufferedReader(new InputStreamReader(socket.getInputStream(), Charset.forName("utf-8")));
		out = new PrintWriter(socket.getOutputStream());
	}
	
	public void webService() {
		// 요청 URL : http://www.someserver.co.kr/index.html
		// 웹 브라우저 HTTP 요청 메시지 구조
		/*
         요청방식 요청URI HTTP버전\r\n(CRLF)     -> 요청라인   GET /index.html HTTP/1.1
         요청헤더명1:요청헤더값1(CRLF)
         요청헤더명2:요청헤더값2(CRLF)           -> 요청헤더
         요청헤더명n:요청헤더값n(CRLFCRLF)
         
         메시지 본문                             -> 메시지바디 GET  -> EMPTY
                                                               POST -> 사용자 제출 데이터
         */
		String requestLine = null;
		try {
			requestLine = in.readLine(); //예) "GET /index.html HTTT/1.1"
			StringTokenizer requstTokenizer = new StringTokenizer(requestLine, " ");
			String method = requstTokenizer.nextToken();
			String uri = requstTokenizer.nextToken(); //예) xxx.html, xxx.jpg, xxx.gif, xxx.zip 등
			// 요청 파일이 없을 경우 (http://www.someserver.co.kr)
			if(uri.equals("/")) {
				uri = "/index.html"; // Welcome 파일(홈 페이지) 설정
			}
			String protocol = requstTokenizer.nextToken(); //예)HTTP/1.1
			// HTTP 요청 메시지 디버깅
			System.out.println("---------------------------------------");
			System.out.println("요청 방식 : " + method);
			System.out.println("요청 URI : " + uri);
			System.out.println("HTTP 버전 : " + protocol);
			System.out.println("---------------------------------------");

			File serviceFile = new File(WebServer.WEB_DIRECTORY, uri);
			
			// 웹 서버 HTTP 응답 메시지 구조 예
			/*
	         HTTP버전 응답코드 응답코드메시지  -> 응답라인
	         응답헤더명1:응답헤더값1
	         응답헤더명2:응답헤더값2           -> 응답헤더
	         응답헤더명n:응답헤더값n
	         
	         메시지 바디(<html>~~</html>)      -> 메시지바디
	         */
			String responseLine = null;
			String responseHeader = null;
			StringBuilder responseMessage = new StringBuilder();
			// 요청 파일 존재시..
			if(serviceFile.exists()){
				fileReader = new BufferedReader(new FileReader(serviceFile));
				responseLine = "HTTP/1.1 200 OK\r\n";
				responseHeader = "Content-Type:text/html; charset=utf-8\r\n\r\n";
				responseMessage
					.append(responseLine)
					.append(responseHeader);
				String html = null;
				while((html = fileReader.readLine()) != null){
					System.out.println(html);
					responseMessage.append(html);
				}
				// 응답 메시지 출력
				out.println(responseMessage.toString());
				out.flush();

			}else{ // 요청 파일이 존재하지 않을 경우 -> 상태코드 404 응답
				responseLine = "HTTP/1.1 404 Not Found\r\n";
				responseHeader = "Content-Type:text/html; charset=utf-8\r\n\r\n";
				responseMessage
					.append(responseLine)
					.append(responseHeader)
					.append("요청하신 파일을 찾을 수 없습니다.");
				out.println(responseMessage.toString());
				out.flush();
			}
		} catch (IOException e) {
		} finally{
			try {
				if(fileReader != null) fileReader.close();
				if(in != null) in.close();
				if(out != null) out.close();
				if(socket != null) socket.close();
			} catch (IOException e) {}
		}
	}

	@Override
	public void run() {
		webService();
	}
}
