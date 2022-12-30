package uo.cpm.videogame.model;

public enum Puntuacion 
{
	COLONIA_3(3, 50),
	COLONIA_4(4, 200),
	COLONIA_5(5, 1000),
	COLONIA_6(6, 5000),
	COLONIA_7(7, 10000),
	COLONIA_LIDERES(5, 20000);
	
	private final int tamaño;
	private final int puntos;
	
	Puntuacion(int tamaño, int puntos) 
	{
		this.tamaño = tamaño;
		this.puntos = puntos;
	}

	public int getTamaño()
	{
		return tamaño;
	}
	
	public int getPuntos() {
		return puntos;
	}
}
