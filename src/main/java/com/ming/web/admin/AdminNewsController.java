package com.ming.web.admin;

import com.ming.po.Blog;
import com.ming.po.News;
import com.ming.po.User;
import com.ming.service.NewsService;
import com.ming.vo.BlogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Ming
 */
@Controller
@RequestMapping("/admin")
public class AdminNewsController {
    @Autowired
    private NewsService newsService;

    @GetMapping("/news")
    public String news(@PageableDefault(size = 10, sort = {"createTime"}, direction = Sort.Direction.DESC)
                               Pageable pageable, Model model) {
        Page<News> newsPage = newsService.findAll(pageable);
        model.addAttribute("page", newsPage);
        return "admin/news";
    }
    @GetMapping("/news/input")
    public String input(Model model) {
        model.addAttribute("news", new News());
        return "admin/news-input";
    }

    @PostMapping("/news")
    public String post(News news, RedirectAttributes attributes, HttpSession session) {
        News b;
        if (news.getId() == null) {
            b = newsService.saveNews(news);
        } else {
            b = newsService.updateNews(news.getId(), news);
        }
        if (b == null) {
            attributes.addFlashAttribute("message", "操作失败");
        } else {
            attributes.addFlashAttribute("message", "操作成功");
        }
        return "redirect:/admin/news";
    }

    @GetMapping("/news/{id}/input")
    public String editInput(@PathVariable Long id, Model model) {
        News news = newsService.findById(id);
        model.addAttribute("news", news);
        return "admin/news-input";
    }

    @GetMapping("/news/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes attributes) {
        newsService.deleteNews(id);
        attributes.addFlashAttribute("message", "删除成功");
        return "redirect:/admin/news";
    }

}
