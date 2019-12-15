package com.sxsduki.blog.web.admin;

import com.sxsduki.blog.pojo.Type;
import com.sxsduki.blog.service.TypeService;
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

import javax.validation.Valid;

/**
 *
 */

@RequestMapping("/admin")
@Controller
public class TypeController {

    @Autowired
    private TypeService typeService;

    @GetMapping("/types")
    public String types(@PageableDefault(size = 10,sort = {"id"},direction = Sort.Direction.DESC)
                                    Pageable pageable, Model model){

        model.addAttribute("page",typeService.listType(pageable));

        return "admin/types";
    }
    @GetMapping("/types/input")
    public String input(Model model){

        model.addAttribute("type",new Type());
        return "admin/types-input";
    }


    //点击编辑后得到该id的值
    @GetMapping("/types/{id}/input")
    public String editInput(@PathVariable Long id,Model model){
        model.addAttribute("type",typeService.getType(id));

        return "admin/types-input";
    }

    //编辑后提交 更新分类的实现
    @PostMapping("/types/{id}")
    public String editPost(@Valid Type type,BindingResult result,RedirectAttributes attributes){

        //此处的会返回给分类修改页面 显示该分类是否已经存在数据库中
        Type type1= typeService.getTypeByName(type.getName());
        if(type1 !=null){
            result.rejectValue("name","nameError","该分类已经存在");
        }
        if(result.hasErrors()){
            return "admin/types-input";
        }
        //此处的会返回给分类列表页面 二次确保分类不会重复添加和提示信息
        Type t =typeService.updateType(type.getId(),type);
        if(t==null){
            attributes.addFlashAttribute("message","更新失败");
        }else {
            attributes.addFlashAttribute("message","更新成功");
        }
        return "redirect:/admin/types";
    }


    //点击新增后 新增分类的实现
    @PostMapping("/types")
    public String post(Type type,RedirectAttributes attributes){


        Type t = typeService.getTypeByName(type.getName());
        if (t != null){
            //
            attributes.addFlashAttribute("message","添加失败：已存在相同分类");

        }else {
            //
            typeService.saveType(type);
            attributes.addFlashAttribute("message","添加成功");
        }
        return "redirect:/admin/types";
    }


    //删除分类
    @GetMapping("/types/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes attributes){

        typeService.deleteType(id);
        attributes.addFlashAttribute("message","删除成功");
        return "redirect:/admin/types";
    }
}
