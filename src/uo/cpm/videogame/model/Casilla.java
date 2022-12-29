package uo.cpm.videogame.model;

public class Casilla 
{
	private Invasor invasor;
	private int posicionTablero;
	private boolean borrar;
	
	public Casilla(Invasor invasor, int posicionTablero) {
		this.invasor = invasor;
		this.posicionTablero = posicionTablero;
		this.borrar = false;
	}
	
	public Casilla()
	{
		this(null, 0);
	}

	public Invasor getInvasor() {
		return invasor;
	}

	public void setInvasor(Invasor invasor) {
		this.invasor = invasor;
	}

	public int getPosicionTablero() {
		return posicionTablero;
	}

	public void setPosicionTablero(int posicionTablero) {
		this.posicionTablero = posicionTablero;
	}

	public boolean isBorrar() {
		return borrar;
	}

	public void setBorrar(boolean borrar) {
		this.borrar = borrar;
	}
}
