package com.PGJ.SGV.service;


import java.io.IOException;

import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

public interface IUploadFileService {

	public Model load(String filename);
	public String copy(MultipartFile file)throws IOException ;
	public boolean delete(String filename);
	public void copyImport(MultipartFile file)throws IOException ;
	public void deleteall();
	public void importarServicios();

}
