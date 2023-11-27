package com.bootx.entity;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Transient;

@Entity
public class Dic extends OrderedEntity<Long>{

    @JsonView({PageView.class})
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

    @Transient
    @JsonView({PageView.class})
    public String getCategoryName(){
        if(dicCategory!=null){
            return dicCategory.getName();
        }
        return null;
    }

}
