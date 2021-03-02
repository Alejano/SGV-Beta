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
@Table(name = "notificaciones")

public class Notificacion implements Serializable {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id_notificacion;

	private Long id_reg;
	private String numero;
	private String fecha;
	private String fecha_actual;
	private String tipo;

	@ManyToOne(fetch = FetchType.LAZY)
	private Vehiculo vehiculo;
	
	public Vehiculo getVehiculo() {
		return vehiculo;
	}

	public void setVehiculo(Vehiculo vehiculo) {
		this.vehiculo = vehiculo;
	}

	public Long getId_notificacion() {
		return id_notificacion;
	}

	public void setId_notificacion(Long id_notificacion) {
		this.id_notificacion = id_notificacion;
	}

	public Long getId_reg() {
		return id_reg;
	}

	public void setId_reg(Long id_reg) {
		this.id_reg = id_reg;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getFecha_actual() {
		return fecha_actual;
	}

	public void setFecha_actual(String fecha_actual) {
		this.fecha_actual = fecha_actual;
	}
	
	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	private static final long serialVersionUID = 1L;
}

