package com.ezen.network;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class InetAddressExample {
    public static void main(String[] args) throws UnknownHostException {
        // 내컴떠의 IP 주소 동적 얻기
        InetAddress inetAddress = InetAddress.getLocalHost();
        String localIp = inetAddress.getHostAddress();
        System.out.println("localIp = " + localIp);

        // 네이버 서버의 아이피 얻기
        //String domainName = "www.naver.com";
        String domainName = "www.google.com";
        InetAddress remoteIp = InetAddress.getByName(domainName);
        System.out.println("remoteIp = " + remoteIp.getHostAddress());

        InetAddress[] inetAddresses = InetAddress.getAllByName(domainName);
        for (InetAddress address : inetAddresses) {
            System.out.println("address = " + address.toString());
        }

        

    }
}
