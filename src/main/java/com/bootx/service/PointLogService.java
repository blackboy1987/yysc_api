
package com.bootx.service;

import com.bootx.common.Page;
import com.bootx.common.Pageable;
import com.bootx.entity.Member;
import com.bootx.entity.PointLog;

public interface PointLogService extends BaseService<PointLog, Long> {

	/**
	 * 查找积分记录分页
	 * 
	 * @param member
	 *            会员
	 * @param pageable
	 *            分页信息
	 * @return 积分记录分页
	 */
	Page<PointLog> findPage(Member member, Pageable pageable);

	PointLog create(Member member, Long rewardPoints, String memo,Integer type);
}