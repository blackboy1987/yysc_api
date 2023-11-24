package com.bootx.dao.impl;

import com.bootx.dao.AppVersionDao;
import com.bootx.entity.AppVersion;
import org.springframework.stereotype.Repository;

/**
 * @author black
 */
@Repository
public class AppVersionDaoImpl extends BaseDaoImpl<AppVersion,Long> implements AppVersionDao {
}
