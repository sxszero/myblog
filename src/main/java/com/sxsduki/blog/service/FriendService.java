package com.sxsduki.blog.service;

import com.sxsduki.blog.pojo.Friend;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface FriendService {

    Friend saveFriend(Friend friend);

    void deleteFriend(Long id);

    Friend updateFriend(Long id,Friend friend);

    Friend getFriend(Long id);

    Friend getFriendByName(String nickniame);

    Page<Friend> listFriend(Pageable pageable);

    List<Friend> listFriend();




}
