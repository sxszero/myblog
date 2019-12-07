package com.sxsduki.blog.web;

import com.sxsduki.blog.pojo.User;
import com.sxsduki.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

/**
 * @Auther: sxs
 * @Date: 2019/12/7 16:07
 * @Description:
 */

@Controller
public class LoginUserController {

    @Autowired
    private UserService userService;


    @GetMapping("/userlogin")
    public String loginPage(){
        return "/login";
    }


    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session,
                        RedirectAttributes attributes){

        User user = userService.checkUser(username,password);
        if(user != null){
            user.setPassword(null);
            session.setAttribute("user",user);
            return "redirect:/";
        }else {
            attributes.addFlashAttribute("message","用户名或密码错误");
            return "redirect:/login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.removeAttribute("user");
        return "redirect:/userlogin";
    }

}
