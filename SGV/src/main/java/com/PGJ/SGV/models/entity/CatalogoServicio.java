package com.PGJ.SGV.models.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "catalogo_servicios")
public class CatalogoServicio implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long ID;
	
	String servicio;
	boolean auto;
	boolean motocicleta;
	boolean blindado;
	boolean gasolina;
	boolean diesel;
	boolean gas;
	
	@Column(name ="1_cilindro")
	boolean un_cilindro;
	@Column(name ="2_cilindro")
	boolean dos_cilindros;
	@Column(name ="3_cilindro")
	boolean tres_cilindros;
	@Column(name ="4_cilindro")
	boolean cuatro_cilindros;
	@Column(name ="5_cilindro")
	boolean cinco_cilindros;
	@Column(name ="6_cilindro")
	boolean seis_cilindros;
	@Column(name ="8_cilindro")
	boolean ocho_cilindros;
	@Column(name ="10_cilindro")
	boolean dies_cilindros;
	
	String categoria;
		
	public Long getId() {
		return ID;
	}

	public void setId(Long id) {
		this.ID = id;
	}

	public String getServicio() {
		return servicio;
	}

	public void setServicio(String servicio) {
		this.servicio = servicio;
	}

	public boolean isAuto() {
		return auto;
	}

	public void setAuto(boolean auto) {
		this.auto = auto;
	}

	public boolean isMotocicleta() {
		return motocicleta;
	}

	public void setMotocicleta(boolean motocicleta) {
		this.motocicleta = motocicleta;
	}

	public boolean isBlindado() {
		return blindado;
	}

	public void setBlindado(boolean blindado) {
		this.blindado = blindado;
	}

	public boolean isGasolina() {
		return gasolina;
	}

	public void setGasolina(boolean gasolina) {
		this.gasolina = gasolina;
	}

	public boolean isDiesel() {
		return diesel;
	}

	public void setDiesel(boolean diesel) {
		this.diesel = diesel;
	}

	public boolean isGas() {
		return gas;
	}

	public void setGas(boolean gas) {
		this.gas = gas;
	}

	public boolean isUn_cilindro() {
		return un_cilindro;
	}

	public void setUn_cilindro(boolean un_cilindro) {
		this.un_cilindro = un_cilindro;
	}

	public boolean isDos_cilindros() {
		return dos_cilindros;
	}

	public void setDos_cilindros(boolean dos_cilindros) {
		this.dos_cilindros = dos_cilindros;
	}

	public boolean isTres_cilindros() {
		return tres_cilindros;
	}

	public void setTres_cilindros(boolean tres_cilindros) {
		this.tres_cilindros = tres_cilindros;
	}

	public boolean isCuatro_cilindros() {
		return cuatro_cilindros;
	}

	public void setCuatro_cilindros(boolean cuatro_cilindros) {
		this.cuatro_cilindros = cuatro_cilindros;
	}

	public boolean isCinco_cilindros() {
		return cinco_cilindros;
	}

	public void setCinco_cilindros(boolean cinco_cilindros) {
		this.cinco_cilindros = cinco_cilindros;
	}

	public boolean isSeis_cilindros() {
		return seis_cilindros;
	}

	public void setSeis_cilindros(boolean seis_cilindros) {
		this.seis_cilindros = seis_cilindros;
	}

	public boolean isOcho_cilindros() {
		return ocho_cilindros;
	}

	public void setOcho_cilindros(boolean ocho_cilindros) {
		this.ocho_cilindros = ocho_cilindros;
	}

	public boolean isDies_cilindros() {
		return dies_cilindros;
	}

	public void setDies_cilindros(boolean dies_cilindros) {
		this.dies_cilindros = dies_cilindros;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}


	private static final long serialVersionUID = 1L;

}
