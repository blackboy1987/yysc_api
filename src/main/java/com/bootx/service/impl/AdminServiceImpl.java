
package com.bootx.service.impl;

import com.bootx.dao.AdminDao;
import com.bootx.entity.Admin;
import com.bootx.service.AdminService;
import com.bootx.util.JWTUtils;
import com.bootx.util.WebUtils;
import io.jsonwebtoken.Claims;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class AdminServiceImpl extends BaseServiceImpl<Admin, Long> implements AdminService {

	@Resource
	private AdminDao adminDao;

	@Override
	@Transactional(readOnly = true)
	public boolean usernameExists(String username) {
		return adminDao.exists("username", StringUtils.lowerCase(username));
	}

	@Override
	@Transactional(readOnly = true)
	public Admin findByUsername(String username) {
		return adminDao.find("username", StringUtils.lowerCase(username));
	}

	@Override
	public Admin getCurrent() {
		try {
			HttpServletRequest request = WebUtils.getRequest();
            assert request != null;
            String token = request.getHeader("token");
			Claims claims = JWTUtils.parseToken(token);
            assert claims != null;
            String id = claims.getId();
			return super.find(Long.valueOf(id));
		}catch (Exception e){
			return null;
		}
	}

	@Override
	public void lock(Admin admin) {
		Long increment = redisService.increment(Admin.FAILED_LOGIN_ATTEMPTS_CACHE_NAME + ":" + admin.getId());
		if(increment>=5){
			admin.setIsLocked(true);
			admin.setLockDate(new Date());
			super.update(admin);
		}
	}

	@Override
	public void unLock(Admin admin) {
		redisService.delete(Admin.FAILED_LOGIN_ATTEMPTS_CACHE_NAME + ":" + admin.getId());
	}

}