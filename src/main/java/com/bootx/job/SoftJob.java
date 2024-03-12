package com.bootx.job;

import com.bootx.entity.Category;
import com.bootx.entity.Member;
import com.bootx.entity.Soft;
import com.bootx.service.CategoryService;
import com.bootx.service.MemberService;
import com.bootx.service.SoftService;
import com.bootx.util.shouji.ShouJiUtils;
import jakarta.annotation.Resource;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import java.net.URL;
import java.util.List;

@Configuration
public class SoftJob {

    @Resource
    private CategoryService categoryService;

    @Resource
    private SoftService softService;

    /**
     * 分类定时器
     */
    //@Scheduled(fixedDelay = 1000*60*60*24)
    public void category(){
        List<Category> categories = ShouJiUtils.category();
        // 持久化
        categories.forEach(root -> {
            Category save = categoryService.create(root);
            root.getChildren().forEach(child -> {
                child.setParent(save);
                categoryService.create(child);
            });
        });
    }

    @Scheduled(fixedDelay = 1000*60*60*24)
    public void soft(){
        List<Category> categories = categoryService.findAll();
        for (Category category : categories) {
            for (int i = 1; i <= 5000; i++) {
                List<Soft> soft = ShouJiUtils.soft(category.getUrl(), i);
                softService.batchSave(category,soft);
                if(soft.size()<20){
                    break;
                }
            }
        }
    }
}
