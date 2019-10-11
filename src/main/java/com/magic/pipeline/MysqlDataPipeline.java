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
public class MysqlDataPipeline implements Pipeline {

    @Resource
    private ArticleDao articleDao;

    @Override
    public void process(ResultItems resultItems, Task task) {
        if (resultItems.isSkip()) {
            return;
        }
        Article article = new Article();
        article.setCreateTime(new Date());
        article.setTitle(resultItems.get("title"));
        article.setHref(resultItems.get("href"));
        articleDao.add(article);
    }
}
