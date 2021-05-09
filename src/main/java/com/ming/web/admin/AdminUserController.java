package com.ming.web.admin;

import com.ming.po.Type;
import com.ming.po.User;
import com.ming.service.UserService;
import com.ming.util.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;


/**
 * @author 邹明
 */

@Controller
@RequestMapping("/admin")
public class AdminUserController {

    @Autowired
    private UserService userService;


    @GetMapping("/users")
    public String users(@PageableDefault(size = 10, sort = {"id"}, direction = Sort.Direction.ASC)
                                Pageable pageable, Model model) {
        model.addAttribute("page", userService.listUser(pageable));
        return "admin/users";
    }


    @GetMapping("/users/{id}/input")
    public String editInput(@PathVariable Long id, Model model) {
        model.addAttribute("user", userService.findById(id));
        return "admin/users-input";
    }

    @GetMapping(value = "/toadduser")
    public String toaddUser(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "admin/users-input";
    }

    @PostMapping(value = "/adduser")
    public String addUser(@RequestParam(value = "username") String username,
                          @RequestParam(value = "pwd") String password,
                          @RequestParam(value = "nickname") String nickname,
                          @RequestParam(value = "email") String email,
                          RedirectAttributes attributes) {
        if (username.isEmpty() | password.isEmpty() | nickname.isEmpty() | email.isEmpty()) {
            attributes.addFlashAttribute("message", "请完整填入用户信息");
            return "redirect:/admin/users";
        }
        userService.saveUser(username, password, nickname, email);
        attributes.addFlashAttribute("message", "添加用户成功");
        return "redirect:/admin/users";
    }

    @PostMapping("/users/{id}")
    public String editPost(@Valid User user,
                           @PathVariable Long id,
                           RedirectAttributes attributes,
                           BindingResult result,
                           HttpSession session) {
        User u1 = userService.findByUsername(user.getUsername());
        //修改前的用户数据
        User user1 = userService.findById(id);
        if (u1 != null && !u1.getId().equals(user1.getId())) {
            result.rejectValue("username", "nameError", "用户名重复");
        }
        if (result.hasErrors()) {
            return "admin/users-input";
        }
        user1.setUsername(user.getUsername());
        user1.setNickname(user.getNickname());
        user1.setEmail(user.getEmail());
        user1.setType(user.getType());
        //密码加密
        if (!StringUtils.isEmpty(user.getPassword())) {
            user1.setPassword(MD5Utils.code(user.getPassword()));
        }
        if (userService.save(user1) == null) {
            attributes.addFlashAttribute("message", "更新失败");
        } else {
            attributes.addFlashAttribute("message", "更新成功");
        }
        session.setAttribute("user", userService.findById(((User)session.getAttribute("user")).getId()));
        return "redirect:/admin/users";
    }

    //ajax

    @ResponseBody
    @PostMapping(value = "/ban")
    public String ban(@RequestParam(value = "id") Long id,
                      @RequestParam(value = "type") Integer type) {
        type = type == 9 ? 1 : 9;
        User user = userService.findById(id);
        user.setType(type);
        if (userService.save(user) != null) {
            return type == 9 ? "成功封禁用户" + user.getUsername() : "成功解封用户" + user.getUsername();
        }
        return "操作失败";
    }
}
