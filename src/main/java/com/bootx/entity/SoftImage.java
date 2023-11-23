package com.bootx.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @author black
 */
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class SoftImage extends BaseEntity<Long> {

    @JsonView({PageView.class})
    private String url;

    /**
     * 0：横屏
     * 1：竖屏
     */
    private Integer type;

    /**
     * 状态
     * 0：待审核
     * 1：已通过
     * 2：已拒绝
     */
    private Integer status;

    @ManyToOne(fetch = FetchType.LAZY)
    private Soft soft;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Soft getSoft() {
        return soft;
    }

    public void setSoft(Soft soft) {
        this.soft = soft;
    }
}
