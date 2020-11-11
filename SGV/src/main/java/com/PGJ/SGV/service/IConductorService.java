package com.PGJ.SGV.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.PGJ.SGV.models.entity.Conductor;


public interface IConductorService {
	
	public List<Conductor> findAll(); 
	
	public Page<Conductor> findAll(Pageable pageable); 
	
	public void save(Conductor conductor);
	
	public Conductor findOne(String no_empleado);
	
	public void delete(String no_empleado);
		
	public List<Conductor> findConductorArea(Long id_adscripcion);
	
	public Page<Conductor> findConductorAreaPage(Long id_adscripcion,Pageable pageable);
	
	public Page<Conductor> findConductorAreaBajasPage(Long id_adscripcion,Pageable pageable);
	
	public Page<Conductor> findCondElemnPage(String elemento,Pageable pageable);
	
	public Page<Conductor> findCondElemnBajasPage(String elemento,Pageable pageable);
	
	public Page<Conductor> findCondElemAreaPage(Long id_adscripcion,String elemento,Pageable pageable);
	
	public Page<Conductor> findCondElemAreaBajasPage(Long id_adscripcion,String elemento,Pageable pageable);
	
    public Page<Conductor> findByCNl(Pageable pageable);
	
	public Page<Conductor> findByCNn(Pageable pageable);
	
	public Long totalConductores();	
	
	public Long totalConductoresBajas();
	
	public Long totalConductorArea(Long id_adscripcion); 
		
	public Long totalConductorAreaBajas(Long id_adscripcion);
	
	public Long totalfindCondElemnPage(String elemento);
	
	public Long totalfindCondBajaElemnPage(String elemento);
	
	public Long totalfindCondElemnAreaPage(Long id_adscripcion,String elemento);
	
	public Long totalfindCondBajaElemnAreaPage(Long id_adscripcion,String elemento);
	
	public List<Conductor> findConductorAreaEstado(Long id_adscripcion);
	
	public List<Conductor> findConductorEstado();
	
	
}
