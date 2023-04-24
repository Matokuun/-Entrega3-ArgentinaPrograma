package entrega2.Entrega3;

public class Equipo {
	private String nombre;
	private String descripcion;
	
	public Equipo(String n, String des) {
		this.setNombre(n);
		this.setDescripcion(des);
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
}
