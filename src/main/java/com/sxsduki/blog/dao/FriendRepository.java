package com.sxsduki.blog.dao;

import com.sxsduki.blog.pojo.Friend;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FriendRepository extends JpaRepository<Friend,Long> {

    Friend findByNickname(String nickname);
}
