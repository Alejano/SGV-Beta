package com.PGJ.SGV.service;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.PGJ.SGV.models.entity.CatalogoServicio;

public interface ICatalogoServicioService {
	
	public Page<CatalogoServicio> findAll(Pageable pageable); 
	public List<CatalogoServicio> findAll();
	
	public void save(CatalogoServicio id);
	
	public CatalogoServicio findOne(Long id);
	
	public void delete(Long id);
	
	public Long totalCatalogoServicios();
	
	public Page<CatalogoServicio> finCatElemntPage(String elemento,Pageable pageable);

}
