package com.bootx.service.impl;

import com.bootx.dao.MemberOptLogDao;
import com.bootx.dao.OptLogDao;
import com.bootx.entity.MemberOptLog;
import com.bootx.entity.OptLog;
import com.bootx.service.MemberOptLogService;
import com.bootx.service.OptLogService;
import jakarta.annotation.Resource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class MemberOptLogServiceImpl extends BaseServiceImpl<MemberOptLog, Long> implements MemberOptLogService {

	@Resource
	private MemberOptLogDao memberOptLogDao;

	@Override
	@Async
	public void create(MemberOptLog optLog) {
		memberOptLogDao.persist(optLog);
	}

	@Override
	public void clear() {
		memberOptLogDao.removeAll();
	}

}