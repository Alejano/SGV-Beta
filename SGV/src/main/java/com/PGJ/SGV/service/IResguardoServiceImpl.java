package com.PGJ.SGV.service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.catalina.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.util.ResourceUtils;


import com.PGJ.SGV.models.dao.IResguardanteDao;

import com.PGJ.SGV.models.entity.Adscripcion;
import com.PGJ.SGV.models.entity.Resguardante;
import com.PGJ.SGV.models.entity.Usuario;
import com.PGJ.SGV.models.entity.Vehiculo;
import com.PGJ.SGV.utilities.SystemDate;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;


@Service
public class IResguardoServiceImpl implements IResguardanteService {

	@Autowired
	private IResguardanteDao resguardoDao;
	
	
	@Override
	@Transactional(readOnly = true)
	public List<Resguardante> findAll() {
		// TODO Auto-generated method stub
		return (List<Resguardante>)resguardoDao.findAll();
	}

	@Override
	@Transactional
	public void save(Resguardante resguardo) {
		// TODO Auto-generated method stub
		resguardoDao.save(resguardo);
	}

	@Override
	@Transactional(readOnly = true)
	public Resguardante findOne(Long id) {
		// TODO Auto-generated method stub
		return resguardoDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		// TODO Auto-generated method stub
		resguardoDao.deleteById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Resguardante> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return (Page<Resguardante>)resguardoDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public int ultimoId() {
		// TODO Auto-generated method stub
		int id=resguardoDao.ultimoId() ;
		if(id == 0) {
			id=1;
		}
				
		return id;
	}
	

	@Override
	@Transactional(readOnly = true)
	public Page<Resguardante> findAllByVehiculo(long id_vehiculo, Pageable pageable) {
		// TODO Auto-generated method stub
		return resguardoDao.findAllByVehiculo(id_vehiculo, pageable);
	}

	@Override
	public List<Resguardante> findActivos(Long id_vehiculo) {
		// TODO Auto-generated method stub
		return resguardoDao.findActivos(id_vehiculo);
	}

	@Override
	public List<Resguardante> findResg(Long id_vehiculo) {
		// TODO Auto-generated method stub
		return resguardoDao.findResg(id_vehiculo);
	}
	
	@Override
	public Long resguardantestotales() {
		return resguardoDao.resguardantestotales();
	}
	
	@Override
	public Long resguardantespvtotales(Long id_vehiculo) {
		return resguardoDao.resguardantespvtotales(id_vehiculo);
	}
	
	@Override
	public  List<Resguardante> findResgAll1(Long id_adscripcion) {
		return resguardoDao.findResgAll1(id_adscripcion);
	}
	
	@Override
	public  List<Integer> findResgIds() {
		return resguardoDao.findResgIds();
	}
	
	@Override
	public  List<Resguardante> findResgInd(Long id_resguardante) {
		return resguardoDao.findResgInd(id_resguardante);
	}
		

	public String exportReport(String format) throws FileNotFoundException, JRException{
		
		String ruta = "c://opt//reportes";

		int con=0;
		
		List<Resguardante> resg = new ArrayList<Resguardante>();
		resg = resguardoDao.findResgAll1(64l);
		
		System.err.println("GOKU"+ resguardoDao.findResgAll1(64l).size());
		
		List<JasperPrint> jasperPrintList = new ArrayList<JasperPrint>();
		       	
		for (int i=0;i<resg.size();i++) {
			
			List<Resguardante> individual = new ArrayList<Resguardante>();
			individual = resguardoDao.findResgInd(resg.get(i).getId_resguardante());
			String nombreUnico= "Resguardo"+con+"_"+individual.get(0).getNombre()+" "+individual.get(0).getApellido1();
			
			String rutacompleta = ruta + "//" + nombreUnico + ".pdf";	
			
			//File file = ResourceUtils.getFile("classpath:templates/resguardantes.jrxml");
			File file = ResourceUtils.getFile("classpath:reportes/ResguardanteIndividual.jrxml");

			JasperReport jasper = JasperCompileManager.compileReport(file.getAbsolutePath());
			JRBeanCollectionDataSource ds1 = new JRBeanCollectionDataSource(individual);
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("fecha", SystemDate.obtenFecha());
			
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasper, parameters, ds1);

			con++;
			
			if(format.equalsIgnoreCase("pdf")) {
			JasperExportManager.exportReportToPdfFile(jasperPrint, rutacompleta);	
			jasperPrintList.add(jasperPrint);
			
			}
			
	        }

		
		JRPdfExporter exporter = new JRPdfExporter();
		
		exporter.setExporterInput(SimpleExporterInput.getInstance(jasperPrintList));
		exporter.setExporterOutput(new SimpleOutputStreamExporterOutput("c://opt//reportes//Concentrado.pdf"));
		SimplePdfExporterConfiguration configuration = new SimplePdfExporterConfiguration();
		configuration.setCreatingBatchModeBookmarks(true);
		exporter.setConfiguration(configuration);
		
		exporter.exportReport();
	
		return "ruta: " + ruta;

		/*File file = ResourceUtils.getFile("classpath:templates/resguardantes.jrxml");
		JasperReport jasper = JasperCompileManager.compileReport(file.getAbsolutePath());
		JRBeanCollectionDataSource ds1 = new JRBeanCollectionDataSource(resg);
		//Map<String,Object> parameters = new HashMap<String,Object>();
		//parameters.put("gain java", "knowledge");
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasper, null, ds1);
		if(format.equalsIgnoreCase("pdf")) {
		JasperExportManager.exportReportToPdfFile(jasperPrint, "c://opt//reportes//resgformata.pdf");		
		}
		return "path: " + path;*/
		
	}

}