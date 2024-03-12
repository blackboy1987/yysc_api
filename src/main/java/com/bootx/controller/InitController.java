package com.bootx.controller;

import com.bootx.common.Result;
import com.bootx.entity.*;
import com.bootx.service.*;
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
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author black
 */
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
    @Resource
    private SoftImageService softImageService;

    @Resource
    private FanService fanService;

    private static final ExecutorService executorService = Executors.newFixedThreadPool(10);

    /**
     * 拉去软件
     * @return
     * @throws IOException
     */
    @GetMapping("/soft")
    public Result soft() throws IOException {
        Member member = memberService.find(1L);
        List<Category> categories = categoryService.findAll();
        for (Category category : categories) {
            String url = "https://m.shouji.com.cn" + category.getUrl();
            for (int i = 1; i <= 1000; i++) {
                long start = System.currentTimeMillis();
                String url1 = url.replace("_1", "_" + i);
                try {
                    Document parse = Jsoup.parse(new URL(url1).openStream(), "utf-8", url);
                    Element game_list_top = parse.getElementById("game_list_top");
                    Elements li = game_list_top.getElementsByTag("li");
                    li.forEach(item -> {
                        executorService.execute(()->{
                            Element a = item.getElementsByTag("a").first();
                            Element img = item.getElementsByTag("img").first();
                            String src = img.attr("src");
                            String name = img.attr("alt");
                            String size = item.getElementsByTag("em").first().text();
                            String memo = item.getElementsByTag("span").last().text();
                            Soft soft = softService.findByUrl(a.attr("href"));
                            if (soft == null) {
                                soft = new Soft();
                                soft.init();
                            }else{
                                soft.init();
                            }
                            soft.setName(name);
                            soft.setSize(size);
                            soft.setUrl(a.attr("href"));
                            soft.getCategories().add(category);
                            soft.setLogo(src);
                            soft.setMember(member);
                            if(soft.isNew()){
                                softService.save(soft);
                            }else{
                                softService.update(soft);
                            }
                        });
                    });
                    System.out.println("end：" + (System.currentTimeMillis() - start));
                    if (li.size() < 20) {
                        break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return Result.success();
    }


    @GetMapping("/member")
    public Result member() {
        for (int i = 0; i < 10000; i++) {
            Member member = new Member();
            member.setPassword("123456");
            member.setUsername("test" + i);
            member.setIsEnabled(true);
            member.setIsLocked(false);
            member.setPoint(0L);
            member.setContinuousSignInDays(0);
            member.setSignInDays(0);
            memberService.save(member);
        }
        return Result.success();
    }

    @GetMapping("/review")
    public Result review() {
        for (int i = 0; i < 50000; i++) {
            Member member = null;
            Soft soft = null;
            while (member == null) {
                Long memberId = new Random().nextLong(11001 + 1L);
                member = memberService.find(memberId);
            }
            while (soft == null) {
                Long softId = new Random().nextLong(28776 + 1L);
                soft = softService.find(softId);
            }
            Review review = new Review();
            review.setForReview(null);
            review.setReplyReviews(new HashSet<>());
            review.setContent(soft.getName() + ":不错");
            review.setIp("1.1.1.1.1");
            review.setMember(member);
            review.setSoft(soft);
            review.setIsShow(true);
            review.setScore(new Random().nextInt(1, 10));
            new Thread(() -> {
                reviewService.save(review);
            }).start();

        }
        return Result.success();
    }

    @GetMapping("/detail1")
    public Result detail(Long start) {
        for (Long j = start; j < 100000L; j++) {
            Soft soft = softService.find(j);
            if (soft == null) {
                continue;
            }
            Document parse = null;
            String url = "https://www.shouji.com.cn" + soft.getUrl();
            try {
                parse = Jsoup.parse(new URL(url).openStream(), "utf-8", url);
            } catch (Exception e) {
                continue;
            }
            Document finalParse = parse;
            executorService.execute(() -> {
                // 基本信息
                Elements info_cent = finalParse.getElementsByClass("info_cent");
                if (!info_cent.isEmpty()) {
                    Element first = info_cent.first();
                    Elements span = first.getElementsByTag("span");
                    Elements p = first.getElementsByTag("p");
                    for (int i = 0; i < span.size(); i++) {
                        String element = span.get(i).text();
                        String text = p.get(i).text();
                        if (StringUtils.equals("版本：", element.trim())) {
                            soft.setVersionName(text.trim());
                        } else if (StringUtils.equals("大小：", element.trim())) {
                            soft.setSize(text.trim());
                        } else if (StringUtils.equals("更新：", element.trim())) {
                            soft.setUpdateDate(text.trim());
                        } else if (StringUtils.equals("资费：", element.trim())) {
                            if (StringUtils.contains("免费", text)) {
                                soft.setPaidType(0);
                            } else {
                                soft.setPaidType(1);
                            }

                        } else if (StringUtils.equals("广告：", element.trim())) {
                            if (StringUtils.contains("没有", text)) {
                                soft.setAdType(0);
                            } else {
                                soft.setAdType(1);
                            }
                        }
                    }


                }
                Elements processingbar = finalParse.getElementsByClass("processingbar");
                if (!processingbar.isEmpty()) {
                    Elements font = processingbar.first().getElementsByTag("font");
                    if (!font.isEmpty()) {
                        String score = font.text();
                        soft.setScore(Double.valueOf(score));
                    }
                }

                // 评论
                Elements lef1 = finalParse.getElementsByClass("Lef1_cent");
                String html = lef1.html();
                // 图片
                Elements snapShotCont = finalParse.getElementsByClass("snapShotCont");
                List<String> images = new ArrayList<>();
                if (snapShotCont != null) {
                    Elements img = snapShotCont.first().getElementsByTag("img");
                    img.forEach(im -> {
                        images.add(im.attr("src"));
                    });
                }
                softImageService.create(soft, images);
                softService.update(soft);
            });
        }

        return Result.success();
    }
}
