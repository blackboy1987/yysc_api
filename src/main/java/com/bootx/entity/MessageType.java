package com.bootx.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotEmpty;

/**
 * 消息类型
 */
@Entity
public class MessageType extends BaseEntity<Long>{

    /**
     * 跳转类型
     */
    private Integer type;

    @NotEmpty
    @Column(nullable = false,unique = true)
    private String name;

    private String image;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
