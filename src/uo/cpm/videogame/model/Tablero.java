package uo.cpm.videogame.model;

import uo.cpm.videogame.Main;

public class Tablero 
{
	public static final int FILAS = 7;
	public static final int COLUMNAS = 7;
	
	private boolean[] posicionesValidas;
	private Invasor[] tablero;
	private Invasor[] invasores;
	
	public Tablero()
	{
		posicionesValidas = new boolean[ this.getDimensionTablero() ]; 
		tablero = new Invasor[ this.getDimensionTablero() ];
		
		// Genero el tablero que contiene las posiciones válidas
		generarPosicionesValidas();
		
		// Genero el array con todos los invasores disponibles
		generarInvasores();
		
		// Genero el tablero con los invasores iniciales
		generarTablero();
		
		imprimirTablero();
	}
	
	public Invasor[] getTablero() {
		return tablero;
	}
	
	public Invasor[] getInvasores()
	{
		return invasores;
	}
	
	public void añadirInvasorAlTablero(Casilla casilla)
	{
		int numeroInvasor = casilla.getNumeroInvasor();
		int posicionTablero = casilla.getPosicionTablero();
		
		tablero[posicionTablero] = invasores[numeroInvasor - 1];
		
		imprimirTablero();
	}
	
	public int getDimensionTablero()
	{
		return FILAS * COLUMNAS;
	}

	private void generarPosicionesValidas()
	{
		for ( int i = 0; i < posicionesValidas.length; i++ )
			posicionesValidas[i] = true;
		
		asignarPosicionesInvalidas();
	}
	
	/**
	 * Establece las posiciones inválidas del tablero por defecto
	 * 
	 * Por defecto: (0,0) = [0], (6,0) = [6], (3,3) = [24], (0,6) = [42], (6,6) = [48]
	 */
	private void asignarPosicionesInvalidas()
	{
		posicionesValidas[0] = false;
		posicionesValidas[6] = false;
		posicionesValidas[24] = false;
		posicionesValidas[42] = false;
		posicionesValidas[48] = false;
	}
	
	/**
	 * Devuelve el valor de la matriz de posición invalidas
	 * 
	 * @param fila
	 * @param columna
	 * @return
	 */
	public boolean EsPosicionValida(int posicion)
	{
		return posicionesValidas[posicion];
	}
	
	public void setPosicionValida(int posicion)
	{
		// Posiciones fuera del rango
		if ( posicion < 0 || posicion >= this.getDimensionTablero() )
			return;
		
		posicionesValidas[posicion] = !posicionesValidas[posicion];
	}
	
	/**
	 * Crea un array con el número total de invasores que participan en el juego
	 */
	private void generarInvasores()
	{
		invasores = new Invasor[Reglas.NUMERO_INVASORES.getValor()];
		
		// Cargo los invasores
		for ( int i = 0; i < invasores.length; i++ )
			invasores[i] = new Invasor( (i + 1), false );
		
		// Asigno el líder de la invasión
		invasores[Reglas.POSICION_LIDER.getValor()].setLider(true);
	}
	
	/**
	 * Genera el tablero con los 5 invasores iniciales
	 */
	private void generarTablero()
	{
		// Genero los 5 invasores iniciales que se colocan en el tablero
		Invasor[] ronda = this.getRondaInvasores();
		
		for ( int i = 0; i < ronda.length; i++ )
			this.colocarInvasor(ronda[i]);
	}
	
	/**
	 * Coloca un invasor en una posición válida del tablero
	 * 
	 * @param invasor Invasor que se añade
	 */
	private void colocarInvasor(Invasor invasor)
	{
		int posicion = Dado.posicionTablero();
		
		// Si es una posición inválida o ya tiene un invasor vuelvo a generar posición
		while ( !EsPosicionValida(posicion) || tablero[posicion] != null )
			posicion = Dado.posicionTablero();
		
		tablero[posicion] = invasor;
	}
	
	/**
	 * Genera al azar 5 invasores eligiendolos del array de invasores
	 * 
	 * @return Los invasores de cada ronda
	 */
	public Invasor[] getRondaInvasores()
	{
		Invasor[] rondaInvasores = new Invasor[Reglas.INVASORES_POR_RONDA.getValor()];
		
		switch ( Main.DEBUG ) 
		{
			case 0: 
			{
				for ( int i = 0; i < rondaInvasores.length; i++ )
					rondaInvasores[i] = invasores[Dado.lanzar()];
			
				break;
			}
			case 1: 
			{
				for ( int i = 0; i < rondaInvasores.length; i++ )
				{
					rondaInvasores[i] = invasores[0];
					rondaInvasores[i].setLider(true);
				}
				break;
			}
			case 2:
			{
				for ( int i = 0; i < rondaInvasores.length; i++ )
				{
					rondaInvasores[i] = invasores[0];
					rondaInvasores[i].setLider(false);
				}
				break;
			}
		}	
		
		return rondaInvasores;
	}
	
	private void imprimirTablero()
	{
		for ( int i = 0; i < tablero.length; i++ )
		{
			if ( i % 7 == 0 && i != 0 )
				System.out.println();
			
			System.out.print( tablero[i] + " " );
		}
		System.out.println();
		System.out.println();
	}
}
