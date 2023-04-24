package entrega2.Entrega3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;


/**
 * Unit test for simple App.
 */
public class PronosticoTest 
{
    private Equipo e1= new Equipo("River","club de futbol argentino");
    private Equipo e2= new Equipo("Boca","club de futbol argentino");
    private Partido p= new Partido(e1,e2,3,1);
    @Test
    public void acertar()
    {
    	Pronostico pronostico = new Pronostico(p, e1, ResultadoEnum.GANADOR);
    	assertEquals(1,pronostico.puntos());
    }
	@Test
    public void errar()
    {
		Pronostico pronostico = new Pronostico(p, e1, ResultadoEnum.EMPATE);
		assertEquals(0,pronostico.puntos());
    }
	@Test
    public void acertarDerrota()
    {
		Pronostico pronostico = new Pronostico(p, e2, ResultadoEnum.PERDEDOR);
		assertEquals(1,pronostico.puntos());
    }
	
}
