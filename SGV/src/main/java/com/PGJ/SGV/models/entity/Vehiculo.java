package com.PGJ.SGV.models.entity;

import java.io.Serializable;
import java.util.ArrayList;
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
@Table(name = "vehiculos")
public class Vehiculo implements Serializable {
	
	
	    @Id
	    @Column(name="id_vehiculo")
	    private long id_vehiculo;
	    
		private String placa;	    
		private String placa_anterior;
		private String no_serie;
		private String no_inventario;
		private String fecha_tarjeta;
		private String vale;		
		private String motivo;
		private double kilometraje_inicial;
		
		@ManyToOne(fetch = FetchType.LAZY)
		private VehiculoEstado vehiculo_estado;

		@ManyToOne(fetch = FetchType.LAZY)
		private VehiculoMarca vehiculo_marca;

		@ManyToOne(fetch = FetchType.LAZY)
		private VehiculoFuncion vehiculo_funcion;
		
		@ManyToOne(fetch = FetchType.LAZY)
		private VehiculoTransmision vehiculo_transmision;
		
		@OneToOne(cascade = CascadeType.ALL)	
		@JoinColumn(name ="id_vehiculo")
		private VehiculoDetalle vehiculo_detalle;
		
		@OneToOne(cascade = CascadeType.ALL)	
		@JoinColumn(name ="id_vehiculo")
		private BajaVehiculo bajavehiculo;
		
		@ManyToOne(fetch = FetchType.LAZY)
		private Adscripcion adscripcion;
		
		@OneToMany(mappedBy = "vehiculo",fetch = FetchType.LAZY)
		private List<Seguro> seguros;
		
		@OneToMany(mappedBy = "vehiculo",fetch = FetchType.LAZY)
		private List<Revista> revistas;
			
		
		//SQL

		
		@OneToMany(mappedBy = "vehiculo", fetch = FetchType.LAZY)
		private List<Viaje> viajes;
		
		@OneToMany(mappedBy = "vehiculo", fetch = FetchType.LAZY)
		private List<Mantenimiento> mantenimientos;
		
		@OneToMany(mappedBy = "vehiculo", fetch = FetchType.LAZY)
		private List<AsigCombustible> asignaciones;			
		
		@OneToMany(mappedBy = "vehiculo", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
		private List<Resguardante> resguardante;

		
	
		
		//SQL			
		
	
		
		public VehiculoEstado getVehiculo_estado() {
			return vehiculo_estado;
		}
		
		public BajaVehiculo getBajavehiculo() {
			return bajavehiculo;
		}

		public void setBajavehiculo(BajaVehiculo bajavehiculo) {
			this.bajavehiculo = bajavehiculo;
		}

		public List<Revista> getRevistas() {
			return revistas;
		}

		public void setRevistas(List<Revista> revistas) {
			this.revistas = revistas;
		}

		public double getKilometraje_inicial() {
			return kilometraje_inicial;
		}

		public void setKilometraje_inicial(double kilometraje_inicial) {
			this.kilometraje_inicial = kilometraje_inicial;
		}

		public List<Resguardante> getResguardante() {
			return resguardante;
		}

		public void setResguardante(List<Resguardante> resguardante) {
			this.resguardante = resguardante;
		}

		public void setVehiculo_estado(VehiculoEstado vehiculo_estado) {
			this.vehiculo_estado = vehiculo_estado;
		}		

		private static final long serialVersionUID = 1L;

		public Vehiculo() {
			
			viajes = new ArrayList<Viaje>();
			mantenimientos = new ArrayList<Mantenimiento>();
			asignaciones = new ArrayList<AsigCombustible>();	
			vehiculo_detalle = new VehiculoDetalle();
			vehiculo_estado = new VehiculoEstado();
			vehiculo_funcion = new VehiculoFuncion();
			vehiculo_marca = new VehiculoMarca();
			vehiculo_transmision = new VehiculoTransmision();
			resguardante = new ArrayList<Resguardante>();
		}
		
				

		public long getId_vehiculo() {
			return id_vehiculo;
		}

		public void setId_vehiculo(long id_vehiculo) {
			this.id_vehiculo = id_vehiculo;
		}

		public String getPlaca() {
			return placa;
		}

		public void setPlaca(String placa) {
			this.placa = placa;
		}
	

		public String getNo_serie() {
			return no_serie;
		}

		public void setNo_serie(String no_serie) {
			this.no_serie = no_serie;
		}	

		public static long getSerialversionuid() {
			return serialVersionUID;
		}
		
		public Adscripcion getAdscripcion() {
			return adscripcion;
		}

		public void setAdscripcion(Adscripcion adscripcion) {
			this.adscripcion = adscripcion;
		}

		public List<Viaje> getViajes() {
			return viajes;
		}

		public void setViajes(List<Viaje> viajes) {
			this.viajes = viajes;
		}
		
		public void adViaje(Viaje viaje) {
			viajes.add(viaje);
		}

		public List<Mantenimiento> getMantenimientos() {
			return mantenimientos;
		}

		public void setMantenimientos(List<Mantenimiento> mantenimientos) {
			this.mantenimientos = mantenimientos;
		}
		
		public void adMantenimiento (Mantenimiento mantenimiento) {
			mantenimientos.add(mantenimiento);
		}

		public List<AsigCombustible> getAsignaciones() {
			return asignaciones;
		}

		public void setAsignaciones(List<AsigCombustible> asignaciones) {
			this.asignaciones = asignaciones;
		}
		public void adAsignaciones (AsigCombustible asignacion) {
			asignaciones.add(asignacion);
		}		
	
		public String getPlaca_anterior() {
			return placa_anterior;
		}

		public void setPlaca_anterior(String placa_anterior) {
			this.placa_anterior = placa_anterior;
		}

		public String getNo_inventario() {
			return no_inventario;
		}

		public void setNo_inventario(String no_inventario) {
			this.no_inventario = no_inventario;
		}

		public String getFecha_tarjeta() {
			return fecha_tarjeta;
		}

		public void setFecha_tarjeta(String fecha_tarjeta) {
			this.fecha_tarjeta = fecha_tarjeta;
		}

		public String getVale() {
			return vale;
		}

		public void setVale(String vale) {
			this.vale = vale;
		}

		public VehiculoDetalle getVehiculo_detalle() {
			return vehiculo_detalle;
		}

		public void setVehiculo_detalle(VehiculoDetalle vehiculo_detalle) {
			this.vehiculo_detalle = vehiculo_detalle;
		}

		public VehiculoFuncion getVehiculo_funcion() {
			return vehiculo_funcion;
		}

		public void setVehiculo_funcion(VehiculoFuncion vehiculo_funcion) {
			this.vehiculo_funcion = vehiculo_funcion;
		}

		public VehiculoMarca getVehiculo_marca() {
			return vehiculo_marca;
		}

		public void setVehiculo_marca(VehiculoMarca vehiculo_marca) {
			this.vehiculo_marca = vehiculo_marca;
		}

		public VehiculoTransmision getVehiculo_transmision() {
			return vehiculo_transmision;
		}

		public void setVehiculo_transmision(VehiculoTransmision vehiculo_transmision) {
			this.vehiculo_transmision = vehiculo_transmision;
		}

		public String getMotivo() {
			return motivo;
		}

		public void setMotivo(String motivo) {
			this.motivo = motivo;
		}

		public List<Seguro> getSeguros() {
			return seguros;
		}

		public void setSeguros(List<Seguro> seguros) {
			this.seguros = seguros;
		}
		
		@Override
		public String toString() {
			return "ID VEHICULO:"  +id_vehiculo+ " ; " +"PLACA:" + placa + " ; " + "NO SERIE:" + no_serie + " ; " +
					"NO INVENTARIO:" + no_inventario + " ; " + "FECHA TARJETA:"+ fecha_tarjeta + " ; " +"VALE:"+ vale + " ; " +
					"KILO INI:"+ kilometraje_inicial + " ; " +  "ADS:" + adscripcion.getId_adscripcion();
		}				
		
}
