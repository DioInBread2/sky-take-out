package com.sky.test;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

@SpringBootTest
public class HttpClientTest {
    /**
     * 测试通过HttpClient发送Get请求
     */
    @Test
    public void testGet(){
        //创建HttpClient对象
        CloseableHttpClient client = HttpClients.createDefault();
        //创建请求对象
        HttpGet httpGet = new HttpGet("http://localhost:8080/user/shop/status");
        //发送请求
        try {
            CloseableHttpResponse response = client.execute(httpGet);
            Integer statusCode =  response.getStatusLine().getStatusCode();
            System.out.println("服务端状态码:" + statusCode);
            HttpEntity entity = response.getEntity();
            String body = EntityUtils.toString(entity);
            System.out.println("服务端返回的数据为:" + body);
            response.close();
            client.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 测试通过HttpClient发送Post请求
     */
    @Test
    public  void testPost() throws Exception {
        //创建HttpClient对象
        CloseableHttpClient client = HttpClients.createDefault();
        //创建请求对象
        HttpPost httpPost = new HttpPost("http://localhost:8080/admin/employee/login");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("username","admin");
        jsonObject.put("password","123456");
        StringEntity entity = new StringEntity(jsonObject.toString());
        entity.setContentEncoding("utf-8");
        entity.setContentType("application/json");
        httpPost.setEntity(entity);
        //发送请求
        CloseableHttpResponse response = client.execute(httpPost);
        Integer statusCode =  response.getStatusLine().getStatusCode();
        System.out.println("服务端状态码:" + statusCode);
        HttpEntity respEntity = response.getEntity();
        String body = EntityUtils.toString(respEntity);
        System.out.println("服务端返回的数据为:" + body);
        response.close();
        client.close();

    }
}
