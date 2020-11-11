package com.PGJ.SGV.models.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import com.PGJ.SGV.models.entity.LogsAudit;


public interface ILogsAuditDao extends CrudRepository<LogsAudit, Long>{

	@Query("select count(l) from LogsAudit l")
	public Long logstotales();
	
}
