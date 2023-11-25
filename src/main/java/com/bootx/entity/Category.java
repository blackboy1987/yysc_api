package com.bootx.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class Category extends OrderedEntity<Long> {

    @JsonView({PageView.class})
    private String name;

    @Column(unique = true,updatable = true,nullable = false)
    private String url;

    @ManyToOne(fetch = FetchType.LAZY)
    private Category parent;

    @OneToMany(mappedBy = "parent",fetch = FetchType.LAZY)
    private Set<Category> children = new HashSet<>();

    @ManyToMany(mappedBy = "categories",fetch = FetchType.LAZY)
    private Set<Soft> softs = new HashSet<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Category getParent() {
        return parent;
    }

    public void setParent(Category parent) {
        this.parent = parent;
    }

    public Set<Category> getChildren() {
        return children;
    }

    public void setChildren(Set<Category> children) {
        this.children = children;
    }

    public Set<Soft> getSofts() {
        return softs;
    }

    public void setSofts(Set<Soft> softs) {
        this.softs = softs;
    }
}
