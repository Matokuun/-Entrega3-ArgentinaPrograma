package entrega2.Entrega3;

import java.util.ArrayList;
/**
 * Clase que nos permite representar una ronda, con
 * sus partidos, su numero de ronda y la fase a la que pertenece
 */
public class Ronda {
  private ArrayList<Partido> partidos;
  private int nro;
  private String fase;	
  public Ronda(ArrayList<Partido> p, int n, String f) {
    setPartidos(p);
	setNro(n);
	setFase(f);
  }
  public ArrayList<Partido> getPartidos() {
	return partidos;
  }
  public void setPartidos(ArrayList<Partido> partidos) {
	this.partidos = partidos;
  }
  public int getNro() {
	return nro;
  }
  public void setNro(int nro) {
	this.nro = nro;
  }
  /**
   * Metodo que use para respetar el UML dado
   * No tiene utilidad
   */
  public int puntos() {
	return 0;//ver puntos
  }
  @Override
  public String toString() {
	String str= "\nRonda "+ nro;
	for(Partido p: this.partidos) {
	  str= str + "\n" + p.getEquipo1().getNombre() + " " + p.getGolesEquipo1() +
	    " - " + p.getGolesEquipo2() + " "+ p.getEquipo2().getNombre();
	}
	return str;
  }
  public String getFase() {
	return fase;
  }
  public void setFase(String fase) {
	this.fase = fase;
  }
}
