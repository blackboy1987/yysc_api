package com.bootx.service.impl;

import com.bootx.common.Pageable;
import com.bootx.dao.CategoryDao;
import com.bootx.dao.SoftDao;
import com.bootx.entity.Category;
import com.bootx.entity.Member;
import com.bootx.entity.Soft;
import com.bootx.entity.SoftImage;
import com.bootx.pojo.SoftPOJO;
import com.bootx.service.MemberService;
import com.bootx.service.SoftImageService;
import com.bootx.service.SoftService;
import com.bootx.util.DateUtils;
import com.bootx.util.ImageUtils;
import com.bootx.util.JsonUtils;
import com.bootx.util.UploadUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import freemarker.cache.SoftCacheStorage;
import jakarta.annotation.Resource;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.SystemUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.File;
import java.net.URL;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class SoftServiceImpl extends BaseServiceImpl<Soft, Long> implements SoftService {

    @Resource
    private SoftDao softDao;

    @Resource
    private CategoryDao categoryDao;

    @Resource
    private SoftImageService softImageService;

    @Resource
    private MemberService memberService;

    @Override
    public Soft findByUrl(String href) {
        return softDao.find("url", href);
    }

    @Override
    public void updateDownloads(Long id, int i) {
        Soft soft = find(id);
        if (soft != null) {
            soft.setDownloads(soft.getDownloads() + i);
            update(soft);
        }
    }

    @Override
    public void create(SoftPOJO softPOJO, Member member) {
        Soft soft = new Soft();
        soft.setMember(member);
        soft.setStatus(0);
        init(soft, softPOJO);
        initCategory(soft, softPOJO);
        super.save(soft);
        initSoftImage(soft, softPOJO);

    }

    /**
     * 状态
     * 0：待审
     * 1：审核通过
     * 2：审核拒绝
     * 100: 草稿
     */
    @Override
    public String getStatus(String status) {
        if (StringUtils.equals("0", status)) {
            return "审核中";
        }
        if (StringUtils.equals("1", status)) {
            return "已上架";
        }
        if (StringUtils.equals("2", status)) {
            return "未通过";
        }
        if (StringUtils.equals("100", status)) {
            return "草稿";
        }

        return null;
    }

    @Override
    public List<Map<String, Object>> get(Pageable pageable, String orderBy, Long categoryId) {
        System.out.println(orderBy);



        List<Map<String, Object>> maps = new ArrayList<>();
        String fromSql = "from soft";
        if (categoryId != null && categoryId != 0) {
            fromSql = "from soft_categories,soft where softs_id=soft.id and categories_id=" + categoryId;
        }
        String pageQuery = "limit " + (pageable.getPageNumber() - 1) * pageable.getPageSize() + "," + pageable.getPageSize();
        if (StringUtils.equalsIgnoreCase("00", orderBy)) {
            // 下载排行
            maps = jdbcTemplate.queryForList("select size, score,versionName, id, downloads,logo,name " + fromSql + " order by downloads desc " + pageQuery);
            maps.forEach(item -> {
                Long downloads = Long.valueOf(item.get("downloads") + "");
                if (downloads >= 10000) {
                    item.put("memo", String.format("%.2f", downloads / 10000.0) + "万次下载");
                } else {
                    item.put("memo", downloads + "次下载");
                }
                item.put("score", (item.get("score") + "").substring(0, 3));
            });
        } else if (StringUtils.equalsIgnoreCase("01", orderBy)) {
            // 评分排行
            maps = jdbcTemplate.queryForList("select id,reviewCount,score,logo,name,versionName,size " + fromSql + " order by score desc " + pageQuery);
            maps.forEach(item -> {
                item.put("memo", "今日" + (item.get("reviewCount") == null ? 0 : item.get("reviewCount")) + "条评论");
                item.put("score", (item.get("score") + "").substring(0, 3));
            });
        } else if (StringUtils.equalsIgnoreCase("2", orderBy)) {
            // 随机
            maps = jdbcTemplate.queryForList("SELECT size, id,downloads,logo,name,score FROM soft WHERE id >= ((SELECT MAX(id) FROM soft)-(SELECT MIN(id) FROM soft)) * RAND() + (SELECT MIN(id) FROM soft) LIMIT 20");
            maps.forEach(item -> {
                Long downloads = Long.valueOf(item.get("downloads") + "");
                if (downloads >= 10000) {
                    item.put("memo", String.format("%.2f", downloads / 10000.0) + "万次下载");
                } else {
                    item.put("memo", downloads + "次下载");
                }
                item.put("score", (item.get("score") + "").substring(0, 3));
            });
        } else if (StringUtils.equalsIgnoreCase("3", orderBy)) {
            // 更新排行
            maps = jdbcTemplate.queryForList("select size, id, downloads,logo,name,score,versionName,updateDate " + fromSql + " order by updateDate desc " + pageQuery);
            maps.forEach(item -> {
                Long downloads = Long.valueOf(item.get("downloads") + "");
                if (downloads >= 10000) {
                    item.put("memo", String.format("%.2f", downloads / 10000.0) + "万次下载");
                } else {
                    item.put("memo", downloads + "次下载");
                }
                item.put("score", (item.get("score") + "").substring(0, 3));
            });
        } else if (StringUtils.equalsIgnoreCase("7", orderBy)) {
            maps = jdbcTemplate.queryForList("select size, id, downloads,logo,name,score,versionName " + fromSql + " order by downloads desc " + pageQuery);
            maps.forEach(item -> {
                Long downloads = Long.valueOf(item.get("downloads") + "");
                if (downloads >= 10000) {
                    item.put("memo", String.format("%.2f", downloads / 10000.0) + "万次下载");
                } else {
                    item.put("memo", downloads + "次下载");
                }
                item.put("score", (item.get("score") + "").substring(0, 3));
            });
        } else if (StringUtils.equalsIgnoreCase("8", orderBy)) {
            maps = jdbcTemplate.queryForList("select size, id,logo,name " + fromSql + " order by downloads desc " + pageQuery);
        } else {
            maps = jdbcTemplate.queryForList("select size, id,downloads ,logo,name,score " + fromSql + " order by downloads desc " + pageQuery);
            maps.forEach(item -> {
                Long downloads = Long.valueOf(item.get("downloads") + "");
                if (downloads >= 10000) {
                    item.put("memo", String.format("%.2f", downloads / 10000.0) + "万次下载");
                } else {
                    item.put("memo", downloads + "次下载");
                }
                item.put("score", (item.get("score") + "").substring(0, 3));
            });
        }
        return maps;
    }

    @Override
    public List<Map<String, Object>> search(String keywords, Pageable pageable) {
        List<Map<String, Object>> maps = jdbcTemplate.queryForList("select versionName, id,downloads,logo,name,size,updateDate,score from soft where name like ? limit ?,?;", "%" + keywords + "%", (pageable.getPageNumber() - 1) * pageable.getPageSize(), pageable.getPageSize());
        maps.forEach(item -> {
            Long downloads = Long.valueOf(item.get("downloads") + "");
            if (downloads >= 10000) {
                item.put("memo", String.format("%.2f", downloads / 10000.0) + "万次下载");
            } else {
                item.put("memo", downloads + "次下载");
            }
            item.put("score", (item.get("score") + "").substring(0, 3));
        });
        return maps;
    }

    @Override
    public void load(Soft soft) {
        if(soft!=null){
            String url = "https://m.shouji.com.cn" + soft.getUrl();
            try {
                Document parse = Jsoup.parse(new URL(url).openStream(), "utf-8", url);
                // 基本信息
                /**
                 * <div id="infocont" class="info">
                 *  <span class="tx"><img src="https://img.ocn187.com/simg/20200306/2020030603360525.png" alt="销服一线通app手机版 1.5.7"></span>
                 *  <h1 class="bt">销服一线通app手机版 1.5.7</h1>
                 *  <div class="txt">2022-3-28 9:21:38更新</div>
                 *  <div class="txt2">提高你的办公效率。</div>
                 *  <div class="btn topdown"><a href="http://gyxzliu2.gda086.com/gx2/19/xfyxt1076513.apk" title="下载销服一线通app手机版 1.5.7" rel="nofollow">立即下载</a></div>
                 * </div>
                 */
                Element info_cent = parse.getElementById("infocont");
                Element first = info_cent.getElementsByTag("img").first();
                String logo = first.attr("href");
                String name = info_cent.getElementsByTag("h1").first().text();
                String updateTime = info_cent.getElementsByClass("txt").first().text().replace("更新","");
                String memo = info_cent.getElementsByClass("txt2").first().text().replace("更新","");
                String downloadUrl = info_cent.getElementsByTag("a").last().attr("href");
                soft.setDownloadUrl(downloadUrl);
                soft.setUpdateDate(updateTime);
                soft.setLogo(logo);
                soft.setName(name);
                super.update(soft);

                SoftPOJO softPOJO = new SoftPOJO();
                softPOJO.setMemo(memo);
                Element first2 = parse.getElementsByClass("txtcont").first();
                softPOJO.setUpdatedContent(first2.html());

                // 图片
                Element first1 = parse.getElementsByClass("img_item Qimgs").first();
                Elements imgs = first1.getElementsByTag("img");
                List<String> urls = new ArrayList<>();
                imgs.forEach(item->{
                    String href = item.attr("href");
                    urls.add(href);
                });
                softPOJO.setUrls(StringUtils.join(urls,","));
                initSoftImage(soft,softPOJO);




            } catch (Exception ignored) {
            }
        }
    }

    @Override
    public void load(Category category) {

    }

    @Override
    public void batchSave(Category category, List<Soft> softs) {
        Member member = memberService.find(1L);
        for (Soft soft : softs) {
            soft.setMember(member);
            Soft current = softDao.find("url", soft.getUrl());
            if(current==null){
                Soft.init(soft);
                soft.getCategories().add(category);
                super.save(soft);
            }else{
                current.getCategories().add(category);
                super.update(current);
            }
        }
    }



    private void initSoftImage(Soft soft, SoftPOJO softPOJO) {
        softImageService.remove(soft);
        String urls = softPOJO.getUrls();
        String[] split = urls.split(",");
        for (int i = 0; i < split.length; i++) {
            SoftImage softImage = new SoftImage();
            softImage.setType(0);
            softImage.setSoft(soft);
            softImage.setStatus(0);
            softImage.setUrl(split[i]);
            softImage.setSoft(soft);
            softImageService.save(softImage);
        }
    }

    private void initCategory(Soft soft, SoftPOJO softPOJO) {
        // 分类
        Long categoryId0 = softPOJO.getCategoryId0();
        Long categoryId1 = softPOJO.getCategoryId1();
        Set<Category> categories = new HashSet<>();
        categories.add(categoryDao.find(categoryId0));
        categories.add(categoryDao.find(categoryId1));
        soft.setCategories(categories);
    }

    private void init(Soft soft, SoftPOJO softPOJO) {
        soft.setSize(softPOJO.getSize() + "");
        soft.setScore(9.0);
        soft.setUpdateDate(DateUtils.formatDateToString(new Date(), "yyyy-MM-dd HH:mm:ss"));
        soft.setVersionName(softPOJO.getVersionName());
        soft.setVersionCode(softPOJO.getVersionCode() + "");
        soft.setDonationMember(0);
        soft.setDownloads(0L);
        soft.setDonationIcon(0L);
        soft.setFullName(softPOJO.getTitle());
        soft.setMonthDownloads(0L);
        soft.setMonthDownloadsDate(null);
        soft.setReviewCount(0);
        soft.setName(softPOJO.getTitle());
        soft.setTodayDownloads(0L);
        soft.setWeekDownloads(0L);
        soft.setTodayDownloadsDate(null);
        soft.setWeekDownloadsDate(null);
        soft.setDownloadUrl(softPOJO.getDownloadUrl());
        soft.setPassword(softPOJO.getPassword());
        soft.setMinSdkVersion(softPOJO.getMinSdkVersion());
        soft.setTargetSdkVersion(softPOJO.getTargetSdkVersion());
        soft.setPackageName(softPOJO.getPackageName());


        String path = "yysc/" + DateUtils.formatDateToString(new Date(), "yyyy/MM/dd/") + UUID.randomUUID().toString().replace("-", "") + ".png";
        File temp = new File(SystemUtils.getJavaIoTmpDir(), path);
        if (!temp.getParentFile().exists()) {
            temp.getParentFile().mkdirs();
        }
        ImageUtils.base64ToImage(softPOJO.getAppLogo(), temp);
        soft.setLogo(UploadUtils.upload(path, temp));
        System.out.println(soft.getLogo());
    }

    @Override
    public void delete(Soft soft) {
        jdbcTemplate.update("delete from softimage where soft_id=?;", soft.getId());
        jdbcTemplate.update("delete from softinfo where soft_id=?;", soft.getId());
        jdbcTemplate.update("delete from softext where soft_id=?;", soft.getId());
        jdbcTemplate.update("delete from review where soft_id=?;", soft.getId());
        jdbcTemplate.update("delete from softiconlog where soft_id=?;", soft.getId());
        super.delete(soft);
    }


    private String getBaseQuerySql(Long categoryId,String queryAttributes,String where,String orderBy,Pageable pageable){
        String sql = "select id,logo,fullName";
        if(StringUtils.isNotBlank(queryAttributes)){
            sql = sql+","+queryAttributes;
        }
        if(categoryId!=null){
            sql = sql+ " from soft,soft_categories where soft.id=soft_categories.softs_id and categories_id="+categoryId;
        }else {
            sql = sql+ " from soft where 1=1";
        }
        if(StringUtils.isNotBlank(where)){
            sql = sql+" and "+where;
        }
        if(StringUtils.isNotBlank(orderBy)){
            sql = sql+ " order by "+orderBy;
        }
        if(pageable!=null){
            sql = sql+ " limit "+(pageable.getPageNumber()-1)*pageable.getPageSize()+","+pageable.getPageSize();
        }
        return sql;
    }

    @Override
    public List<Map<String, Object>> list(Pageable pageable, Long categoryId,String where, String orderBy) {
        String baseQuerySql = getBaseQuerySql(categoryId, null, where, orderBy, pageable);
        String cacheKey = DigestUtils.md5Hex(baseQuerySql);
        List<Map<String, Object>> maps;
        try {
            maps = JsonUtils.toObject(redisService.get(cacheKey), new TypeReference<List<Map<String, Object>>>() {
            });
        }catch (Exception e){
            maps = jdbcTemplate.queryForList(baseQuerySql);
            redisService.set(cacheKey,JsonUtils.toJson(maps),10, TimeUnit.MINUTES);
        }
        return maps;
    }

    @Override
    public Map<String, Object> detail(Long id) {
        String cacheKey = "soft:detail_" + id;
        Map<String,Object> data = new HashMap<>();
        String s = redisService.get(cacheKey);
        try {
            data = JsonUtils.toObject(s, new TypeReference<Map<String, Object>>() {
            });
        }catch (Exception e){

        }
        if(!data.isEmpty()){
            return data;
        }
        Soft soft = super.find(id);
        if(soft.getMember()!=null){
            data.put("author",soft.getMember().getUsername());
            data.put("avatar",soft.getMember().getAvatar());
        }
        data.put("versionCode",soft.getVersionCode());
        data.put("versionName",soft.getVersionName());
        data.put("id",id);
        data.put("donationMember",soft.getDonationMember());
        data.put("donationIcon",soft.getDonationIcon());
        data.put("reviewCount",soft.getReviewCount());
        data.put("fullName",soft.getFullName());
        data.put("score",String.format("%.2f", soft.getScore()));
        data.put("name",soft.getName());
        data.put("logo",soft.getLogo());
        data.put("size",soft.getSize());
        data.put("updateDate",soft.getUpdateDate());
        data.put("introduce",soft.getIntroduce());
        data.put("memo",soft.getMemo());
        data.put("updatedContent",soft.getUpdatedContent());
        if(soft.getSoftImages()!=null){
            data.put("images",soft.getSoftImages().stream().map(SoftImage::getUrl).collect(Collectors.toList()));
        }else{
            data.put("images",Collections.emptyList());
        }
        if(soft.getDownloads()>=10000){
            data.put("downloads",String.format("%.2f",soft.getDownloads()/10000.0)+"万");
        }else{
            data.put("downloads",soft.getDownloads()+"次下载");
        }
        redisService.set(cacheKey,JsonUtils.toJson(data));

        return data;
    }
}