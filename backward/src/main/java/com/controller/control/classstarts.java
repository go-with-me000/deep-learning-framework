package com.controller.control;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@CrossOrigin
@RestController
public class classstarts {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @RequestMapping(value = "/classstarts/consult",method = RequestMethod.GET)
    public List<Map<String,Object>> getCourses(){

        String sql="select * from course ,department where course.yxh=department.yxh order by course.courseNum";//SQL查询语句
        List<Map<String,Object>> list=jdbcTemplate.queryForList(sql);
//        Map<String,Object> map = null;
//        map.put("title","3");
//        list.add(0, map);
        return list;
    }

    @RequestMapping(value = "/classstarts/add",method = RequestMethod.GET)
    public int addCourses(@RequestParam("courseNum") Integer courseNum,
                          @RequestParam("courseName") String courseName,
                          @RequestParam("credit") Integer credit,
                          @RequestParam("yxh") Integer yxh){
        String sql = "INSERT INTO course VALUES (?, ?, ?,?);";
        int i = jdbcTemplate.update(sql, courseNum, courseName, yxh,credit);
        return i;
    }

    @RequestMapping(value = "/classstarts/delete",method = RequestMethod.GET)
    public int deleteCourses(@RequestParam("courseNum") Integer courseNum){

        String sql = "DELETE FROM course WHERE courseNum=?;";
        int i = jdbcTemplate.update(sql, courseNum);
        return i;
    }
    @RequestMapping(value = "/classstarts/update",method = RequestMethod.GET)
    public int updateCourses(@RequestParam("courseNum") Integer courseNum,
                          @RequestParam("courseName") String courseName,
                          @RequestParam("credit") Integer credit,
                          @RequestParam("yxh") Integer yxh){
        String sql = "UPDATE course SET courseName=?, credit=?, yxh=? WHERE courseNum=?;";
        int i = jdbcTemplate.update(sql,  courseName, credit,yxh,courseNum);
        return i;
    }
}
