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

    @GetMapping("types")
    public String types(@PageableDefault(size = 4,sort = {"id"},direction = Sort.Direction.DESC)
                                    Pageable pageable, Model model){

        model.addAttribute("page",typeService.listType(pageable));

        return "admin/types";
    }

    @GetMapping("/types/input")
    public String input(){

        return "admin/types-input";
    }

    @PostMapping("/types")
    public String post(Type type, BindingResult result, RedirectAttributes attributes){

//        Type type1 = typeService.getTypeByName(type.getName());
//        if(type1 !=null){
//            result.rejectValue("name","nameError","该分类已经存在");
//        }

        Type t = typeService.saveType(type);
        if (t != null){
            //
            attributes.addFlashAttribute("message","添加成功");

        }else {
            //
            attributes.addFlashAttribute("message","添加失败");
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
