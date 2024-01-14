
package com.bootx.service.impl;

import com.bootx.common.Pageable;
import com.bootx.dao.MemberDao;
import com.bootx.entity.Member;
import com.bootx.entity.MemberRank;
import com.bootx.service.MemberService;
import com.bootx.util.JWTUtils;
import com.bootx.util.WebUtils;
import io.jsonwebtoken.Claims;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

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
			System.out.println("current:"+token);
			Claims claims = JWTUtils.parseToken(token);
            assert claims != null;
            String id = claims.getId();
			return super.find(Long.valueOf(id));
		}catch (Exception e){
			e.printStackTrace();
			return null;
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
	public List<Map<String, Object>> search(String keywords, Pageable pageable) {
		if(StringUtils.isBlank(keywords)){
			return Collections.emptyList();
		}
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append("select ");
		stringBuffer.append("member.id, ");
		stringBuffer.append("member.avatar, ");
		stringBuffer.append("member.username, ");
		stringBuffer.append("member.remainPoint point, ");
		stringBuffer.append("memberrank.name rankName, ");
		stringBuffer.append("(select count(fan.id) from fan where member_id=1 and fan.fan_id=member.id) isConcern ");
		stringBuffer.append("from member,memberrank ");
		stringBuffer.append("where memberrank.id=member.memberRank_id ");
		stringBuffer.append("and member.username like ? ");
		stringBuffer.append(" order by member.createdDate desc ");
		stringBuffer.append("limit ?,?; ");


		return jdbcTemplate.queryForList(stringBuffer.toString(), "%"+keywords+"%",(pageable.getPageNumber()-1)*pageable.getPageSize(),pageable.getPageSize());
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

	@Override
	@Transactional(readOnly = true)
	public boolean emailExists(String email) {
		return memberDao.exists("email", StringUtils.lowerCase(email));
	}

	@Override
	@Transactional(readOnly = true)
	public boolean emailUnique(Long id, String email) {
		return memberDao.unique(id, "email", StringUtils.lowerCase(email));
	}

	@Override
	@Transactional(readOnly = true)
	public Member findByEmail(String email) {
		return memberDao.find("email", StringUtils.lowerCase(email));
	}


}