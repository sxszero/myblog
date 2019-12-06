package com.sxsduki.blog.web.admin;


import com.sxsduki.blog.pojo.Friend;
import com.sxsduki.blog.service.FriendService;
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

@Controller
@RequestMapping("/admin")
public class FriendController {

    @Autowired
    private FriendService friendService;

    @GetMapping("/friends")
    public String friends(@PageableDefault(size = 4,sort = {"id"},direction = Sort.Direction.DESC)
                                      Pageable pageable, Model model){

        model.addAttribute("page",friendService.listFriend(pageable));
        return "admin/friends";
    }

    /**
     * 新增按钮
     * @param model
     * @return
     */
    @GetMapping("/friends/input")
    public String input(Model model){

        model.addAttribute("friend",new Friend());
        return "admin/friends-input";
    }

    /**
     * 删除友链
     * @param id
     * @param attributes
     * @return
     */
    @GetMapping("/friends/{id}/delete")
    public String deleteFriend(@PathVariable Long id, RedirectAttributes attributes){

        friendService.deleteFriend(id);
        attributes.addFlashAttribute("message","友链删除成功");
        return "redirect:/admin/friends";
    }

    /**
     * 编辑按钮 进入编辑修改友链页面
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/friends/{id}/input")
    public String input(@PathVariable Long id,Model model){

        model.addAttribute("friend",friendService.getFriend(id));

        return "admin/friends-input";
    }

    /**
     * 新增友链 点击提交按钮触发
     * @param friend
     * @param attributes
     * @return
     */
    @PostMapping("/friends")
    public String post(Friend friend,RedirectAttributes attributes){
        Friend friend1 = friendService.getFriendByName(friend.getNickname());
        if(friend1 != null){
            attributes.addFlashAttribute("message","错误：该友链昵称已经存在");
        }else {
            friendService.saveFriend(friend);
            attributes.addFlashAttribute("message","友链添加成功");
        }

        return "redirect:/admin/friends";

    }

    /**
     * 编辑友链 点击提交
     * @param friend
     * @param result
     * @param attributes
     * @return
     */
    @PostMapping("/friends/{id}")
    public String editpost(Friend friend, BindingResult result,RedirectAttributes attributes){

        //如果 不对昵称做任何修改 则提交失败
        Friend friend1 = friendService.getFriendByName(friend.getNickname());
        if(friend1 != null){
            result.rejectValue("nickname","nameError","该友链昵称已经存在");
        }
        if (result.hasErrors()){
            return "admin/friends-input";
        }

        //验证是否修改成功
        Friend f = friendService.updateFriend(friend.getId(), friend);
        if(f == null){
            attributes.addFlashAttribute("message","友链修改失败");
        }else {
            attributes.addFlashAttribute("message","友链修改成功");
        }
        return "redirect:/admin/friends";
    }


}
