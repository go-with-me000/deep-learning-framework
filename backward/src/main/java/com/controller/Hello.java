package com.controller;

import com.model.person;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@RestController
public class Hello {
//    @GetMapping
    public  Integer handle(@RequestParam("age") Integer age){
        return age;
    }

    @GetMapping
    public person getPerson(){
        person person = new person();
        person.setId(1);
        person.setVotesNumber(123);
        person.setGroupCandidateName("采用");
        return person;
    }
}
