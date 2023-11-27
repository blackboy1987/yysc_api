package com.bootx.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;

@Entity
public class Dic extends OrderedEntity<Long>{

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    private DicCategory dicCategory;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DicCategory getDicCategory() {
        return dicCategory;
    }

    public void setDicCategory(DicCategory dicCategory) {
        this.dicCategory = dicCategory;
    }
}
