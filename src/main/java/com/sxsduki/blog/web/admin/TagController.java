package com.sxsduki.blog.web.admin;

import com.sxsduki.blog.pojo.Tag;
import com.sxsduki.blog.pojo.Type;
import com.sxsduki.blog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 */


@Controller
@RequestMapping("/admin")
public class TagController {

    @Autowired
    private TagService tagService;

    @GetMapping("/tags")
    public String tags(@PageableDefault(size = 10,sort = {"id"},direction = Sort.Direction.DESC)
                       Pageable pageable, Model model){

        model.addAttribute("page",tagService.listTag(pageable));
        return "admin/tags";
    }

    @GetMapping("/tags/input")
    public String input(Model model){

        model.addAttribute("tag",new Tag());
        return "admin/tags-input";
    }

    @GetMapping("/tags/{id}/delete")
    public String deleteTag(@PathVariable Long id, RedirectAttributes attributes){

        tagService.deleteTage(id);
        attributes.addFlashAttribute("message","删除成功");
        return "redirect:/admin/tags";
    }

    //编辑tag
    @GetMapping("/tags/{id}/input")
    public String input(@PathVariable Long id,Model model){

        model.addAttribute("tag",tagService.getTag(id));
        return "admin/tags-input";
    }

    //新标签提交
    @PostMapping("/tags")
    public String editPost(Tag tag,RedirectAttributes attributes){
        Tag tag1 = tagService.getTageByName(tag.getName());
        if(tag1 != null){
            attributes.addFlashAttribute("message","错误：该标签已经存在");
        }else {
            tagService.saveTage(tag);
            attributes.addFlashAttribute("message","标签添加成功");
        }
        return "redirect:/admin/tags";

    }

    @PostMapping("/tags/{id}")
    public String post(Tag tag, BindingResult result, RedirectAttributes attributes){


        //此处的会返回给标签修改页面 显示标签是否已经存在数据库中
        Tag tag1 = tagService.getTageByName(tag.getName());
        if (tag1 !=null){
            result.rejectValue("name","nameError","该标签已经存在");
        }
        if(result.hasErrors()){
            return "admin/tags-input";
        }

        //此处的会返回给标签列表页面 二次确保标签不会重复添加和提示信息
        Tag t = tagService.updateTage(tag.getId(),tag);
        if(t == null){
            attributes.addFlashAttribute("message","标签更新失败");
        }else {
            attributes.addFlashAttribute("message","标签更新成功");
        }
        return "redirect:/admin/tags";

    }





}
