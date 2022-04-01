package com.controller.control;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
@CrossOrigin
@RestController
public class teacher {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @RequestMapping(value = "/teacher/consult",method = RequestMethod.GET)
    public List<Map<String,Object>> getTeacher(){

        String sql="select * from teacher,department where teacher.yxh=department.yxh order by teacher.teacherNum";//SQL查询语句
        List<Map<String,Object>> list=jdbcTemplate.queryForList(sql);
//        Map<String,Object> map = null;
//        map.put("title","3");
//        list.add(0, map);
        return list;
    }

    @RequestMapping(value = "/teacher/add",method = RequestMethod.GET)
    public int addTeacher(@RequestParam("teacherNum") Integer teacherNum,
                          @RequestParam("teacherName") String teacherName,
                          @RequestParam("age") Integer age,
                          @RequestParam("sex") String sex,
                          @RequestParam("yxh") Integer yxh){
        String sql = "INSERT INTO teacher VALUES (?, ?, ?,?,?);";
        int i = jdbcTemplate.update(sql, teacherNum, teacherName, age,sex,yxh);
        return i;
    }

    @RequestMapping(value = "/teacher/delete",method = RequestMethod.GET)
    public int deleteTeacher(@RequestParam("teacherNum") Integer teacherNum){

        String sql = "DELETE FROM teacher WHERE teacherNum=?;";
        int i = jdbcTemplate.update(sql, teacherNum);
        return i;
    }
    @RequestMapping(value = "/teacher/update",method = RequestMethod.GET)
    public int updateTeacher(@RequestParam("teacherNum") Integer teacherNum,
                             @RequestParam("teacherName") String teacherName,
                             @RequestParam("age") Integer age,
                             @RequestParam("sex") String sex,
                             @RequestParam("yxh") Integer yxh){
        String sql = "UPDATE teacher SET teacherName=?, age=?, yxh=?, sex=? WHERE teacherNum=?;";
        int i = jdbcTemplate.update(sql,  teacherName, age,yxh,sex,teacherNum);
        return i;
    }
}
