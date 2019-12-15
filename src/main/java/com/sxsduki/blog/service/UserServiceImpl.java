package com.sxsduki.blog.service;

import com.sxsduki.blog.dao.UserRepository;
import com.sxsduki.blog.pojo.User;
import com.sxsduki.blog.utils.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {


    @Autowired
    private UserRepository userRepository;
    @Override
    public User checkUser(String username, String password) {

        User user = userRepository.findByUsernameAndPassword(username, MD5Utils.code(password));

        return user;
    }

    @Transactional
    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Transactional
    @Override
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
