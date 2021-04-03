package com.lrm.service;

import com.lrm.po.Comment;

import java.util.List;
/**
 * @author 邹明
 */
public interface CommentService {

    List<Comment> listCommentByBlogId(Long blogId);

    Comment saveComment(Comment comment);
}
