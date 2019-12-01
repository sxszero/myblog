package com.sxsduki.blog.web.admin;

import com.sxsduki.blog.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 */

@RequestMapping("/admin")
@Controller
public class TypeController {

    @Autowired
    private TypeService typeService;

    @GetMapping("types")
    public String types(@PageableDefault(size = 10,sort = {"id"},direction = Sort.Direction.DESC)
                                    Pageable pageable, Model model){

        model.addAttribute("page",typeService.listType(pageable));

        return "admin/types";
    }

    @GetMapping("/types/input")
    public String input(){

        return "admin/types-input";
    }

}
