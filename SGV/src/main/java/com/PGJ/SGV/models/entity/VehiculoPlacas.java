package com.PGJ.SGV.models.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;


@Entity
@Table(name = "vehiculos_placas")

public class VehiculoPlacas implements Serializable {
		
		    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

			@Id
		    @Column(name="id_placa")
		    private long id_placa;
		    
			private String placa_anterior;	    
			private String fecha_inicio;
			private String fecha_fin;
		
			@ManyToOne(fetch = FetchType.LAZY)
			private Vehiculo vehiculo;

			
			public Vehiculo getVehiculo() {
				return vehiculo;
			}

			public void setVehiculo(Vehiculo vehiculo) {
				this.vehiculo = vehiculo;
			}
			
			public static long getSerialversionuid() {
				return serialVersionUID;
			}


			public String getPlaca_anterior() {
				return placa_anterior;
			}


			public void setPlaca_anterior(String placa_anterior) {
				this.placa_anterior = placa_anterior;
			}


			public String getFecha_fin() {
				return fecha_fin;
			}


			public void setFecha_fin(String fecha_fin) {
				this.fecha_fin = fecha_fin;
			}


			public String getFecha_inicio() {
				return fecha_inicio;
			}


			public void setFecha_inicio(String fecha_inicio) {
				this.fecha_inicio = fecha_inicio;
			}
			
	}


