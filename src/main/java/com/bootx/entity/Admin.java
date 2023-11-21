package com.bootx.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.Length;

/**
 * @author black
 */
@Entity
public class Admin extends User {

    /**
     * "登录失败尝试次数"缓存名称
     */
    public static final String FAILED_LOGIN_ATTEMPTS_CACHE_NAME = "adminFailedLoginAttempts";


    /**
     * 用户名
     */
    @NotEmpty(groups = Save.class)
    @Length(min = 4, max = 20)
    @Pattern.List({ @Pattern(regexp = "^[0-9a-zA-Z_\\u4e00-\\u9fa5]+$"), @Pattern(regexp = "^.*[^\\d].*$") })
    @Column(nullable = false, updatable = false)
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




    @Transient
    @Override
    public boolean isValidCredentials(Object credentials) {
        return credentials != null && StringUtils.equals(DigestUtils.md5Hex(credentials instanceof char[] ? String.valueOf((char[]) credentials) : String.valueOf(credentials)), getEncodedPassword());
    }
}
