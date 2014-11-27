package models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


@Entity
public class Meta implements Comparable<Meta>{
	
	@Id
    @GeneratedValue
    private Long idMeta;
	
	@ManyToOne
	@JoinColumn(name="idSemana")
	private Semana semana;
	
	public enum Prioridade { Baixa, Normal, Alta };
	
	private String nome;
	private String descricao;
	private Prioridade prioridade;
	private boolean status;
	
	public Meta(String nome, String descricao, Prioridade prioridade) {
		this.nome = nome;
		this.descricao = descricao;
		this.prioridade = prioridade;
		this.status = false;
	}
	
	public Meta() {
	}

	public Semana getSemana() {
		return semana;
	}

	public void setSemana(Semana semana) {
		this.semana = semana;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Prioridade getPrioridade() {
		return prioridade;
	}

	public void setPrioridade(Prioridade prioridade) {
		this.prioridade = prioridade;
	}

	public boolean getStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public Long getId() {
		return idMeta;
	}

	@Override
	public int compareTo(Meta meta) {
		return meta.getPrioridade().ordinal() - this.prioridade.ordinal();
	}
	
}
