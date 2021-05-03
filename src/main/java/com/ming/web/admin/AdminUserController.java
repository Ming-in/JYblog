package com.ming.web.admin;

import com.ming.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


/**
 * @author 邹明
 */

@Controller
@RequestMapping("/admin")
public class AdminUserController {

    @Autowired
    private UserService userService;


    @GetMapping("/users")
    public String users(@PageableDefault(size = 10,sort = {"id"},direction = Sort.Direction.ASC)
                                Pageable pageable, Model model) {
        model.addAttribute("page",userService.listUser(pageable));
        return "admin/users";
    }

/*

    @GetMapping("/users/{id}/input")
    public String editInput(@PathVariable Long id, Model model) {
        model.addAttribute("user", userService.getUser(id));
        return "admin/users-input";
    }
*/

/*
    @PostMapping("/users")
    public String post(@Valid user user, BindingResult result, RedirectAttributes attributes) {
        user user1 = userService.getuserByName(user.getName());
        if (user1 != null) {
            result.rejectValue("name","nameError","不能添加重复的分类");
        }
        if (result.hasErrors()) {
            return "admin/users-input";
        }
        user t = userService.saveuser(user);
        if (t == null ) {
            attributes.addFlashAttribute("message", "新增失败");
        } else {
            attributes.addFlashAttribute("message", "新增成功");
        }
        return "redirect:/admin/users";
    }


    @PostMapping("/users/{id}")
    public String editPost(@Valid user user, BindingResult result,@PathVariable Long id, RedirectAttributes attributes) {
        user user1 = userService.getuserByName(user.getName());
        if (user1 != null) {
            result.rejectValue("name","nameError","不能添加重复的分类");
        }
        if (result.hasErrors()) {
            return "admin/users-input";
        }
        user t = userService.updateuser(id,user);
        if (t == null ) {
            attributes.addFlashAttribute("message", "更新失败");
        } else {
            attributes.addFlashAttribute("message", "更新成功");
        }
        return "redirect:/admin/users";
    }

*/

/*
    @GetMapping("/users/{id}/delete")
    public String delete(@PathVariable Long id,RedirectAttributes attributes) {
        userService.deleteUser(id);
        attributes.addFlashAttribute("message", "删除成功");
        return "redirect:/admin/users";
    }
*/

}
