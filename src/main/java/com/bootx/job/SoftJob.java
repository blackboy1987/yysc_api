package com.bootx.job;

import com.bootx.entity.Category;
import com.bootx.entity.Soft;
import com.bootx.entity.SoftImage;
import com.bootx.entity.SoftInfo;
import com.bootx.service.*;
import jakarta.annotation.Resource;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Component
public class SoftJob {

    @Resource
    private SoftService softService;

    @Resource
    private CategoryService categoryService;

    @Resource
    private SoftImageService softImageService;

    @Resource
    private SoftInfoService softInfoService;

    @Resource
    private RedisService redisService;

    Random random = new Random();

    @Scheduled(fixedRate = 1000*60*30)
    public void run(){
        List<Category> categories = categoryService.findAll();
        for (Category category : categories) {

            String url = "https://m.shouji.com.cn"+category.getUrl();
            Integer page = 1;
            try {
                String s = redisService.get(category.getId() + "");
                page = Integer.valueOf(s);
            }catch (Exception e){
                page = 1;
            }
            for (int i = page; i <= 300; i++) {
                String url1 = url.replace("_1", "_"+i);
                try {
                    Document parse = Jsoup.parse(new URL(url1).openStream(), "utf-8", url);
                    Element game_list_top = parse.getElementById("game_list_top");
                    Elements li = game_list_top.getElementsByTag("li");
                    redisService.set(category.getId()+"",i+"");
                    li.forEach(item->{
                        Element a = item.getElementsByTag("a").first();
                        Element img = item.getElementsByTag("img").first();
                        String src = img.attr("src");
                        String name = img.attr("alt");
                        String size = item.getElementsByTag("em").first().text();
                        String memo = item.getElementsByTag("span").last().text();
                        Soft soft = softService.findByUrl(a.attr("href"));
                        if(soft==null){
                            soft = new Soft();
                        }
                        soft.setName(name);
                        soft.setMemo(memo);
                        soft.setSize(size);
                        soft.setUrl(a.attr("href"));
                        soft.getCategories().add(category);
                        soft.setLogo(src);
                        soft.setDownloads(random.nextLong(1000000));
                        soft.setDownloadCount(random.nextLong(1000000));
                        soft.setTodayDownloads(random.nextLong(1000000));
                        soft.setWeekDownloads(random.nextLong(1000000));
                        soft.setMonthDownloads(random.nextLong(1000000));
                        soft.setTodayDownloadsDate(new Date());
                        soft.setWeekDownloadsDate(new Date());
                        soft.setMonthDownloadsDate(new Date());
                        soft.setScore(new Random().nextDouble(10));
                        try {
                            softDetail(soft);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    });
                    if(li.size()<20){
                        break;
                    }
                }catch (Exception e){

                }
            }
        }
    }

    public void softDetail(Soft soft) throws IOException {
        String url = "https://m.shouji.com.cn"+soft.getUrl();
        Document parse = Jsoup.parse(new URL(url).openStream(), "utf-8", url);
        Element infocont = parse.getElementById("infocont");
        Elements h1 = infocont.getElementsByTag("h1");
        String fullName = h1.first().text();
        soft.setFullName(fullName);
        Elements div = infocont.getElementsByTag("div");
        Element element = div.get(1);
        String updateDate = element.text().replace("更新","");
        soft.setUpdateDate(updateDate);
        Element element1 = div.get(2);
        String memo = element1.text();
        soft.setMemo(memo);
        Element element2 = div.get(3);
        String downloadUrl = element2.getElementsByTag("a").first().attr("href");
        soft.setDownloadUrl(downloadUrl);

        Element baseboxBqWrap = parse.getElementsByClass("basebox bq_wrap").first();
        baseboxBqWrap.getElementsByTag("li").forEach(tag->{
            String text = tag.text();
            if(text.contains("大小")){
                soft.setSize(text.replace("大小：",""));
            }else if(text.contains("版本")){
                soft.setVersionName(text.replace("版本：",""));
            }
        });
        if(soft.isNew()){
            softService.save(soft);
        }else{
            softService.update(soft);
        }

        Element imgItem = parse.getElementsByClass("img_item").first();
        Elements img = imgItem.getElementsByTag("img");
        // 清除其全部图片
        softImageService.remove(soft);
        img.forEach(i->{
            SoftImage softImage = new SoftImage();
            softImage.setSoft(soft);
            softImage.setUrl(i.attr("src"));
            softImage.setStatus(1);
            softImage.setType(1);
            softImageService.save(softImage);
        });
        Element txtcont = parse.getElementsByClass("txtcont").first();
        String description = txtcont.html();
        // 清除内容
        softInfoService.remove(soft);
        SoftInfo softInfo = new SoftInfo();
        softInfo.setSoft(soft);
        softInfo.setIntroduce(description);
        softInfoService.save(softInfo);
    }
}
