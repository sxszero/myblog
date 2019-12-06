package com.sxsduki.blog.web;


import com.sxsduki.blog.service.FriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FriendShowController {

    @Autowired
    private FriendService friendService;


    @GetMapping("/friends")
    public String friends(Model model){

        model.addAttribute("friends",friendService.listFriend());

        return "friends";
    }

}
