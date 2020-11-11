package com.PGJ.SGV.models.entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "odometro_combusext")
public class OdometroAcombusExt implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id_odo;
	private long id_asignacion;
	private String odometro;

	public long getId_odo() {
		return id_odo;
	}

	public void setId_odo(long id_odo) {
		this.id_odo = id_odo;
	}

	public long getId_asignacion() {
		return id_asignacion;
	}

	public void setId_asignacion(long id_asignacion) {
		this.id_asignacion = id_asignacion;
	}

	public String getOdometro() {
		return odometro;
	}

	public void setOdometro(String odometro) {
		this.odometro = odometro;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
