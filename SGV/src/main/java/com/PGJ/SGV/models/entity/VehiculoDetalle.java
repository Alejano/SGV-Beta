package com.PGJ.SGV.models.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "vehiculo_detalle")
public class VehiculoDetalle implements Serializable {
	
	
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name= "id_detalle")
	private long id_detalle;
	
	private String rin;
	private String color;
	private String no_motor;
	private String no_economico;
	private String no_cilindros;
	private String no_personas;
	private String no_puertas;
	private String balisado;
	private String blindaje;
	private String fecha_compra;
	private String valor_compra;
	private String tipo_combustible;
	private String tarjeta_circulacion;
	private String vigencia_circulacion;	
	
	@OneToOne(mappedBy = "vehiculo_detalle")
	private Vehiculo vehiculo;

	public long getId_detalle() {
		return id_detalle;
	}

	public void setId_detalle(long id_detalle) {
		this.id_detalle = id_detalle;
	}

	public String getRin() {
		return rin;
	}

	public void setRin(String rin) {
		this.rin = rin;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getNo_motor() {
		return no_motor;
	}

	public void setNo_motor(String no_motor) {
		this.no_motor = no_motor;
	}

	public String getNo_economico() {
		return no_economico;
	}

	public void setNo_economico(String no_economico) {
		this.no_economico = no_economico;
	}

	public String getNo_cilindros() {
		return no_cilindros;
	}

	public void setNo_cilindros(String no_cilindros) {
		this.no_cilindros = no_cilindros;
	}

	public String getNo_personas() {
		return no_personas;
	}

	public void setNo_personas(String no_personas) {
		this.no_personas = no_personas;
	}

	public String getNo_puertas() {
		return no_puertas;
	}

	public void setNo_puertas(String no_puertas) {
		this.no_puertas = no_puertas;
	}

	public String getBalisado() {
		return balisado;
	}

	public void setBalisado(String balisado) {
		this.balisado = balisado;
	}

	public String getBlindaje() {
		return blindaje;
	}

	public void setBlindaje(String blindaje) {
		this.blindaje = blindaje;
	}

	public String getFecha_compra() {
		return fecha_compra;
	}

	public void setFecha_compra(String fecha_compra) {
		this.fecha_compra = fecha_compra;
	}

	public String getValor_compra() {
		return valor_compra;
	}

	public void setValor_compra(String valor_compra) {
		this.valor_compra = valor_compra;
	}

	public String getTipo_combustible() {
		return tipo_combustible;
	}

	public void setTipo_combustible(String tipo_combustible) {
		this.tipo_combustible = tipo_combustible;
	}

	public String getTarjeta_circulacion() {
		return tarjeta_circulacion;
	}

	public void setTarjeta_circulacion(String tarjeta_circulacion) {
		this.tarjeta_circulacion = tarjeta_circulacion;
	}

	public String getVigencia_circulacion() {
		return vigencia_circulacion;
	}

	public void setVigencia_circulacion(String vigencia_circulacion) {
		this.vigencia_circulacion = vigencia_circulacion;
	}

	public Vehiculo getVehiculo() {
		return vehiculo;
	}

	public void setVehiculo(Vehiculo vehiculo) {
		this.vehiculo = vehiculo;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	@Override
	public String toString() {
		return "ID DETALLE:" +id_detalle+ " " + "RIN:" + rin + " " + "MOTOR:"+ no_motor + " " +"NO ECONOMICO:"+no_economico+ " "+
				"CILINDROS:"+ no_cilindros + " " +  "PERS:" + no_personas + " " + "PUERTAS:" + no_puertas + " " + "COMBUS:" + tipo_combustible + " " +
			     "COLOR:" + color;
	}

}
