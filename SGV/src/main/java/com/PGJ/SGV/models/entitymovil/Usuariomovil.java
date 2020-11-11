package com.PGJ.SGV.models.entitymovil;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Usuariomovil implements Serializable {
	
private static final long serialVersionUID = 1L;
	
	@Id
	private String no_empleado;
	private String nombre;
	private String apellido1;
	private String apellido2;	
	private boolean enabled;
	private String contrasena;
	private String authority;
	private String adscripcion;
	public String getNo_empleado() {
		return no_empleado;
	}
	public void setNo_empleado(String no_empleado) {
		this.no_empleado = no_empleado;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getApellido1() {
		return apellido1;
	}
	public void setApellido1(String apellido1) {
		this.apellido1 = apellido1;
	}
	public String getApellido2() {
		return apellido2;
	}
	public void setApellido2(String apellido2) {
		this.apellido2 = apellido2;
	}
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	public String getContrasena() {
		return contrasena;
	}
	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}
	public String getAuthority() {
		return authority;
	}
	public void setAuthority(String authority) {
		this.authority = authority;
	}
	public String getAdscripcion() {
		return adscripcion;
	}
	public void setAdscripcion(String adscripcion) {
		this.adscripcion = adscripcion;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	

}
