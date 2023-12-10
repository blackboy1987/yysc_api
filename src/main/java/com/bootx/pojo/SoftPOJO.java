package com.bootx.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SoftPOJO implements Serializable {

    private String title;
    private String memo;
    private String introduce;
    private String updatedContent;
    private String packageName;
    private Integer adType0;
    private Integer adType1;
    private Integer adType2;
    private Integer adType3;
    private String appLogo;
    private Long categoryId0;
    private Long categoryId1;
    private String quDaoIndex;
    private String urls;
    private Integer versionCode;
    private String versionName;
    private Integer minSdkVersion;
    private Integer targetSdkVersion;
    private Long size;

    private String downloadUrl;

    private String password;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public String getUpdatedContent() {
        return updatedContent;
    }

    public void setUpdatedContent(String updatedContent) {
        this.updatedContent = updatedContent;
    }

    public Integer getAdType0() {
        return adType0;
    }

    public void setAdType0(Integer adType0) {
        this.adType0 = adType0;
    }

    public Integer getAdType1() {
        return adType1;
    }

    public void setAdType1(Integer adType1) {
        this.adType1 = adType1;
    }

    public Integer getAdType2() {
        return adType2;
    }

    public void setAdType2(Integer adType2) {
        this.adType2 = adType2;
    }

    public Integer getAdType3() {
        return adType3;
    }

    public void setAdType3(Integer adType3) {
        this.adType3 = adType3;
    }

    public String getAppLogo() {
        return appLogo;
    }

    public void setAppLogo(String appLogo) {
        this.appLogo = appLogo;
    }

    public Long getCategoryId0() {
        return categoryId0;
    }

    public void setCategoryId0(Long categoryId0) {
        this.categoryId0 = categoryId0;
    }

    public Long getCategoryId1() {
        return categoryId1;
    }

    public void setCategoryId1(Long categoryId1) {
        this.categoryId1 = categoryId1;
    }

    public String getQuDaoIndex() {
        return quDaoIndex;
    }

    public void setQuDaoIndex(String quDaoIndex) {
        this.quDaoIndex = quDaoIndex;
    }

    public String getUrls() {
        return urls;
    }

    public void setUrls(String urls) {
        this.urls = urls;
    }

    public Integer getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(Integer versionCode) {
        this.versionCode = versionCode;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public Integer getMinSdkVersion() {
        return minSdkVersion;
    }

    public void setMinSdkVersion(Integer minSdkVersion) {
        this.minSdkVersion = minSdkVersion;
    }

    public Integer getTargetSdkVersion() {
        return targetSdkVersion;
    }

    public void setTargetSdkVersion(Integer targetSdkVersion) {
        this.targetSdkVersion = targetSdkVersion;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }
}
