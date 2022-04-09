package com.controller.control;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.Result;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

@RestController
@CrossOrigin
public class pic extends HttpServlet {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @RequestMapping("/file/uploadss")
    @ResponseBody
    public String uploadPhoto(@RequestParam("pic") MultipartFile pic, HttpServletResponse response) throws IOException {
        System.out.println(pic);

        /*
         * 编码为字符串*/
        String s = Base64Utils.encodeToString(pic.getBytes());
        System.out.println("s:"+s);

        /* *
         *2.解码成字节数组
         */
        Base64.Decoder decoder = Base64.getDecoder();
        byte[] bytes = decoder.decode(s);

        /*
         * 3.字节流转文件
         */
        String d = "D:\\programming\\python\\deep-learning-exam\\graduation\\django\\deep\\myapp\\jpg";
        File fileMkdir = new File(d);
        if (!fileMkdir.exists()){
            fileMkdir.mkdir();
        }
        String pathName = fileMkdir.getPath() + "\\" + pic.getOriginalFilename();

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(pathName);
            fos.write(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null){
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return pic.getOriginalFilename();

    }

    @RequestMapping("/file/uploadss2")
    @ResponseBody
    public String uploadPhoto2(@RequestParam("pic") MultipartFile pic, HttpServletResponse response) throws IOException {
        System.out.println(pic);

        /*
         * 编码为字符串*/
        String s = Base64Utils.encodeToString(pic.getBytes());
        System.out.println("s:"+s);

        /* *
         *2.解码成字节数组
         */
        Base64.Decoder decoder = Base64.getDecoder();
        byte[] bytes = decoder.decode(s);

        /*
         * 3.字节流转文件
         */
        String d = "D:\\programming\\python\\deep-learning-exam\\graduation\\frontend\\static\\images\\avatar";
        File fileMkdir = new File(d);
        if (!fileMkdir.exists()){
            fileMkdir.mkdir();
        }
        String pathName = fileMkdir.getPath() + "\\" + pic.getOriginalFilename();

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(pathName);
            fos.write(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null){
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return pic.getOriginalFilename();

    }


}
