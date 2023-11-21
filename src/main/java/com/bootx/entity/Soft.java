package com.bootx.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import org.hibernate.validator.constraints.Length;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @author black
 */
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class Soft extends BaseEntity<Long> {

    @JsonView({PageView.class})
    private String name;

    @Column(unique = true,updatable = false,nullable = false)
    @JsonView({PageView.class})
    private String url;

    @ManyToMany(fetch = FetchType.LAZY)
    private Set<Category> categories = new HashSet<>();

    @JsonView({PageView.class})
    private String size;

    @JsonView({PageView.class})
    private String memo;

    @JsonView({PageView.class})
    private String logo;

    @JsonView({PageView.class})
    private String fullName;

    @JsonView({PageView.class})
    private String updateDate;

    @JsonView({PageView.class})
    private String downloadUrl;

    private Long downloadCount;

    @JsonView({PageView.class})
    @Column(columnDefinition = "longtext")
    private String description;

    @JsonView({PageView.class})
    @Column(length = 2000)
    private String images;

    /**
     * 评分
     */
    private Double score;

    /**
     * 今日下载数
     */
    @Column(nullable = false)
    private Long todayDownloads;

    /**
     * 周下载数
     */
    @Column(nullable = false)
    private Long weekDownloads;

    /**
     * 月下载数
     */
    @Column(nullable = false)
    private Long monthDownloads;

    /**
     * 下载数
     */
    @Column(nullable = false)
    private Long downloads;

    /**
     * 今日下载数更新日期
     */
    @Column(nullable = false)
    private Date todayDownloadsDate;

    /**
     * 周点下载更新日期
     */
    @Column(nullable = false)
    private Date weekDownloadsDate;

    /**
     * 月下载数更新日期
     */
    @Column(nullable = false)
    private Date monthDownloadsDate;

    private Integer reviewCount;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public Long getDownloadCount() {
        return downloadCount;
    }

    public void setDownloadCount(Long downloadCount) {
        this.downloadCount = downloadCount;
    }

    public Long getTodayDownloads() {
        return todayDownloads;
    }

    public void setTodayDownloads(Long todayDownloads) {
        this.todayDownloads = todayDownloads;
    }

    public Long getWeekDownloads() {
        return weekDownloads;
    }

    public void setWeekDownloads(Long weekDownloads) {
        this.weekDownloads = weekDownloads;
    }

    public Long getMonthDownloads() {
        return monthDownloads;
    }

    public void setMonthDownloads(Long monthDownloads) {
        this.monthDownloads = monthDownloads;
    }

    public Long getDownloads() {
        return downloads;
    }

    public void setDownloads(Long downloads) {
        this.downloads = downloads;
    }

    public Date getTodayDownloadsDate() {
        return todayDownloadsDate;
    }

    public void setTodayDownloadsDate(Date todayDownloadsDate) {
        this.todayDownloadsDate = todayDownloadsDate;
    }

    public Date getWeekDownloadsDate() {
        return weekDownloadsDate;
    }

    public void setWeekDownloadsDate(Date weekDownloadsDate) {
        this.weekDownloadsDate = weekDownloadsDate;
    }

    public Date getMonthDownloadsDate() {
        return monthDownloadsDate;
    }

    public void setMonthDownloadsDate(Date monthDownloadsDate) {
        this.monthDownloadsDate = monthDownloadsDate;
    }

    public Integer getReviewCount() {
        return reviewCount;
    }

    public void setReviewCount(Integer reviewCount) {
        this.reviewCount = reviewCount;
    }
}