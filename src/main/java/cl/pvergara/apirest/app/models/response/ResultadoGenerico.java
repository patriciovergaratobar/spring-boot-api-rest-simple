package cl.pvergara.apirest.app.models.response;

import java.util.Optional;

public class ResultadoGenerico<T> {

	private Boolean estado;
	private String msg;
	private Optional<T> data;
	public Boolean getEstado() {
		return estado;
	}
	public void setEstado(Boolean estado) {
		this.estado = estado;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public Optional<T> getData() {
		return data;
	}
	public void setData(Optional<T> data) {
		this.data = data;
	} 
	
}
