package com.PGJ.SGV.service;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.PGJ.SGV.models.entity.LogsAudit;


public interface ILogsAuditService {
	
	public List<LogsAudit> findAll(); 
	
	public LogsAudit findOne(Long id);
	
	public void save(LogsAudit logs);
	
	public Long logstotales();

}
