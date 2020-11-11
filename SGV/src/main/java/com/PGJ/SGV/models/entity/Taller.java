package com.PGJ.SGV.models.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "talleres")
public class Taller implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id_taller;

	private String nombre;
	private String calle;
	private String numero;
	private String cp;
	private String alcaldia;
	private String entidad;
	private String no_contrato;

	@OneToMany(mappedBy = "talleres", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Mantenimiento> mantenimiento;

	public Taller() {
		mantenimiento = new ArrayList<Mantenimiento>();
	}

	public List<Mantenimiento> getMantenimiento() {
		return mantenimiento;
	}

	public void setMantenimiento(List<Mantenimiento> mantenimiento) {
		this.mantenimiento = mantenimiento;
	}

	public Long getId_taller() {
		return id_taller;
	}

	public void setId_taller(Long id_taller) {
		this.id_taller = id_taller;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getCalle() {
		return calle;
	}

	public void setCalle(String calle) {
		this.calle = calle;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getCp() {
		return cp;
	}

	public void setCp(String cp) {
		this.cp = cp;
	}

	public String getAlcaldia() {
		return alcaldia;
	}

	public void setAlcaldia(String alcaldia) {
		this.alcaldia = alcaldia;
	}

	public String getEntidad() {
		return entidad;
	}

	public void setEntidad(String entidad) {
		this.entidad = entidad;
	}

	public String getNo_contrato() {
		return no_contrato;
	}

	public void setNo_contrato(String no_contrato) {
		this.no_contrato = no_contrato;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
