package com.PGJ.SGV.models.entity;

import java.io.Serializable;
import java.util.ArrayList;
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
@Table(name = "tipo_asignacion")
public class TipoAsignacion implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id_tipo;

	private String nombre_asignacion;
	

	@OneToMany(mappedBy = "tipo_asignacion", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Viaje> viaje;
	

	public Long getId_tipo() {
		return id_tipo;
	}

	public void setId_tipo(Long id_tipo) {
		this.id_tipo = id_tipo;
	}

	public String getNombre_asignacion() {
		return nombre_asignacion;
	}

	public void setNombre_asignacion(String nombre_asignacion) {
		this.nombre_asignacion = nombre_asignacion;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * 
	 */
	
	private static final long serialVersionUID = 1L;

}
