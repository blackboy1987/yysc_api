package com.bootx.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;

import java.util.HashSet;
import java.util.Set;

@Entity
public class DicCategory extends OrderedEntity<Long>{

    private String name;

    @OneToMany(mappedBy = "dicCategory",fetch = FetchType.LAZY)
    private Set<Dic> dics = new HashSet<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Dic> getDics() {
        return dics;
    }

    public void setDics(Set<Dic> dics) {
        this.dics = dics;
    }
}
