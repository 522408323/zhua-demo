package com.magic.pipeline;

import com.magic.dao.ArticleDao;
import com.magic.model.Article;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.PageModelPipeline;
import us.codecraft.webmagic.pipeline.Pipeline;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @ClassName: MysqlDataPipeline
 * @Author: shenyafei
 * @Date: 2019/9/11
 * @Desc
 **/
@Component
public class NewMysqlDataPipeline implements PageModelPipeline<Article> {

    @Resource
    private ArticleDao articleDao;

    @Override
    public void process(Article article, Task task) {

    }
}
