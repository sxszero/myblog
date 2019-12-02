package com.sxsduki.blog.service;


import com.sxsduki.blog.pojo.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * tag标签页面的业务逻辑层
 */

public interface TagService {

    Tag saveTage(Tag tag);

    void deleteTage(Long id);

    Tag updateTage(Long id,Tag tag);

    Tag getTag(Long id);

    Tag getTageByName(String name);

    //分页 springboot的处理
    Page<Tag> listTag(Pageable pageable);

    List<Tag> listTag();

    List<Tag> listTag(String ids);

    List<Tag> listTagTop(Integer size);



}
