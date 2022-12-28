package uo.cpm.videogame.model;

public class Dado 
{
	public static int lanzar()
	{ 
		return ((int) (Math.random() * Reglas.NUMERO_INVASORES.getValor()));
	}
	
	public static int posicionTablero()
	{		
		return ((int) (Math.random() * Tablero.FILAS * Tablero.COLUMNAS));
	}	
}
