package com.bootx.service.impl;

import com.bootx.dao.CategoryDao;
import com.bootx.entity.Category;
import com.bootx.service.CategoryService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl extends BaseServiceImpl<Category, Long> implements CategoryService {

    @Resource
    private CategoryDao categoryDao;

    @Override
    public Category create(Category category) {
        Category current = categoryDao.find("url", category.getUrl());
        if(current==null){
            return super.save(category);
        }
        current.setParent(category.getParent());
        current.setName(category.getName());
        current.setUrl(category.getUrl());
        return super.update(current);
    }
}