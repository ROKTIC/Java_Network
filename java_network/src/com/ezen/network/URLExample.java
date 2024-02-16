package com.ezen.network;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;

public class URLExample {

    public static void main(String[] args) {
        // 내컴퓨터의 리소스를 가리키는 형식(Path)
        // C:/xxxx/yyy/some.txt
        //File file = new File();
        // new FileInputStream(file);

        String str = "https://www.naver.com";
        String image = "https://www.google.com/images/branding/googlelogo/2x/googlelogo_color_272x92dp.png";

        // 인터넷상의 특정 url의 정보 얻기
        InputStream in = null;
        try {
            URL url = new URL(str);
            // URL 관련 정보 얻기
            String protocol = url.getProtocol();
            String host = url.getHost();
            int port = url.getPort();
            
            System.out.println("protocol = " + protocol);
            System.out.println("host = " + host);
            System.out.println("port = " + port);

            // URL 상의 리소스 읽어 오기
            in  = url.openStream();
            //int data = in.read();
            //System.out.println("data = " + data);
//            byte[] buffer = new byte[1024];
//            int count = 0;
//            while( (count=in.read(buffer)) != -1 ){
//                String html = new String(buffer, 0, count);
//                System.out.print(html);
//            }

//            BufferedReader br = new BufferedReader(System.in);
            InputStreamReader isr = new InputStreamReader(in, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(isr);
            String html = null;
            while( (html=bufferedReader.readLine()) != null ){
                System.out.println(html);
            }

        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }


    }
}
