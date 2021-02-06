package com.PGJ.SGV.models.dao;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.PGJ.SGV.models.entity.Conductor;
import com.PGJ.SGV.models.entity.TipoVale;

public interface ITipoValeDao extends PagingAndSortingRepository<TipoVale, Long> {

	@Query("select count(t) from TipoVale t")
	public Long tipovalestotales();
}
