package com.bootx.controller;

import com.bootx.common.Result;
import com.bootx.entity.Category;
import com.bootx.entity.Member;
import com.bootx.entity.Review;
import com.bootx.entity.Soft;
import com.bootx.service.CategoryService;
import com.bootx.service.MemberService;
import com.bootx.service.ReviewService;
import com.bootx.service.SoftService;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URL;
import java.util.*;

@RestController
@RequestMapping("/init")
public class InitController {

    @Resource
    private CategoryService categoryService;

    @Resource
    private SoftService softService;

    @Resource
    private MemberService memberService;
    @Resource
    private ReviewService reviewService;

    /*@GetMapping
    public Result index() throws IOException {
        List<Category> categories = new ArrayList<>();
        String url = "https://m.shouji.com.cn/soft_category.html";
        Document parse = Jsoup.parse(new URL(url).openStream(), "gbk", url);
        Elements subExtImgList = parse.getElementsByClass("sub_extImgList");
        subExtImgList.forEach(item->{
            Elements a = item.getElementsByTag("a");
            Category parent = new Category();
            for (int i = 0; i < a.size(); i++) {
                if(i==0){
                    parent.setName(a.get(i).text());
                    parent.setUrl(a.get(i).attr("href"));
                }else{
                    Category child = new Category();
                    child.setName(a.get(i).text());
                    child.setUrl(a.get(i).attr("href"));
                    child.setParent(parent);
                    parent.getChildren().add(child);
                }
            }
            categories.add(parent);
        });
        // 持久化
        categories.forEach(root->{
            Category save = categoryService.save(root);
            root.getChildren().forEach(child->{
                child.setParent(save);
                categoryService.save(child);
            });
        });

        return Result.success();
    }*/


    @GetMapping("/soft")
    public Result soft() throws IOException {

        List<Category> categories = categoryService.findAll();

        Random random = new Random();
        for (Category category : categories) {
            if(category.getId()<90){
                continue;
            }
            String url = "https://m.shouji.com.cn"+category.getUrl();
            for (int i = 7; i <= 8; i++) {
                String url1 = url.replace("_1", "_"+i);
                try {
                    Document parse = Jsoup.parse(new URL(url1).openStream(), "utf-8", url);
                    Element game_list_top = parse.getElementById("game_list_top");
                    Elements li = game_list_top.getElementsByTag("li");
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
                            e.printStackTrace();
                        }
                        Soft finalSoft = soft;
                        new Thread(()->{
                            if(finalSoft.isNew()){
                                softService.save(finalSoft);
                            }else{
                                softService.update(finalSoft);
                            }
                        }).start();
                    });



                    if(li.size()<20){
                        break;
                    }
                }catch (Exception e){

                }
            }
        }
        return Result.success();
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
        Element imgItem = parse.getElementsByClass("img_item").first();
        Elements img = imgItem.getElementsByTag("img");
        List<String> imgs = new ArrayList<>();
        img.forEach(i->{
            imgs.add(i.attr("src"));
        });
        soft.setImages(StringUtils.join(imgs,","));
        Element txtcont = parse.getElementsByClass("txtcont").first();
        String description = txtcont.html();
        soft.setDescription(description);
    }


    @GetMapping("/member")
    public Result member(){
        for (int i = 0; i < 10000; i++) {
            Member member = new Member();
            member.setPassword("123456");
            member.setUsername("test"+i);
            member.setIsEnabled(true);
            member.setIsLocked(false);
            member.setPoint(0L);
            member.setContinuousSignInDays(0L);
            member.setSignInDays(0L);
            memberService.save(member);
        }
        return Result.success();
    }

    @GetMapping("/review")
    public Result review(){
        for (int i = 0; i < 50000; i++) {
            Member member = null;
            Soft soft = null;
            while (member==null){
                Long memberId = new Random().nextLong(11001+1L);
                member = memberService.find(memberId);
            }
            while (soft==null){
                Long softId = new Random().nextLong(28776+1L);
                soft = softService.find(softId);
            }
            Review review = new Review();
            review.setForReview(null);
            review.setReplyReviews(new HashSet<>());
            review.setContent(soft.getName()+":不错");
            review.setIp("1.1.1.1.1");
            review.setMember(member);
            review.setSoft(soft);
            review.setIsShow(true);
            review.setScore(new Random().nextInt(1,10));
            new Thread(()->{
                reviewService.save(review);
            }).start();

        }
        return Result.success();
    }
}
