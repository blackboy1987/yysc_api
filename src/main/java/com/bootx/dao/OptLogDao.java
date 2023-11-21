
package com.bootx.dao;


import com.bootx.entity.OptLog;

public interface OptLogDao extends BaseDao<OptLog, Long> {

	/**
	 * 删除所有
	 */
	void removeAll();

}