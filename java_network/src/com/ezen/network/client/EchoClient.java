package com.ezen.network.client;

import java.io.*;
import java.net.Socket;

/**
 * TCP/IP 기반의 클라이언트 구현
 */
public class EchoClient {
    public static void main(String[] args) throws IOException {
        // ServerSocket에서 연결하기 위해 Socket 생성
        //String serverIp = "192.168.0.38";
        //String serverIp = "localhost"; // 별칭
        String serverIp = "127.0.0.1";   // 가상IP
        int port = 2024;
        // ip, port를 이용하여 ServerSocket 연결
        Socket socket = new Socket( serverIp, port );
        // Domain Name으로 ServerSocket 연결
        //Socket socket1 = new Socket(new InetSocketAddress("www.bangry.com", 2024));
        System.out.println("[클라이언트] 서버와 연결되었습니다....");

        // Socket과의 입출력 스트림 생성
//        OutputStream out = socket.getOutputStream();
//        InputStream in = socket.getInputStream();

        DataOutputStream out = new DataOutputStream(socket.getOutputStream());
        DataInputStream in = new DataInputStream(socket.getInputStream());

        // 서버(Socket)에 데이터 전송
        String sendMessage = "즐거운 하루 되세요...";
//        byte[] bytes = sendMessage.getBytes("utf-8"); // 문자인코딩
//        out.write(10);
//        out.write(bytes);
//        out.flush();

        out.writeUTF(sendMessage);
        out.flush();

        // 서버(Socket)에서 데이터 수신
        //byte[] buffer = new byte[1024];
        //int count = in.read(buffer);
        //String receiveMessage = new String(buffer, 0, count);
        String receiveMessage = in.readUTF();
        System.out.println("[클라이언트] Server -> Client : " + receiveMessage);

        out.close();
        in.close();
        // 연결 해제
        socket.close();
    }
}
