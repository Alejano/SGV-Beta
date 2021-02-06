package com.PGJ.SGV.models.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name = "tipo_vale")
public class TipoVale implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id_vale;

	private String descr_vale;
	private Double month_28;
	private Double month_29;
	private Double month_30;
	private Double month_31;
	private Long ano_min;
	private Long ano_max;
	private boolean status;
	
	
	public Long getId_vale() {
		return id_vale;
	}

	public void setId_vale(Long id_vale) {
		this.id_vale = id_vale;
	}

	public String getDescr_vale() {
		return descr_vale;
	}

	public void setDescr_vale(String descr_vale) {
		this.descr_vale = descr_vale;
	}

	public Double getMonth_28() {
		return month_28;
	}

	public void setMonth_28(Double month_28) {
		this.month_28 = month_28;
	}

	public Double getMonth_29() {
		return month_29;
	}

	public void setMonth_29(Double month_29) {
		this.month_29 = month_29;
	}
	
	public Double getMonth_30() {
		return month_30;
	}

	public void setMonth_30(Double month_30) {
		this.month_30 = month_30;
	}

	public Double getMonth_31() {
		return month_31;
	}

	public void setMonth_31(Double month_31) {
		this.month_31 = month_31;
	}

	public Long getAno_min() {
		return ano_min;
	}

	public void setAno_min(long ano_min) {
		this.ano_min = ano_min;
	}

	public Long getAno_max() {
		return ano_max;
	}

	public void setAno_max(long ano_max) {
		this.ano_max = ano_max;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}
	
	@Override
	public String toString() {
		return "ID_VIAJE:"+id_vale ;
	}
}
