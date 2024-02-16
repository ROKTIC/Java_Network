package com.ezen.network.server;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 * TCP/IP 기반의 서버 구현
 */
public class EchoServer {

    private static final int PORT = 2024;
    private static ServerSocket serverSocket = null;

    public static void main(String[] args) throws IOException {
        System.out.println("-------------------------------------------------------------");
        System.out.println(" 서버를 종료하려면 q 또는 Q를 입력하고 Enter 키를 입력하세요.");
        System.out.println("-------------------------------------------------------------");
        // TCP 서버 구동
        startServer();

        // 키보드 입력
        Scanner scanner = new Scanner(System.in);
        while(true){
            String command = scanner.nextLine();
            if(command.equalsIgnoreCase("q")){
                break;
            }
        }
        scanner.close();
        stopServer();
    }

    /**
     * 서버 시작(구동)
     */
    public static void startServer(){
        // 네트워크에 연결된 원격의 클라이언트 연결을 수신하기 위해 ServerSocket 생성
        new Thread() {
            @Override
            public void run() {
                try {
                    serverSocket = new ServerSocket(PORT);
                    System.out.println("[서버] 실행됨.");
                    boolean stop = false;
                    // 멀티 클라언트의 연결을 수신하기 위해 Loop
                    while(!stop) {
                        System.out.println("[서버] 클라이언트 연결 요청을 기다림.");
                        Socket socket = serverSocket.accept();
                        InetSocketAddress isa = (InetSocketAddress) socket.getRemoteSocketAddress();
                        String clientIp = isa.getAddress().getHostAddress();
                        System.out.println("[서버] 원격 클라이언트(" + clientIp + ") 연결을 해옴...");

                        // 연결해온 클라이언트와 데이터 송수신
                        // Socket과의 입출력 스트림 생성
//                        InputStream in = socket.getInputStream();
//                        OutputStream out = socket.getOutputStream();
                        DataInputStream in = new DataInputStream(socket.getInputStream());
                        DataOutputStream out = new DataOutputStream(socket.getOutputStream());

                        // 클라이언트가 전송한 데이터 읽기
                        //int data = in.read();
                        //byte[] buffer = new byte[1024];
                        //int count = in.read(buffer);
                        // 문자 디코딩 필요
                        //String receiveMessage = new String(buffer, 0, count);
                        String receiveMessage = in.readUTF();
                        System.out.println("[서버] Client -> Server : " + receiveMessage);

                        // 메시지 에코(Echo)
                        //out.write(buffer, 0, count);
                        out.writeUTF(receiveMessage);
                        out.flush();
                        System.out.println("[서버] 수신한 데이터를 다시 보냄");
                        in.close();
                        // 원격 클라이언트와 연결 끊기
                        socket.close();
                    }

                } catch (IOException e) {
                    System.err.println("[서버] " + e.getMessage());
                }
            }
        }.start();
    }

    /**
     * 서버 종료
     */
    public static void stopServer(){
        try {
            serverSocket.close();
            System.out.println("[서버] 종료됨.");
        } catch (IOException e) {}
    }
}
