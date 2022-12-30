package uo.cpm.videogame.model;

public class Partida 
{
	private int puntos;
	private boolean finalizada;
	private int ronda;
	
	public Partida(int puntos, boolean finalizada, int ronda) 
	{
		this.puntos = puntos;
		this.finalizada = finalizada;
		this.ronda = ronda;
	}

	public Partida()
	{
		this(0, false, 1);
	}
	
	public int getPuntos() {
		return puntos;
	}

	public void setPuntos(int puntos) {
		this.puntos = puntos;
	}

	public boolean isFinalizada() {
		return finalizada;
	}

	public void setFinalizada(boolean finalizada) {
		this.finalizada = finalizada;
	}

	public int getRonda() {
		return ronda;
	}

	public void setRonda(int ronda) {
		this.ronda = ronda;
	}
	
}
