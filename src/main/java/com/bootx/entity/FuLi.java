package com.bootx.entity;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.Entity;

/**
 * 福利
 * @author black
 */
@Entity
public class FuLi extends OrderedEntity<Long>{

    @JsonView({ListView.class})
    private String title1;

    @JsonView({ListView.class})
    private String title2;

    @JsonView({ListView.class})
    private String image;

    @JsonView({ListView.class})
    private String url;

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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    public interface ListView extends DefaultView {

    }
}
