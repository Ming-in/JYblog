package com.ming.service;

import com.ming.po.Blog;
import com.ming.po.News;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author Ming
 * 创建时间：2021/4/25 22:15
 */

public interface NewsService {
    Page<News> findAll(Pageable pageable);

    List<News> findTop(int size);

    Blog getAndConvert(Long id);
}
