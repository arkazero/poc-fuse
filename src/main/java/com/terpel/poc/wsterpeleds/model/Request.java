package com.terpel.poc.wsterpeleds.model;

import javax.validation.constraints.NotNull;

public class Request {
	
	private int idTransaccion;	
	@NotNull(message = "idIDS cannot be null")
	private String idDS;
	private int idSurtidor;
	private int idCara;
	
	
	public int getIdTransaccion() {
		return idTransaccion;
	}
	public void setIdTransaccion(int idTransaccion) {
		this.idTransaccion = idTransaccion;
	}
	public String getIdDS() {
		return idDS;
	}
	public void setIdDS(String idDS) {
		this.idDS = idDS;
	}
	public int getIdSurtidor() {
		return idSurtidor;
	}
	public void setIdSurtidor(int idSurtidor) {
		this.idSurtidor = idSurtidor;
	}
	public int getIdCara() {
		return idCara;
	}
	public void setIdCara(int idCara) {
		this.idCara = idCara;
	}
	
	
	
	

}
