package com.PGJ.SGV.models.dao;

import java.util.List;

//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.PGJ.SGV.models.entity.AsigCombustible;
import com.PGJ.SGV.models.entity.AsigCombustibleExt;


public interface IAsigComExtDao extends PagingAndSortingRepository<AsigCombustibleExt, Long> {
	

	//@Query("select a from AsigCombustibleExt a inner join AsigCombustible v on a.asigcombustible.id_asignacion = v.id_asignacion where v.id_asignacion like %?1%")
	//public List<AsigCombustibleExt> findAsig(String id_asignacion);
	
	@Query("select a from AsigCombustibleExt a where a.asigCombustible.id_asignacion = ?1")
	public List<AsigCombustibleExt> findextId(long id_asignacion);
	
	@Query("select p.id_asignacion from AsigCombustibleExt p where p.id_asignacion = (select max(p.id_asignacion) from AsigCombustibleExt p)")
	public long ultimoId();
	


}
