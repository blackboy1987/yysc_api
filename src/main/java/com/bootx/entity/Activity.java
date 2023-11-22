package com.bootx.entity;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.Entity;

import java.util.Date;

/**
 * @author black
 */
@Entity
public class Activity extends BaseEntity<Long>{

    @JsonView({PageView.class})
    private String image;

    @JsonView({PageView.class})
    private String title;

    @JsonView({PageView.class})
    private String url;

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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
