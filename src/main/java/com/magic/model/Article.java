package com.magic.model;

import java.util.Date;

/**
 * @ClassName: Article
 * @Author: shenyafei
 * @Date: 2019/9/11
 * @Desc
 **/
public class Article {

    private Long id;

    private Date createTime;

    private String title;

    private String href;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

}
