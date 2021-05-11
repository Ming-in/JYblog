package com.ming.web;

import com.ming.po.Blog;
import com.ming.po.User;
import com.ming.service.BlogService;
import com.ming.service.TagService;
import com.ming.service.TypeService;
import com.ming.util.FileUtils;
import com.ming.util.MimeTypeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 邹明
 */
@Controller
public class BlogController {

    @Autowired
    private BlogService blogService;
    @Autowired
    private TypeService typeService;
    @Autowired
    private TagService tagService;
    @Value("${files.blogImgs}")
    private String blogImg;
    @Value("${server.port}")
    private String port;


    private void setTypeAndTag(Model model) {
        model.addAttribute("types", typeService.listType());
        model.addAttribute("tags", tagService.listTag());
    }

    @PostMapping("/blogs")
    public String post(Blog blog, RedirectAttributes attributes, HttpSession session) {
        User user = (User) session.getAttribute("user");
        blog.setUser(user);
        blog.setType(typeService.getType(blog.getType().getId()));
        blog.setTags(tagService.listTag(blog.getTagIds()));
        if ("".equals(blog.getFlag()) || blog.getFlag() == null) {
            blog.setFlag("原创");
        }
        Blog b;
        if (blog.getId() == null) {
            b = blogService.saveBlog(blog);
        } else {
            b = blogService.updateBlog(blog.getId(), blog);
        }
        if (b == null) {
            attributes.addFlashAttribute("message", "操作失败");
        } else {
            attributes.addFlashAttribute("message", "操作成功");
        }
        return "redirect:/about/"+user.getId();
    }

    /**
     * 用户编辑博客
     * @param id 博客id
     * @param model
     * @return
     */
    @GetMapping("/blogs/{id}/input")
    public String editInput(@PathVariable Long id, Model model) {
        setTypeAndTag(model);
        Blog blog = blogService.getBlog(id);
        blog.init();
        model.addAttribute("blog", blog);
        return "write-blog";
    }

    /**
     * 用户删除博客
     * @param id 博客id
     * @param attributes
     * @return
     */
    @GetMapping("/blogs/{id}/delete")
    public String delete(@PathVariable Long id,RedirectAttributes attributes,HttpSession session) {
        User user = (User) session.getAttribute("user");
        blogService.deleteBlog(id);
        attributes.addFlashAttribute("message", "删除成功");
        return "redirect:/about/"+user.getId();
    }

    @GetMapping("/writeblog")
    public String input(Model model) {
        setTypeAndTag(model);
        model.addAttribute("blog", new Blog());
        return "write-blog";
    }

    @ResponseBody
    @PostMapping(value = "/file/imgupload")
    public Map<String, Object> imgUpload(@RequestParam(value = "editormd-image-file") MultipartFile file,
                                         HttpSession session) {
        Map<String, Object> map = new HashMap<>();
        User user = (User) session.getAttribute("user");
        //获取文件后缀
        final String fileSuffix = FileUtils.getExtension(file);
        String path = "";
        //判断是否为有效图片
        if (isAllowedExtension(fileSuffix, MimeTypeUtils.IMAGE_EXTENSION)) {
            path = blogImg;
        } else {
            //上传失败
            map.put("error", "error");
            map.put("message", "\n图片上传失败，不支持的图片格式。\n\n请上传bmp, gif, jpg, jpeg, png格式图片");
            return map;
        }
        //生成加密字符
//        String str = UploadUtils.aesEncrypt(user.getUsername());
        String str = user.getUsername();
        path = path + str + "/";
        String fileName = FileUtils.upload(file, path);
        String realPath = "http://localhost:"+port+"/blogImgs/"+str+"/"+fileName;
        map.put("success", 1);
        map.put("url", realPath);
        return map;
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
