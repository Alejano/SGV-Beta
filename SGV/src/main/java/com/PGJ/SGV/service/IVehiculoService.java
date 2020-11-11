package com.PGJ.SGV.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.PGJ.SGV.models.entity.Vehiculo;
import com.PGJ.SGV.models.entity.VehiculoDetalle;
import com.PGJ.SGV.models.entity.VehiculoEstado;
import com.PGJ.SGV.models.entity.VehiculoFuncion;
import com.PGJ.SGV.models.entity.VehiculoMarca;
import com.PGJ.SGV.models.entity.VehiculoTransmision;

public interface IVehiculoService {
	
	public List<Vehiculo> findAll(); 
	//detalle
	public void saveDetalle(VehiculoDetalle detalle);
	public List<VehiculoDetalle> findAllDetalle(); 
	public VehiculoDetalle findDetalle(Long id_detalle);
	public VehiculoDetalle findByVehiculoDetalle(Long id_vehiculo);
	public String findTCDetalle(Long id_vehiculo);
	//Estado
	public List<VehiculoEstado> findAllEstado();
	public VehiculoEstado findByVehiculoEstado(Long id_estado);
	//Transmision
	public List<VehiculoTransmision> findAllTransmision();
	//Funcion
	public List<VehiculoFuncion> findAllFuncion();
	//Marca
	public List<VehiculoMarca> findAllMarca();
	public List<VehiculoMarca> findByMarca(String nombre_marca);	
	public List<String> findallsubMarcaUnica(String nombre_marca);
	public List<String> findAllMarcaUnica();
	public List<String> findallModeloUnico(String nombre_marca,String nombre_submarca);
	public List<String> findallTipoUnico(String nombre_marca,String nombre_submarca,String modelo);
	public List<String> findallClaseUnico(String nombre_marca,String nombre_submarca,String modelo,String tipo);
	public List<String> findallByClase();
	public VehiculoMarca findMarca(String nombre_marca,String nombre_submarca,String modelo,String tipo,String clase);
	
	public Page<Vehiculo> findAllPage(Pageable pageable); 
	
	public void save(Vehiculo vehiculo);
	
	public Vehiculo findOne(Long id_vehiculo);
	
	public void delete(Long id_vehiculo);
		
	public List<Vehiculo> findVehiculosArea(Long id_adscripcion);
	
	public Page<Vehiculo> findVehiculosAreaPage(Long id_adscripcion,Pageable pageable);
	
	public Page<Vehiculo> findVehElemntoPage(String elemento,Pageable pageable);
	
	public Page<Vehiculo> findVehElemAreaPage(Long id_adscripcion,String elemento,Pageable pageable);
	
	public Long totalVehiculo();
	
	public int totalVehiculoArea(Long id_adscripcion);
		
	public Page<Vehiculo> findTVechiulo(String vehiculo,Pageable pageable);
	
	public int ultimoId();
	
	public Vehiculo findByPlaca(String Placa);
	
	public String findPlaca(long Placa);
	
}
