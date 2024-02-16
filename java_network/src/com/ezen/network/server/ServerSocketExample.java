package com.ezen.network.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * TCP/IP 기반의 서버 구현
 */
public class ServerSocketExample {
    public static void main(String[] args) throws IOException {
        System.out.println("[서버] 실행됨...");
        // 네트워크에 연결된 원격의 클라이언트 연결을 수신하기 위해 ServerSocket 생성
        int port = 2024;
        ServerSocket serverSocket = new ServerSocket(port);
        //ServerSocket serverSocket = new ServerSocket();
        //serverSocket.bind(new InetSocketAddress("xxx.xxx.xxx.xxx", port));

        boolean stop = false;
        // 멀티 클라언트의 연결을 수신하기 위해 Loop
        while(!stop) {
            Socket socket = serverSocket.accept(); // 블락메소드
            InetSocketAddress isa = (InetSocketAddress) socket.getRemoteSocketAddress();
            String clientIp = isa.getAddress().getHostAddress();
            int clientPort = isa.getPort();

            System.out.println("원격 클라이언트(" + clientIp + ") 연결을 해옴...");

            // 연결해온 클라이언트와 데이터 송수신
        }
        serverSocket.close();
        System.out.println("[서버] 종료됨...");
    }
}
