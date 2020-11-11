package com.PGJ.SGV.models.entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "siniestros")

   public class Siniestro implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id_siniestro;
	
    private Long numero_siniestro;
	private String fecha_siniestro; 
	private String nombre_conductor;
	private String apellido1_conductor;
	private String apellido2_conductor;
	private String url_identificacion_fgjcdmx;
	private String url_ine;
	private String url_licencia;
	private String url_declaracion_universal;
	private String fecha_ingreso_taller;
	private String url_constancia_ingreso_taller;
	private String fecha_salida_taller;
	private String url_constancia_salida_taller;

	@ManyToOne(fetch = FetchType.LAZY)
	private Vehiculo vehiculo;
	
	public Long getId_siniestro() {
		return id_siniestro;
	}

	public void setId_siniestro(Long id_siniestro) {
		this.id_siniestro = id_siniestro;
	}
	
	public Vehiculo getVehiculo() {
		return vehiculo;
	}

	public void setVehiculo(Vehiculo vehiculo) {
		this.vehiculo = vehiculo;
	}

	public Long getNumero_siniestro() {
		return numero_siniestro;
	}

	public void setNumero_siniestro(Long numero_siniestro) {
		this.numero_siniestro = numero_siniestro;
	}

	public String getFecha_siniestro() {
		return fecha_siniestro;
	}

	public void setFecha_siniestro(String fecha_siniestro) {
		this.fecha_siniestro = fecha_siniestro;
	}

	public String getNombre_conductor() {
		return nombre_conductor;
	}

	public void setNombre_conductor(String nombre_conductor) {
		this.nombre_conductor = nombre_conductor;
	}

	public String getUrl_identificacion_fgjcdmx() {
		return url_identificacion_fgjcdmx;
	}

	public void setUrl_identificacion_fgjcdmx(String url_identificacion_fgjcdmx) {
		this.url_identificacion_fgjcdmx = url_identificacion_fgjcdmx;
	}

	public String getUrl_ine() {
		return url_ine;
	}

	public void setUrl_ine(String url_ine) {
		this.url_ine = url_ine;
	}

	public String getUrl_licencia() {
		return url_licencia;
	}

	public void setUrl_licencia(String url_licencia) {
		this.url_licencia = url_licencia;
	}

	public String getUrl_declaracion_universal() {
		return url_declaracion_universal;
	}

	public void setUrl_declaracion_universal(String url_declaracion_universal) {
		this.url_declaracion_universal = url_declaracion_universal;
	}

	public String getFecha_ingreso_taller() {
		return fecha_ingreso_taller;
	}

	public void setFecha_ingreso_taller(String fecha_ingreso_taller) {
		this.fecha_ingreso_taller = fecha_ingreso_taller;
	}

	public String getUrl_constancia_ingreso_taller() {
		return url_constancia_ingreso_taller;
	}

	public void setUrl_constancia_ingreso_taller(String url_constancia_ingreso_taller) {
		this.url_constancia_ingreso_taller = url_constancia_ingreso_taller;
	}

	public String getFecha_salida_taller() {
		return fecha_salida_taller;
	}

	public void setFecha_salida_taller(String fecha_salida_taller) {
		this.fecha_salida_taller = fecha_salida_taller;
	}

	public String getUrl_constancia_salida_taller() {
		return url_constancia_salida_taller;
	}

	public void setUrl_constancia_salida_taller(String url_constancia_salida_taller) {
		this.url_constancia_salida_taller = url_constancia_salida_taller;
	}

	public String getApellido1_conductor() {
		return apellido1_conductor;
	}

	public void setApellido1_conductor(String apellido1_conductor) {
		this.apellido1_conductor = apellido1_conductor;
	}

	public String getApellido2_conductor() {
		return apellido2_conductor;
	}

	public void setApellido2_conductor(String apellido2_conductor) {
		this.apellido2_conductor = apellido2_conductor;
	}
	
	@Override
	public String toString() {
		return "ID SINIESTRO:"+id_siniestro+ " ; " +"NO SINIESTRO:" + numero_siniestro + " ; " + "FECHA SINIESTRO:" + fecha_siniestro + " ; " +
				"NOMBRE:"+ nombre_conductor + " " + apellido1_conductor + " " + apellido2_conductor + " ; " +
				 "FECHA INGRESO:"+fecha_ingreso_taller + " ; " + "FECHA SALIDA:"+ fecha_salida_taller;
	}
	
}
