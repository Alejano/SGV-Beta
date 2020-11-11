package com.PGJ.SGV.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.PGJ.SGV.models.dao.ICatalogoSerDao;
import com.PGJ.SGV.models.entity.CatalogoServicio;
@Service
public class ICatalogoServicioImpl implements ICatalogoServicioService{

	@Autowired
	private ICatalogoSerDao catalogoServicios;
	
	@Override
	@Transactional(readOnly = true)
	public Page<CatalogoServicio> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return catalogoServicios.findAll(pageable);
	}

	@Override
	@Transactional
	public void save(CatalogoServicio Catalogo) {
		// TODO Auto-generated method stub
		catalogoServicios.save(Catalogo);
	}

	@Override
	@Transactional(readOnly = true)
	public CatalogoServicio findOne(Long id) {
		// TODO Auto-generated method stub
		return catalogoServicios.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		// TODO Auto-generated method stub
		catalogoServicios.deleteById(id);
	}

	@Override
	public List<CatalogoServicio> findAll() {
		// TODO Auto-generated method stub
		return (List<CatalogoServicio>) catalogoServicios.findAll();
	}

	@Override
	public Long totalCatalogoServicios() {
		// TODO Auto-generated method stub
		return catalogoServicios.totalCatalogoServicios();
	}

	@Override
	public Page<CatalogoServicio> finCatElemntPage(String elemento, Pageable pageable) {
		// TODO Auto-generated method stub
		return catalogoServicios.finCatElemntPage(elemento, pageable);
	}

}
