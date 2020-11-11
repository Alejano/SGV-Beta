package com.PGJ.SGV.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
//import org.springframework.core.io.Resource;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import com.PGJ.SGV.models.dao.IUploadDao;

@Service
public class UploadFileServiceImplement implements IUploadFileService {
	@Autowired 
	private IUploadDao uploadService;
		
	private final static String ruta= "c://opt//upload";
	private final static String rutaImportar= "c://opt//import";
	
	@Override
	public Model load(String filename) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String copy(MultipartFile file) throws IOException {
		
		String nombreUnico= UUID.randomUUID().toString()+"_"+file.getOriginalFilename();
		Path rutacompleta = Paths.get(ruta + "//" + nombreUnico);
		
			Files.copy(file.getInputStream(), rutacompleta);
		
				return nombreUnico;
	}
	
	@Override
	public void copyImport(MultipartFile file) throws IOException {
		
		
		
		Path rutacompleta = Paths.get(rutaImportar + "//catalogo_servicios.csv");
		Files.copy(file.getInputStream(), rutacompleta);
		
	}

	@Override
	public boolean delete(String filename) {
		Path rutacompleta = getPath(filename);
		File archivo = rutacompleta.toFile();
		
		if (archivo.exists()&& archivo.canRead()) {
			if (archivo.delete()) {
				return true;
			}
		}

		
		return false;
	}
	
	public Path getPath(String nombreunico) {
		return Paths.get(ruta + "//" + nombreunico);
	}

	@Override
	public void deleteall() {
		// TODO Auto-generated method stub
		uploadService.deleteAll();
	}

	@Override
	public void importarServicios() {
		// TODO Auto-generated method stub
		uploadService.importarServicios();
	}

}
