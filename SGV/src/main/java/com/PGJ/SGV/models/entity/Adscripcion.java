package com.PGJ.SGV.models.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "adscripcion")
public class Adscripcion implements Serializable {
	
		
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id_adscripcion;
	
	
	private String nombre_adscripcion;
	private String calle;
	private String numero;
	private String alcaldia;
	private String codigo_postal;
	private String entidad;
	

	// Relaciones a las tablas SQL	

	@OneToMany(mappedBy = "adscripcion", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Conductor> conductores;
	
	@OneToMany(mappedBy = "adscripcion", fetch = FetchType.EAGER)
	private List<Usuario> usuarios;
	
	@OneToMany(mappedBy = "adscripcion", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Vehiculo> vehiculos;
	
	

	//Terminan relaciones a las tablas SQL

	// Inicio de metodos
	public Adscripcion() {
		conductores = new ArrayList<Conductor>();
		usuarios = new ArrayList<Usuario>();
		vehiculos = new ArrayList<Vehiculo>();
	}

	
	public Long getId_adscripcion() {
		return id_adscripcion;
	}

	public void setId_adscripcion(Long id_adscripcion) {
		this.id_adscripcion = id_adscripcion;
	}

	public String getNombre_adscripcion() {
		return nombre_adscripcion;
	}

	public void setNombre_adscripcion(String nombre_adscripcion) {
		this.nombre_adscripcion = nombre_adscripcion;
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

	public String getAlcaldia() {
		return alcaldia;
	}

	public void setAlcaldia(String alcaldia) {
		this.alcaldia = alcaldia;
	}

	public String getCodigo_postal() {
		return codigo_postal;
	}

	public void setCodigo_postal(String codigo_postal) {
		this.codigo_postal = codigo_postal;
	}

	public String getEntidad() {
		return entidad;
	}

	public void setEntidad(String entidad) {
		this.entidad = entidad;
	}
	
	public List<Conductor> getConductores() {
		return conductores;
	}


	public void setConductores(List<Conductor> conductores) {
		this.conductores = conductores;
	}


	public void adConductor(Conductor conductor) {
		conductores.add(conductor);
		
	}

	
	public List<Usuario> getUsuarios() {
		return usuarios;
	}


	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}
	
	public void adUsuario(Usuario usuario) {
		usuarios.add(usuario);
	}
	

	public List<Vehiculo> getVehiculos() {
		return vehiculos;
	}


	public void setVehiculos(List<Vehiculo> vehiculos) {
		this.vehiculos = vehiculos;
	}
	
	public void adVehiculo(Vehiculo vehiculo) {
		vehiculos.add(vehiculo);
	}

	private static final long serialVersionUID = 1L;
	
	@Override
	public String toString() {
		return "ID_ADSCRIPCION:"+id_adscripcion+ ";" +"NOMBRE:" + nombre_adscripcion + ";" + 
	           "DIRECCION: " + calle + ", " + numero + ", " + alcaldia + ", " + codigo_postal + ", " + entidad;
	}
}
