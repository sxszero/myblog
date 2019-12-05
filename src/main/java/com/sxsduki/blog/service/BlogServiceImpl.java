package com.sxsduki.blog.service;

import com.sxsduki.blog.NotFoundException;
import com.sxsduki.blog.dao.BlogRepository;
import com.sxsduki.blog.pojo.Blog;
import com.sxsduki.blog.pojo.BlogQuery;
import com.sxsduki.blog.pojo.Type;
import com.sxsduki.blog.utils.MarkDownUtils;
import com.sxsduki.blog.utils.MyBeanUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.util.*;

@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    private BlogRepository blogRepository;

    @Transactional
    @Override
    public Blog getBlog(Long id) {
        return blogRepository.getOne(id);
    }

    /**
     * 把blog的内容转换成html格式
     * @param id
     * @return
     */
    @Transactional
    @Override
    public Blog getAndConvert(Long id) {

        Blog blog = blogRepository.getOne(id);
        if (blog == null){
            throw new NotFoundException("该博客不存在");
        }
        //为了保持原有数据库内容不改变 新建一个blog对象来临时保存信息 避免content变为html格式存入数据库
        Blog b =new Blog();
        BeanUtils.copyProperties(blog,b);
        String content = b.getContent();
        b.setContent(MarkDownUtils.markdownToHtmlExtensions(content));

        //观看次数
        blogRepository.updateViews(id);

        return b;
    }

    /**
     * blogs页面调用 用于查询博客
     * @param pageable
     * @param blog
     * @return
     */
    @Transactional
    @Override
    public Page<Blog> listBlog(Pageable pageable, BlogQuery blog) {
        return blogRepository.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {

                List<Predicate> predicates = new ArrayList<>();
                //标题查询
                if(!"".equals(blog.getTitle()) && blog.getTitle() !=null){
                    predicates.add(cb.like(root.<String>get("title"),"%"+blog.getTitle()+"%"));
                }

                //分类查询
                if (blog.getTypeId() != null){
                    predicates.add(cb.equal(root.<Type>get("type").get("id"),blog.getTypeId()));
                }

                if(blog.isRecommend()){
                    predicates.add(cb.equal(root.<Boolean>get("recommend"),blog.isRecommend()));

                }
                cq.where(predicates.toArray(new Predicate[predicates.size()]));
                return null;
            }
        },pageable) ;
    }

    /**
     * 首页调用 分页使用
     * @param pageable
     * @return
     */

    @Transactional
    @Override
    public Page<Blog> listBlog(Pageable pageable) {
        return blogRepository.findAll(pageable);
    }

    //tags和blogs关联表的查询
    @Override
    public Page<Blog> listBlog(Long tagId, Pageable pageable) {

        return blogRepository.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                //构建关联对象
                Join join =root.join("tags");
                //"id"为关联表中tag的id  和传过来的tagId是否相同
                return cb.equal(join.get("id"),tagId);
            }
        },pageable);
    }

    @Override
    public Page<Blog> listBlog(String query, Pageable pageable) {
        return blogRepository.findByQuery(query,pageable);
    }

    /**
     * 首页展示推荐的博客的条数
     * @param size
     * @return
     */
    @Override
    public List<Blog> listRecommendBlogTop(Integer size) {
        Sort sort = new Sort(Sort.Direction.DESC, "updateTime");
        Pageable pageable = PageRequest.of(0,size,sort);
        return blogRepository.findTop(pageable);
    }

    @Override
    public Map<String, List<Blog>> archiveBlog() {
        List<String> years =blogRepository.findGroupYear();
        Map<String,List<Blog>> map = new HashMap<>();
        for (String year : years){
            map.put(year,blogRepository.findByYear(year));

        }

        return map;
    }


    @Override
    public Long countBlog() {
        return blogRepository.count();
    }

    @Transactional
    @Override
    public Blog saveBlog(Blog blog) {
        //新增博客初始化
        if (blog.getId() == null){
            blog.setCreateTime(new Date());
            blog.setUpdateTime(new Date());
            blog.setViews(0);
        }else {
            //修改博客
            blog.setUpdateTime(new Date());
        }


        return blogRepository.save(blog);
    }
    @Transactional
    @Override
    public Blog updateBlog(Long id, Blog blog) {
        Blog b = blogRepository.getOne(id);
        if(b == null){
            throw new NotFoundException("该博客不存在");
        }

        //MyBeanUtils.getNullPropertyNames(blog)过滤掉blog表的空的属性值
        BeanUtils.copyProperties(blog,b, MyBeanUtils.getNullPropertyNames(blog));
        b.setUpdateTime(new Date());
        return blogRepository.save(b);
    }
    @Transactional
    @Override
    public void deleteBlog(Long id) {
        blogRepository.deleteById(id);
    }
}
