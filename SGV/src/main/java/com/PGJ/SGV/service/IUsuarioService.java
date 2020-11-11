package com.PGJ.SGV.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.PGJ.SGV.models.entity.Usuario;

public interface IUsuarioService {
	
    public List<Usuario> findAll(); 
    
    public Page<Usuario> findAll(Pageable pageable);
	
	public void save(Usuario usuario);
	
	public Usuario findOne(String id);
	
	public void delete(String id);
	
	public Usuario findbyAdscripcion(String no_empleado);
	
	public Page<Usuario> findByAreaPage(String no_empleado,Pageable pageable);
	
	public Page<Usuario> finUsuElemntPage(String elemento,Pageable pageable);
	
	public Page<Usuario> finUsuElemntBajasPage(String elemento,Pageable pageable);
	
	public Page<Usuario> findByReg(Pageable pageable);
	
	public Page<Usuario> findByNl(Pageable pageable);
	
	public Page<Usuario> findByNn(Pageable pageable);
		
	public Long totalUsuarios();
	
	public Long totalUsuariosBajas();
	
	public Long totalfinUsuElemnt(String elemento);
	
	public Long totalfinUsuElemntBajas(String elemento);
	
}
