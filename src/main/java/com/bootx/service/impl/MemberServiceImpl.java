
package com.bootx.service.impl;

import com.bootx.dao.MemberDao;
import com.bootx.entity.Member;
import com.bootx.entity.MemberRank;
import com.bootx.service.MemberService;
import com.bootx.util.JWTUtils;
import com.bootx.util.WebUtils;
import io.jsonwebtoken.Claims;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

/**
 * @author black
 */
@Service
public class MemberServiceImpl extends BaseServiceImpl<Member, Long> implements MemberService {

	@Resource
	private MemberDao memberDao;

	@Override
	@Transactional(readOnly = true)
	public boolean usernameExists(String username) {
		return memberDao.exists("username", StringUtils.lowerCase(username));
	}

	@Override
	@Transactional(readOnly = true)
	public Member findByUsername(String username) {
		return memberDao.find("username", StringUtils.lowerCase(username));
	}

	@Override
	public boolean usernameUnique(Long id, String username) {
		return memberDao.unique(id, "username", StringUtils.lowerCase(username));
	}

	@Override
	public Member getCurrent() {
		try {
			HttpServletRequest request = WebUtils.getRequest();
            assert request != null;
			String token = request.getHeader("token");
			Claims claims = JWTUtils.parseToken(token);
            assert claims != null;
            String id = claims.getId();
			return super.find(Long.valueOf(id));
		}catch (Exception e){
			return find(1L);
		}
	}

	@Override
	public void lock(Member member) {
		Long increment = redisService.increment(Member.FAILED_LOGIN_ATTEMPTS_CACHE_NAME + ":" + member.getId());
		if(increment>=5){
			member.setIsLocked(true);
			member.setLockDate(new Date());
			super.update(member);
		}
	}

	@Override
	public void unLock(Member member) {
		redisService.delete(Member.FAILED_LOGIN_ATTEMPTS_CACHE_NAME + ":" + member.getId());
	}


	@Override
	public Member update(Member member) {
		List<Map<String, Object>> maps = jdbcTemplate.queryForList("select id,point from memberrank where point>=? order by point limit 1;", member.getPoint());
		// TODO 调整会员等级
		if(!maps.isEmpty()){
			try {
				Long memberRankId = Long.valueOf(maps.getFirst().get("id") + "");

			}catch (Exception e){

			}
		}




		return super.update(member);
	}
}