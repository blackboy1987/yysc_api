package com.bootx.entity;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

/**
 * @author black
 */
@Entity
public class Member extends User {

    /**
     * "登录失败尝试次数"缓存名称
     */
    public static final String FAILED_LOGIN_ATTEMPTS_CACHE_NAME = "memberFailedLoginAttempts";


    /**
     * 用户名
     */
    @NotEmpty(groups = Save.class)
    @Length(min = 4, max = 20)
    @Pattern.List({ @Pattern(regexp = "^[0-9a-zA-Z_\\u4e00-\\u9fa5]+$"), @Pattern(regexp = "^.*[^\\d].*$") })
    @Column(nullable = false, updatable = false,unique = true)
    @JsonView({PageView.class})
    private String username;

    /**
     * 密码
     */
    @NotEmpty(groups = Save.class)
    @Length(min = 4, max = 20)
    @Transient
    private String password;

    /**
     * 加密密码
     */
    @Column(nullable = false)
    private String encodedPassword;

    /**
     * 积分
     */
    @NotNull
    @Min(0)
    @Column(nullable = false)
    @JsonView({PageView.class})
    private Long point;
    @NotNull
    @Min(0)
    @Column(nullable = false)
    @JsonView({PageView.class})
    private Long remainPoint;


    /**
     * 累计签到的天数
     */
    @NotNull
    @Min(0)
    @Column(nullable = false)
    @JsonView({PageView.class})
    private Integer signInDays;


    /**
     * 连续签到的天数
     */
    @NotNull
    @Min(0)
    @Column(nullable = false)
    @JsonView({PageView.class})
    private Integer continuousSignInDays;

    /**
     * 最近一次签到的时间
     */
    @JsonView({PageView.class})
    private Date signInDate;
    @JsonView({PageView.class})
    private String avatar;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    private MemberRank memberRank;

    /**
     * 获取用户名
     *
     * @return 用户名
     */
    public String getUsername() {
        return username;
    }

    /**
     * 设置用户名
     *
     * @param username
     *            用户名
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * 获取密码
     *
     * @return 密码
     */
    public String getPassword() {
        return password;
    }

    /**
     * 设置密码
     *
     * @param password
     *            密码
     */
    public void setPassword(String password) {
        this.password = password;
        if (password != null) {
            setEncodedPassword(DigestUtils.md5Hex(password));
        }
    }

    /**
     * 获取加密密码
     *
     * @return 加密密码
     */
    public String getEncodedPassword() {
        return encodedPassword;
    }

    /**
     * 设置加密密码
     *
     * @param encodedPassword
     *            加密密码
     */
    public void setEncodedPassword(String encodedPassword) {
        this.encodedPassword = encodedPassword;
    }

    public Long getPoint() {
        return point;
    }

    public void setPoint(Long point) {
        this.point = point;
    }

    public Long getRemainPoint() {
        return remainPoint;
    }

    public void setRemainPoint(Long remainPoint) {
        this.remainPoint = remainPoint;
    }

    public Integer getSignInDays() {
        return signInDays;
    }

    public void setSignInDays(Integer signInDays) {
        this.signInDays = signInDays;
    }

    public Integer getContinuousSignInDays() {
        return continuousSignInDays;
    }

    public void setContinuousSignInDays(Integer continuousSignInDays) {
        this.continuousSignInDays = continuousSignInDays;
    }

    public Date getSignInDate() {
        return signInDate;
    }

    public void setSignInDate(Date signInDate) {
        this.signInDate = signInDate;
    }

    public String getAvatar() {
        if(StringUtils.isBlank(avatar)){
            avatar = "https://bootx-tuchuang.oss-cn-hangzhou.aliyuncs.com/avatar/" + ((getId()==null?0:getId()) % 50) + ".png";
        }
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public MemberRank getMemberRank() {
        return memberRank;
    }

    public void setMemberRank(MemberRank memberRank) {
        this.memberRank = memberRank;
    }

    @Transient
    @Override
    public boolean isValidCredentials(Object credentials) {
        return credentials != null && StringUtils.equals(DigestUtils.md5Hex(credentials instanceof char[] ? String.valueOf((char[]) credentials) : String.valueOf(credentials)), getEncodedPassword());
    }

    @Transient
    public String getMemberRankName() {
        if(memberRank!=null){
            return memberRank.getName();
        }
        return null;
    }
}
