package com.PGJ.SGV.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.PGJ.SGV.models.dao.ISeguroDao;
import com.PGJ.SGV.models.entity.Seguro;

@Service
public class ISeguroServiceImpl implements ISeguroService {
	
	@Autowired
	private ISeguroDao seguroDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<Seguro> findAll() {
		// TODO Auto-generated method stub
		return (List<Seguro>)seguroDao.findAll();
	}

	@Override
	@Transactional
	public void save(Seguro seguro) {
		// TODO Auto-generated method stub
		seguroDao.save(seguro);
	}

	@Override
	@Transactional(readOnly = true)
	public Seguro findOne(Long id_seguro) {
		// TODO Auto-generated method stub
		return seguroDao.findById(id_seguro).orElse(null);
		//return null;
	}

	@Override
	@Transactional
	public void delete(Long id_seguro) {
		// TODO Auto-generated method stub
		seguroDao.deleteById(id_seguro);
	}

	
	@Override
	public Page<Seguro> FindsegVehi(Long id_vehiculo,Pageable pageable) {
		// TODO Auto-generated method stub
		return seguroDao.FindsegVehi(id_vehiculo,pageable);
	}
	
	@Override
	public Page<Seguro> FindSeguroAreaPage(Long id_adscripcion, Pageable pageable) {
		 //TODO Auto-generated method stub
		return seguroDao.FindSeguroAreaPage(id_adscripcion, pageable);
	}
	
	@Override
	public Page<Seguro> FindsegVehiArea(Long id_vehiculo,Long id_adscripcion,Pageable pageable) {
		// TODO Auto-generated method stub
		return seguroDao.FindsegVehiArea(id_vehiculo,id_adscripcion,pageable);
	}
	
	@Override
	public Page<Seguro> FindSegElemVehiPage(Long id_vehiculo,String elemento, Pageable pageable) {
		// TODO Auto-generated method stub
		return seguroDao.FindSegElemVehiPage(id_vehiculo,elemento, pageable);
	}
	
	@Override
	public Page<Seguro> FindSegElemVehiAreaPage(Long id_vehiculo,Long id_adscripcion,String elemento, Pageable pageable) {
		// TODO Auto-generated method stub
		return seguroDao.FindSegElemVehiAreaPage(id_vehiculo,id_adscripcion,elemento, pageable);
	}
	
	@Override
	public Page<Seguro> FindSegElemenAreaPage(Long id_adscripcion,String elemento, Pageable pageable) {
		// TODO Auto-generated method stub
		return seguroDao.FindSegElemenAreaPage(id_adscripcion,elemento, pageable);
	}
	
	@Override
	public Page<Seguro> FindSegElemenPage(String elemento, Pageable pageable) {
		// TODO Auto-generated method stub
		return seguroDao.FindSegElemenPage(elemento, pageable);
	}
		
	@Override
	public Page<Seguro> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return seguroDao.findAll(pageable);
	}
	
	@Override
	public Long totalSeguros() {
		// TODO Auto-generated method stub
		return seguroDao.totalSeguro();
	}
	
	@Override
	public Long totalSeguroAreaPage(Long id_adscripcion) {
		// TODO Auto-generated method stub
		return seguroDao.totalSeguroAreaPage(id_adscripcion);
	}
	
	@Override
	public Long totalSegElemVehiPage(Long id_vehiculo,String elemento) {
		// TODO Auto-generated method stub
		return seguroDao.totalSegElemVehiPage(id_vehiculo,elemento);
	}
	
	@Override
	public Long totalSegElemenAreaPage(Long id_adscripcion,String elemento){
		// TODO Auto-generated method stub
		return seguroDao.totalSegElemenAreaPage(id_adscripcion,elemento);
	}
	
	@Override
	public Long totalSegElemenPage(String elemento) {
		// TODO Auto-generated method stub
		return seguroDao.totalSegElemenPage(elemento);
	}
	
}