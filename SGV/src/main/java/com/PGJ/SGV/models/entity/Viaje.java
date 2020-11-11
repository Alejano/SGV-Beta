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
@Table(name = "viajes")
public class Viaje implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id_viaje;
	
	private Double distancia_mensual;
	private String finicial_registro;
	private String ffinal_registro;
	private Double kilometraje_inicial;
	private Double kilometraje_final;

	
	@ManyToOne(fetch = FetchType.LAZY)
	private Vehiculo vehiculo;

	public Long getId_viaje() {
		return id_viaje;
	}

	public void setId_viaje(Long id_viaje) {
		this.id_viaje = id_viaje;
	}

	public Double getDistancia_mensual() {
		return distancia_mensual;
	}

	public void setDistancia_mensual(Double distancia_mensual) {
		this.distancia_mensual = distancia_mensual;
	}

	public String getFinicial_registro() {
		return finicial_registro;
	}

	public void setFinicial_registro(String finicial_registro) {
		this.finicial_registro = finicial_registro;
	}

	public String getFfinal_registro() {
		return ffinal_registro;
	}

	public void setFfinal_registro(String ffinal_registro) {
		this.ffinal_registro = ffinal_registro;
	}

	public Double getKilometraje_inicial() {
		return kilometraje_inicial;
	}

	public void setKilometraje_inicial(Double kilometraje_inicial) {
		this.kilometraje_inicial = kilometraje_inicial;
	}

	public Double getKilometraje_final() {
		return kilometraje_final;
	}

	public void setKilometraje_final(Double kilometraje_final) {
		this.kilometraje_final = kilometraje_final;
	}

	public Vehiculo getVehiculo() {
		return vehiculo;
	}

	public void setVehiculo(Vehiculo vehiculo) {
		this.vehiculo = vehiculo;
	}
	
	@Override
	public String toString() {
		return "ID_VIAJE:"+id_viaje+ " ; " +"DISTANCIA_MENSUAL:" + distancia_mensual + " ; " + "KILOMETRAJE INICIAL:" + kilometraje_inicial + " ; " +
				"KILOMETRAJE FINAL:"+ kilometraje_final + " ; " + "FECHA INICIAL:"+ finicial_registro + " ; " +
				"FECHA FINAL:"+ ffinal_registro ;
	}
	
}
