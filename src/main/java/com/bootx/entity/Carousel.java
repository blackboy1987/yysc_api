package com.bootx.entity;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.Entity;

import java.util.Date;

@Entity
public class Carousel extends BaseEntity<Long>{

    @JsonView({PageView.class})
    private String image;

    @JsonView({PageView.class})
    private String logo;

    @JsonView({PageView.class})
    private String title1;

    @JsonView({PageView.class})
    private String title2;

    @JsonView({PageView.class})
    private String downloadUrl;

    @JsonView({PageView.class})
    private Date beginDate;

    @JsonView({PageView.class})
    private Date endDate;


    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getTitle1() {
        return title1;
    }

    public void setTitle1(String title1) {
        this.title1 = title1;
    }

    public String getTitle2() {
        return title2;
    }

    public void setTitle2(String title2) {
        this.title2 = title2;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
