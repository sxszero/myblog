package com.sxsduki.blog.service;

import com.sxsduki.blog.NotFoundException;
import com.sxsduki.blog.dao.TagRepository;
import com.sxsduki.blog.pojo.Tag;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private TagRepository tagRepository;


    @Transactional
    @Override
    public Tag saveTage(Tag tag) {
        return tagRepository.save(tag);
    }

    @Transactional
    @Override
    public void deleteTage(Long id) {
        tagRepository.deleteById(id);
    }

    @Transactional
    @Override
    public Tag updateTage(Long id, Tag tag) {
        Tag t = tagRepository.getOne(id);
        if (t == null){
            throw new NotFoundException("该标签不存在");
        }
        BeanUtils.copyProperties(tag,t);
        return tagRepository.save(t);
    }

    @Transactional
    @Override
    public Tag getTag(Long id) {
        return tagRepository.getOne(id);
    }

    @Transactional
    @Override
    public Tag getTageByName(String name) {
        return tagRepository.findByName(name);
    }

    @Transactional
    @Override
    public Page<Tag> listTag(Pageable pageable) {
        return tagRepository.findAll(pageable);
    }

    @Transactional
    @Override
    public List<Tag> listTag() {
        return tagRepository.findAll();
    }


    /**
     *
     * @param ids 处理前端页面blogs-input传来的 1,2,3这种格式的 ids
     * @return 一组的tag对象 1 2 3去掉逗号
     */
    @Transactional
    @Override
    public List<Tag> listTag(String ids) {//1,2,3这种格式的 ids

        List<Tag> list = new ArrayList<>();
        if(!"".equals(ids) && ids != null){
            String[] idarray = ids.split(",");
            for (int i = 0;i<idarray.length;i++){
                long tempId = Long.parseLong(idarray[i]);
                list.add(tagRepository.getOne(tempId));
            }

        }

        return list;
    }

    @Transactional
    @Override
    public List<Tag> listTagTop(Integer size) {

        Sort sort = new Sort(Sort.Direction.DESC, "blogs.size");
        Pageable pageable = PageRequest.of(0, size, sort);

        return tagRepository.findTop(pageable);
    }
}
