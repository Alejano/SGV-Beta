package com.PGJ.SGV.models.entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "logs_audit")

public class LogsAudit  implements Serializable {
	
	    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;


		@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;
		
	    
	   
		private String id_usuario;
		private String tipo_role;
		private String fecha;
		private String hora;
		private String name_table;
		private String valor_viejo;
	    private String tipo_accion;

	    
	    public Long getId() {
			return id;
		}
		public void setId(Long id) {
			this.id = id;
		}
		public String getFecha() {
			return fecha;
		}
		public void setFecha(String fecha) {
			this.fecha = fecha;
		}
		public String getId_usuario() {
			return id_usuario;
		}
		public void setId_usuario(String id_usuario) {
			this.id_usuario = id_usuario;
		}
		
		public String getValor_viejo() {
			return valor_viejo;
		}
		public void setValor_viejo(String valor_viejo) {
			this.valor_viejo = valor_viejo;
		}
		public String getTipo_accion() {
			return tipo_accion;
		}
		public void setTipo_accion(String tipo_accion) {
			this.tipo_accion = tipo_accion;
		}
		public String getTipo_role() {
			return tipo_role;
		}
		public void setTipo_role(String tipo_role) {
			this.tipo_role = tipo_role;
		}
		public String getHora() {
			return hora;
		}
		public void setHora(String hora) {
			this.hora = hora;
		}
		public String getName_table() {
			return name_table;
		}
		public void setName_table(String name_table) {
			this.name_table = name_table;
		}
	    
	    

}
