package com.sxsduki.blog.service;

import com.sxsduki.blog.NotFoundException;
import com.sxsduki.blog.dao.FriendRepository;
import com.sxsduki.blog.pojo.Friend;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FriendServiceImpl implements FriendService {


    @Autowired
    private FriendRepository friendRepository;

    @Transactional
    @Override
    public Friend saveFriend(Friend friend) {
        return friendRepository.save(friend);
    }

    @Transactional
    @Override
    public void deleteFriend(Long id) {
        friendRepository.deleteById(id);
    }
    @Transactional
    @Override
    public Friend updateFriend(Long id, Friend friend) {
        Friend f = friendRepository.getOne(id);
        if (f == null){
            throw new NotFoundException("这条友链不存在");
        }

        BeanUtils.copyProperties(friend,f);
        return friendRepository.save(f);
    }
    @Transactional
    @Override
    public Friend getFriend(Long id) {
        return friendRepository.getOne(id);
    }
    @Transactional
    @Override
    public Friend getFriendByName(String nickniame) {
        return friendRepository.findByNickname(nickniame);
    }
    @Transactional
    @Override
    public Page<Friend> listFriend(Pageable pageable) {
        return friendRepository.findAll(pageable);
    }
    @Transactional
    @Override
    public List<Friend> listFriend() {
        return friendRepository.findAll();
    }
}
