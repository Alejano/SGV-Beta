package com.PGJ.SGV.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.PGJ.SGV.models.dao.ILogsAuditDao;
import com.PGJ.SGV.models.entity.LogsAudit;


@Service
public class ILogsAuditServiceImpl  implements ILogsAuditService   {
	
	
	@Autowired
	private ILogsAuditDao LogsAuditDao;
	

	@Transactional(readOnly = true)
	public List<LogsAudit> findAll() {
		// TODO Auto-generated method stub
		return (List<LogsAudit>) LogsAuditDao.findAll();
	}
   
	@Transactional(readOnly = true)
	public LogsAudit findOne(Long id) {
		// TODO Auto-generated method stub
		return LogsAuditDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public void save(LogsAudit logs) {
		// TODO Auto-generated method stub
		LogsAuditDao.save(logs);
	}
   
	@Override
	public Long logstotales() {
		return LogsAuditDao.logstotales();
	}
	
}
