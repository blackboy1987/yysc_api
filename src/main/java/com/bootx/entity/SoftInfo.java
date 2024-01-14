package com.bootx.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import org.apache.commons.lang3.StringUtils;

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
        if(StringUtils.isBlank(memo)){
            return "";
        }
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getIntroduce() {
        if(StringUtils.isBlank(introduce)){
            return "";
        }
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public String getUpdatedContent() {
        if(StringUtils.isBlank(updatedContent)){
            return "";
        }
        return updatedContent;
    }

    public void setUpdatedContent(String updatedContent) {
        this.updatedContent = updatedContent;
    }
}
