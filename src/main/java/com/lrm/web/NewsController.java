package com.lrm.web;

import com.lrm.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author 邹明
 */
@Controller
public class NewsController {
    @Autowired
    private NewsService newsService;

    @GetMapping("/news")
    public String index(@PageableDefault(size = 10, sort = {"createTime"}, direction = Sort.Direction.DESC)
                                    Pageable pageable, Model model) {
        model.addAttribute("page", newsService.findAll(pageable));
        return "news";
    }

}
