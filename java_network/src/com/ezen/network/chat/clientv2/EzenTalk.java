package com.ezen.network.chat.clientv2;

import org.json.JSONObject;

import java.io.IOException;
import java.util.Scanner;

public class EzenTalk {

    public static void main(String[] args) {
        ChatClient chatClient = new ChatClient();
//        ChatClient chatClient = new ChatClient("xxx.xxx.xxx.xxx", 2024);
        try {
            chatClient.connect();
        } catch (IOException e) {
            System.err.println("[채팅서버]에 연결할 수 없습니다.");
            System.err.println("네트워크 상태를 확인하여 주세요");
            return;
        }

        Scanner scanner = new Scanner(System.in);
        try{
            System.out.print("대화명 입력 : ");
            String nickName = scanner.nextLine();
            chatClient.setNickName(nickName);
            // JSON으로 문자열 전송
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("command", "CONNECT");
            jsonObject.put("nickName", nickName);
            chatClient.sendMessage(jsonObject.toString());

            System.out.println("------------------------------------------");
            System.out.println("서버에 전송하고자 하는 메시지를 입력하세요");
            System.out.println("종료하려면 q 또는 Q를 입력하세요.");
            System.out.println("------------------------------------------");
            
            // 서버 연결 완료 후 서버로 부터 전송되는 메시지 수신을 위한 스레드 생성 및 시작
            chatClient.receive();

            boolean stop = false;
            while(!stop) {
                String inputMessage = scanner.nextLine();
                if(inputMessage.equalsIgnoreCase("q")){
                    // 서버에 연결 종료메시지 전송
                    jsonObject.put("command", "DIS_CONNECT");
                    jsonObject.put("nickName", nickName);
                    chatClient.sendMessage(jsonObject.toString());
                    break;
                }else {
                    jsonObject.put("command", "MULTI_CHAT");
                    jsonObject.put("nickName", nickName);
                    jsonObject.put("message", inputMessage);
                    chatClient.sendMessage(jsonObject.toString());
                }
            }
        }catch (IOException e){
            System.err.println("네트워크 상태를 확인하여 주세요.");
        } finally {
            try {
                scanner.close();
                chatClient.unConnect();
            } catch (IOException e) { }
        }
    }
}
