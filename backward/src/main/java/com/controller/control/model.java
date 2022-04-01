package com.controller.control;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
@RestController
@CrossOrigin
public class model {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @RequestMapping(value = "/model/one",method = RequestMethod.GET)
    public Map<String,Object> getstudent(
            @RequestParam("modelName") String modelName
    ){
        System.out.println(modelName);
        String sql="select * from model where model_name = ?";//SQL查询语句
        Map<String,Object> map=jdbcTemplate.queryForMap(sql,modelName);

        return map;
    }

    @RequestMapping(value = "/model/delete",method = RequestMethod.GET)
    public int deletestudents(@RequestParam("model_id") String model_id){

        String sql = "DELETE FROM model WHERE model_id=?;";
        int i = jdbcTemplate.update(sql, model_id);
        return i;
    }
    @RequestMapping(value = "/model/add",method = RequestMethod.GET)
    public int addstudents(@RequestParam("user_id") Integer userId,
                           @RequestParam("model_name") String modelName,
                           @RequestParam("dataset_name") String datasetName
                           ){
        String sql = "INSERT INTO model(user_id, model_name,dataset_name, accuracy)" +
                " VALUES (?,?, ?, ?);";
        int i = jdbcTemplate.update(sql, userId, modelName, datasetName,null);
        return i;
    }

    @RequestMapping(value = "/model/update",method = RequestMethod.GET)
    public int updatestudents(@RequestParam("model_id") Integer model_id,
                              @RequestParam("accuracy") Double accuracy){
        String sql = "UPDATE model SET accuracy=? WHERE model_id=?;";
        int i = jdbcTemplate.update(sql,  accuracy, model_id);
        return i;
    }
}
