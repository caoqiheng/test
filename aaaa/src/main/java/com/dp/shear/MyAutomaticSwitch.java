package com.dp.shear;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

public class MyAutomaticSwitch {

    //获取数据方法
    public static StringBuilder getHttp(String url) {
        StringBuilder result = new StringBuilder();
        BufferedReader in = null;
        URL realUrl = null;
        try {
            realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            //设置超时时间
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(15000);
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");

            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
//                System.out.println(key + "--->" + map.get(key));
            }
            // 定义 BufferedReader输入流来读取URL的响应，设置utf8防止中文乱码
            in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
            in.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return result;
    }

    public static void main(String[] args) {
        String b = "https://this-api-will-break-all-the-time-do-not-use-2124.chiaexplorer.com/coinsForAddress/xch10lmw870g85xsugnz53vacsh4gj9ntz0arc30xsgvmsyv9qn3hq6scmfxny?page=1";
        StringBuilder http = getHttp(b);
        System.out.println("weCon----->" + http);
        JSONObject json = new JSONObject(http.toString());
        System.out.println("json----->" + json);
        if(json.has("coins"))
        {
            JSONArray coins = json.getJSONArray("coins");
            System.out.println("coins---->" + coins);
            JSONObject jsonObject1 = coins.getJSONObject(0);
            System.out.println("jsonObject1----->" + jsonObject1);

            long timestamp = jsonObject1.getLong("timestamp");
            long amount = jsonObject1.getLong("amount");

            //判断上次爆快时间及数量
            long timeDifference = System.currentTimeMillis()-timestamp*1000;//时间差
            if(amount >= 250000000000L && timeDifference >= 1*24*60*60*1000)
            {
                System.out.println("时间到了 ");
                //条件满足 判断当前状态
            }else
                {
                    System.out.println("时间没到了 ");
                }

            System.out.println("amount1--->" + amount + "timestamp---->" + timestamp);
        }
    }
}
