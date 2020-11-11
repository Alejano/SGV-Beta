package com.PGJ.SGV.models.dao;

import org.springframework.data.repository.PagingAndSortingRepository;
import com.PGJ.SGV.models.entity.OdometroAcombus;


public interface IOdomDao extends PagingAndSortingRepository<OdometroAcombus, Long> {
	
}
