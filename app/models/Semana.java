package models;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="semana")
public class Semana implements Comparable<Semana> {

	@Id
    @GeneratedValue
    private Long idSemana;
	
	private GregorianCalendar calendario;
	
	@OneToMany(mappedBy = "semana")
	private List<Meta> metas;
	
	public Semana(GregorianCalendar calendario) {
		this.calendario = calendario;
		this.metas = new ArrayList<Meta>();
	}
	
	public Semana() {
		this.metas = new ArrayList<Meta>();
		this.calendario = new GregorianCalendar();
	}

	public List<Meta> getMetas() {
		return metas;
	}
	
	public Meta getMeta(Long idMeta) {
		for (int i = 0; i < metas.size(); i++) {
			if(metas.get(i).getId().equals(idMeta)) return metas.get(i);
		}
		return null;
	}

	public void addMeta(Meta meta) {
		this.metas.add(meta);
	}
	
	public void removeMeta(Meta meta) {
		this.metas.remove(meta);
	}
	
	public int getTotalDeMetas() {
		return metas.size();
	}
	
	public int getTotalDeMetasAlcancadas() {
		int cont = 0;
		for (int i = 0; i < metas.size(); i++) {
			if(metas.get(i).getStatus()) cont++;
		}
		return cont;
	}
	
	public int getTotalDeMetasNaoAlcancadas() {
		return getTotalDeMetas() - getTotalDeMetasAlcancadas();
	}

	public GregorianCalendar getCalendario() {
		return calendario;
	}

	public void setCalendario(GregorianCalendar calendario) {
		this.calendario = calendario;
	}

	public Long getId() {
		return idSemana;
	}
	
	public String toString() {
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
	
	@Override
	public int compareTo(Semana semana) {
		if(this.calendario.get(GregorianCalendar.YEAR) < semana.getCalendario().get(GregorianCalendar.YEAR)) {
			return -1;
		}
		if(this.calendario.get(GregorianCalendar.YEAR) > semana.getCalendario().get(GregorianCalendar.YEAR)) {
			return 1;
		}
		if(this.calendario.get(GregorianCalendar.MONTH) < semana.getCalendario().get(GregorianCalendar.MONTH)) {
			return -1;
		}
		if(this.calendario.get(GregorianCalendar.MONTH) > semana.getCalendario().get(GregorianCalendar.MONTH)) {
			return 1;
		}
		if(this.calendario.get(GregorianCalendar.DAY_OF_MONTH) < semana.getCalendario().get(GregorianCalendar.DAY_OF_MONTH)) {
			return -1;
		}
		if(this.calendario.get(GregorianCalendar.DAY_OF_MONTH) > semana.getCalendario().get(GregorianCalendar.DAY_OF_MONTH)) {
			return 1;
		}
		return 0;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((calendario == null) ? 0 : calendario.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof Semana)) {
			return false;
		}
		Semana temp = (Semana) obj;
		return (this.calendario.get(GregorianCalendar.DAY_OF_MONTH) == temp.getCalendario().get(GregorianCalendar.DAY_OF_MONTH) &&
				this.calendario.get(GregorianCalendar.MONTH) == temp.getCalendario().get(GregorianCalendar.MONTH) &&
				this.calendario.get(GregorianCalendar.YEAR) == temp.getCalendario().get(GregorianCalendar.YEAR));
	}

}

