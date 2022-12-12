package com.ming.service;

import com.ming.po.Comment;

import java.util.List;
/**
 * @author Ming
 */
public interface CommentService {

    List<Comment> listCommentByBlogId(Long blogId);

    Comment saveComment(Comment comment);
}
