package com.sxsduki.blog.dao;

import com.sxsduki.blog.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 */
public interface UserRepository extends JpaRepository<User,Long> {

    User findByUsernameAndPassword(String username,String password);


}
