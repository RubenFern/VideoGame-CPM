package uo.cpm.videogame.model;

public class Partida 
{
	private int puntos;
	private boolean finalizada;
	private int ronda;
	private int movimientos;
	
	public Partida(int puntos, boolean finalizada, int ronda, int movimientos) 
	{
		this.puntos = puntos;
		this.finalizada = finalizada;
		this.ronda = ronda;
		this.movimientos = movimientos;
	}

	public Partida()
	{
		this(0, false, 1, Reglas.INVASORES_POR_RONDA.getValor());
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

	public int getMovimientos() {
		return movimientos;
	}

	public void setMovimientos(int movimientos) {
		this.movimientos = movimientos;
	}
	
}
