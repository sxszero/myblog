package com.sxsduki.blog.web.admin;

import com.sxsduki.blog.pojo.Blog;
import com.sxsduki.blog.pojo.BlogQuery;
import com.sxsduki.blog.pojo.User;
import com.sxsduki.blog.service.BlogService;
import com.sxsduki.blog.service.TagService;
import com.sxsduki.blog.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;


/**
 *
 */

@Controller
@RequestMapping("/admin")
public class BlogController {



    @Autowired
    private BlogService blogService;
    @Autowired
    private TypeService typeService;
    @Autowired
    private TagService tagService;

    @GetMapping("/blogs")
    public String blogs(@PageableDefault(size = 2,sort = {"updateTime"},direction = Sort.Direction.DESC) Pageable pageable,
                        BlogQuery blog, Model model){

        //用于发送给前端页面 type的数据 用于动态获取type的数据
        model.addAttribute("types",typeService.listType());
        model.addAttribute("page",blogService.listBlog(pageable,blog));
        return "admin/blogs";

    }

    @PostMapping("/blogs/search")
    public String search(@PageableDefault(size = 2,sort = {"updateTime"},direction = Sort.Direction.DESC) Pageable pageable,
                         BlogQuery blog, Model model){

        model.addAttribute("page",blogService.listBlog(pageable,blog));
        return "admin/blogs :: blogList";//局部刷新

    }

    @GetMapping("/blogs/input")
    public String input(Model model){

        model.addAttribute("blog",new Blog());
        //初始化分类，目的让页面动态获取分类的内容
        model.addAttribute("types",typeService.listType());
        model.addAttribute("tags",tagService.listTag());


        return "admin/blogs-input";
    }

    /**
     * 新增博客
     * @param blog
     * @param attributes
     * @param session
     * @return
     */
    @PostMapping("/blogs")
    public String post(Blog blog, RedirectAttributes attributes, HttpSession session){

        //初始化
        blog.setUser((User) session.getAttribute("user"));
        blog.setType(typeService.getType(blog.getType().getId()));
        blog.setTags(tagService.listTag(blog.getTagIds()));

        Blog b = blogService.saveBlog(blog);
        if(b == null){
            attributes.addFlashAttribute("message","操作失败");
        }else {
            attributes.addFlashAttribute("message","操作成功");
        }

        return "redirect:/admin/blogs";

    }
}
