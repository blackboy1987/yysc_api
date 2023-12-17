
package com.bootx.service.impl;


import com.bootx.common.Page;
import com.bootx.common.Pageable;
import com.bootx.dao.PointLogDao;
import com.bootx.entity.Member;
import com.bootx.entity.PointLog;
import com.bootx.service.PointLogService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PointLogServiceImpl extends BaseServiceImpl<PointLog, Long> implements PointLogService {

	@Resource
	private PointLogDao pointLogDao;

	@Override
	@Transactional(readOnly = true)
	public Page<PointLog> findPage(Member member, Pageable pageable) {
		return pointLogDao.findPage(member, pageable);
	}

	@Override
	public PointLog create(Member member, Long point, String memo,Integer type) {
		PointLog pointLog = new PointLog();
		pointLog.setBeforePoint(member.getRemainPoint());
		pointLog.setMember(member);
		pointLog.setType(type);
		pointLog.setCredit(point);
		pointLog.setMemo(memo);
		pointLog.setAfterPoint(pointLog.getBeforePoint()+pointLog.getCredit());
		return super.save(pointLog);
	}

}