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
@Table(name = "acombus_ext")

public class AsigCombustibleExt implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id_asignacion;	
	
	private Double presupuesto_ext;
	private Double kilometraje_ext;
	private String fecha_ini_ext;
	private String fecha_fin_ext;	
	
	@ManyToOne(fetch = FetchType.LAZY)
	private AsigCombustible asigCombustible;
	
	public AsigCombustibleExt() {
		asigCombustible =new AsigCombustible();
	}
	
	
	public AsigCombustible getAsigCombustible() {
		return asigCombustible;
	}

	public void setAsigCombustible(AsigCombustible asigCombustible) {
		this.asigCombustible = asigCombustible;
	}

	public Long getId_asignacion() {
		return id_asignacion;
	}
	
	public void setId_asignacion(Long id_asignacion) {
		this.id_asignacion = id_asignacion;
	}	
	public Double getPresupuesto_ext() {
		return presupuesto_ext;
	}
	public void setPresupuesto_ext(Double presupuesto_ext) {
		this.presupuesto_ext = presupuesto_ext;
	}
	public Double getKilometraje_ext() {
		return kilometraje_ext;
	}
	public void setKilometraje_ext(Double kilometraje_ext) {
		this.kilometraje_ext = kilometraje_ext;
	}
	public String getFecha_ini_ext() {
		return fecha_ini_ext;
	}
	public void setFecha_ini_ext(String fecha_ini_ext) {
		this.fecha_ini_ext = fecha_ini_ext;
	}
	public String getFecha_fin_ext() {
		return fecha_fin_ext;
	}
	public void setFecha_fin_ext(String fecha_fin_ext) {
		this.fecha_fin_ext = fecha_fin_ext;
	}
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	@Override
	public String toString() {
		return "ID_ASIGNACION:"+id_asignacion+ " ; " +"PRESUPUESTO:" + presupuesto_ext + " ; " + "FECHA INICIO:"+ fecha_ini_ext + " ; " + 
	           "FECHA FINAL:"+ fecha_fin_ext + " ; " + "KILOMETRAJE:"+ kilometraje_ext ;
	}
	
}
