package com.sxsduki.blog.service;

import com.sxsduki.blog.pojo.User;

/**
 *
 */
public interface UserService {

    User checkUser(String username,String password);
}
