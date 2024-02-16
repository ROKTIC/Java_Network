package com.ezen.network.client;

import java.io.IOException;
import java.net.Socket;

/**
 * TCP/IP 기반의 클라이언트 구현
 */
public class SocketExample {
    public static void main(String[] args) throws IOException {
        // ServerSocket에서 연결하기 위해 Socket 생성
        //String serverIp = "192.168.0.38";
        //String serverIp = "localhost"; // 별칭
        String serverIp = "127.0.0.1";   // 가상IP
        int port = 2024;
        // ip, port를 이용하여 ServerSocket 연결
        Socket socket = new Socket( serverIp, port);
        System.out.println("[클라이언트] 서버와 연결되었습니다....");

        // 서버와 데이터 송수신

        // 연결 해제
        socket.close();
    }
}
