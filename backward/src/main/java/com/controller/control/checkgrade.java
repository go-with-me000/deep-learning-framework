package com.controller.control;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
public class checkgrade {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @RequestMapping(value = "/checkgrade",method = RequestMethod.GET)
    public List<Map<String,Object>> getGrades(
            @RequestParam("studentNum") Integer studentNum,
            @RequestParam("semester") Integer semester
    ){

        String sql="SELECT * FROM courseselection, course,teacher WHERE courseselection.studentNum = ? AND courseselection.oid = (SELECT oid FROM coursecreated WHERE courseselection.oid = oid AND semester =? and teacher.teacherNum = teacherNum and course.courseNum = courseNum) ";
        List<Map<String,Object>> list=jdbcTemplate.queryForList(sql,studentNum, semester);

        return list;
    }

}
