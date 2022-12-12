package com.ming.web;

import com.ming.po.News;
import com.ming.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Ming
 */
@Controller
public class NewsController {
    @Autowired
    private NewsService newsService;

    @GetMapping("/news")
    public String index(@PageableDefault(size = 10, sort = {"createTime"}, direction = Sort.Direction.DESC)
                                Pageable pageable, Model model) {
        final byte size = 3;
        List<News> newsList = newsService.findAll(pageable).getContent();
        List<News> page = new ArrayList<>();
        List<News> pageTop = new ArrayList<>();
        if (newsList.size() >= size) {
            for (int i = 0; i < size; i++) {
                pageTop.add(newsList.get(i));
            }
        } else {
            pageTop.addAll(newsList);
        }

        for (int i = size; i < newsList.size(); i++) {
            page.add(newsList.get(i));
        }
        pageTop = pageTop.stream().filter(news -> news.getFirstPicture().contains("/")).collect(Collectors.toList());
        model.addAttribute("page", page);
        model.addAttribute("pageTop", pageTop);
        model.addAttribute("pages", newsService.findAll(pageable));
        return "news";
    }

    @GetMapping("/news/{id}")
    public String blog(@PathVariable Long id, Model model) {
        model.addAttribute("blog", newsService.getAndConvert(id));
        return "blog";
    }
}
