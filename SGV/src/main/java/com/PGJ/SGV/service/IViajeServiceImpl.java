package com.PGJ.SGV.service;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.PGJ.SGV.models.dao.IViajeDao;
import com.PGJ.SGV.models.entity.Viaje;

@Service
public class IViajeServiceImpl implements IViajeService {

	@Autowired
	private IViajeDao viajeDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<Viaje> findAll() {
		// TODO Auto-generated method stub
		 return (List<Viaje>) viajeDao.findAll();
	}

	@Override
	@Transactional
	public void save(Viaje viaje) {
		// TODO Auto-generated method stub
		viajeDao.save(viaje);
	}

	@Override
	@Transactional(readOnly = true)
	public Viaje findOne(Long id_viaje) {
		// TODO Auto-generated method stub
		return viajeDao.findById(id_viaje).orElse(null);
	}

	@Override
	@Transactional
	public void delete(Long id_viaje) {
		// TODO Auto-generated method stub
		viajeDao.deleteById(id_viaje);
		
	}
	
	@Override
	public Page<Viaje> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return viajeDao.findAll(pageable);
	}

	@Override
	public Page<Viaje> ViajesAreaPage(Long id_adscripcion, Pageable pageable) {
		// TODO Auto-generated method stub
		return viajeDao.ViajesAreaPage(id_adscripcion,pageable);
	}

	@Override
	public Page<Viaje> ViajeElemPage(String elemento,Pageable pageable) {
		// TODO Auto-generated method stub
		return viajeDao.ViajeElemPage(elemento,pageable);
	}

	@Override
	public Page<Viaje> ViajesElemAreaPage(Long id_adscripcion, String elemento, Pageable pageable) {
		// TODO Auto-generated method stub
		return viajeDao.ViajesElemAreaPage(id_adscripcion, elemento, pageable);
	}

	@Override
	public Long viajestotales() {
		// TODO Auto-generated method stub
		return viajeDao.viajestotales();
	}
	
	@Override
	public Long totalviajeElem(String elemento) {
		// TODO Auto-generated method stub
		return viajeDao.totalviajeElem(elemento);
	}
	
	@Override
	public Long totalViajesArea(Long id_adscipcion) {
		// TODO Auto-generated method stub
		return viajeDao.totalViajesArea(id_adscipcion);
	}
	
	@Override
	public Long totalViajesElemArea(Long id_adscripcion,String elemento) {
		// TODO Auto-generated method stub
		return viajeDao.totalViajesElemArea(id_adscripcion,elemento);
	}
	
	@Override
	public String fechamax(Long id_vehiculo) {
		// TODO Auto-generated method stub
		return viajeDao.fechamax(id_vehiculo);
	}	
	
	@Override
	public  Page<Viaje> FindviajeVehi(Long id_vehiculo,Pageable pageable) {
		// TODO Auto-generated method stub
		return viajeDao.FindviajeVehi(id_vehiculo,pageable); 
	}
	
	@Override
	public  Page<Viaje> ViajeElemVehiPage(Long id_vehiculo,String elemento,Pageable pageable){
		// TODO Auto-generated method stub
		return viajeDao.ViajeElemVehiPage(id_vehiculo,elemento,pageable);
	}

}
