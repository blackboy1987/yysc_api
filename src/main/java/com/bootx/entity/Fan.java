package com.bootx.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;

/**
 * 关注
 * @author black
 */
@Entity
public class Fan extends BaseEntity<Long>{

    /**
     * 关注
     */
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    /**
     * 被关注的人
     */
    @ManyToOne(fetch = FetchType.LAZY)
    private Member fan;
}
