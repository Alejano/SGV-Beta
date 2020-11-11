package com.PGJ.SGV.models.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
//import javax.persistence.PrePersist;
import javax.persistence.Table;

@Entity
@Table(name = "aseguradora")
public class Aseguradora implements Serializable {

	private static final long serialVersionUID = 1L;

       @Id
       @GeneratedValue(strategy = GenerationType.IDENTITY)
       private Long id_aseguradora;
       
       private String nombre_aseguradora;
       private Long no_poliza_padre;
   	   private String no_contrato;
   	   private String rfc_aseguradora;
   	   private String fecha_inicio;
   	   private String fecha_fin;
   	   private String tel_aseguradora;
   	   private String calle_domicilio;
   	   private Long numero_domicilio;
   	   private String colonia_domicilio;
   	   private Long codigopos_domicilio;
       private String alcaldia_domicilio;
   	   private boolean enabled;
	
       @OneToMany(mappedBy = "aseguradora",fetch = FetchType.LAZY)
		private List<Seguro> seguros;
       
       public Long getId_aseguradora() {
   		return id_aseguradora;
   	   }

   	   public void setId_aseguradora(Long id_aseguradora) {
   		this.id_aseguradora = id_aseguradora;
   	   }
 
   	   public static long getSerialversionuid() {
   		return serialVersionUID;
   	   }

	   public String getNombre_aseguradora() {
		return nombre_aseguradora;
	   }

	   public void setNombre_aseguradora(String nombre_aseguradora) {
		this.nombre_aseguradora = nombre_aseguradora;
	   }

	   public Long getNo_poliza_padre() {
		return no_poliza_padre;
	   }

	   public void setNo_poliza_padre(Long no_poliza_padre) {
		this.no_poliza_padre = no_poliza_padre;
	   }

	   public String getNo_contrato() {
		return no_contrato;
	   }

	   public void setNo_contrato(String no_contrato) {
		this.no_contrato = no_contrato;
	   }

	   public String getRfc_aseguradora() {
		return rfc_aseguradora;
	   }

	   public void setRfc_aseguradora(String rfc_aseguradora) {
		this.rfc_aseguradora = rfc_aseguradora;
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

	   public String getTel_aseguradora() {
		return tel_aseguradora;
	   }

	   public void setTel_aseguradora(String tel_aseguradora) {
		this.tel_aseguradora = tel_aseguradora;
	   }

	   public String getCalle_domicilio() {
		return calle_domicilio;
	   }

	   public void setCalle_domicilio(String calle_domicilio) {
		this.calle_domicilio = calle_domicilio;
	   }

	   public Long getNumero_domicilio() {
		return numero_domicilio;
	   }

	   public void setNumero_domicilio(Long numero_domicilio) {
		this.numero_domicilio = numero_domicilio;
	   }

	   public String getColonia_domicilio() {
		return colonia_domicilio;
	   }

	   public void setColonia_domicilio(String colonia_domicilio) {
		this.colonia_domicilio = colonia_domicilio;
	   }

	   public Long getCodigopos_domicilio() {
		return codigopos_domicilio;
	   }

	   public void setCodigopos_domicilio(Long codigopos_domicilio) {
		this.codigopos_domicilio = codigopos_domicilio;
	   }

	   public String getAlcaldia_domicilio() {
		return alcaldia_domicilio;
	   }

	   public void setAlcaldia_domicilio(String alcaldia_domicilio) {
	    this.alcaldia_domicilio = alcaldia_domicilio;
	   }
	   
	   public boolean isEnabled() {
			return enabled;
		}
		
	   public void setEnabled(boolean enabled) {
			this.enabled = enabled;
		}
	   
	   @Override
		public String toString() {
			return "ID ASEG:"+id_aseguradora+ " ; " +"NOMBRE ASEG:" + nombre_aseguradora + " ; " + "POLIZA PADRE:" + no_poliza_padre + " ; " +
					"NO CONTRATO:"+ no_contrato + " ; " + "RFC ASEG:"+ rfc_aseguradora + " ; " +"FECHA INICIO:"+ fecha_inicio + " ; " +
					"FECHA FIN:"+ fecha_fin+ " ; " +  "TEL:" + tel_aseguradora +"ENABLED:" + enabled;

		}
		
	 		
		
}

