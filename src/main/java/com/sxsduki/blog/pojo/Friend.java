package com.sxsduki.blog.pojo;


import javax.persistence.*;

@Entity
@Table(name = "t_friend")
public class Friend {

    @Id
    @GeneratedValue
    private Long id;


    private String nickname;

    //职业 可以从这进行判断 企业网站标注为 “企业”
    private String vocation;


    private String description;


    private String avatar;


    //链接的网址
    private String linkweb;

    public Friend() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getVocation() {
        return vocation;
    }

    public void setVocation(String vocation) {
        this.vocation = vocation;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getLinkweb() {
        return linkweb;
    }

    public void setLinkweb(String linkweb) {
        this.linkweb = linkweb;
    }

    @Override
    public String toString() {
        return "friend{" +
                "id=" + id +
                ", nickname='" + nickname + '\'' +
                ", vocation='" + vocation + '\'' +
                ", description='" + description + '\'' +
                ", avatar='" + avatar + '\'' +
                ", linkweb='" + linkweb + '\'' +
                '}';
    }
}
