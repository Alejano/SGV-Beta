package com.PGJ.SGV.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import com.PGJ.SGV.models.entity.Seguro;
import com.PGJ.SGV.models.entity.Siniestro;

public interface ISiniestroService {

    public List<Siniestro> findAll(); 
    
    public Page<Siniestro> findAll(Pageable pageable);
	
	public void save(Siniestro siniestro);
	
	public Siniestro findOne(Long id_siniestro);
	
	public void delete(Long id_siniestro);
	
	public Long totalSiniestro();
	
	public Page<Siniestro> FindsegVehi(Long id_vehiculo,Pageable pageable);
	
	public Page<Siniestro> FindSinElemVehiPage(Long id_vehiculo,String elemento,Pageable pageable);

	public Page<Siniestro> FindSinElemenAreaPage(Long id_adscripcion,String elemento,Pageable pageable);
	
	public Page<Siniestro> FindSinElemenPage(String elemento,Pageable pageable);
	
	
}
