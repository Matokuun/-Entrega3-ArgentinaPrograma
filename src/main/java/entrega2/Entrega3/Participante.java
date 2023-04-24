package entrega2.Entrega3;

public class Participante {
	private String nombre;
	private String apellido;
	private int puntaje;
	private int puntosASumar;
	
	public Participante() {

	}
	
	public Participante(String n, String a,int p) {
		this.setNombre(n);
		this.setApellido(a);
		this.setPuntaje(0);
		this.setPuntosASumar(p);
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getPuntaje() {
		return puntaje;
	}

	public void setPuntosASumar(int puntaje) {
		this.puntosASumar = puntaje;
	}
	public int getPuntosASumar() {
		return puntosASumar;
	}

	public void setPuntaje(int puntaje) {
		this.puntaje = puntaje;
	}
	public void incrementar() {
		this.puntaje= this.puntaje+ puntosASumar;
	}
	public void incrementarExtra(int extra) {
		this.puntaje= this.puntaje+ extra;
	}
	public String toString() {
		return "Participante: " + this.nombre +" "+this.apellido+", puntaje: "+ this.puntaje + "\n";
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
}
