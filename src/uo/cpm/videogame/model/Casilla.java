package uo.cpm.videogame.model;


public class Casilla 
{
	private int numeroInvasor;
	private int posicionTablero;
	
	public Casilla(int numeroInvasor, int posicionTablero) 
	{
		this.numeroInvasor = numeroInvasor;
		this.posicionTablero = posicionTablero;
	}
	
	public Casilla()
	{
		this(0, 0);
	}

	public int getNumeroInvasor() {
		return numeroInvasor;
	}

	public void setNumeroInvasor(int numeroInvasor) {
		this.numeroInvasor = numeroInvasor;
	}

	public int getPosicionTablero() {
		return posicionTablero;
	}

	public void setPosicionTablero(int posicionTablero) {
		this.posicionTablero = posicionTablero;
	}
}
