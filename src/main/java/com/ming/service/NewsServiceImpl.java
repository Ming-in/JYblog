package com.ming.service;

import com.ming.dao.NewsRepository;
import com.ming.po.News;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


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
}
