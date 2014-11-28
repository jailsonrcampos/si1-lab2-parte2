import static org.junit.Assert.*;

import java.util.GregorianCalendar;

import models.Calendario;
import org.junit.Test;


public class CalendarioTest {

	@Test
	public void testDeveObterCalendarioDaProximaSemana() {
		GregorianCalendar semana = Calendario.getCalendarioDaSemana(0);
		GregorianCalendar semana2 = Calendario.getCalendarioDaSemana(1);
		GregorianCalendar semana3 = Calendario.getCalendarioDaSemana(2);
		assertEquals(1,semana.get(GregorianCalendar.DAY_OF_WEEK));
		assertEquals(1,semana2.get(GregorianCalendar.DAY_OF_WEEK));
		assertEquals(1,semana3.get(GregorianCalendar.DAY_OF_WEEK));
		semana.add(GregorianCalendar.DATE, 7);
		assertEquals(semana2.get(GregorianCalendar.DATE), semana.get(GregorianCalendar.DATE));
		semana.add(GregorianCalendar.DATE, 7);
		assertEquals(semana3.get(GregorianCalendar.DATE), semana.get(GregorianCalendar.DATE));
	}

	@Test
	public void testDeveObterStringDaSemana() {
		GregorianCalendar calendario = Calendario.getCalendarioDaSemana(0);
		String temp = new String();
		temp = calendario.get(GregorianCalendar.DAY_OF_MONTH) + "/" +
				(calendario.get(GregorianCalendar.MONTH)+1) + "/" +
				calendario.get(GregorianCalendar.YEAR) + " - ";
		calendario.add(GregorianCalendar.DATE, 6);
		temp += calendario.get(GregorianCalendar.DAY_OF_MONTH) + "/" +
				(calendario.get(GregorianCalendar.MONTH)+1) + "/" +
				calendario.get(GregorianCalendar.YEAR);
		GregorianCalendar semana = Calendario.getCalendarioDaSemana(0);
		assertEquals(Calendario.getSemana(semana), temp);
		assertEquals(Calendario.getSemana(0), temp);
	}
	
	@Test
	public void testDeveObterMatrizDeStringDasSemanas() {
		String semana1[] = new String[3];
		semana1[0] = Calendario.getSemana(0);
		semana1[1] = Calendario.getSemana(1);
		semana1[2] = Calendario.getSemana(2);
		assertArrayEquals(semana1, Calendario.getSemanas(3));
	}
}
