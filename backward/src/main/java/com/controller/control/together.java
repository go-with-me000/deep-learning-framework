package com.controller.control;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
@CrossOrigin
@RestController
public class together {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @RequestMapping(value = "/semesters",method = RequestMethod.GET)
    public List<Map<String,Object>> getSemesters(){

        String sql="select * from semesters";//SQL查询语句
        List<Map<String,Object>> list=jdbcTemplate.queryForList(sql);
//        Map<String,Object> map = null;
//        map.put("title","3");
//        list.add(0, map);
        return list;
    }
    @RequestMapping(value = "/departments",method = RequestMethod.GET)
    public List<Map<String,Object>> getDepartments(){

        String sql="select * from department";//SQL查询语句
        List<Map<String,Object>> list=jdbcTemplate.queryForList(sql);
//        Map<String,Object> map = null;
//        map.put("title","3");
//        list.add(0, map);
        return list;
    }
    @RequestMapping(value = "/login",method = RequestMethod.GET)
    public Map<String,Object> getLogin(
            @RequestParam("account") Integer account,
            @RequestParam("password") String password
    ){

        String sql="select * from user where user.account = ? and user.password = ?;";//SQL查询语句
        Map<String,Object> map =jdbcTemplate.queryForMap(sql,account,password );

        return map;
    }
}
