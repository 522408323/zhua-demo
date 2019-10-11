package com.magic;

import com.alibaba.fastjson.JSON;
import com.magic.pipeline.MysqlDataPipeline;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.List;

/**
 * @ClassName: MyProcess
 * @Author: shenyafei
 * @Date: 2019/8/7
 * @Desc
 **/
@Component
public class MySqlProcess implements PageProcessor {

    private Site site = Site.me().setRetryTimes(3).setSleepTime(100);

    @Autowired
    private MysqlDataPipeline  mysqlDataPipeline;

    @Override
    public void process(Page page) {
        System.out.println("======url===="+ page.getUrl());
        //判断链接是否符合规则：http://www.cnblogs.com/任意个数字字母-/p/7个数字.html格式
        if (!page.getUrl().regex("https://www.cnblogs.com/justcooooode/p/[0-9]{7}.html").match()) {
            List<String> list = page.getHtml().xpath("//div[@class=\"postTitle\"]/a/@href").all();
            System.out.println("======list："+ JSON.toJSONString(list));
            List<String> otherList = page.getHtml().xpath("//div[@id=\"nav_next_page\"]/a/@href").all();
            System.out.println("======otherList："+ JSON.toJSONString(otherList));
            list.addAll(otherList);
            //list.addAll(list);
            System.out.println("======total-list："+ JSON.toJSONString(list));
            page.addTargetRequests(list);
            page.setSkip(true);
        } else {
            String title = page.getHtml().xpath("//*[@id=\"cb_post_title_url\"]/text()").toString();
            String href = page.getHtml().xpath("//*[@id=\"cb_post_title_url\"]/@href").toString();
            page.putField("title", title);
            page.putField("href", href);
            System.out.println(title+"=========="+href);
        }
    }

    @Override
    public Site getSite() {
        return site;
    }

    public void execute(){
        System.out.println("开始爬取...");
        Spider.create(new MySqlProcess())
                //从哪个url开始抓取信息
                .addUrl("http://www.cnblogs.com/justcooooode/")
                //设置Pipeline，结果会打印到控制台上
                .addPipeline(mysqlDataPipeline)
                //开启3个线程同时执行
                .thread(3)
                //启动爬虫
                .run();
        System.out.println("爬取结束...");
    }

    public static void main(String[] args){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:/spring/applicationContext*.xml");
        final MySqlProcess process = applicationContext.getBean(MySqlProcess.class);
        process.execute();
    }
}
