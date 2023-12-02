
package com.bootx.dao;


import com.bootx.entity.MemberOptLog;

public interface MemberOptLogDao extends BaseDao<MemberOptLog, Long> {

	/**
	 * 删除所有
	 */
	void removeAll();

}