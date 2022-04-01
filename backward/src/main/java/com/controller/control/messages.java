package com.controller.control;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
@CrossOrigin
@RestController
public class messages {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @RequestMapping(value = "/controlmessage",method = RequestMethod.GET)
    public Map<String,Object> getTeacherMessages(@RequestParam("teacherNum") Integer teacherNum){

        String sql="select * from teacher where teacherNum=?";//SQL查询语句
        Map<String,Object> map=jdbcTemplate.queryForMap(sql, teacherNum);

        return map;
    }
    @RequestMapping(value = "/studentmessage",method = RequestMethod.GET)
    public Map<String,Object> getStudentMessages(@RequestParam("studentNum") Integer studentNum){

        String sql="select * from student,department where studentNum=? and student.yxh=department.yxh";//SQL查询语句
        Map<String,Object> map=jdbcTemplate.queryForMap(sql, studentNum);

        return map;
    }
    @RequestMapping(value = "/studentupdate",method = RequestMethod.GET)
    public Integer updateStudentMessages(@RequestParam("studentNum") Integer studentNum,
            @RequestParam("sex") String sex,
           @RequestParam("age") Integer age

   ){

        String sql = "UPDATE student SET age=?, sex=? WHERE studentNum=?;";
        int i = jdbcTemplate.update(sql,  age,sex,studentNum);
        return i;

    }
    @RequestMapping(value = "/teacherupdate",method = RequestMethod.GET)
    public Integer updateTeacherMessages(@RequestParam("teacherNum") Integer teacherNum,
                                         @RequestParam("sex") String sex,
                                         @RequestParam("age") Integer age

    ){

        String sql = "UPDATE teacher SET age=?, sex=? WHERE teacherNum=?;";
        int i = jdbcTemplate.update(sql,  age,sex,teacherNum);
        return i;

    }
}
