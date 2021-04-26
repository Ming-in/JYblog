package com.ming.web;

import com.ming.po.User;
import com.ming.service.UserService;
import com.ming.util.MarkdownUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;

/**
 * 个人中心页面
 *
 * @author 邹明
 */
@Controller
public class AboutShowController {

    @Autowired
    private UserService userService;

    @GetMapping("/about/{id}")
    public String about(@PathVariable Long id, Model model) {
        User user = userService.findById(id);
        User u = new User();
        BeanUtils.copyProperties(user, u);
        if (!"".equals(user.getIntroduction()) && user.getIntroduction() != null) {
            u.setIntroduction(MarkdownUtils.markdownToHtmlExtensions(user.getIntroduction()));
        }
        model.addAttribute("zone", u);
        return "about";
    }
}
