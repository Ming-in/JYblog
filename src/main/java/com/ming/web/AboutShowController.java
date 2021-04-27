package com.ming.web;

import com.ming.po.User;
import com.ming.service.UserService;
import com.ming.util.FileUtils;
import com.ming.util.MarkdownUtils;
import com.ming.util.MimeTypeUtils;
import com.ming.util.UploadUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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


    @PostMapping(value = "/file/avatar")
    public String avatarUpload(@RequestPart(value = "avatar_file") MultipartFile avatar_file,
                               HttpSession session,
                               Model model) {
        User user = (User) session.getAttribute("user");
        //获取文件后缀
        final String fileSuffix = FileUtils.getExtension(avatar_file);
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
        String fileName = FileUtils.upload(avatar_file, path);
        model.addAttribute("message", "头像上传成功");
        model.addAttribute("filename", fileName);
        return "about";
    }

    @PostMapping(value = "/avatar")
    public void avatarUpload(@RequestParam String file,
                             HttpSession session,
                             Model model) {
        System.out.println(file);
        User user = (User) session.getAttribute("user");
        user = userService.findById(user.getId());
        user.setAvatar(file);
        userService.save(user);
        user.setPassword(null);
        session.setAttribute("user", user);
        model.addAttribute("message", "头像上传成功");
//        model.addAttribute("filename", fileName);
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
