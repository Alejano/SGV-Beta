package com.PGJ.SGV.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.PGJ.SGV.models.dao.IConductorDao;
import com.PGJ.SGV.models.entity.Conductor;

@Service
public class IConductorServiceImpl implements IConductorService {

	@Autowired
	private IConductorDao conductorDao;

	
	@Override
	@Transactional(readOnly = true)
	public List<Conductor> findAll() {
		// TODO Auto-generated method stub					
		return  (List<Conductor>) conductorDao.findAll();
	}

	@Override
	@Transactional
	public void save(Conductor conductor) {
		conductorDao.save(conductor);
		
	}

	@Override
	@Transactional(readOnly = true)
	public Conductor findOne(String no_empleado) {
		// TODO Auto-generated method stub
		return conductorDao.findById(no_empleado).orElse(null);
	}

	@Override
	@Transactional
	public void delete(String no_empleado) {
	//TODO Auto-generated method stub
		conductorDao.deleteById(no_empleado);
	}

	@Override
	public List<Conductor> findConductorArea(Long id_adscripcion) {
		// TODO Auto-generated method stub
		return (List<Conductor>) conductorDao.findConductorArea(id_adscripcion);
	}

	@Override
	public Page<Conductor> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return conductorDao.findAll(pageable);
	}

	@Override
	public Page<Conductor> findConductorAreaPage(Long id_adscripcion, Pageable pageable) {
		// TODO Auto-generated method stub
		return conductorDao.findConductorAreaPage(pageable, id_adscripcion);
	}

	@Override
	public Page<Conductor> findConductorAreaBajasPage(Long id_adscripcion, Pageable pageable) {
		// TODO Auto-generated method stub
		return conductorDao.findConductorAreaBajasPage(pageable, id_adscripcion);
	}
	
	@Override
	public Page<Conductor> findCondElemnPage(String elemento, Pageable pageable) {
		// TODO Auto-generated method stub
		return conductorDao.findCondElemnPage(elemento, pageable);
	}
	
	@Override
	public Page<Conductor> findCondElemnBajasPage(String elemento, Pageable pageable) {
		// TODO Auto-generated method stub
		return conductorDao.findCondElemnBajasPage(elemento, pageable);
	}

	@Override
	public Page<Conductor> findCondElemAreaPage(Long id_adscripcion, String elemento, Pageable pageable) {
		// TODO Auto-generated method stub
		return conductorDao.findCondElemnAreaPage(id_adscripcion, elemento, pageable);
	}
	
	@Override
	public Page<Conductor> findCondElemAreaBajasPage(Long id_adscripcion, String elemento, Pageable pageable) {
		// TODO Auto-generated method stub
		return conductorDao.findCondElemnAreaBajasPage(id_adscripcion, elemento, pageable);
	}
	
	@Override
	public Page<Conductor> findByCNl(Pageable pageable) {
		// TODO Auto-generated method stub
		return conductorDao.findByCNl(pageable);
	}
		
	@Override
	public Page<Conductor> findByCNn(Pageable pageable) {
		// TODO Auto-generated method stub
		return conductorDao.findByCNn(pageable);
	}
	
	@Override
	public Long totalConductores() {
		// TODO Auto-generated method stub
		return conductorDao.totalConductor();
	}

	@Override
	public Long totalConductoresBajas() {
		// TODO Auto-generated method stub
		return conductorDao.totalConductorBajas();
	}
	
	@Override
	public Long totalConductorArea(Long id_adscripcion) {
		// TODO Auto-generated method stub
		return conductorDao.totalConductorArea(id_adscripcion);
	}

	@Override
	public Long totalConductorAreaBajas(Long id_adscripcion) {
		// TODO Auto-generated method stub
		return conductorDao.totalConductorAreaBajas(id_adscripcion);
	}
	
	@Override
	public Long totalfindCondElemnPage(String elemento) {
		// TODO Auto-generated method stub
		return conductorDao.totalfindCondElemnPage(elemento);
	}
		
	@Override
	public Long totalfindCondBajaElemnPage(String elemento) {
		// TODO Auto-generated method stub
		return conductorDao.totalfindCondBajaElemnPage(elemento);
	}

	@Override
	public Long totalfindCondElemnAreaPage(Long id_adscripcion,String elemento) {
		// TODO Auto-generated method stub
		return conductorDao.totalfindCondElemnAreaPage(id_adscripcion,elemento);
	}
	
	@Override
	public Long totalfindCondBajaElemnAreaPage(Long id_adscripcion,String elemento) {
		// TODO Auto-generated method stub
		return conductorDao.totalfindCondBajaElemnAreaPage(id_adscripcion,elemento);
	}
	
	@Override
	public List<Conductor> findConductorAreaEstado(Long id_adscripcion) {
		// TODO Auto-generated method stub
		return conductorDao.findConductorAreaEstado(id_adscripcion);
	}

	@Override
	public List<Conductor> findConductorEstado() {
		// TODO Auto-generated method stub
		return conductorDao.findConductorEstado();
	}



}
