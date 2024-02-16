package com.ezen.network.chat.clientv2;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * TCP/IP 기반의 클라이언트 구현
 */
public class ChatClient {
    private String serverIp;
    private int port;

    private Socket socket;
    private DataOutputStream out;
    private DataInputStream in;
    private String nickName;

    public ChatClient(){
        this("localhost", 2024);
    }

    public ChatClient(String serverIp, int port){
        this.serverIp = serverIp;
        this.port = port;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    /**
     * 채팅서버 연결
     * @throws IOException
     */
    public void connect() throws IOException {
        socket = new Socket(serverIp, port);
        out = new DataOutputStream(socket.getOutputStream());
        in = new DataInputStream(socket.getInputStream());
        System.out.println("[클라이언트] 채팅서버("+serverIp+")에 연결되었습니다.");
    }

    /**
     * 채팅서버에 메시지 전송
     * @param message
     * @throws IOException
     */
    public void sendMessage(String message) throws IOException {
        out.writeUTF(message);
        out.flush();
    }

    /**
     * 채팅서버로부터 메시지 수신
     * @throws IOException
     */
    public void receive() throws IOException {
        Thread thread = new Thread(){
            @Override
            public void run() {
                try{
                    while(true) {
                        String message = in.readUTF();
                        System.out.println(message);
                    }
                }catch (IOException e){
                    System.out.println("[클라이언트] 채팅서버와 연결 해제");
                }
            }
        };
        thread.start();
    }

    /**
     * 채팅서버 연결 종료
     */
    public void unConnect() throws IOException {
        socket.close();
        System.out.println("[클라이언트] 채팅서버("+serverIp+")와 연결이 종료되었습니다.");
    }

}
