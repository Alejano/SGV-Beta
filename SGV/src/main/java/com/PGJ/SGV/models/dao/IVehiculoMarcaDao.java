package com.PGJ.SGV.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import com.PGJ.SGV.models.entity.VehiculoMarca;

public interface IVehiculoMarcaDao extends CrudRepository<VehiculoMarca, Long> {

	@Query("select distinct v.nombre_marca from VehiculoMarca v")
	public List<String> findallMarcaUnica();
	
	@Query("select distinct v.clase from VehiculoMarca v")
	public List<String> findallClaseUnica();
	
	@Query("select distinct v.nombre_submarca from VehiculoMarca v where v.nombre_marca like ?1")
	public List<String> findallsubMarcaUnica(String nombre_marca);
	
	@Query("select m from VehiculoMarca m where m.nombre_marca like ?1")
	public List<VehiculoMarca> findByMarca(String nombre_marca);
	
	@Query("select distinct v.modelo from VehiculoMarca v where v.nombre_marca like ?1 and v.nombre_submarca like ?2")
	public List<String> findallModeloUnico(String nombre_marca, String nombre_submarca);
	
	@Query("select distinct v.tipo from VehiculoMarca v where v.nombre_marca like ?1 and v.nombre_submarca like ?2 and v.modelo like ?3")
	public List<String> findallTipoUnico(String nombre_marca, String nombre_submarca, String modelo);
	
	@Query("select distinct v.tipo from VehiculoMarca v where v.nombre_marca like ?1 and v.nombre_submarca like ?2 and v.modelo like ?3 and v.tipo like ?4")
	public List<String> findallClaseUnico(String nombre_marca,String nombre_submarca,String modelo,String tipo);	
	
	@Query("select v from VehiculoMarca v where v.nombre_marca like ?1 and v.nombre_submarca like ?2 and v.modelo like ?3 and v.tipo like ?4 and v.tipo like ?5")
	public VehiculoMarca findMarca(String nombre_marca,String nombre_submarca,String modelo,String tipo,String clase);	
	
}
