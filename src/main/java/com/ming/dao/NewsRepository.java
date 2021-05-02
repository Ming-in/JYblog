package com.ming.dao;

import com.ming.po.News;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Ming
 * 创建时间：2021/4/25 22:14
 */
public interface NewsRepository extends JpaRepository<News, Long>, JpaSpecificationExecutor<News> {


    @Query("select n from News n")
    List<News> findTop(Pageable pageable);

    @Transactional
    @Modifying
    @Query("update News n set n.views = n.views+1 where n.id = ?1")
    int updateViews(Long id);
}
