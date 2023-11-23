package com.bootx.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToOne;

/**
 * @author black
 */
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class SoftExt extends BaseEntity<Long> {

    @OneToOne(fetch = FetchType.LAZY)
    private Soft soft;

    /**
     * 0: 完全免费
     * 1：会员制
     * 2：没钱不给用
     */
    private Integer paidType;

    /**
     * 0: 无广告
     * 1：少量广告
     * 2：超过广告
     */
    private Integer adType;

    /**
     * 0: 企业开发
     * 1：独立开发
     */
    private Integer operationType;

    /**
     * 0:白嫖
     * 1：Material Design
     * 2：神作
     */
    private Integer featuresType;

    public Soft getSoft() {
        return soft;
    }

    public void setSoft(Soft soft) {
        this.soft = soft;
    }

    public Integer getPaidType() {
        return paidType;
    }

    public void setPaidType(Integer paidType) {
        this.paidType = paidType;
    }

    public Integer getAdType() {
        return adType;
    }

    public void setAdType(Integer adType) {
        this.adType = adType;
    }

    public Integer getOperationType() {
        return operationType;
    }

    public void setOperationType(Integer operationType) {
        this.operationType = operationType;
    }

    public Integer getFeaturesType() {
        return featuresType;
    }

    public void setFeaturesType(Integer featuresType) {
        this.featuresType = featuresType;
    }
}
