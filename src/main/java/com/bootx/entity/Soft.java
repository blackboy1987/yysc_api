package com.bootx.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.Date;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * @author black
 */
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class Soft extends BaseEntity<Long> {

    @JsonView({PageView.class,DownloadView.class})
    private String name;

    /**
     * 渠道
     */
    private String channel;

    @Column(unique = true)
    @JsonView({PageView.class})
    private String url;

    @ManyToMany(fetch = FetchType.LAZY)
    private Set<Category> categories = new HashSet<>();

    @JsonView({PageView.class,DownloadView.class})
    private String size;

    @JsonView({PageView.class,DownloadView.class})
    private String logo;

    @JsonView({PageView.class})
    private String fullName;

    @JsonView({PageView.class})
    private String updateDate;

    @JsonView({PageView.class,DownloadView.class})
    private String downloadUrl;

    /**
     * 下载地址的密码
     */
    @JsonView({PageView.class})
    private String password;




    @OneToOne(mappedBy = "soft", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private SoftInfo softInfo;

    @OneToOne(mappedBy = "soft", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private SoftExt softExt;

    @OneToMany(mappedBy = "soft",fetch = FetchType.LAZY)
    private Set<SoftImage> softImages = new HashSet<>();

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
    private Date todayDownloadsDate;

    /**
     * 周点下载更新日期
     */
    private Date weekDownloadsDate;

    /**
     * 月下载数更新日期
     */
    private Date monthDownloadsDate;

    /**
     * 今日评论
     */
    @Column(nullable = false)
    private Long todayReviewCount;

    /**
     * 周评论
     */
    @Column(nullable = false)
    private Long weekReviewCount;

    /**
     * 月评论
     */
    @Column(nullable = false)
    private Long monthReviewCount;

    /**
     * 评论数
     */
    private Integer reviewCount;

    @JsonView({DownloadView.class})
    private String versionName;

    private String versionCode;

    /**
     * 今日投币人数
     */
    @Column(nullable = false)
    private Long todayDonationMember;

    /**
     * 周投币人数
     */
    @Column(nullable = false)
    private Long weekDonationMember;

    /**
     * 月投币人数
     */
    @Column(nullable = false)
    private Long monthDonationMember;

    /**
     * 投币人数
     */
    @Column(nullable = false)
    private Integer donationMember;

    /**
     * 今日投币数
     */
    @Column(nullable = false)
    private Long todayDonationIcon;

    /**
     * 周投币数
     */
    @Column(nullable = false)
    private Long weekDonationIcon;

    /**
     * 月投币数
     */
    @Column(nullable = false)
    private Long monthDonationIcon;

    /**
     * 投币数
     */
    @Column(nullable = false)
    private Long donationIcon;
    /**
     * 状态
     * 0：待审
     * 1：审核通过
     * 2：审核拒绝
     * 100: 草稿
     * 101: 用户删除
     * 102：后台删除
     */
    @Column(nullable = false)
    private Integer status;

    @NotNull
    @JoinColumn(nullable = false,updatable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    private Integer minSdkVersion;
    private Integer targetSdkVersion;

    @JsonView({DownloadView.class})
    private String packageName;


    @ManyToMany(fetch = FetchType.LAZY)
    private Set<SoftTag> softTags = new HashSet<>();



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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
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

    public Long getTodayReviewCount() {
        return todayReviewCount;
    }

    public void setTodayReviewCount(Long todayReviewCount) {
        this.todayReviewCount = todayReviewCount;
    }

    public Long getWeekReviewCount() {
        return weekReviewCount;
    }

    public void setWeekReviewCount(Long weekReviewCount) {
        this.weekReviewCount = weekReviewCount;
    }

    public Long getMonthReviewCount() {
        return monthReviewCount;
    }

    public void setMonthReviewCount(Long monthReviewCount) {
        this.monthReviewCount = monthReviewCount;
    }

    public Integer getReviewCount() {
        return reviewCount;
    }

    public void setReviewCount(Integer reviewCount) {
        this.reviewCount = reviewCount;
    }


    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public SoftInfo getSoftInfo() {
        return softInfo;
    }

    public void setSoftInfo(SoftInfo softInfo) {
        this.softInfo = softInfo;
    }

    public Set<SoftImage> getSoftImages() {
        return softImages;
    }

    public void setSoftImages(Set<SoftImage> softImages) {
        this.softImages = softImages;
    }

    public SoftExt getSoftExt() {
        return softExt;
    }

    public void setSoftExt(SoftExt softExt) {
        this.softExt = softExt;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public Integer getDonationMember() {
        return donationMember;
    }

    public void setDonationMember(Integer donationMember) {
        this.donationMember = donationMember;
    }

    public Long getDonationIcon() {
        return donationIcon;
    }

    public void setDonationIcon(Long donationIcon) {
        this.donationIcon = donationIcon;
    }

    public String getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(String versionCode) {
        this.versionCode = versionCode;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
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

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public Long getTodayDonationMember() {
        return todayDonationMember;
    }

    public void setTodayDonationMember(Long todayDonationMember) {
        this.todayDonationMember = todayDonationMember;
    }

    public Long getWeekDonationMember() {
        return weekDonationMember;
    }

    public void setWeekDonationMember(Long weekDonationMember) {
        this.weekDonationMember = weekDonationMember;
    }

    public Long getMonthDonationMember() {
        return monthDonationMember;
    }

    public void setMonthDonationMember(Long monthDonationMember) {
        this.monthDonationMember = monthDonationMember;
    }

    public Long getTodayDonationIcon() {
        return todayDonationIcon;
    }

    public void setTodayDonationIcon(Long todayDonationIcon) {
        this.todayDonationIcon = todayDonationIcon;
    }

    public Long getWeekDonationIcon() {
        return weekDonationIcon;
    }

    public void setWeekDonationIcon(Long weekDonationIcon) {
        this.weekDonationIcon = weekDonationIcon;
    }

    public Long getMonthDonationIcon() {
        return monthDonationIcon;
    }

    public void setMonthDonationIcon(Long monthDonationIcon) {
        this.monthDonationIcon = monthDonationIcon;
    }

    public Set<SoftTag> getSoftTags() {
        return softTags;
    }

    public void setSoftTags(Set<SoftTag> softTags) {
        this.softTags = softTags;
    }

    public interface DownloadView extends DefaultView{}
    
    
    public void init(){
        Random random = new Random();
        this.setDownloads(random.nextLong(1000000));
        this.setTodayDownloads(random.nextLong(1000000));
        this.setWeekDownloads(random.nextLong(1000000));
        this.setMonthDownloads(random.nextLong(1000000));
        this.setTodayDownloadsDate(new Date());
        this.setWeekDownloadsDate(new Date());
        this.setMonthDownloadsDate(new Date());
        this.setDonationIcon(0L);
        this.setDonationMember(0);
        this.setMonthDonationIcon(0L);
        this.setMonthDonationMember(0L);
        this.setWeekDonationIcon(0L);
        this.setWeekDonationMember(0L);
        this.setMonthReviewCount(0L);
        this.setWeekReviewCount(0L);
        this.setStatus(0);
        this.setTodayDonationIcon(0L);
        this.setTodayDonationMember(0L);
        this.setTodayReviewCount(0L);
        this.setScore(new Random().nextDouble(10));
    }
}
