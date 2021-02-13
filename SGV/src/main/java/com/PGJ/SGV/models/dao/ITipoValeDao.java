package com.PGJ.SGV.models.dao;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.PGJ.SGV.models.entity.Conductor;
import com.PGJ.SGV.models.entity.TipoVale;
import com.PGJ.SGV.models.entity.Vehiculo;
import com.PGJ.SGV.models.entity.VehiculoDetalle;

public interface ITipoValeDao extends PagingAndSortingRepository<TipoVale, Long> {

	@Query("select count(t) from TipoVale t")
	public Long tipovalestotales();
	
	@Query("select t.month_28 from TipoVale t where t.id_vale = ?1")
	public String month28(Long id_vale);
	
	@Query("select t.month_29 from TipoVale t where t.id_vale = ?1")
	public String month29(Long id_vale);
	
	@Query("select t.month_30 from TipoVale t where t.id_vale = ?1")
	public String month30(Long id_vale);
	
	@Query("select t.month_31 from TipoVale t where t.id_vale = ?1")
	public String month31(Long id_vale);
		
}
