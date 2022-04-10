package com.controller.control;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSON;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
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
            connection.setConnectTimeout(1500000);
            // 设置读取远程返回的数据时间：60000毫秒
            connection.setReadTimeout(6000000);
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

    @RequestMapping(path = "/file/upload", method = {RequestMethod.POST})
    public String addDish(@RequestParam("photos") MultipartFile file, HttpServletRequest request) throws Exception {
        String path = null;// 文件路径
        double fileSize = file.getSize();
//        System.out.println("文件的大小是"+ fileSize);

        byte[] sizebyte=file.getBytes();
//        System.out.println("文件的byte大小是"+ sizebyte.toString());



        if (file != null) {// 判断上传的文件是否为空
            String type = null;// 文件类型
            String fileName = file.getOriginalFilename();// 文件原名称
//            System.out.println("上传的文件原名称:" + fileName);
            // 判断文件类型
            type = fileName.indexOf(".") != -1 ? fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length()) : null;
            if (type != null) {// 判断文件类型是否为空

                if ("GIF".equals(type.toUpperCase()) || "PNG".equals(type.toUpperCase()) || "JPG".equals(type.toUpperCase())) {

                    // 项目在容器中实际发布运行的根路径
                    String realPath = request.getSession().getServletContext().getRealPath("/");
                    // 自定义的文件名称
                    String trueFileName = String.valueOf(System.currentTimeMillis()) + "." + type;
                    // 设置存放图片文件的路径
                    String save_path = "D:\\programming\\python\\deep-learning-exam\\graduation\\django\\deep\\myapp\\jpg";
                    path = save_path+fileName;
//                    System.out.println("存放图片文件的路径:" + path);

                    // 转存文件到指定的路径
                    file.transferTo(new File(path));
//                    System.out.println("文件成功上传到指定目录下");

                    return "文件成功上传到指定目录下";
                }

            } else {
                System.out.println("不是我们想要的文件类型,请按要求重新上传");
                return "不是我们想要的文件类型,请按要求重新上传";
            }
        } else {
            System.out.println("文件类型为空");
            return "文件类型为空";
        }

        return "已经成功上传到指定目录";
    }

    @RequestMapping(value = "/train",method = RequestMethod.GET)
    public JSONObject train(@RequestParam("isTrain") Integer isTrain,
                           @RequestParam("isFirst") Integer isFirst,
                            @RequestParam("epoch") Integer epoch,
                           @RequestParam("model_name") String model_name,
                           @RequestParam("dataset_name") String dataset_name
    ){
        String url = "http://127.0.0.1:8085/myapp/train/";
        url = url+isTrain+'/'+dataset_name+'/'+model_name+'/'+isFirst+'/'+epoch;

        String res = doGet(url);
        JSONObject obj=JSON.parseObject(res);
//        System.out.println(res);
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
//        System.out.println(url);
        String res = doGet(url);
        JSONObject obj=JSON.parseObject(res);
        return obj;
    }


}
