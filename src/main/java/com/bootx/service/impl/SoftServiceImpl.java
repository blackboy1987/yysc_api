package com.bootx.service.impl;

import com.bootx.dao.SoftDao;
import com.bootx.entity.Soft;
import com.bootx.service.SoftService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class SoftServiceImpl extends BaseServiceImpl<Soft, Long> implements SoftService {

    @Resource
    private SoftDao softDao;

    @Override
    public Soft findByUrl(String href) {
        return softDao.find("url",href);
    }

    @Override
    public void updateDownloads(Long id,int i) {
        Soft soft = find(id);
        if(soft!=null){
            soft.setDownloads(soft.getDownloads()+i);
        }
    }
}