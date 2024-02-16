package com.ezen.network;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class URLExample2 {

    public static void main(String[] args) {
        String image = "https://www.google.com/images/branding/googlelogo/2x/googlelogo_color_272x92dp.png";
        InputStream in = null;
        OutputStream out = null;
        try {
            URL url = new URL(image);
            // URL 상의 리소스 읽어 오기
            in  = url.openStream();

            out = new FileOutputStream("google.png");

            byte[] buffer = new byte[1024];
            int count = 0;
            while( (count=in.read(buffer)) != -1 ){
                // 읽어들인 바이트 배열을 내컴떠의 특정 디렉터리에 저장(다운로드)
                out.write(buffer, 0, count);
            }
            System.out.println("다운로드 완료...");

        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                in.close();
                out.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }


    }
}
