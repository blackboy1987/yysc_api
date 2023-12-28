package com.bootx.controller.api;

import com.bootx.common.Pageable;
import com.bootx.common.Result;
import com.bootx.controller.admin.BaseController;
import com.bootx.service.SoftService;
import com.bootx.util.JsonUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController("ApiAppRankController")
@RequestMapping("/api/appRank")
public class AppRankController extends BaseController {

    @Resource
    private SoftService softService;

    @PostMapping
    public Result appRank(){
        List<Map<String,Object>> list = new ArrayList<>();

        Map<String,Object> map1 = new HashMap<>();
        map1.put("id",1);
        map1.put("title","今日应用榜");
        List<Map<String,Object>> children1 = new ArrayList<>();
        Map<String,Object> child11 = new HashMap<>();
        child11.put("id",1);
        child11.put("title","今日下载榜");
        children1.add(child11);
        Map<String,Object> child112 = new HashMap<>();
        child112.put("id",2);
        child112.put("title","今日投币榜");
        children1.add(child112);
        Map<String,Object> child113 = new HashMap<>();
        child113.put("id",2);
        child113.put("title","今日好评榜");
        children1.add(child113);
        map1.put("children",children1);
        list.add(map1);

        Map<String,Object> map2 = new HashMap<>();
        map2.put("id",2);
        map2.put("title","应用总榜");
        List<Map<String,Object>> children21 = new ArrayList<>();
        Map<String,Object> child21 = new HashMap<>();
        child21.put("id",1);
        child21.put("title","下载总榜");
        children21.add(child21);
        Map<String,Object> child122 = new HashMap<>();
        child122.put("id",2);
        child122.put("title","投币总榜");
        children21.add(child122);
        Map<String,Object> child123 = new HashMap<>();
        child123.put("id",2);
        child123.put("title","最多评论");
        children21.add(child123);
        map2.put("children",children21);
        list.add(map2);

        Map<String,Object> map3 = new HashMap<>();
        map3.put("id",3);
        map3.put("title","封神榜");
        List<Map<String,Object>> children31 = new ArrayList<>();
        Map<String,Object> child31 = new HashMap<>();
        child31.put("id",1);
        child31.put("title","投稿大师");
        children31.add(child31);
        Map<String,Object> child132 = new HashMap<>();
        child132.put("id",2);
        child132.put("title","全站富豪");
        children31.add(child132);
        Map<String,Object> child133 = new HashMap<>();
        child133.put("id",3);
        child133.put("title","诸神黄昏");
        children31.add(child133);
        Map<String,Object> child134 = new HashMap<>();
        child134.put("id",4);
        child134.put("title","激励大帝");
        children31.add(child134);
        map3.put("children",children31);
        list.add(map3);

        Map<String,Object> map4 = new HashMap<>();
        map4.put("id",4);
        map4.put("title","达人榜");
        List<Map<String,Object>> children41 = new ArrayList<>();
        Map<String,Object> child41 = new HashMap<>();
        child41.put("id",1);
        child41.put("title","邀请达人");
        children41.add(child41);
        Map<String,Object> child142 = new HashMap<>();
        child142.put("id",2);
        child142.put("title","签到达人");
        children41.add(child142);
        Map<String,Object> child143 = new HashMap<>();
        child143.put("id",3);
        child143.put("title","名门望族");
        children41.add(child143);
        Map<String,Object> child144 = new HashMap<>();
        child144.put("id",4);
        child144.put("title","检查官");
        children41.add(child144);
        Map<String,Object> child145 = new HashMap<>();
        child145.put("id",5);
        child145.put("title","称号狂魔");
        children41.add(child145);
        map4.put("children",children41);
        list.add(map4);

        return Result.success(list);
    }

   @PostMapping("/search")
    public Result search(Pageable pageable,Integer type, Integer type1){
       /**
        * 0: 今日应用榜单
        *   0：下载
        *   1：投币
        *   2：好评
        * 1：应用总榜
        *   0：下载
        *   1：投币
        *   2：最多评论
        * 2：风神榜
        *   0：投稿大师
        *   1：全站富豪
        *   2：诸神黄昏
        *   3：激励大帝
        * 3：达人榜
        *   0：邀请达人
        *   1：签到达人
        *   2：名门望族
        *   3：检查官
        *   4：称号狂魔
        *
        *
        *
        */
       List<Map<String, Object>> maps = softService.get(pageable,"00",null);
       return Result.success(maps);
   }

}
