package com.bootx.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * 软件的投币记录
 * @author black
 */
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class SoftIconLog extends BaseEntity<Long> {

    @ManyToOne(fetch = FetchType.LAZY)
    private Soft soft;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    private Long point;

    private String memo;


    public Soft getSoft() {
        return soft;
    }

    public void setSoft(Soft soft) {
        this.soft = soft;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public Long getPoint() {
        return point;
    }

    public void setPoint(Long point) {
        this.point = point;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
}
