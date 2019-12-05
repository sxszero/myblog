package com.sxsduki.blog.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AboutShowController {


    @GetMapping("/aboutme")
    public String aboutMe(){
        return "aboutme";
    }
}
