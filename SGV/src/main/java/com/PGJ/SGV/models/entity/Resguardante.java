package com.PGJ.SGV.models.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "resguardante")
public class Resguardante implements Serializable {

	@Id
	@Column(name = "id_resguardante")
	private long id_resguardante;

	private String nombre;
	private String apellido1;
	private String apellido2;
	private String cargo;
	private String fecha_inicio;
	private String fecha_fin;
	private String ids_adscripcion;//PAra obtener los id del formulario
	private String no_licencia;
	private String rfc;
	private String ine;
	private String telefono;
	private String domicilio;
	private boolean activo;

	
	@ManyToOne(fetch = FetchType.LAZY)
	private Adscripcion adscripcion;
	//fin extras resguardantes

	@ManyToOne(fetch = FetchType.LAZY)
	private TipoResguardante tipo_resguardante;

	@ManyToOne(fetch = FetchType.LAZY)
	private Vehiculo vehiculo;
	
	

	public String getNo_licencia() {
		return no_licencia;
	}

	public void setNo_licencia(String no_licencia) {
		this.no_licencia = no_licencia;
	}

	public String getRfc() {
		return rfc;
	}

	public void setRfc(String rfc) {
		this.rfc = rfc;
	}

	public String getIne() {
		return ine;
	}

	public void setIne(String ine) {
		this.ine = ine;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getDomicilio() {
		return domicilio;
	}

	public void setDomicilio(String domicilio) {
		this.domicilio = domicilio;
	}

	
	public Adscripcion getAdscripcion() {
		return adscripcion;
	}

	public void setAdscripcion(Adscripcion adscripcion) {
		this.adscripcion = adscripcion;
	}

	public String getIds_adscripcion() {
		return ids_adscripcion;
	}

	public void setIds_adscripcion(String ids_adscripcion) {
		this.ids_adscripcion = ids_adscripcion;
	}
	
	public Resguardante() {
		tipo_resguardante = new TipoResguardante();
		vehiculo = new Vehiculo();
	}

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

	public TipoResguardante getTipo_resguardante() {
		return tipo_resguardante;
	}

	public void setTipo_resguardante(TipoResguardante tipo_resguardante) {
		this.tipo_resguardante = tipo_resguardante;
	}

	public TipoResguardante getTipo_resguardante_id() {
		return tipo_resguardante;
	}

	public void setTipo_resguardante_id(TipoResguardante tipo_resguardante_id) {
		this.tipo_resguardante = tipo_resguardante_id;
	}

	public Vehiculo getVehiculo() {
		return vehiculo;
	}

	public void setVehiculo(Vehiculo vehiculo) {
		this.vehiculo = vehiculo;
	}

	public long getId_resguardante() {
		return id_resguardante;
	}

	public void setId_resguardante(long id_resguardante) {
		this.id_resguardante = id_resguardante;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido1() {
		return apellido1;
	}

	public void setApellido1(String apellido1) {
		this.apellido1 = apellido1;
	}

	public String getApellido2() {
		return apellido2;
	}

	public void setApellido2(String apellido2) {
		this.apellido2 = apellido2;
	}

	public String getCargo() {
		return cargo;
	}

	public void setCargo(String cargo) {
		this.cargo = cargo;
	}

	public String getFecha_inicio() {
		return fecha_inicio;
	}

	public void setFecha_inicio(String fecha_inicio) {
		this.fecha_inicio = fecha_inicio;
	}

	public String getFecha_fin() {
		return fecha_fin;
	}

	public void setFecha_fin(String fecha_fin) {
		this.fecha_fin = fecha_fin;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String toString() {
		return "ID RESG:" +id_resguardante+ " " + "ID VEHI:" + vehiculo.getId_vehiculo() + " " + "TIPO RESG:"+ tipo_resguardante.getId() + " " +
	           "NOMBRE:"+nombre+ " "+apellido1+" "+apellido2 + " "+
				"FECHA INI:"+ fecha_inicio + " " + "ENABLED:" + activo + " ";
	}

	
	
}
