package com.controller.control;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSON;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;
@RestController
@CrossOrigin
public class train {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public static String doGet(String httpurl) {
        HttpURLConnection connection = null;
        InputStream is = null;
        BufferedReader br = null;
        String result = null;// 返回结果字符串
        try {
            // 创建远程url连接对象
            URL url = new URL(httpurl);
            // 通过远程url连接对象打开一个连接，强转成httpURLConnection类
            connection = (HttpURLConnection) url.openConnection();
            // 设置连接方式：get
            connection.setRequestMethod("GET");
            // 设置连接主机服务器的超时时间：15000毫秒
            connection.setConnectTimeout(15000);
            // 设置读取远程返回的数据时间：60000毫秒
            connection.setReadTimeout(60000);
            // 发送请求
            connection.connect();
            // 通过connection连接，获取输入流
            if (connection.getResponseCode() == 200) {
                is = connection.getInputStream();
                // 封装输入流is，并指定字符集
                br = new BufferedReader(new InputStreamReader(is));
                // 存放数据
                StringBuffer sbf = new StringBuffer();
                String temp = null;
                while ((temp = br.readLine()) != null) {
                    sbf.append(temp);
                    sbf.append("\r\n");
                }
                result = sbf.toString();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭资源
            if (null != br) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (null != is) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            connection.disconnect();// 关闭远程连接
        }

        return result;
    }
    @RequestMapping(value = "/train",method = RequestMethod.GET)
    public JSONObject train(@RequestParam("isTrain") Integer isTrain,
                           @RequestParam("isFirst") Integer isFirst,
                           @RequestParam("model_name") String model_name,
                           @RequestParam("dataset_name") String dataset_name
    ){
        String url = "http://127.0.0.1:8085/myapp/train/";
        url = url+isTrain+'/'+dataset_name+'/'+model_name+'/'+isFirst;

        String res = doGet(url);
        JSONObject obj=JSON.parseObject(res);
        return obj;
    }

    @RequestMapping(value = "/test",method = RequestMethod.GET)
    public JSONObject test(
                            @RequestParam("pic") String pic,
                            @RequestParam("model_name") String model_name,
                            @RequestParam("dataset_name") String dataset_name
    ){
        String url = "http://127.0.0.1:8085/myapp/test/";
        url = url+pic+'/'+dataset_name+'/'+model_name;

        String res = doGet(url);
        JSONObject obj=JSON.parseObject(res);
        return obj;
    }


}
