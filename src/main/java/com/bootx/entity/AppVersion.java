package com.bootx.entity;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity
public class AppVersion extends BaseEntity<Long>{

    /**
     * 版本号
     */
    @JsonView({PageView.class})
    private String versionCode;

    /**
     * 下载地址
     */
    @JsonView({PageView.class})
    private String downloadUrl;

    /**
     * 更新说明
     */
    @JsonView({PageView.class})
    @Column(length = 2000)
    private String memo;


    public String getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(String versionCode) {
        this.versionCode = versionCode;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
}
