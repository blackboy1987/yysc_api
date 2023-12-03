package com.bootx.dao.impl;

import com.bootx.dao.ComplaintDao;
import com.bootx.entity.Complaint;
import org.springframework.stereotype.Repository;

/**
 * @author black
 */
@Repository
public class ComplaintDaoImpl extends BaseDaoImpl<Complaint,Long> implements ComplaintDao {
}
