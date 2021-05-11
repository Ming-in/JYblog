package com.ming.web;

import com.ming.po.Blog;
import com.ming.po.User;
import com.ming.service.BlogService;
import com.ming.service.UserService;
import com.ming.util.MarkdownUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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
    @Autowired
    private BlogService blogService;

    /**
     * 进入个人主页
     * @param id 当前登录用户id
     * @param model
     * @return 将用户数据，用户所写博客列表传入主页
     */
    @GetMapping("/about/{id}")
    public String about(@PageableDefault(size = 5, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                        @PathVariable Long id, Model model) {
        User user = userService.findById(id);
        User u = new User();
        BeanUtils.copyProperties(user, u);
        if (!"".equals(user.getIntroduction()) && user.getIntroduction() != null) {
            u.setIntroduction(MarkdownUtils.markdownToHtmlExtensions(user.getIntroduction()));
        }
        model.addAttribute("zone", u);
        model.addAttribute("page", blogService.userBlog(id, pageable, false));
        return "about";
    }

    @GetMapping(value = "/introduction")
    public String avatarUpload(HttpServletRequest request,
                               Model model) {
        request.setAttribute("introduction", true);
        User user = (User) request.getSession().getAttribute("user");
        Blog blog = new Blog();
        blog.setContent(user.getIntroduction());
        model.addAttribute("blog", blog);
        return "write-blog";
    }

    /**
     * 从个人主页进入个人介绍页面
     * @param session
     * @param blog 个人介绍中的博客
     * @return
     */
    @PostMapping(value = "/introduction")
    public String avatarUpload(HttpSession session, Blog blog) {
        User user = (User) session.getAttribute("user");
        user = userService.findById(user.getId());
        user.setIntroduction(blog.getContent());
        userService.save(user);
        user.setPassword(null);
        session.setAttribute("user", user);
        return "redirect:/about/" + user.getId();
    }

    @GetMapping("/search/{id}")
    public String search(@PageableDefault(size = 10, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                         @PathVariable Long id, Model model) {
        model.addAttribute("page", blogService.userBlog(id, pageable, true));
        return "search";
    }

    @PostMapping("/about/userblog")
    public String searchUserBlog(@PageableDefault(size = 5, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                         HttpSession session,Model model) {
        User user = (User) session.getAttribute("user");
        Page<Blog> page = blogService.userBlog(user.getId(), pageable, false);
        model.addAttribute("page",page);
        return "about :: blogTable";
    }
    @GetMapping(value = "/avatar")
    public String avatar() {
        return "uploadAvatar";
    }

    /**
     * 上传头像
     * @param file 头像base64格式
     * @param session
     * @return
     */
    @ResponseBody
    @PostMapping(value = "/uploadavatar")
    public String avatarUpload(@RequestParam String file,
                               HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "";
        }
        user = userService.findById(user.getId());
        user.setAvatar(file);
        userService.save(user);
        user.setPassword(null);
        session.setAttribute("user", user);
        return user.getId().toString();
    }

}
