package com.PGJ.SGV.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;

import com.PGJ.SGV.models.dao.IUsuarioDao;
import com.PGJ.SGV.models.entity.Usuario;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

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

	@Override
	public List<Usuario> NotifyUVigLic(String fecha1, String fecha2) {
		// TODO Auto-generated method stub
		return usuarioDao.NotifyUVigLic(fecha1,fecha2);
	}
	
	@Override
	public List<Usuario> NotifyUVigIne(String fecha1, String fecha2) {
		// TODO Auto-generated method stub
		return usuarioDao.NotifyUVigIne(fecha1,fecha2);
	}
	
	@Override
	public Long TotalUVigLic(String fecha1, String fecha2) {
		// TODO Auto-generated method stub
		return usuarioDao.TotalUVigLic(fecha1,fecha2);
	}
	
	@Override
	public Long TotalUVigIne(String fecha1, String fecha2) {
		// TODO Auto-generated method stub
		return usuarioDao.TotalUVigIne(fecha1,fecha2);
	}	
	
	public String exportReport(String format) throws FileNotFoundException, JRException{
		
		List<Usuario> usuarios = (List<Usuario>) usuarioDao.findAll();
		String ruta = "c://opt//reportes//usuariosformat.pdf";
		
		//File destino = new File ("C:/pruebas/archivo.ext");

		File file = ResourceUtils.getFile("classpath:templates/usuarios.jrxml");
		JasperReport jasper = JasperCompileManager.compileReport(file.getAbsolutePath());
		JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(usuarios);
		Map<String,Object> parameters = new HashMap<String,Object>();
		parameters.put("gain java", "knowledge");
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasper, parameters, ds);
		
		if(format.equalsIgnoreCase("pdf")) {
		JasperExportManager.exportReportToPdfFile(jasperPrint, ruta);		
		}
		
		return "ruta: " + ruta;
		
	}
	
}


