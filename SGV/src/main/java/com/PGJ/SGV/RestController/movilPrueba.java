package com.PGJ.SGV.RestController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.PGJ.SGV.models.entity.Usuario;
import com.PGJ.SGV.models.entitymovil.Usuariomovil;
import com.PGJ.SGV.service.IUsuarioService;




@RestController
public class movilPrueba {
	
	@Autowired
	private IUsuarioService usuarioService;
	
	
	@RequestMapping(value = "/Usuariosmovil", method = RequestMethod.GET)
	public ResponseEntity<Usuariomovil> list(@RequestParam(value = "id") String id) {
		Usuario usuarios = new Usuario();
		Usuariomovil usuariosrest = new Usuariomovil();
		 usuarios = usuarioService.findOne(id);
		 usuariosrest.setNo_empleado(usuarios.getNo_empleado());
		 usuariosrest.setNombre(usuarios.getNombre());
		 usuariosrest.setApellido1(usuarios.getApellido1());
		 usuariosrest.setApellido2(usuarios.getApellido2());		 
		 usuariosrest.setAdscripcion(usuarios.getAdscripcion().getNombre_adscripcion());
		 usuariosrest.setAuthority(usuarios.getAuthority().getAuthority());
		 usuariosrest.setContrasena(usuarios.getContrasena());
		 
		return new ResponseEntity<Usuariomovil>(usuariosrest,HttpStatus.OK);
		// TODO Auto-generated constructor stub
	}
	
	

}
