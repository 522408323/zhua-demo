package com.magic.dao;

import com.magic.model.Article;
import org.apache.ibatis.annotations.Insert;

/**
 * @ClassName: ArticleDao
 * @Author: shenyafei
 * @Date: 2019/9/11
 * @Desc
 **/
public interface ArticleDao {

    @Insert("insert into t_article (`title`,`href`,`create_time`) values (#{title},#{href},#{createTime})")
    public int add(Article article);
}
