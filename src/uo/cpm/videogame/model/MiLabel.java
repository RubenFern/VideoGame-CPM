package uo.cpm.videogame.model;

import javax.swing.JLabel;


public class MiLabel extends JLabel
{
	private static final long serialVersionUID = 1L;
	
	private int id;
	private int numeroInvasor;
	private boolean usada;
	
	public MiLabel(String label)
	{
		super(label);
		this.numeroInvasor = -1;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getNumeroInvasor() {
		return numeroInvasor;
	}

	public void setNumeroInvasor(int numeroInvasor) {
		this.numeroInvasor = numeroInvasor;
	}

	public boolean isUsada() {
		return usada;
	}

	public void setUsada(boolean usada) {
		this.usada = usada;
	}

	@Override
	public String toString() {
		return "MiLabel [id=" + id + ", numeroInvasor=" + numeroInvasor + ", usada=" + usada + "]";
	}
	
}
