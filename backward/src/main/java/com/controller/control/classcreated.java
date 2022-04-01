package com.controller.control;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
@CrossOrigin
@RestController
public class classcreated {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @RequestMapping(value = "/classcreated/consult",method = RequestMethod.GET)
    public List<Map<String,Object>> getcoursecreateds(){

        String sql="select * from coursecreated,teacher,course where coursecreated.teacherNum=teacher.teacherNum and coursecreated.courseNum=course.courseNum";//SQL查询语句
        List<Map<String,Object>> list=jdbcTemplate.queryForList(sql);

        return list;
    }

    @RequestMapping(value = "/classcreated/add",method = RequestMethod.GET)
    public int addcoursecreateds(@RequestParam("oid") Integer oid,
                          @RequestParam("courseNum") String courseNum,
                          @RequestParam("teacherNum") Integer teacherNum,
                          @RequestParam("time") Integer time,
                                 @RequestParam("semester") String semester){
        String sql = "INSERT INTO coursecreated VALUES (?, ?, ?,?,?);";
        int i = jdbcTemplate.update(sql, oid, teacherNum, courseNum,time,semester);
        return i;
    }

    @RequestMapping(value = "/classcreated/delete",method = RequestMethod.GET)
    public int deletecoursecreateds(@RequestParam("oid") Integer oid){

        String sql = "DELETE FROM coursecreated WHERE oid=?;";
        int i = jdbcTemplate.update(sql, oid);
        return i;
    }

}
