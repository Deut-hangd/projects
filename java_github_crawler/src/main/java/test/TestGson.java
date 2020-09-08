package test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;

public class TestGson {

    static class Test{
        private int aaa;
        private int bbb;
    }
    public static void main(String[] args){
        Gson gson = new GsonBuilder().create();
//        HashMap<String, String> hashMap = new HashMap<>();
//        hashMap.put("name", "曹操");
//        hashMap.put("skill1", "三段跳");
//        hashMap.put("skill2", "剑气");
//        hashMap.put("skill3", "吸血");
//        hashMap.put("被动", "加攻速");
//        String result = gson.toJson(hashMap);
//        System.out.println(result);
        String jsonString = "{\"aaa\" : 1, \"bbb\" : 2}";
        Test test = gson.fromJson(jsonString, Test.class);
        System.out.println(test.aaa);
        System.out.println(test.bbb);
    }

}
