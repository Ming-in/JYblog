package com.ming.service;

import com.ming.NotFoundException;
import com.ming.dao.NewsRepository;
import com.ming.po.Blog;
import com.ming.po.News;
import com.ming.util.MarkdownUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * @author Ming
 * 创建时间：2021/4/25 22:16
 */
@Service
public class NewsServiceImpl implements NewsService {
    @Autowired
    private NewsRepository newsRepository;

    @Override
    public Page<News> findAll(Pageable pageable){
        return newsRepository.findAll(pageable);
    }

    @Override
    public List<News> findTop(int size) {
        Sort sort = new Sort(Sort.Direction.DESC, "createTime");
        Pageable pageable = new PageRequest(0, size, sort);
        return newsRepository.findTop(pageable);
    }
    @Transactional
    @Override
    public Blog getAndConvert(Long id) {
        News news = newsRepository.findOne(id);
        if (news == null) {
            throw new NotFoundException("新闻可能已被删除！");
        }
        Blog b = new Blog();
        BeanUtils.copyProperties(news, b);
        String content = b.getContent();
        b.setContent(MarkdownUtils.markdownToHtmlExtensions(content));
        newsRepository.updateViews(id);
        return b;
    }


}
