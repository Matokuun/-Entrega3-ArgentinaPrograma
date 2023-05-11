package entrega2.Entrega3;

/**
 * Clase que nos permite representar un participante
 */
public class Participante {
  private String nombre;
  private String apellido;
  private int puntaje;
  private int puntosASumar;
	
  public Participante() {}
	
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
  /**
   * Metodo que sirve para incrementar los puntos
   * del participante
   */
  public void incrementar() {
	this.puntaje= this.puntaje+ puntosASumar;
  }
  /**
   * Metodo que sirve para incrementar los puntos
   * del participante, pero la cantidad obtenida como
   * parametro
   */
  public void incrementarExtra(int extra) {
	this.puntaje= this.puntaje+ extra;
  }
  @Override
  public String toString() {
	return "Participante: " + this.nombre +" "+
      this.apellido+", puntaje: "+ this.puntaje + "\n";
  }
  public String getApellido() {
	return apellido;
  }
  public void setApellido(String apellido) {
	this.apellido = apellido;
  }
}
