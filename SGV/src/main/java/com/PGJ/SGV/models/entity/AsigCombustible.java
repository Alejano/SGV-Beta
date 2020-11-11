package com.PGJ.SGV.models.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "acombus")
public class AsigCombustible implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id_asignacion;
	private Double presupuesto_ord;
	private String no_tarjeta;
	private Double kilometraje_ord;
	private String fecha_ini_ord;
	private String fecha_fin_ord;

	@ManyToOne(fetch = FetchType.LAZY)
	private Vehiculo vehiculo;

	@OneToMany(mappedBy = "asigCombustible",fetch = FetchType.LAZY)	
	private List<AsigCombustibleExt> asigCombustibleExt;

	public AsigCombustible() {
		asigCombustibleExt = new ArrayList<AsigCombustibleExt>();
		vehiculo = new Vehiculo();
	}

	public Vehiculo getVehiculo() {
		return vehiculo;
	}

	public void setVehiculo(Vehiculo vehiculo) {
		this.vehiculo = vehiculo;
	}

	public List<AsigCombustibleExt> getCombustiblext() {
		return asigCombustibleExt;
	}

	public void setCombustiblext(List<AsigCombustibleExt> asigCombustibleExt) {
		this.asigCombustibleExt = asigCombustibleExt;
	}

	public long getId_asignacion() {
		return id_asignacion;
	}

	public void setId_asignacion(long id_aignacion) {
		this.id_asignacion = id_aignacion;
	}

	public Double getPresupuesto_ord() {
		return presupuesto_ord;
	}

	public void setPresupuesto_ord(Double presupuesto_ord) {
		this.presupuesto_ord = presupuesto_ord;
	}

	public String getNo_tarjeta() {
		return no_tarjeta;
	}

	public void setNo_tarjeta(String no_tarjeta) {
		this.no_tarjeta = no_tarjeta;
	}

	public Double getKilometraje_ord() {
		return kilometraje_ord;
	}

	public void setKilometraje_ord(Double kilometraje_ord) {
		this.kilometraje_ord = kilometraje_ord;
	}

	public String getFecha_ini_ord() {
		return fecha_ini_ord;
	}

	public void setFecha_ini_ord(String fecha_ini_ord) {
		this.fecha_ini_ord = fecha_ini_ord;
	}

	public String getFecha_fin_ord() {
		return fecha_fin_ord;
	}

	public void setFecha_fin_ord(String fecha_fin_ord) {
		this.fecha_fin_ord = fecha_fin_ord;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	@Override
	public String toString() {
		return "ID_ASIGNACION:"+id_asignacion+ " ; " +"PRESUPUESTO:" + presupuesto_ord + " ; " + "NO TARJETA:" + no_tarjeta + " ; " +
				"FECHA INICIO:"+ fecha_ini_ord + " ; " + "FECHA FINAL:"+ fecha_fin_ord + " ; " +
				"KILOMETRAJE:"+ kilometraje_ord ;
	}

}
