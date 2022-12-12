package com.ming.web;

import com.ming.po.User;
import com.ming.service.UserService;
import com.ming.util.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

/**
 * @author Ming
 */
@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }


    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session,
                        RedirectAttributes attributes) {
        User user = userService.checkUser(username, password);
        if (user != null) {
            //被封禁用户
            if (user.getType() == 9) {
                attributes.addFlashAttribute("message", "用户已被封禁，禁止登录");
                return "redirect:/login";
            }
            user.setPassword(null);
            session.setAttribute("user", user);
            return "redirect:/";
        } else {
            attributes.addFlashAttribute("message", "用户名或密码错误");
            return "redirect:/login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("user");
        return "redirect:/";
    }

    /**
     * 密码：202cb962ac59075b964b07152d234b70
     * 注册用户
     */
    @PostMapping("/register")
    public String register(@RequestParam(value = "username") String username,
                           @RequestParam(value = "password") String password,
                           @RequestParam(value = "nickname") String nickname,
                           @RequestParam(value = "email") String email,
                           HttpSession session,
                           RedirectAttributes attributes) {
        if (username.isEmpty() | password.isEmpty() | nickname.isEmpty() | email.isEmpty()) {
            attributes.addFlashAttribute("message", "请完整填入注册信息");
            return "redirect:/login";
        } else {
            if (userService.findByUsername(username) != null) {
                attributes.addFlashAttribute("message", "该用户名已存在");
                return "redirect:/login";
            } else {
                User user = userService.saveUser(username, password, nickname, email);
                user.setPassword(null);
                session.setAttribute("user", user);
                return "redirect:/";
            }
        }
    }

    //以下ajax调用

    @ResponseBody
    @PostMapping("/repwd")
    public String repairPassword(@RequestParam(value = "oldPwd") String oldPwd,
                                 @RequestParam(value = "pwd") String pwd,
                                 HttpSession session) {
        User user = (User) session.getAttribute("user");
        user = userService.findById(user.getId());
        user.setPassword(MD5Utils.code(pwd));
        userService.save(user);
        return "修改成功";
    }

    @ResponseBody
    @PostMapping("/reinfo")
    public String repairInfo(String username,
                             @RequestParam(value = "nickname") String nickname,
                             @RequestParam(value = "email") String email,
                             HttpSession session) {
        User user = (User) session.getAttribute("user");
        user = userService.findById(user.getId());
        if (!StringUtils.isEmpty(username)) {
            user.setUsername(username);
        }
        user.setNickname(nickname);
        user.setEmail(email);
        userService.save(user);
        user.setPassword(null);
        session.setAttribute("user", user);
        return "修改成功";
//        return "redirect:/about"+user.getId();
    }
}
