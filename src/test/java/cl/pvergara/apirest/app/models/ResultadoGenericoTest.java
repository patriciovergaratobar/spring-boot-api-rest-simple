package cl.pvergara.apirest.app.models;

public class ResultadoGenericoTest<T> {

	private Boolean estado;
	private String msg;
	private T data;
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
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	} 
	
}

