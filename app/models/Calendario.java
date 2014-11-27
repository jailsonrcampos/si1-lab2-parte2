package models;

import java.util.GregorianCalendar;

public class Calendario {
	
	public static GregorianCalendar getCalendarioDaSemana(int semana) {
		if(semana < 0) return null;
    	GregorianCalendar temp = new GregorianCalendar();

    	while(temp.get(temp.DAY_OF_WEEK) != 1) {
			temp.add(GregorianCalendar.DATE, 1);
    	}
    	temp.add(GregorianCalendar.DATE, semana*7);
        return temp;
	}
	
	public static String getSemana(int semana) {
		return getSemana(getCalendarioDaSemana(semana)) ;
	}
	
	public static String getSemana(Semana semana) {
		return getSemana(semana.getCalendario()) ;
	}
	
	public static String getSemana(GregorianCalendar calendario) {
		String temp = new String();
		temp = calendario.get(GregorianCalendar.DAY_OF_MONTH) + "/" +
				(calendario.get(GregorianCalendar.MONTH)+1) + "/" +
				calendario.get(GregorianCalendar.YEAR) + " - ";
		calendario.add(GregorianCalendar.DATE, 6);
		temp += calendario.get(GregorianCalendar.DAY_OF_MONTH) + "/" +
				(calendario.get(GregorianCalendar.MONTH)+1) + "/" +
				calendario.get(GregorianCalendar.YEAR);
		calendario.add(GregorianCalendar.DATE, -6);
		return temp;
	}

	public static String[] getSemanas(int quantidadeSemanas) {
		if(quantidadeSemanas < 1) return null;
    	String semanas[] = new String[quantidadeSemanas];
        for (int i = 0; i < quantidadeSemanas; i++) {
			semanas[i] = getSemana(i);
		}
        return semanas;
	}
}
