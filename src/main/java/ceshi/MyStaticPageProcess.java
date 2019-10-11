package ceshi;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.pipeline.JsonFilePageModelPipeline;
import us.codecraft.webmagic.pipeline.JsonFilePipeline;
import us.codecraft.webmagic.processor.PageProcessor;

import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

/**
 * @ClassName: MyProcess
 * @Author: shenyafei
 * @Date: 2019/8/7
 * @Desc
 **/
public class MyStaticPageProcess implements PageProcessor {

    private Site site = Site.me().setRetryTimes(3).setSleepTime(100);
    private static final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    // 目录页
    public static final String startPage = "http://blog.sina.com.cn/s/articlelist_1197161814_0_1.html";

    private int count = 0;

    @Override
    public void process(Page page) {
        System.out.println("page---url:"+page.getUrl()+"，resultItem:"+page.getResultItems().toString());
        String url = page.getRequest().getUrl();
        if(url.startsWith("http://blog.sina.com.cn/s/articlelist_1197161814_0_"))  // 目录页开始
        {
            // 提取目录列表
            List<String> links = new LinkedList<>();
            links.add(page.getHtml().xpath("//span[@class='atc_title']/a/@href").get());
            //links.add(page.getHtml().xpath("//span[@class='atc_title']/a/@href").all());
            links.add(page.getHtml().xpath("//span[@class='atc_title']/a/@href").get());
            System.out.println("----查询当前页符合的链接：个数：" + links.size() + ",list:"+JSON.toJSONString(links));
            page.addTargetRequests(links);
            //然后提取其他列表页地址
            List<String> otherList = page.getHtml().xpath("//ul[@class='SG_pages']/li/a/@href").all();
            System.out.println("----查询当前页符合的链接：个数：" + otherList.size() + ",list:"+JSON.toJSONString(otherList));
            links.addAll(otherList);
            page.addTargetRequests(links);
            page.setSkip(true);
        }else if(url.startsWith("http://blog.sina.com.cn/s")){
            if (count > 1) {
                System.out.println("count="+count+"，跳过！");
                return;
            }
            count++;
            //内容页
            // 获取 第一篇 李开复：母亲的十件礼物|悼文 内容 文章标题
            // 标题 //*[@id="module_928"]/div[2]/div[1]/div[2]/div[1]/p[1]/span[2]/a    //*[@id="module_928"]/div[2]/div[1]/div[2]/div[3]/p[1]/span[2]/a
            String title = page.getHtml().xpath("//h1[@class='h1_tit']/text()").get();
            // 内容
            String content = page.getHtml().xpath("//div[@class='BNE_cont']/allText()").get();
            // 时间
            String timeAndSource = page.getHtml().xpath("//span[@class='time SG_txtc']/text()").get();


//			// 阅读数
//			String readingAmount = page.getHtml().xpath("//div[@class='BNE_txtA OL']/span[1]/text()").get();
//			//评论数
//			String comment = page.getHtml().xpath("//div[@class='BNE_txtA OL']/span[2]/text()").get();


            if(title==null && content == null ) // 说明不是第一篇 李开复：母亲的十件礼物|悼文 ，需要重新 get 内容
            {
                title = page.getHtml().xpath("//div[@class='articalTitle']/h2/text()").get();
                content = page.getHtml().xpath("//div[@id='sina_keyword_ad_area2']/allText()").get();
                if (StringUtils.isNotEmpty(content) && content.length() > 10) {
                    content = content.substring(0,20);
                }
                // 抓取样式 ： ((2018-01-26 18:24:31)
                timeAndSource = page.getHtml().regex("\\d{4}-\\d{2}-\\d{2}\\s\\d+.\\d+.\\d+").toString();


            }// 最终保证   不为空
            System.out.println("----标题：" + title);
            page.putField("CONTENT",content);               //置入 文章内容
            page.putField("TITLE",title);                   // 置入文章标题
            page.putField("URL", url);                      // 存入 url
            page.putField("PUBTIME",timeAndSource);     // 置入文章标题
        }
        System.out.println("每次==rawTest:"+(page.getRawText() != null ? page.getRawText().substring(0,20):"") + page.getResultItems().toString());
    }

    @Override
    public Site getSite() {
        return site;
    }
    public static void main(String[] args){
        System.out.println("-------开始抓取--------");
        Spider.create(new MyStaticPageProcess())
                .addUrl(startPage)
                .addPipeline(new JsonFilePipeline("D:\\www\\page"))
                .thread(5)
                .run();
        System.out.println("-------结束抓取--------");
    }
}
