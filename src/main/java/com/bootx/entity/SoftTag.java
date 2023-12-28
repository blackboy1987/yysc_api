package com.bootx.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.NotEmpty;

import java.util.HashSet;
import java.util.Set;

/**
 * @author black
 */
@Entity
public class SoftTag extends BaseEntity<Long> {

    @NotEmpty
    @Column(nullable = false,unique = true)
    private String name;

    @ManyToMany(mappedBy = "softTags",fetch = FetchType.LAZY)
    private Set<Soft> softs = new HashSet<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Soft> getSofts() {
        return softs;
    }

    public void setSofts(Set<Soft> softs) {
        this.softs = softs;
    }
}
