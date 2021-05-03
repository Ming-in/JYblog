package com.ming.web;

import com.ming.po.Blog;
import com.ming.po.User;
import com.ming.service.BlogService;
import com.ming.service.UserService;
import com.ming.util.FileUtils;
import com.ming.util.MarkdownUtils;
import com.ming.util.MimeTypeUtils;
import com.ming.util.UploadUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;

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

    @GetMapping(value = "/introduction")
    public String avatarUpload(HttpServletRequest request,
                               Model model) {
        request.setAttribute("introduction",true);
        User user = (User) request.getSession().getAttribute("user");
        Blog blog = new Blog();
        blog.setContent(user.getIntroduction());
        model.addAttribute("blog", blog);
        return "write-blog";
    }

    @PostMapping(value = "/introduction")
    public String avatarUpload(HttpSession session,Blog blog) {
        User user = (User) session.getAttribute("user");
        user = userService.findById(user.getId());
        user.setIntroduction(blog.getContent());
        userService.save(user);
        user.setPassword(null);
        session.setAttribute("user",user);
        return "redirect:/about/"+user.getId();
    }

    @GetMapping("/search/{id}")
    public String search(@PageableDefault(size = 10, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                         @PathVariable Long id, Model model) {
        model.addAttribute("page", blogService.userBlog(id,pageable));
        return "search";
    }

    @PostMapping(value = "/file/avatar")
    public String avatarUpload(@RequestPart(value = "avatarFile") MultipartFile avatarFile,
                               HttpSession session,
                               Model model) {
        User user = (User) session.getAttribute("user");
        //获取文件后缀
        final String fileSuffix = FileUtils.getExtension(avatarFile);
        String path = "";
        //判断是否为有效图片
        if (isAllowedExtension(fileSuffix, MimeTypeUtils.IMAGE_EXTENSION)) {
            path = "/resources/static/images/avatar/";
        } else {
            //上传失败
            model.addAttribute("message", "头像上传失败");
            return "about";
        }
        //生成加密字符
        String str = UploadUtils.aesEncrypt(user.getUsername());
        path = path + str + File.separator;
        String fileName = FileUtils.upload(avatarFile, path);
        model.addAttribute("message", "头像上传成功");
        model.addAttribute("filename", fileName);
        return "about";
    }

    @ResponseBody
    @PostMapping(value = "/avatar")
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

    /**
     * 判断是否是允许的文件类型
     *
     * @param extension        文件后缀
     * @param allowedExtension MIME类型
     */
    private static boolean isAllowedExtension(String extension, String[] allowedExtension) {
        for (String str : allowedExtension) {
            if (str.equalsIgnoreCase(extension)) {
                return true;
            }
        }
        return false;
    }


}
