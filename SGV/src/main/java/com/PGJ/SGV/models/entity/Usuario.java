package com.PGJ.SGV.models.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "usuarios")
public class Usuario implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	private String no_empleado;
	private String nombre;
	private String apellido1;
	private String apellido2;	
	private boolean enabled;
	private String contrasena;
	private String fecha_alta;
	private String fecha_baja;
	
	@ManyToOne(fetch = FetchType.EAGER)
	private Authority authority;
	
	@ManyToOne(fetch = FetchType.EAGER)
	private Adscripcion adscripcion;
	
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
	public Authority getAuthority() {
		return authority;
	}
	public void setAuthority(Authority authority) {
		this.authority = authority;
	}
	public Adscripcion getAdscripcion() {
		return adscripcion;
	}
	public void setAdscripcion(Adscripcion adscripcion) {
		this.adscripcion = adscripcion;
	}
	
	public String getFecha_alta() {
		return fecha_alta;
	}
	
    public void setFecha_alta(String fecha_alta) {
    	this.fecha_alta = fecha_alta;
    }
    
    public String getFecha_baja() {
		return fecha_baja;
	}
	public void setFecha_baja(String fecha_baja) {
		this.fecha_baja = fecha_baja;
	}
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
