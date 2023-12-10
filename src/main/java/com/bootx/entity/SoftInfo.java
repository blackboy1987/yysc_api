package com.bootx.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;

/**
 * @author black
 */
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class SoftInfo extends BaseEntity<Long> {

    @OneToOne(fetch = FetchType.LAZY)
    private Soft soft;

    /**
     * 投稿说明
     */
    @JsonView({PageView.class})
    @Column(columnDefinition = "longtext")
    private String memo;

    /**
     * 应用介绍
     */
    @JsonView({PageView.class})
    @Column(columnDefinition = "longtext")
    private String introduce;

    /**
     * 更新内容
     */
    @JsonView({PageView.class})
    @Column(columnDefinition = "longtext")
    private String updatedContent;




    public Soft getSoft() {
        return soft;
    }

    public void setSoft(Soft soft) {
        this.soft = soft;
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
}
