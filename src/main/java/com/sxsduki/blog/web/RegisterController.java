package com.sxsduki.blog.web;

import com.sxsduki.blog.pojo.User;
import com.sxsduki.blog.service.UserService;
import com.sxsduki.blog.utils.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Date;

/**
 * @Auther: sxs
 * @Date: 2019/12/15 16:40
 * @Description:用户注册
 */

@Controller
public class RegisterController {

    @Autowired
    UserService userService;

    //默认头像地址
    @Value("${comment.avatar}")
    private String avatar;

    @GetMapping("/register")
    public String registerpage(){

        return "register";

    }

    @PostMapping("/register")
    public String register(@RequestParam String username,
                           @RequestParam String password,
                           @RequestParam String nickname,
                           @RequestParam String email,
                           RedirectAttributes attributes){

        User user1 = userService.getUserByUsername(username);
        if (user1 != null){
            attributes.addFlashAttribute("message","该用户名已存在");
            return "redirect:/register";
        }else {
            User user = new User();
            user.setUsername(username);
            user.setPassword(MD5Utils.code(password));
            user.setAvatar(avatar);
            user.setEmail(email);
            user.setNickname(nickname);
            user.setCreateTime(new Date());
            user.setUpdateTime(new Date());
            user.setType(0);
            user.setRole("USER");
            userService.saveUser(user);


            return "login";
        }
    }

}
