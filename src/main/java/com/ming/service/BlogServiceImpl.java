package com.ming.service;

import com.ming.NotFoundException;
import com.ming.dao.BlogRepository;
import com.ming.dao.UserRepository;
import com.ming.po.Blog;
import com.ming.po.Type;
import com.ming.po.User;
import com.ming.util.MarkdownUtils;
import com.ming.util.MyBeanUtils;
import com.ming.vo.BlogQuery;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.util.*;

/**
 * @author 邹明
 */
@Service
public class BlogServiceImpl implements BlogService {


    @Autowired
    private BlogRepository blogRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public Blog getBlog(Long id) {
        return blogRepository.findOne(id);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Blog getAndConvert(Long id) {
        Blog blog = blogRepository.findOne(id);
        if (blog == null) {
            throw new NotFoundException("该博客不存在");
        }
        Blog b = new Blog();
        BeanUtils.copyProperties(blog, b);
        String content = b.getContent();
        b.setContent(MarkdownUtils.markdownToHtmlExtensions(content));
        blogRepository.updateViews(id);
        return b;
    }


    @Override
    public Page<Blog> listBlog(Pageable pageable, BlogQuery blog, Boolean filterPublished) {
        return blogRepository.findAll((root, cq, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (!"".equals(blog.getTitle()) && blog.getTitle() != null) {
                predicates.add(cb.like(root.get("title"), "%" + blog.getTitle() + "%"));
            }
            if (blog.getTypeId() != null) {
                predicates.add(cb.equal(root.<Type>get("type").get("id"), blog.getTypeId()));
            }
            if (blog.isRecommend()) {
                predicates.add(cb.equal(root.<Boolean>get("recommend"), blog.isRecommend()));
            }
            if (filterPublished) {
                predicates.add(cb.equal(root.get("published"), true));
            }
            cq.where(predicates.toArray(new Predicate[predicates.size()]));
            return null;
        }, pageable);
    }

    @Override
    public Page<Blog> listBlog(Pageable pageable, Boolean filterPublished) {
        return blogRepository.findAll((root, cq, cb) ->
                filterPublished ? cb.equal(root.get("published"), true) : null, pageable);
    }

    /**
     * 依据用户ID查询用户下所有博客
     * @param id    用户ID
     * @param pageable  分页
     * @param filterPublished   是否过滤草稿
     * @return
     */
    @Override
    public Page<Blog> userBlog(Long id, Pageable pageable, Boolean filterPublished) {
        User user = userRepository.findOne(id);
        return blogRepository.findAll((root, cq, cb) -> {
            if (filterPublished) {
                return cb.and(cb.equal(root.get("user"), user), cb.equal(root.get("published"), true));
            } else {
                return cb.equal(root.get("user"), user);
            }
        }, pageable);
    }

    @Override
    public Page<Blog> listBlog(Long tagId, Pageable pageable, Boolean filterPublished) {
        return blogRepository.findAll((root, cq, cb) -> {
            Join join = root.join("tags");
            Predicate p1 = cb.equal(join.get("id"), tagId);
            Predicate p2 = cb.equal(root.get("published"), true);
            return cb.and(p1, p2);
        }, pageable);
    }

    @Override
    public Page<Blog> listBlog(String query, Pageable pageable, Boolean filterPublished) {
        return blogRepository.findByQuery(query, pageable);
    }

    @Override
    public List<Blog> listRecommendBlogTop(Integer size) {
        Sort sort = new Sort(Sort.Direction.DESC, "updateTime");
        Pageable pageable = new PageRequest(0, size, sort);
        return blogRepository.findTop(pageable);
    }

    @Override
    public Map<String, List<Blog>> archiveBlog() {
        List<String> years = blogRepository.findGroupYear();
        Map<String, List<Blog>> map = new HashMap<>();
        for (String year : years) {
            map.put(year, blogRepository.findByYear(year));
        }
        return map;
    }

    @Override
    public Long countBlog() {
        return blogRepository.count();
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public Blog saveBlog(Blog blog) {
        if (blog.getId() == null) {
            blog.setCreateTime(new Date());
            blog.setUpdateTime(new Date());
            blog.setViews(0);
        } else {
            blog.setUpdateTime(new Date());
        }
        return blogRepository.save(blog);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Blog updateBlog(Long id, Blog blog) {
        Blog b = blogRepository.findOne(id);
        if (b == null) {
            throw new NotFoundException("该博客不存在");
        }
        BeanUtils.copyProperties(blog, b, MyBeanUtils.getNullPropertyNames(blog));
        b.setUpdateTime(new Date());
        return blogRepository.save(b);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteBlog(Long id) {
        blogRepository.delete(id);
    }
}
