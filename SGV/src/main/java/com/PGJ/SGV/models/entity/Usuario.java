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
	private String no_licencia;
	private String ine;
	private String vig_licencia;
	private String vig_ine;
	private String rfc;
	private String telefono;
	private String domicilio;
	private String cargo;
	
	
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
	
	public String getNo_licencia() {
		return no_licencia;
	}
	
	public void setNo_licencia(String no_licencia) {
		this.no_licencia = no_licencia;
	}
	
	public String getIne() {
		return ine;
	}
	
	public void setIne(String ine) {
		this.ine = ine;
	}
	
	
	public String getCargo() {
		return cargo;
	}
	
	public void setCargo(String cargo) {
		this.cargo = cargo;
	}
	
	public String getRfc() {
		return rfc;
	}
	
	public void setRfc(String rfc) {
		this.rfc = rfc;
	}
	
	
	public String getTelefono() {
		return telefono;
	}
	
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	
	public String getDomicilio() {
		return domicilio;
	}
	
	public void setDomicilio(String domicilio) {
		this.domicilio = domicilio;
	}
	
	public String getVig_licencia() {
		return vig_licencia;
	}

	public void setVig_licencia(String vig_licencia) {
		this.vig_licencia = vig_licencia;
	}

	public String getVig_ine() {
		return vig_ine;
	}

	public void setVig_ine(String vig_ine) {
		this.vig_ine = vig_ine;
	}
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	@Override
	public String toString() {
		return "NO EMPLEADO:"+no_empleado+ ";" +"NOMBRE:" + nombre + " " + apellido1 + " " + apellido2 + ";"  +
	           "ENABLED:" + enabled + ";" + "FECHA ALTA:"+ fecha_alta + ";" + "FECHA BAJA:"+ fecha_baja;
	}

	
}
