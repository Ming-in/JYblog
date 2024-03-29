package com.ming.web.admin;

import com.ming.po.User;
import com.ming.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

/**
 * @author Ming
 */
@Controller
@RequestMapping("/admin")
public class AdminLoginController {


    @Autowired
    private UserService userService;

    @GetMapping
    public String loginPage() {
        return "admin/login";
    }


    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session,
                        RedirectAttributes attributes) {
        User user = userService.checkUser(username, password);
        if (user != null) {
            //管理员type=0
            if (user.getType() == 0){
                user.setPassword(null);
                session.setAttribute("user", user);
                return "redirect:/admin/users";
            }else {
                attributes.addFlashAttribute("message", "请使用管理员账户登录！");
                return "redirect:/admin";
            }
        } else {
            attributes.addFlashAttribute("message", "管理员用户名或密码错误");
            return "redirect:/admin";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("user");
        return "redirect:/admin";
    }
}
