package entrega2.Entrega3;

import java.io.IOException;
import java.sql.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import static conexion.ConectorSQL.USER;
import static conexion.ConectorSQL.PASS;
public class App {
  public static void main(String[] args) {

  //argumentos...
  Path archivo =Paths.get(args[0]);
  //archivo csv con resultados
  Path archivo2 =Paths.get(args[1]);
  //archivo csv con configuracion= conexion a BD, puntaje x partido bien, puntos extra
  int puntajeASumarActual= 1;
  int puntosExtraActual= 5;
  String DB_URL= "a";

  List<String> lineasResultados = null;
  String lineaConfiguracion = null;

  try {
    lineasResultados = Files.readAllLines(archivo);
  }
  catch (Exception e){
	System.out.println("hay problemas (seccion resultados): " + e);
  }
  try {
	lineaConfiguracion = Files.readString(archivo2);
	String [] lineaCortada= lineaConfiguracion.split(",");
	DB_URL=lineaCortada[0];
	puntajeASumarActual= Integer.parseInt(lineaCortada[1]);
	puntosExtraActual= Integer.parseInt(lineaCortada[2]);
  }
  catch (Exception e){
	System.out.println("hay problemas (seccion configuracion): " + e);
  }
  //Terminamos con args, ahora resultados...

  ArrayList<Partido> partidosRonda= new ArrayList<Partido>();
  ArrayList<Ronda> rondas= new ArrayList<Ronda>();
  ArrayList<ArrayList<Ronda>> fases= new ArrayList<ArrayList<Ronda>>();
  //es un arrayList con las Rondas de cada fase

  int numeroRonda=1;
  String faseActual="Grupos";
  Ronda r= new Ronda(partidosRonda,1,faseActual);
  String [] lineaCortada= {"a","b","c"};

  for (String linea : lineasResultados){
    lineaCortada= linea.split(",");
	if(Integer.parseInt(lineaCortada[0])== numeroRonda) {
	  Equipo e1= new Equipo(lineaCortada[1],"seleccion nacional");
	  Equipo e2= new Equipo(lineaCortada[4],"seleccion nacional");
	  Partido p= new Partido(e1,e2,Integer.parseInt(lineaCortada[2]),
	    Integer.parseInt(lineaCortada[3]));
	  partidosRonda.add(p);
	}
	else {
	  r.setPartidos(partidosRonda);
	  rondas.add(r);
	  if(!lineaCortada[5].equals(faseActual)) {
		fases.add(rondas);
		faseActual=lineaCortada[5];
	    rondas= new ArrayList<Ronda>();
	  }
	  numeroRonda++;
	  partidosRonda= new ArrayList<Partido>();
	  r= new Ronda(partidosRonda,numeroRonda,faseActual);
	  Equipo e1= new Equipo(lineaCortada[1],"seleccion nacional");
	  Equipo e2= new Equipo(lineaCortada[4],"seleccion nacional");
	  Partido p= new Partido(e1,e2,Integer.parseInt(lineaCortada[2]),
	    Integer.parseInt(lineaCortada[3]));
	  partidosRonda.add(p);
	}
  }
  r.setPartidos(partidosRonda);
  rondas.add(r);
  fases.add(rondas);

  fases.stream().forEach(rondasDeFases -> rondasDeFases.stream().
    forEach(ronda -> System.out.println("Fase: "+ronda.getFase()+" "+ronda.toString()+"\n")));

  //analizando pronosticos con los datos de la base de datos...
  Participante participante= new Participante();
  String nombreActual= "unknown";
  int rondaActual=0;
  String faseActualTabla="a";
  Scanner s= new Scanner(System.in);
  System.out.println("Elija un equipo para ver si los participantes"+
    "acertaron sus resultados en una fase (Si alguien acierta gana puntos extra)");
  String EquipoFase= s.next();
  int i=0;
  ArrayList<Participante> participantes= new ArrayList<Participante>();
  ArrayList<Boolean> tablaAciertosRonda= new ArrayList<Boolean>();
  ArrayList<Boolean> tablaAciertosFase= new ArrayList<Boolean>();
  boolean cambioRonda= false;
  boolean cambioFase= false;
  Connection conexion = null;
  Statement consulta = null;


  try {
	//abrir la conexion
	conexion = DriverManager.getConnection(DB_URL, USER, PASS);
	//ejecutar consulta
	consulta = conexion.createStatement();
	String sql= "SELECT id, Apellido, Nombre, Puntos, EquipoLocal,"
	  + " EquipoVisitante, EquipoGanador FROM pronosticos.participantes";
	//en la variable resultado obtendremos las distintas filas que nos devolvió la base
	ResultSet resultadoSQL = consulta.executeQuery(sql);
	//obtener cada fila
	while (resultadoSQL.next()) {
	  //obtengo las columnas que necesito (id, y puntaje no, por ahora)
	  String Apellido = resultadoSQL.getString("Apellido");
	  String Nombre = resultadoSQL.getString("Nombre");
	  String EquipoLocal = resultadoSQL.getString("EquipoLocal");
	  String EquipoVisitante = resultadoSQL.getString("EquipoVisitante");
	  String EquipoGanador = resultadoSQL.getString("EquipoGanador");
	  if(i==0) { //si o si hay que crear el primer participante
		nombreActual=Apellido+","+Nombre;
		System.out.println("Participante actual: "+ nombreActual);
		participante= new Participante(Nombre,Apellido,puntajeASumarActual);
		i++;
	  }
	  else {
		if(!((Apellido+","+Nombre).equals(nombreActual))) { 
		  /** si cambia el nombre y apellido, es porque es otra persona,
		   *  asi que tengo que ver sus aciertos y demas, y sumar puntos extra o no
		  */
		  if(acertoTodo(tablaAciertosRonda)) {
		    participante.incrementarExtra(puntosExtraActual);
			System.out.println("SUMASTE PUNTOS EXTRA POR RONDA");
		  }
		  else {
		    System.out.println("NO SUMASTE PUNTOS EXTRA POR RONDA");
		  }

		  if(acertoTodo(tablaAciertosFase)) {
		    participante.incrementarExtra(puntosExtraActual);
		    System.out.println("SUMASTE PUNTOS EXTRA POR FASE");
		  }
		  else {
			System.out.println("NO SUMASTE PUNTOS EXTRA POR FASE");
		  }
		  participantes.add(participante);
		  tablaAciertosRonda= new ArrayList<Boolean>();
		  tablaAciertosFase= new ArrayList<Boolean>();
		  nombreActual=Apellido+","+Nombre;
		  System.out.println("--------------------");
		  System.out.println("Participante actual: "+ nombreActual);
		  participante= new Participante(Nombre,Apellido,puntajeASumarActual);
		  i=1;
		}
	  }
	  Equipo equipo1=new Equipo(EquipoLocal,"seleccion nacional");
	  Equipo equipo2=new Equipo(EquipoVisitante,"seleccion nacional");
	  Partido partidoActual= null;
      /** buscando partido, la unica desventaja que tiene es que si se repite un partido,
	  *  se guarda el ultimo encontrado
	  */
	  for(ArrayList<Ronda> fase: fases) {
		for (Ronda ronda : fase) {
		  for(Partido partido: ronda.getPartidos()) {
			if (partido.getEquipo1().getNombre().equals(equipo1.getNombre())
				&& partido.getEquipo2().getNombre().equals(equipo2.getNombre())){
			  partidoActual = partido;
			  if(i==1) {
			    rondaActual= ronda.getNro();
			    faseActualTabla= ronda.getFase();
			  }
			  if(faseActualTabla!=ronda.getFase()) {
				faseActualTabla= ronda.getFase();
				cambioFase= true;
			  }
			  else {
			    cambioFase= false;
			  }
			  if(rondaActual!=ronda.getNro()) {
				rondaActual= ronda.getNro();
				cambioRonda= true;
			  }
			  else {
				cambioRonda= false;
			  }
			}
		  }
		}
	  }
	  if(cambioRonda) {
	    //analisis, y sumar puntos extra o no
		if(acertoTodo(tablaAciertosRonda)) {
		  participante.incrementarExtra(puntosExtraActual);
		  System.out.println("SUMASTE PUNTOS EXTRA POR RONDA");
		}
		else {
		  System.out.println("NO SUMASTE PUNTOS EXTRA POR RONDA");
		}
		tablaAciertosRonda= new ArrayList<Boolean>();
	  }
	  if(cambioFase) {
	    //analisis, y sumar puntos extra o no
		if(acertoTodo(tablaAciertosFase)) {
		  participante.incrementarExtra(puntosExtraActual);
		  System.out.println("SUMASTE PUNTOS EXTRA POR FASE");
		}
		else {
		  System.out.println("NO SUMASTE PUNTOS EXTRA POR FASE");
		}
		tablaAciertosFase= new ArrayList<Boolean>();
	  }
	  ResultadoEnum resultado = null;
	  //estos if son desde el punto de vista del equipo Local
	  if(EquipoGanador.equals(EquipoLocal)) {
		resultado = ResultadoEnum.GANADOR;
	  }
	  if(EquipoGanador.equals("Empate")) {
		resultado = ResultadoEnum.EMPATE;
	  }
	  if(EquipoGanador.equals(EquipoVisitante)) {
	    resultado = ResultadoEnum.PERDEDOR;
	  }
	  //a pronostico lo use nomas para ver si la prediccion es correcta, si lo es devuelve 1, sino 0
	  Pronostico pronostico = new Pronostico(partidoActual, equipo1, resultado);
	  if(pronostico.puntos()==1) {
	    tablaAciertosRonda.add(true);
		participante.incrementar();
		if((equipo1.getNombre().equals(EquipoFase))||(equipo2.getNombre().equals(EquipoFase))) {
		  //si acerto al resultado, suma
		  tablaAciertosFase.add(true);
		}
		System.out.println(partidoActual.toString()+": SUMA PUNTOS");
	  }
	  else {
	    System.out.println(partidoActual.toString()+": NO SUMA PUNTOS");
		tablaAciertosRonda.add(false);
		if((equipo1.getNombre().equals(EquipoFase))||(equipo2.getNombre().equals(EquipoFase))) {
			tablaAciertosFase.add(false);
		}
	  }
	  i++;
	}
	//aca ver lo de los extras
	if(acertoTodo(tablaAciertosRonda)) {
	  participante.incrementarExtra(puntosExtraActual);
	  System.out.println("SUMASTE PUNTOS EXTRA POR RONDA");
	}
	else {
	  System.out.println("NO SUMASTE PUNTOS EXTRA POR RONDA");
	}
	if(acertoTodo(tablaAciertosFase)) {
	  participante.incrementarExtra(puntosExtraActual);
	  System.out.println("SUMASTE PUNTOS EXTRA POR FASE");
	}
	else {
	  System.out.println("NO SUMASTE PUNTOS EXTRA POR FASE");
	}
	participantes.add(participante);
	//fin del programa, imprimiendo resultados...
	System.out.println("\nPUNTAJES: \n");
	participantes.stream().forEach(persona -> System.out.println(persona.toString()));
	//cerrando conexion
	resultadoSQL.close();
	consulta.close();
	conexion.close();
	}
    catch (SQLException se) {
	  //exepcion ante problemas de conexion
	  se.printStackTrace();
	}
    finally {
	  //cerrar conexiones si falla algo
	  try {
		if (consulta != null) {
		  consulta.close();
		}
	  }
	  catch (SQLException se2) {
		se2.printStackTrace();
	  }
	  try {
		if (conexion != null) {
		  conexion.close();
		}
	  }
	  catch (SQLException se) {
	    se.printStackTrace();
	  }
	}
  }  
  static boolean acertoTodo(ArrayList<Boolean> booleans) { 
	//esta funcion es muy utilizable si añado mas reglas
    boolean acerto= true;
	for(Boolean b: booleans) {
	  if(b==false) {
		acerto=false;
	  }
	}
	return acerto;
  }
  /** en caso de querer crear mas reglas para los puntos extra,
  *   se debe crear un nuevo arraylist de booleans,
  *   un booleano de seguimiento (para los cambios),
  *   y la variable actual para ver cuando se hace el analisis
  *   Entonces despues agregar los if debajo de los otros chequeos
  */
}