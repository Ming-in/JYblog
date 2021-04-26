package com.ming.dao;

import com.ming.po.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author Ming
 * 创建时间：2021/4/25 22:14
 */
public interface NewsRepository extends JpaRepository<News, Long>, JpaSpecificationExecutor<News> {

}
