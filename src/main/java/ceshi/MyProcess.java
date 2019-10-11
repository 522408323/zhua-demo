package ceshi;

import com.alibaba.fastjson.JSON;
import com.magic.pipeline.MysqlDataPipeline;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.downloader.Downloader;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.pipeline.JsonFilePipeline;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.List;

/**
 * @ClassName: MyProcess
 * @Author: shenyafei
 * @Date: 2019/8/7
 * @Desc
 **/
public class MyProcess implements PageProcessor {
    /***
     * 抓取网站相关配置，重试次数，抓取间隔时间
     */
    private Site site = Site.me().setRetryTimes(3).setSleepTime(100);

    @Override
    public void process(Page page) {
        System.out.println("page--------------------url："+ page.getUrl());
        //判断链接是否符合规则：http://www.cnblogs.com/任意个数字字母-/p/7个数字.html格式
        if (!page.getUrl().regex("https://www.cnblogs.com/justcooooode/p/[0-9]{7}.html").match()) {
            //List<String> allUrlsList = page.getHtml().links().regex("https://www.cnblogs.com/justcooooode/[\\W\\w]*").all();
            //System.out.println("=====allUrlsList："+ JSON.toJSONString(allUrlsList));
            List<String> list = page.getHtml().xpath("//div[@class=\"postTitle\"]/a/@href").all();
            System.out.println("=====list："+ JSON.toJSONString(list));
            List<String> otherList = page.getHtml().xpath("//div[@id=\"nav_next_page\"]/a/@href").all();
            System.out.println("=====otherList："+ JSON.toJSONString(otherList));
            //list.addAll(otherList);
            page.addTargetRequests(list);
            page.setSkip(true);
        } else {
            String title = page.getHtml().xpath("//*[@id=\"cb_post_title_url\"]/text()").toString();
            String href = page.getHtml().xpath("//*[@id=\"cb_post_title_url\"]/@href").toString();
            page.putField("title", title);
            page.putField("href", href);
        }
    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args){
        System.out.println("开始爬取...");
        //MyProcess是实现PageProcessor类的子类
        Spider.create(new MyProcess())
                //从哪个url开始抓取信息
                .addUrl("http://www.cnblogs.com/justcooooode/")
                //设置Pipeline，结果会打印到控制台上
                .addPipeline(new ConsolePipeline())
                //.addPipeline(new JsonFilePipeline("D:\\www\\cnblogs"))
                //开启3个线程同时执行
                .thread(3)
                //启动爬虫
                .run();
        System.out.println("爬取结束。。。");
    }
}
