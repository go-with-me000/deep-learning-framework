package com.controller.control;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;
@RestController
@CrossOrigin
public class model {
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


    @RequestMapping(value = "/model/one",method = RequestMethod.GET)
    public Map<String,Object> getstudent(
            @RequestParam("model_id") String model_id
    ){
        String sql="select * from model where model_id = ?";//SQL查询语句
        Map<String,Object> map=jdbcTemplate.queryForMap(sql,model_id);

        return map;
    }
    @RequestMapping(value = "/allmodal",method = RequestMethod.GET)
    public List<Map<String,Object>> getModels(){

        String sql="select * from model";//SQL查询语句
        List<Map<String,Object>> list=jdbcTemplate.queryForList(sql);
        return list;
    }
    @RequestMapping(value = "/model/getnumbers",method = RequestMethod.GET)
    public JSONObject getNumber(){
        String url = "http://127.0.0.1:8085/myapp/getarray";

        String res = doGet(url);
        JSONObject obj= JSON.parseObject(res);
        return obj;

    }

    @RequestMapping(value = "/model/delete",method = RequestMethod.GET)
    public int deletestudents(@RequestParam("model_id") String model_id){

        String sql = "DELETE FROM model WHERE model_id=?;";
        int i = jdbcTemplate.update(sql, model_id);
        return i;

    }
    @RequestMapping(value = "/model/add",method = RequestMethod.GET)
    public int addstudents(@RequestParam("user_id") Integer userId,
                           @RequestParam("model_name") String modelName,
                           @RequestParam("dataset_name") String datasetName,
                           @RequestParam("network") String network,
                           @RequestParam("description") String description,
                           @RequestParam("pic") String pic
                           ){
        String sql = "INSERT INTO model(user_id, model_name,dataset_name, accuracy,network,description,pic)" +
                " VALUES (?,?, ?, ?,?,?,?);";
        int i = jdbcTemplate.update(sql, userId, modelName, datasetName,null, network,description,pic);
        return i;

    }
    @RequestMapping(value = "/dataset/change",method = RequestMethod.GET)
    public int addstudents(
                           @RequestParam("name") String name,
                           @RequestParam("description") String description
    ){
        String sql = "UPDATE dataset SET description=? where name = ?";
        int i = jdbcTemplate.update(sql, description,name);
        return i;

    }

    @RequestMapping(value = "/alldata",method = RequestMethod.GET)
    public List<Map<String,Object>> getDataset(){

        String sql="select * from dataset";//SQL查询语句
        List<Map<String,Object>> list=jdbcTemplate.queryForList(sql);
        return list;
    }
    @RequestMapping(value = "/dataset/get",method = RequestMethod.GET)
    public int addstudents(){



        String url = "http://127.0.0.1:8085/myapp/getDataset";

        String res = doGet(url);
        JSONObject obj= JSON.parseObject(res);
        for(String str:obj.keySet()){

            String names = obj.getString(str);
            String sql = "INSERT INTO dataset(name) VALUES (?);";
            String sql2="select * from dataset where name = ?";//SQL查询语句
            List<Map<String,Object>> list=jdbcTemplate.queryForList(sql2,names);

            int len = list.size();
            if(len==0){
                int i = jdbcTemplate.update(sql,  names);
//                System.out.println(names);
            }
        }
        return 1;

    }

    @RequestMapping(value = "/model/update",method = RequestMethod.GET)
    public int updatestudents(@RequestParam("model_id") Integer model_id,
                              @RequestParam("model_name") String model_name,
                              @RequestParam("description") String description,
                              @RequestParam("pic") String pic
    ){
        String sql = "UPDATE model SET model_name=?,description=?,pic=? WHERE model_id=?;";
        int i = jdbcTemplate.update(sql,  model_name,description,pic, model_id);
        return i;
    }

    @RequestMapping(value = "/update_res",method = RequestMethod.GET)
    public int updateaccuracy(@RequestParam("model_id") Integer model_id,
                              @RequestParam("train_res") Integer train_res
    ){
        String sql = "UPDATE model SET accuracy=? WHERE model_id=?;";
        int i = jdbcTemplate.update(sql,  train_res, model_id);
        return i;
    }


}
