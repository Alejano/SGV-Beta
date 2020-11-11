package com.PGJ.SGV.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.PGJ.SGV.models.dao.ITallerDao;
import com.PGJ.SGV.models.entity.Taller;

@Service
public class ITallerServiceImpl implements ITallerService{
	@Autowired
	ITallerDao tallerdao;
	
	@Override
	@Transactional(readOnly = true)
	public List<Taller> findAll() {
		// TODO Auto-generated method stub
		return (List<Taller>) tallerdao.findAll();
	}

	@Override
	@Transactional
	public void save(Taller taller) {
		// TODO Auto-generated method stub
		tallerdao.save(taller);
	}

	@Override
	@Transactional(readOnly = true)
	public Taller findOne(Long id_taller) {
		// TODO Auto-generated method stub
		return tallerdao.findById(id_taller).orElse(null);
	}

	@Override
	@Transactional
	public void delete(Long id_taller) {
		// TODO Auto-generated method stub
		tallerdao.deleteById(id_taller);
	}

	@Override
	public Page<Taller> FindTallerElemPageL(String elemento, Pageable page) {
		// TODO Auto-generated method stub
		return tallerdao.FindTallerElemPageL(elemento, page);
	}

}
