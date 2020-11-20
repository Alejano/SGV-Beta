package com.PGJ.SGV.models.dao;

import java.util.List;

//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.PGJ.SGV.models.entity.AsigCombustible;
import com.PGJ.SGV.models.entity.AsigCombustibleExt;


public interface IAsigComExtDao extends PagingAndSortingRepository<AsigCombustibleExt, Long> {
	

	@Query("select a from AsigCombustibleExt a inner join AsigCombustible c on a.asigCombustible.id_asignacion = c.id_asignacion inner join Vehiculo v on c.vehiculo.id_vehiculo = v.id_vehiculo where v.id_vehiculo = ?1")
	public List<AsigCombustibleExt> findAsigVehi(long id_vehiculo);
	
	@Query("select a from AsigCombustibleExt a where a.asigCombustible.id_asignacion = ?1")
	public List<AsigCombustibleExt> findextId(long id_asignacion);
	
	@Query("select p.id_asignacion from AsigCombustibleExt p where p.id_asignacion = (select max(p.id_asignacion) from AsigCombustibleExt p)")
	public int ultimoId();
	


}
