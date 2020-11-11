package com.PGJ.SGV.models.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "vehiculo_transmision")
public class VehiculoTransmision implements Serializable{

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id_transmision;
	
	private String nombre_transmision;
	
	@OneToMany(mappedBy = "vehiculo_transmision", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Vehiculo> vehiculos;

	public long getId_transmision() {
		return id_transmision;
	}

	public void setId_transmision(long id_transmision) {
		this.id_transmision = id_transmision;
	}

	public String getNombre_transmision() {
		return nombre_transmision;
	}

	public void setNombre_transmision(String nombre_transmision) {
		this.nombre_transmision = nombre_transmision;
	}

	public List<Vehiculo> getVehiculos() {
		return vehiculos;
	}

	public void setVehiculos(List<Vehiculo> vehiculos) {
		this.vehiculos = vehiculos;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	
	private static final long serialVersionUID = 1L;

}
