package com.controller.control;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
@RestController
@CrossOrigin
public class student {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @RequestMapping(value = "/student/consult",method = RequestMethod.GET)
    public List<Map<String,Object>> getstudents(){

        String sql="select * from student,department where student.yxh=department.yxh order by student.studentNum";//SQL查询语句
        List<Map<String,Object>> list=jdbcTemplate.queryForList(sql);
        return list;
    }

    @RequestMapping(value = "/student/one",method = RequestMethod.GET)
    public Map<String,Object> getstudent(
            @RequestParam("studentNum") Integer studentNum
    ){

        String sql="select * from student where studentNum = ?";//SQL查询语句
        Map<String,Object> map=jdbcTemplate.queryForMap(sql,studentNum);

        return map;
    }

    @RequestMapping(value = "/student/add",method = RequestMethod.GET)
    public int addstudents(@RequestParam("studentNum") Integer studentNum,
                          @RequestParam("studentName") String studentName,
                          @RequestParam("age") Integer age,
                          @RequestParam("sex") String sex,
                          @RequestParam("password") String password,
                          @RequestParam("yxh") Integer yxh){
        String sql = "INSERT INTO student VALUES (?, ?, ?,?,?,?);";
        int i = jdbcTemplate.update(sql, studentNum, studentName, sex,age,yxh,password);
        return i;
    }

    @RequestMapping(value = "/student/delete",method = RequestMethod.GET)
    public int deletestudents(@RequestParam("studentNum") Integer studentNum){

        String sql = "DELETE FROM student WHERE studentNum=?;";
        int i = jdbcTemplate.update(sql, studentNum);
        return i;
    }
    @RequestMapping(value = "/student/update",method = RequestMethod.GET)
    public int updatestudents(@RequestParam("studentNum") Integer studentNum,
                             @RequestParam("studentName") String studentName,
                              @RequestParam("age") Integer age,
                              @RequestParam("sex") String sex,
                              @RequestParam("password") String password,
                              @RequestParam("yxh") Integer yxh){
        String sql = "UPDATE student SET studentName=?, age=?, yxh=?,sex=?,password=? WHERE studentNum=?;";
        int i = jdbcTemplate.update(sql,  studentName, age,yxh,sex,password,studentNum);
        return i;
    }
}
