package com.PGJ.SGV.models.entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "chofer")
public class Conductor implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	private String no_empleado;

	@ManyToOne(fetch = FetchType.LAZY)
	private Adscripcion adscripcion;

	private String nombre;
	private String apellido1;
	private String apellido2;
	private boolean enabled;
	private String fecha_alta;
	private String fecha_baja;
	private String no_ine;
	private String vig_ine;
	private String no_licencia;
	private String vig_licencia;

	//@OneToMany(mappedBy = "conductor", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	//private List<Viaje> viajes;
	
	//public Conductor() {
	//	viajes = new ArrayList<Viaje>();
	//}

	public Adscripcion getAdscripcion() {
		return adscripcion;
	}

	public void setAdscripcion(Adscripcion adscripcion) {
		this.adscripcion = adscripcion;
	}

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
	
	/*public List<Viaje> getViajes() {
		return viajes;
	}

	public void setViajes(List<Viaje> viajes) {
		this.viajes = viajes;
	}
	public void adViaje(Viaje viaje) {
		viajes.add(viaje);
	}*/

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

	public String getNo_ine() {
		return no_ine;
	}

	public void setNo_ine(String no_ine) {
		this.no_ine = no_ine;
	}

	public String getVig_ine() {
		return vig_ine;
	}

	public void setVig_ine(String vig_ine) {
		this.vig_ine = vig_ine;
	}

	public String getNo_licencia() {
		return no_licencia;
	}

	public void setNo_licencia(String no_licencia) {
		this.no_licencia = no_licencia;
	}

	public String getVig_licencia() {
		return vig_licencia;
	}

	public void setVig_licencia(String vig_licencia) {
		this.vig_licencia = vig_licencia;
	}
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	@Override
	public String toString() {
		return "NO EMPLEADO:"+no_empleado+ " ; " + "NOMBRE:" + nombre + " " + apellido1 + " " + apellido2 + " ; " + "ENABLED:" + enabled + " ; " +
				"FECHA ALTA:"+ fecha_alta + " ; " + "FECHA BAJA:"+ fecha_baja + " ; " + "NO INE:"+ no_ine + " ; " + "VIG INE:"+ vig_ine + " ; " + 
				"NO LICENCIA:"+ no_licencia + " ; " + "VIG LICENCIA: "+ vig_licencia ;
	}
}
