package com.PGJ.SGV.models.entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
//import javax.persistence.PrePersist;
import javax.persistence.Table;

@Entity
@Table(name = "seguros")
public class Seguro implements Serializable {

	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id_seguro;
	
	private String nombre_contratante;
    private Long no_poliza_hijo;
	private String rfc_contratante; 
	private String codigo_cliente; 
	private String calle_contratante;
    private Long numero_contratante; 
	private String colonia_contratante;
	private Long codigopos_contratante;
	private String alcaldia_contratante;
	private String url_poliza; 
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	private Vehiculo vehiculo;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private Aseguradora aseguradora;
	

	public Long getId_seguro() {
		return id_seguro;
	}

	public void setId_seguro(Long id_seguro) {
		this.id_seguro = id_seguro;
	}

	public Vehiculo getVehiculo() {
		return vehiculo;
	}

	public void setVehiculo(Vehiculo vehiculo) {
		this.vehiculo = vehiculo;
	}

	public Aseguradora getAseguradora() {
		return aseguradora;
	}

	public void setAseguradora(Aseguradora aseguradora) {
		this.aseguradora = aseguradora;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getNombre_contratante() {
		return nombre_contratante;
	}

	public void setNombre_contratante(String nombre_contratante) {
		this.nombre_contratante = nombre_contratante;
	}

	public Long getNo_poliza_hijo() {
		return no_poliza_hijo;
	}

	public void setNo_poliza_hijo(Long no_poliza_hijo) {
		this.no_poliza_hijo = no_poliza_hijo;
	}

	public String getRfc_contratante() {
		return rfc_contratante;
	}

	public void setRfc_contratante(String rfc_contratante) {
		this.rfc_contratante = rfc_contratante;
	}

	public String getCodigo_cliente() {
		return codigo_cliente;
	}

	public void setCodigo_cliente(String codigo_cliente) {
		this.codigo_cliente = codigo_cliente;
	}

	public String getCalle_contratante() {
		return calle_contratante;
	}

	public void setCalle_contratante(String calle_contratante) {
		this.calle_contratante = calle_contratante;
	}

	public Long getNumero_contratante() {
		return numero_contratante;
	}

	public void setNumero_contratante(Long numero_contratante) {
		this.numero_contratante = numero_contratante;
	}

	public String getColonia_contratante() {
		return colonia_contratante;
	}

	public void setColonia_contratante(String colonia_contratante) {
		this.colonia_contratante = colonia_contratante;
	}

	public Long getCodigopos_contratante() {
		return codigopos_contratante;
	}

	public void setCodigopos_contratante(Long codigopos_contratante) {
		this.codigopos_contratante = codigopos_contratante;
	}

	public String getAlcaldia_contratante() {
		return alcaldia_contratante;
	}

	public void setAlcaldia_contratante(String alcaldia_contratante) {
		this.alcaldia_contratante = alcaldia_contratante;
	}

	public String getUrl_poliza() {
		return url_poliza;
	}

	public void setUrl_poliza(String url_poliza) {
		this.url_poliza = url_poliza;
	}
	
	@Override
	public String toString() {
		return "ID SEGURO:"+id_seguro+ " ; " +"NOMBRE CONTRATANTE:" + nombre_contratante + " ; " + "POLIZA HIJA:" + no_poliza_hijo + " ; " +
				"RFC:"+ rfc_contratante + " ; "  + "CODIGO CLIENTE:"+ codigo_cliente + " ; "  +
				"DIRECCION:"+ calle_contratante + ", " + numero_contratante + ", " + colonia_contratante + ", " + codigopos_contratante + ", " +
				alcaldia_contratante;
	}
	
}
