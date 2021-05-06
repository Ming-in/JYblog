package com.ming.service;

import com.ming.po.Blog;
import com.ming.vo.BlogQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
/**
 * @author 邹明
 */
public interface BlogService {

    Blog getBlog(Long id);

    Blog getAndConvert(Long id);

    Page<Blog> listBlog(Pageable pageable,BlogQuery blog,Boolean filterPublished);

    Page<Blog> listBlog(Pageable pageable,Boolean filterPublished);

    Page<Blog> listBlog(Long tagId,Pageable pageable,Boolean filterPublished);

    Page<Blog> listBlog(String query,Pageable pageable,Boolean filterPublished);

    List<Blog> listRecommendBlogTop(Integer size);

    Map<String,List<Blog>> archiveBlog();

    Page<Blog> userBlog(Long id,Pageable pageable, Boolean filterPublished);

    Long countBlog();

    Blog saveBlog(Blog blog);

    Blog updateBlog(Long id,Blog blog);

    void deleteBlog(Long id);
}
