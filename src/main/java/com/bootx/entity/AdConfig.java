package com.bootx.entity;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.Entity;

/**
 * @author black
 */
@Entity
public class AdConfig extends BaseEntity<Long>{

    /**
     * 媒体ID
     */
    @JsonView({PageView.class})
    private String mediaId;

    /**
     * 开屏广告ID
     */
    @JsonView({PageView.class})
    private String splashAdId;

    /**
     * 横幅广告ID
     */
    @JsonView({PageView.class})
    private String bannerAdId;

    /**
     * 模板广告ID
     */
    @JsonView({PageView.class})
    private String templateAdId;

    /**
     * 插屏广告ID
     */
    @JsonView({PageView.class})
    private String interAdId;

    /**
     * 信息流广告ID
     */
    @JsonView({PageView.class})
    private String feedAdId;

    /**
     * 模板视频信息流广告ID
     */
    @JsonView({PageView.class})
    private String videoFeedAdId;

    /**
     * 全屏视频广告ID
     */
    @JsonView({PageView.class})
    private String fullScreenVideoAdId;

    /**
     * 激励视频广告ID
     */
    @JsonView({PageView.class})
    private String rewardVideoAdId;


    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }

    public String getSplashAdId() {
        return splashAdId;
    }

    public void setSplashAdId(String splashAdId) {
        this.splashAdId = splashAdId;
    }

    public String getBannerAdId() {
        return bannerAdId;
    }

    public void setBannerAdId(String bannerAdId) {
        this.bannerAdId = bannerAdId;
    }

    public String getTemplateAdId() {
        return templateAdId;
    }

    public void setTemplateAdId(String templateAdId) {
        this.templateAdId = templateAdId;
    }

    public String getInterAdId() {
        return interAdId;
    }

    public void setInterAdId(String interAdId) {
        this.interAdId = interAdId;
    }

    public String getFeedAdId() {
        return feedAdId;
    }

    public void setFeedAdId(String feedAdId) {
        this.feedAdId = feedAdId;
    }

    public String getVideoFeedAdId() {
        return videoFeedAdId;
    }

    public void setVideoFeedAdId(String videoFeedAdId) {
        this.videoFeedAdId = videoFeedAdId;
    }

    public String getFullScreenVideoAdId() {
        return fullScreenVideoAdId;
    }

    public void setFullScreenVideoAdId(String fullScreenVideoAdId) {
        this.fullScreenVideoAdId = fullScreenVideoAdId;
    }

    public String getRewardVideoAdId() {
        return rewardVideoAdId;
    }

    public void setRewardVideoAdId(String rewardVideoAdId) {
        this.rewardVideoAdId = rewardVideoAdId;
    }
}
