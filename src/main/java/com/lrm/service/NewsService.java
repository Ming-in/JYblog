package com.lrm.service;

import com.lrm.po.News;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author Ming
 * 创建时间：2021/4/25 22:15
 */

public interface NewsService {
    Page<News> findAll(Pageable pageable);
}
