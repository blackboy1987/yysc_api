package com.bootx.service.impl;

import com.bootx.dao.*;
import com.bootx.entity.*;
import com.bootx.pojo.SoftPOJO;
import com.bootx.service.SoftImageService;
import com.bootx.service.SoftInfoService;
import com.bootx.service.SoftService;
import com.bootx.util.DateUtils;
import com.bootx.util.ImageUtils;
import com.bootx.util.UploadUtils;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.SystemUtils;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Service
public class SoftServiceImpl extends BaseServiceImpl<Soft, Long> implements SoftService {

    @Resource
    private SoftDao softDao;

    @Resource
    private CategoryDao categoryDao;

    @Resource
    private SoftExtDao softExtDao;
    @Resource
    private SoftImageDao softImageDao;
    @Resource
    private SoftInfoDao softInfoDao;

    @Override
    public Soft findByUrl(String href) {
        return softDao.find("url",href);
    }

    @Override
    public void updateDownloads(Long id,int i) {
        Soft soft = find(id);
        if(soft!=null){
            soft.setDownloads(soft.getDownloads()+i);
        }
    }

    @Override
    public void create(SoftPOJO softPOJO) {
        Soft soft = new Soft();
        init(soft,softPOJO);
        initCategory(soft,softPOJO);
        super.save(soft);
        initSoftImage(soft,softPOJO);
        initSoftInfo(soft,softPOJO);
        initSoftExt(soft,softPOJO);

    }

    private void initSoftExt(Soft soft, SoftPOJO softPOJO) {
        SoftExt softExt = new SoftExt();
        softExt.setAdType(softPOJO.getAdType0());
        softExt.setPaidType(softPOJO.getAdType1());
        softExt.setOperationType(softPOJO.getAdType2());
        softExt.setFeaturesType(softPOJO.getAdType3());
        softExt.setSoft(soft);
        soft.setSoftExt(softExt);
        softExtDao.persist(softExt);
    }

    private void initSoftInfo(Soft soft, SoftPOJO softPOJO) {
        SoftInfo softInfo = new SoftInfo();
        softInfo.setIntroduce(softPOJO.getIntroduce());
        softInfo.setMemo(softPOJO.getMemo());
        softInfo.setUpdatedContent(softPOJO.getUpdatedContent());
        softInfo.setSoft(soft);
        softInfoDao.persist(softInfo);
    }

    private void initSoftImage(Soft soft, SoftPOJO softPOJO) {
        String urls = softPOJO.getUrls();
        String[] split = urls.split(",");
        for (int i = 0; i < split.length; i++) {
            SoftImage softImage = new SoftImage();
            softImage.setType(0);
            softImage.setSoft(soft);
            softImage.setStatus(0);
            softImage.setUrl(split[i]);
            softImage.setSoft(soft);
            softImageDao.persist(softImage);
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
        soft.setSize(softPOJO.getSize()+"");
        soft.setScore(9.0);
        soft.setUpdateDate(DateUtils.formatDateToString(new Date(),"yyyy-MM-dd HH:mm:ss"));
        soft.setVersionName(softPOJO.getVersionName());
        soft.setVersionCode(softPOJO.getVersionCode()+"");
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

        String path = "yysc/"+DateUtils.formatDateToString(new Date(),"yyyy/MM/dd/")+ UUID.randomUUID().toString().replace("-","")+".png";
        File temp = new File(SystemUtils.getJavaIoTmpDir(),path);
        if(!temp.getParentFile().exists()){
            temp.getParentFile().mkdirs();
        }
        ImageUtils.base64ToImage(softPOJO.getAppLogo(),temp);
        soft.setLogo(UploadUtils.upload(path,temp));
        System.out.println(soft.getLogo());
    }
}