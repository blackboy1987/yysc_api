package com.bootx.job;

import com.bootx.entity.Soft;
import com.bootx.entity.SoftExt;
import com.bootx.service.*;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class DemoJob {

    @Resource
    private CategoryService categoryService;

    @Resource
    private SoftService softService;
    @Resource
    private SoftInfoService softInfoService;
    @Resource
    private SoftExtService softExtService;
    @Resource
    private SoftImageService softImageService;

    public static Long id = 1L;

    //@Scheduled(fixedDelay = 1000)
    public void detail(){
        Soft soft = softService.find(id);
        if (soft == null) {
            id = id + 1L;
            return;
        }
        id = id + 1L;
        Document parse = null;
        String url = "https://m.shouji.com.cn" + soft.getUrl();
        try {
            parse = Jsoup.parse(new URL(url).openStream(), "utf-8", url);
        } catch (Exception e) {
            return;
        }
        Document finalParse = parse;
        // 基本信息
        Elements info_cent = finalParse.getElementsByClass("info_cent");
        SoftExt softExt = new SoftExt();
        softExt.setSoft(soft);
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
                        softExt.setPaidType(0);
                    } else {
                        softExt.setPaidType(1);
                    }

                } else if (StringUtils.equals("广告：", element.trim())) {
                    if (StringUtils.contains("没有", text)) {
                        softExt.setAdType(0);
                    } else {
                        softExt.setAdType(1);
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
        softInfoService.create(soft, html);

        // 图片
        Elements snapShotCont = finalParse.getElementsByClass("snapShotCont");
        List<String> images = new ArrayList<>();
        if (!snapShotCont.isEmpty()) {
            Elements img = snapShotCont.first().getElementsByTag("img");
            img.forEach(im -> {
                images.add(im.attr("src"));
            });
        }
        // 下载地址$($(".downLi a")[0]).attr("href")
        Element first = finalParse.getElementsByClass("downLi").first().getElementsByTag("a").first();
        String href = first.attr("href");
        soft.setDownloadUrl(href);
        softImageService.create(soft, images);
        softService.update(soft);
        softExtService.create(softExt);
    }
}
