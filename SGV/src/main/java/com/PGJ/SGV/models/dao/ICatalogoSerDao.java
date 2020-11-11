package com.PGJ.SGV.models.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import com.PGJ.SGV.models.entity.CatalogoServicio;

public interface ICatalogoSerDao extends PagingAndSortingRepository<CatalogoServicio,Long>{
	
	@Query("select x from CatalogoServicio x where x.servicio like %?1% or x.auto like %?1% or x.motocicleta like %?1% or x.blindado like %?1% or x.gasolina like %?1% or x.diesel like %?1% or x.gas like %?1% or x.un_cilindro like %?1% or x.dos_cilindros like %?1% or x.tres_cilindros like %?1% or x.cuatro_cilindros like %?1% or x.cinco_cilindros like %?1% or x.seis_cilindros like %?1% or x.ocho_cilindros like %?1% or x.dies_cilindros like %?1% or x.categoria like %?1%")
	public Page<CatalogoServicio> finCatElemntPage(String elemento,Pageable pageable);
	
	@Query("select count(c)from CatalogoServicio c")
	public Long totalCatalogoServicios();
		
}
