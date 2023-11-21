
package com.bootx.dao.impl;

import com.bootx.dao.OptLogDao;
import com.bootx.entity.OptLog;
import org.springframework.stereotype.Repository;

@Repository
public class OptLogDaoImpl extends BaseDaoImpl<OptLog, Long> implements OptLogDao {

	@Override
	public void removeAll() {
		String jpql = "delete from OptLog optLog";
		entityManager.createQuery(jpql).executeUpdate();
	}

}