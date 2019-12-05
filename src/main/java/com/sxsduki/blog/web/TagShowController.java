package com.sxsduki.blog.web;


import com.sxsduki.blog.pojo.BlogQuery;
import com.sxsduki.blog.pojo.Tag;
import com.sxsduki.blog.service.BlogService;
import com.sxsduki.blog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class TagShowController {

    @Autowired
    private TagService tagService;


    @Autowired
    private BlogService blogService;


    @GetMapping("/tags/{id}")
    public String tags(@PageableDefault(size = 8,sort = {"updateTime"},direction = Sort.Direction.DESC) Pageable pageable, @PathVariable Long id, Model model){

        //tags页面展示的分类个数
        List<Tag> tags =tagService.listTagTop(10000);

        if (id == -1){
            id=tags.get(0).getId();
        }
        model.addAttribute("tags",tags);
        model.addAttribute("page",blogService.listBlog(id,pageable));
        model.addAttribute("activeTagId",id);
        model.addAttribute("recommends",blogService.listRecommendBlogTop(3));


        return "tags";
    }

}
