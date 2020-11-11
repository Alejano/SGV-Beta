package com.PGJ.SGV.models.entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "vehiculo_bajas")
public class BajaVehiculo implements Serializable {


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id_baja_vehiculo;

	
	private String fecha_baja;
	private String url_acta_fnq;
	private String url_oficio_baja;
	private String url_dictamen;
	
	@OneToOne(fetch = FetchType.LAZY)
	private Vehiculo vehiculo;
	
	public Vehiculo getVehiculo() {
		return vehiculo;
	}

	public void setVehiculo(Vehiculo vehiculo) {
		this.vehiculo = vehiculo;
	}

	public Long getId_baja_vehiculo() {
		return id_baja_vehiculo;
	}

	public void setId_baja_vehiculo(Long id_baja_vehiculo) {
		this.id_baja_vehiculo = id_baja_vehiculo;
	}

	public String getFecha_baja() {
		return fecha_baja;
	}

	public void setFecha_baja(String fecha_baja) {
		this.fecha_baja = fecha_baja;
	}


	public String getUrl_acta_fnq() {
		return url_acta_fnq;
	}

	public void setUrl_acta_fnq(String url_acta_fnq) {
		this.url_acta_fnq = url_acta_fnq;
	}

	public String getUrl_oficio_baja() {
		return url_oficio_baja;
	}

	public void setUrl_oficio_baja(String url_oficio_baja) {
		this.url_oficio_baja = url_oficio_baja;
	}

	public String getUrl_dictamen() {
		return url_dictamen;
	}

	public void setUrl_dictamen(String url_dictamen) {
		this.url_dictamen = url_dictamen;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	private static final long serialVersionUID = 1L;
}
