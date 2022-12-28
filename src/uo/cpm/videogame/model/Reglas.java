package uo.cpm.videogame.model;

public enum Reglas 
{
	RONDAS(10),
	NUMERO_INVASORES(8),
	INVASORES_POR_RONDA(5),
	POSICION_LIDER(0);
	
	private final int valor;
	
	Reglas(int valor)
	{
		this.valor = valor;
	}
	
	public int getValor()
	{
		return valor;
	}
}
