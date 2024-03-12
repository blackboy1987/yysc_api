package com.bootx.util.shouji;

import com.bootx.entity.Category;
import com.bootx.entity.Soft;
import com.bootx.util.WebUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ShouJiUtils {

    public static List<Category> category(){
        List<Category> categories = new ArrayList<>();
        String url = "https://m.shouji.com.cn/soft_category.html";
        try {
            Document parse = Jsoup.parse(new URL(url).openStream(), "gbk", url);
            Elements subExtImgList = parse.getElementsByClass("sub_extImgList");
            subExtImgList.forEach(item -> {
                Elements a = item.getElementsByTag("a");
                Category parent = new Category();
                for (int i = 0; i < a.size(); i++) {
                    if (i == 0) {
                        parent.setName(a.get(i).text());
                        parent.setUrl(a.get(i).attr("href"));
                    } else {
                        Category child = new Category();
                        child.setName(a.get(i).text());
                        child.setUrl(a.get(i).attr("href"));
                        child.setParent(parent);
                        parent.getChildren().add(child);
                    }
                }
                categories.add(parent);
            });
        }catch (Exception e){
            e.printStackTrace();
        }
        return categories;
    }

    public static List<Soft> soft(String categoryUrl,Integer page){
        List<Soft> softs = new ArrayList<>();
        String url = "https://m.shouji.com.cn" + categoryUrl;
        String url1 = url.replace("_1", "_" + page);
        System.out.println(url1);
        try {
            Document parse = Jsoup.parse(new URL(url1).openStream(), "utf-8", url);
            Element gameListTop = parse.getElementById("game_list_top");
            if(gameListTop!=null){
                Elements li = gameListTop.getElementsByTag("li");
                li.forEach(item -> {
                    Soft soft = new Soft();
                    Element a = item.getElementsByTag("a").first();
                    String href = a.attr("href");
                    soft.setUrl(href);
                    Element img = item.getElementsByTag("img").first();
                    String src = img.attr("src");
                    soft.setLogo(src);
                    String alt = img.attr("alt");
                    soft.setName(alt);
                    Element em = item.getElementsByTag("em").first();
                    soft.setSize(em.text());
                    softs.add(soft);
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return softs;
    }

    public static void main(String[] args) {
        for (int i = 1; i < 200; i++) {
            List<Soft> soft = soft("/applist/soft_4050_1.html",i);
            System.out.println(soft.size());
            if(soft.size()<20){
                break;
            }
        }
    }

}
