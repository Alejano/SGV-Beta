package com.PGJ.SGV.models.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "tipo_resguardante")
public class TipoResguardante implements Serializable {

	@Id
	private long id;

	private String nombre;

	@OneToMany(mappedBy = "tipo_resguardante", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Resguardante> resguardante;

	public TipoResguardante() {
		resguardante = new ArrayList<Resguardante>();
	}
	
	
	public List<Resguardante> getResguardante() {
		return resguardante;
	}

	public void setResguardante(List<Resguardante> resguardante) {
		this.resguardante = resguardante;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
