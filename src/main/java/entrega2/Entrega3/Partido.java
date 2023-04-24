package entrega2.Entrega3;

public class Partido {
	private Equipo equipo1;
	private Equipo equipo2;
	private int golesEquipo1;
	private int golesEquipo2;
	
	public Partido() {
		
	}
	public Partido(Equipo a, Equipo b, int golesA, int golesB) {
		this.setEquipo1(a);
		this.setEquipo2(b);
		this.setGolesEquipo1(golesA);
		this.setGolesEquipo2(golesB);
	}
	public Equipo getEquipo1() {
		return equipo1;
	}
	public void setEquipo1(Equipo equipo1) {
		this.equipo1 = equipo1;
	}
	public Equipo getEquipo2() {
		return equipo2;
	}
	public void setEquipo2(Equipo equipo2) {
		this.equipo2 = equipo2;
	}
	public int getGolesEquipo1() {
		return golesEquipo1;
	}
	public void setGolesEquipo1(int golesEquipo1) {
		this.golesEquipo1 = golesEquipo1;
	}
	public int getGolesEquipo2() {
		return golesEquipo2;
	}
	public void setGolesEquipo2(int golesEquipo2) {
		this.golesEquipo2 = golesEquipo2;
	}
	
	public String toString() {
		return this.equipo1.getNombre() +" "+ this.golesEquipo1+ " - "+ this.golesEquipo2+ " "+ this.equipo2.getNombre();
	}
	public ResultadoEnum resultado(Equipo a) {
		int state= 0;
		if(a.getNombre().equals(equipo1.getNombre())) {
			if(golesEquipo1 > golesEquipo2) {
				state=1;
			}
			else {
				if(golesEquipo1 < golesEquipo2) {
					state=2;
				}
				else {
					state=3;
				}
			}
		}
		if(a.getNombre().equals(equipo2.getNombre())) {
			if(golesEquipo2 > golesEquipo1) {
				state=1;
			}
			else {
				if(golesEquipo2 < golesEquipo1) {
					state=2;
				}
				else {
					state=3;
				}
			}
		}
		ResultadoEnum res;
		switch (state){
		case 1:
			//gano
			res= ResultadoEnum.GANADOR;
			break;
		case 2:
			//perdio
			res= ResultadoEnum.PERDEDOR;
			break;
		case 3:
			//empato
			res= ResultadoEnum.EMPATE;
			break;
		default:
			res=ResultadoEnum.EMPATE;
		}
		return res;
	}
	
}
