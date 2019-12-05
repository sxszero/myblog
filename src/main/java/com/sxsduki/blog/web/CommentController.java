package com.sxsduki.blog.web;


import com.sxsduki.blog.pojo.Comment;
import com.sxsduki.blog.pojo.User;
import com.sxsduki.blog.service.BlogService;
import com.sxsduki.blog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private BlogService blogService;

    //默认头像地址
    @Value("${comment.avatar}")
    private String avatar;

    @GetMapping("/comments/{blogId}")
    public String comments(@PathVariable Long blogId, Model model){

        model.addAttribute("comments",commentService.listCommentByBlogId(blogId));

        return "blog :: commentList";
    }

    @PostMapping("/comments")
    public String post(Comment comment,HttpSession session){
        Long blogId = comment.getBlog().getId();
        comment.setBlog(blogService.getBlog(blogId));
        User user = (User) session.getAttribute("user");

        //这里如果添加用户登录功能时需要加判断， 因为注册时会保存头像地址，所以不会用默认头像地址
        if (user != null){
            comment.setAvatar(user.getAvatar());
            comment.setAdminComment(true);
        }else {
            comment.setAvatar(avatar);
        }

        commentService.saveComment(comment);

        return "redirect:/comments/"+blogId;
    }

}
