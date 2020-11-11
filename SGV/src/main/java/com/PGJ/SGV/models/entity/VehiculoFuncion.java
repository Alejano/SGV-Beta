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
@Table(name = "vehiculo_funcion")
public class VehiculoFuncion implements Serializable{

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id_funcion;
	
	private String nombre_funcion;
	

	@OneToMany(mappedBy = "vehiculo_funcion", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Vehiculo> vehiculos;
	
	
	public long getId_funcion() {
		return id_funcion;
	}

	public void setId_funcion(long id_funcion) {
		this.id_funcion = id_funcion;
	}

	public String getNombre_funcion() {
		return nombre_funcion;
	}

	public void setNombre_funcion(String nombre_funcion) {
		this.nombre_funcion = nombre_funcion;
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
