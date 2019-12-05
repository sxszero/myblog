package com.sxsduki.blog.web;


import com.sxsduki.blog.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ArchiveShowController {

    @Autowired
    private BlogService blogService;


    @GetMapping("/archives")
    public String archives(Model model){

        model.addAttribute("blogCount",blogService.countBlog());
        model.addAttribute("archiveMap",blogService.archiveBlog());
        return "archives";

    }

}
