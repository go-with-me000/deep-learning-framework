package com.controller.control;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
@CrossOrigin
@RestController
public class classselection {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @RequestMapping(value = "/classselection/consult",method = RequestMethod.GET)
    public List<Map<String,Object>> getselection(
            @RequestParam("oid") Integer oid
    ){

        String sql="select * from courseselection where oid = ?";//SQL查询语句
        List<Map<String,Object>> list=jdbcTemplate.queryForList(sql,oid);

        return list;
    }
    @RequestMapping(value = "/classselection/basic",method = RequestMethod.GET)
    public Map<String,Object> getBasic(
            @RequestParam("oid") Integer oid
    ){

        String sql="select * from coursecreated, teacher,course where oid = ? and coursecreated.teacherNum=teacher.teacherNum and coursecreated.courseNum=course.courseNum";//SQL查询语句
        Map<String,Object> map=jdbcTemplate.queryForMap(sql,oid);

        return map;
    }

    @RequestMapping(value = "/classselection/update",method = RequestMethod.GET)
    public int updateselection(@RequestParam("eid") Integer eid,
                             @RequestParam("score") Integer score
                             ){
        String sql = "UPDATE courseselection SET score=? WHERE eid=?;";
        int i = jdbcTemplate.update(sql,  score, eid);
        return i;
    }
}
