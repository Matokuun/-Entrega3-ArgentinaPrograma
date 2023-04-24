package entrega2.Entrega3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;


/**
 * Unit test for simple App.
 */
public class PartidoTest 
{
    private Equipo e1= new Equipo("River","club de futbol argentino");
    private Equipo e2= new Equipo("Boca","club de futbol argentino");
    private Partido p= new Partido(e1,e2,3,1);
    private Partido pEmpatado= new Partido(e1,e2,2,2);
    
    @Test
    public void ganador()
    {
        assertEquals(p.resultado(e1),ResultadoEnum.GANADOR);
    }
	@Test
    public void empate()
    {
		assertEquals(pEmpatado.resultado(e1),ResultadoEnum.EMPATE);
		assertEquals(pEmpatado.resultado(e2),ResultadoEnum.EMPATE);
    }
	@Test
    public void perdedor()
    {
		assertEquals(p.resultado(e2),ResultadoEnum.PERDEDOR);
    }
	
}
