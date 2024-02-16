package com.ezen.network;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Iterator;

public class JSONExample {
    public static void main(String[] args) {
        // JSON 표기법(JavaScript Object Notation)
        String json = "{\"name\": \"김기정\", \"age\" : 30}";
        System.out.println(json);

        JSONObject root = new JSONObject();
        root.put("id" , "bangry");
        root.put("name" , "김기정");
        root.put("age" , 40);
        root.put("isStudent" , false);

        JSONObject tel = new JSONObject();
        tel.put("home", "02-1234-1234");
        tel.put("mobile", "010-1234-1234");

        JSONArray skill = new JSONArray();
        skill.put("Java");
        skill.put("SQL");
        skill.put("JavaSkill");

        root.put("tel", tel);
        root.put("skill", skill);

        String myInfo = root.toString(); // 객체 -> JSON 변환
        System.out.println(myInfo);

        // JSON -> 자바 Object로 변환
        JSONObject member = new JSONObject(json);
        String name = member.getString("name");
        int age = member.getInt("age");
        System.out.println(name);
        System.out.println(age);

        JSONObject member2 = new JSONObject(myInfo);
        System.out.println(member2.getString("id"));
        System.out.println(member2.getString("name"));
        System.out.println(member2.getInt("age"));
        System.out.println(member2.getBoolean("isStudent"));

        JSONObject tel2 = member2.getJSONObject("tel");
        System.out.println(tel2.getString("home"));
        System.out.println(tel2.getString("mobile"));

        JSONArray skills = member2.getJSONArray("skill");
        System.out.println(skills.get(0));
        System.out.println(skills.get(1));
        System.out.println(skills.get(2));
        // 반복
        Iterator<Object> iter = skills.iterator();
        while(iter.hasNext()){
            String ski = (String)iter.next();
            System.out.println(ski);
        }
        // 향상 for문 활용
        for(Object ski : skills){
            System.out.println(ski);
        }

    }
}
