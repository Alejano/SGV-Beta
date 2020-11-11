package com.PGJ.SGV.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.PGJ.SGV.models.entity.Seguro;

public interface ISeguroService {
	
    public List<Seguro> findAll(); 
    
    public Page<Seguro> findAll(Pageable pageable);
	
	public void save(Seguro seguro);
	
	public Seguro findOne(Long id_seguro);
	
	public void delete(Long id_seguro);
	
	public Page<Seguro> FindsegVehi(Long id_vehiculo,Pageable pageable);
	
	public Page<Seguro> FindSeguroAreaPage(Long id_adscripcion,Pageable pageable);
	
	public Page<Seguro> FindsegVehiArea(Long id_vehiculo,Long adscripcion,Pageable pageable);
	
	public Page<Seguro> FindSegElemVehiPage(Long id_vehiculo,String elemento,Pageable pageable);
	
	public Page<Seguro> FindSegElemVehiAreaPage(Long id_vehiculo,Long id_adscripcion,String elemento,Pageable pageable);
	
	public Page<Seguro> FindSegElemenAreaPage(Long id_adscripcion,String elemento,Pageable pageable);
	
	public Page<Seguro> FindSegElemenPage(String elemento,Pageable pageable);
		
	public Long totalSeguros();
	
	public Long totalSeguroAreaPage(Long id_adscripcion);
	
	public Long totalSegElemVehiPage(Long id_vehiculo,String elemento);
	
	public Long totalSegElemenAreaPage(Long id_adscripcion,String elemento);
	
	public Long totalSegElemenPage(String elemento);
 
}
