package com.PGJ.SGV.models.dao;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;


import com.PGJ.SGV.models.entity.VehiculoEstado;

public interface IEstadoVehiculoDao extends CrudRepository<VehiculoEstado, Long> {
	
		
	@Query("select e from VehiculoEstado e inner join Vehiculo v on v.vehiculo_estado.id_estado = e.id_estado where v.placa = ?1")
	public VehiculoEstado findbyplaca(String placa);

}
