package com.PGJ.SGV.models.entity;

public class respuestaRevista {
	
	private long id_vehiculo;
	private Long evento;
	private String fecha_inicio;
	private String fecha_fin;
	private boolean check;
	
	public respuestaRevista() {
		
	}
	
	public respuestaRevista(long id_vehiculo,Long evento,String fecha_inicio,String fecha_fin,boolean check) {
		this.id_vehiculo = id_vehiculo;
		this.evento = evento;
		this.fecha_inicio = fecha_inicio;
		this.fecha_fin = fecha_fin;
		this.check = check;
		
	}
	
	public long getId_vehiculo() {
		return id_vehiculo;
	}
	public void setId_vehiculo(long id_vehiculo) {
		this.id_vehiculo = id_vehiculo;
	}
	public Long getEvento() {
		return evento;
	}
	public void setEvento(Long evento) {
		this.evento = evento;
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
	public boolean isCheck() {
		return check;
	}
	public void setCheck(boolean check) {
		this.check = check;
	}
	
	

}
