package com.PGJ.SGV.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.PGJ.SGV.models.dao.IUsuarioDao;
import com.PGJ.SGV.models.entity.Usuario;

@Service
public class IUsuarioServiceImpl implements IUsuarioService {

	@Autowired
	private IUsuarioDao usuarioDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<Usuario> findAll() {
		// TODO Auto-generated method stub
		return (List<Usuario>)usuarioDao.findAll();
	}

	@Override
	@Transactional
	public void save(Usuario usuario) {
		// TODO Auto-generated method stub
		usuarioDao.save(usuario);
	}

	@Override
	@Transactional(readOnly = true)
	public Usuario findOne(String id) {
		// TODO Auto-generated method stub
		return usuarioDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public void delete(String id) {
		// TODO Auto-generated method stub
		usuarioDao.deleteById(id);
	}

	@Override
	public Usuario findbyAdscripcion(String no_empleado) {
		// TODO Auto-generated method stub
		return usuarioDao.findByid_adscripcion(no_empleado);
	}

	@Override
	public Page<Usuario> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return usuarioDao.findAll(pageable);
	}

	@Override
	public Page<Usuario> findByAreaPage(String no_empleado, Pageable pageable) {
		// TODO Auto-generated method stub
		return usuarioDao.findByAreaPage(no_empleado, pageable);
	}
	
	@Override
	public Page<Usuario> finUsuElemntPage(String elemento, Pageable pageable) {
		// TODO Auto-generated method stub
		return usuarioDao.finUsuElemntPage(elemento, pageable);
	}
 
	@Override
	public Page<Usuario> finUsuElemntBajasPage(String elemento, Pageable pageable) {
		// TODO Auto-generated method stub
		return usuarioDao.finUsuElemntBajasPage(elemento, pageable);
	}

	@Override
	public Page<Usuario> findByReg(Pageable pageable) {
		// TODO Auto-generated method stub
		return usuarioDao.findByReg(pageable);
	}
	
	@Override
	public Page<Usuario> findByNl(Pageable pageable) {
		// TODO Auto-generated method stub
		return usuarioDao.findByNl(pageable);
	}
	
	
	@Override
	public Page<Usuario> findByNn(Pageable pageable) {
		// TODO Auto-generated method stub
		return usuarioDao.findByNn(pageable);
	}
	
	@Override
	public Long totalUsuarios() {
		// TODO Auto-generated method stub
		return usuarioDao.totalUsuarios();
	}
	
	public Long totalUsuariosBajas() {
		// TODO Auto-generated method stub
		return usuarioDao.totalUsuariosBajas();
	}

	public Long totalfinUsuElemnt(String elemento){
		// TODO Auto-generated method stub
		return usuarioDao.totalfinUsuElemnt(elemento);
	}
	
	public Long totalfinUsuElemntBajas(String elemento){
		// TODO Auto-generated method stub
		return usuarioDao.totalfinUsuElemntBajas(elemento);
	}

}


