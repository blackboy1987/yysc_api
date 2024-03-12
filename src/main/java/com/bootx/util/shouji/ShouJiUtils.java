package com.bootx.util.shouji;

import com.bootx.entity.Category;
import com.bootx.entity.Soft;
import com.bootx.entity.SoftImage;
import com.bootx.pojo.SoftAttr;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
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


    public static Soft mobileDetail(Soft soft){
        String url = "https://m.shouji.com.cn" + soft.getUrl();
        System.out.println(url);
        try {
            Document parse = Jsoup.parse(new URL(url).openStream(), "utf-8", url);
            Element appBox = parse.getElementById("appBox");
            if(appBox!=null){
                String appName = appBox.attr("data-appname");
                String packageName = appBox.attr("data-package");
                soft.setAppName(appName);
                soft.setPackageName(packageName);


                Element infocont = appBox.getElementById("infocont");
                if(infocont!=null){
                    Element h1 = infocont.getElementsByTag("h1").first();
                    if(h1!=null){
                        String fullName = h1.text();
                        soft.setFullName(fullName);
                    }

                    Element first = infocont.select("div.txt").first();
                    if(first!=null){
                        String updateDate = first.text().replace("更新","");
                        soft.setUpdateDate(updateDate);
                    }

                    Element second = infocont.select("div.txt2").first();
                    if(second!=null){
                        String subTitle = second.text();
                        soft.setSubTitle(subTitle);
                    }

                    Element download = infocont.select("a[rel='nofollow']").first();
                    if(download!=null){
                        String downloadUrl = download.attr("href");
                        soft.setDownloadUrl(downloadUrl);
                    }
                }
                Element first = parse.select("div.img_item").first();
                if(first!=null){
                    Elements img = first.getElementsByTag("img");
                    List<SoftImage> softImages = new ArrayList<>();
                    for (Element element : img) {
                        String attr = element.attr("src");
                        SoftImage softImage = new SoftImage();
                        softImage.setSoft(soft);
                        softImage.setType(0);
                        softImage.setUrl(attr);
                        softImage.setStatus(1);
                        softImages.add(softImage);
                    }
                    soft.setSoftImages(new HashSet<>(softImages));
                }
                Element first1 = parse.select("div.txtcont").first();
                if(first1!=null){
                    soft.setMemo(parseHtml(first1.html()));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return soft;
    }


    public static Soft pcDetail(Soft soft){
        String url = "https://www.shouji.com.cn" + soft.getUrl();
        List<SoftAttr> softAttrs = new ArrayList<>();
        try {
            Document parse = Jsoup.parse(new URL(url).openStream(), "utf-8", url);
            Element infoCent = parse.getElementsByClass("info_cent").first();
            Elements span = infoCent.getElementsByTag("span");
            Elements p = infoCent.getElementsByTag("p");
            for (int i = 0; i < span.size(); i++) {
                Element element1 = span.get(i);
                Element element = p.get(i);
                SoftAttr softAttr = new SoftAttr();
                softAttr.setKey(element1.text());
                softAttr.setValue(element.text());
                softAttrs.add(softAttr);
                if(StringUtils.equalsIgnoreCase(softAttr.getKey(),"更新：")){
                    soft.setUpdateDate(softAttr.getValue());
                }else if(StringUtils.equalsIgnoreCase(softAttr.getKey(),"版本：")){
                    soft.setVersionName(softAttr.getValue());
                }else if(StringUtils.equalsIgnoreCase(softAttr.getKey(),"大小：")){
                    soft.setSize(softAttr.getValue());
                }else if(StringUtils.equalsIgnoreCase(softAttr.getKey(),"评分：")){
                    soft.setScore(Double.valueOf(softAttr.getValue()));
                }else if(StringUtils.equalsIgnoreCase(softAttr.getKey(),"广告：")){
                    // 默认都是设置无广告
                    soft.setAdType(0);
                }
            }
            soft.setOperationType(1);
            soft.setFeaturesType(0);
            soft.setSoftAttrs(softAttrs);
            if(soft.getScore()==null){
                Element first = parse.getElementsByClass("processingbar").first();
                if(first!=null){
                    Element first1 = first.getElementsByTag("font").first();
                    if(first1!=null){
                        soft.setScore(Double.valueOf(first1.text()));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return soft;
    }

    private static String parseHtml(String html) {
        Document parse = Jsoup.parse(html);
        parse.getElementsByTag("a").forEach(item->{
            item
                    .removeAttr("href")
                    .removeAttr("title")
                    .removeAttr("name");
        });
        return parse.body().html();
    }

}
