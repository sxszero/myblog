package com.sxsduki.blog.service;

import com.sxsduki.blog.pojo.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class BlogServiceImpl implements BlogService {
    @Override
    public Blog getBlog(Long id) {
        return null;
    }

    @Override
    public Page<Blog> listBlog(Pageable pageable, Blog blog) {
        return null;
    }

    @Override
    public Blog saveBlog(Blog blog) {
        return null;
    }

    @Override
    public Blog updateBlog(Long id, Blog blog) {
        return null;
    }

    @Override
    public void deleteBlog(Long id) {

    }
}
