package com.PGJ.SGV.service;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.PGJ.SGV.models.entity.TipoVale;

public interface ITipoValeService {
	
    public List<TipoVale> findAll(); 
	
	public Page<TipoVale> findAll(Pageable pageable); 
	
	public void save(TipoVale tipovale);
	
	public TipoVale findOne(Long id_vale);
	
	public Long tipovalestotales();

}
