package com.bootx.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

/**
 * @author black
 */
@Entity
public class Setting extends BaseEntity<Long>{

    @Column(length = 2000)
    private String homeCenterBar;


    public String getHomeCenterBar() {
        return homeCenterBar;
    }

    public void setHomeCenterBar(String homeCenterBar) {
        this.homeCenterBar = homeCenterBar;
    }
}
