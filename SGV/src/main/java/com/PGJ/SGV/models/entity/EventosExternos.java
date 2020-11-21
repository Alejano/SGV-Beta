package com.PGJ.SGV.models.entity;


public class EventosExternos {


	private static final long serialVersionUID = 1L;
	private Long id;
	private Long id_adscripcion;
	private String nombre_adscripcion;

	public Long getId_adscripcion() {
		return id_adscripcion;
	}

	public void setId_adscripcion(Long id_adscripcion) {
		this.id_adscripcion = id_adscripcion;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre_adscripcion() {
		return nombre_adscripcion;
	}

	public void setNombre_adscripcion(String nombre_adscripcion) {
		this.nombre_adscripcion = nombre_adscripcion;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	

}
