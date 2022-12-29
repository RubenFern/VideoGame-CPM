package uo.cpm.videogame.model;

import uo.cpm.videogame.Main;

public class Tablero 
{
	public static final int FILAS = 7;
	public static final int COLUMNAS = 7;
	
	private boolean[] posicionesValidas;
	private Casilla[] tablero;
	private Invasor[] invasores;
	private int numeroInvasoresEnTablero;
	
	public Tablero()
	{
		posicionesValidas = new boolean[ this.getDimensionTablero() ]; 
		tablero = new Casilla[ this.getDimensionTablero() ];
		
		// Genero el tablero que contiene las posiciones válidas
		generarPosicionesValidas();
		
		// Genero el array con todos los invasores disponibles
		generarInvasores();
		
		// Genero el tablero con los invasores iniciales
		generarTablero();
		
		//imprimirTablero();
	}
	
	public Casilla[] getTablero() {
		return tablero;
	}
	
	public Invasor[] getInvasores()
	{
		return invasores;
	}
	
	public int getNumeroInvasoresEnTablero() {
		return numeroInvasoresEnTablero;
	}

	public void setNumeroInvasoresEnTablero(int numeroInvasoresEnTablero) {
		this.numeroInvasoresEnTablero = numeroInvasoresEnTablero;
	}

	public void añadirInvasorAlTablero(Casilla casilla)
	{
		int numeroInvasor = casilla.getInvasor().getNumero();
		int posicionTablero = casilla.getPosicionTablero();
		
		// Añado el invasor en el tablero y asigno su posición como no válida
		if ( posicionesValidas[posicionTablero] )
		{
			tablero[posicionTablero].setInvasor( invasores[numeroInvasor - 1] );
			posicionesValidas[posicionTablero] = false;
			
			this.setNumeroInvasoresEnTablero( this.getNumeroInvasoresEnTablero() + 1 );
		}
		//imprimirTablero();
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
		
		// Genero el tablero
		for ( int i = 0; i < this.getDimensionTablero(); i++ )
			tablero[i] = new Casilla();
		
		// Genero los 5 invasores inicialies
		for ( int i = 0; i < ronda.length; i++ )
			this.colocarInvasor(ronda[i]);
		
		// Sumo a 5 los invasores del tablero
		this.setNumeroInvasoresEnTablero( ronda.length );
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
		while ( !EsPosicionValida(posicion) || tablero[posicion].getInvasor() != null )
			posicion = Dado.posicionTablero();
		
		tablero[posicion].setInvasor(invasor);
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
	
	public void imprimirTablero()
	{
		System.out.println("Número de invasores: " + this.getNumeroInvasoresEnTablero());
		
		for ( int i = 0; i < tablero.length; i++ )
		{
			if ( i % 7 == 0 && i != 0 )
				System.out.println();
			
			System.out.print( tablero[i].getInvasor() + " " );
		}
		System.out.println();
		System.out.println();
	}
	
	public void imprPosicionesValidas()
	{
		for ( int i = 0; i < tablero.length; i++ )
		{
			if ( i % 7 == 0 && i != 0 )
				System.out.println();
			
			System.out.print( posicionesValidas[i] + " " );
		}
		System.out.println();
		System.out.println();
	}
	
	public int eliminarColonias()
	{
		int puntosTotales = 0;
		
		/**
		 * Recorro el tablero, cuando encuentro un invasor comprueba si tiene un invasor de su mismo tipo alrdededor
		 * Si lo tiene llamo a calcular puntos
		 */
		for ( int i = 0; i < this.getDimensionTablero(); i++ )
			// Si hay un invasor y la casilla no se marcó para borrar compruebo si tiene colonias
			if ( tablero[i].getInvasor() != null && !tablero[i].isBorrar() )
					puntosTotales += comprobarColonia( tablero[i].getInvasor(), i );

		// Borro las colonias marcadas
		if ( puntosTotales > 0 )
			actualizarTablero();
		
		return puntosTotales;
	}
	
	/**
	 * Borra los invasores marcados para borrar y reinicia su estado
	 */
	private void actualizarTablero()
	{
		for ( int i = 0; i < this.getDimensionTablero(); i++ )
			if ( tablero[i].isBorrar() )
			{
				posicionesValidas[i] = true;
				tablero[i].setInvasor(null);
				tablero[i].setBorrar(false);
				this.setNumeroInvasoresEnTablero( this.getNumeroInvasoresEnTablero() - 1 );
			}
		
		// Resto uno más para compensar con el que se añade en el drag and drop
		this.setNumeroInvasoresEnTablero( this.getNumeroInvasoresEnTablero() - 1 );
	}
	
	/**
	 * Cuando se encuentra en el tablero un invasor que tiene un invasor adyacente del mismo tipo se llama a esta función
	 * que calcula el tamaño de la colonia y suma los puntos
	 * 
	 * @return Los puntos de una colonia de invasores
	 */
	private int comprobarColonia(Invasor invasor, int posicionTablero)
	{
		int derecha = posicionTablero + 1;
		int abajo = posicionTablero + COLUMNAS;
		
		int puntos = 0;
		
		// Si tiene un invasor igual a su derecha
		if ( derecha < this.getDimensionTablero() && tablero[derecha].getInvasor() != null && tablero[derecha].getInvasor().equals(invasor) )
		{
			int tamañoColonia = this.calcularTamañoColoniaFila(invasor, derecha);
			
			if ( tamañoColonia >= 3 )
			{
				marcarCasillasBorrado( posicionTablero, tamañoColonia, 1 );
				
				puntos += getPuntuacion(tamañoColonia);
			}
		}
		
		if ( abajo < this.getDimensionTablero() && tablero[abajo].getInvasor() != null && tablero[abajo].getInvasor().equals(invasor) )
		{
			int tamañoColonia = this.calcularTamañoColoniaColumna(invasor, abajo);
			
			if ( tamañoColonia >= 3 )
			{
				marcarCasillasBorrado( posicionTablero, tamañoColonia, COLUMNAS );
				
				puntos += getPuntuacion(tamañoColonia);
			}
		}
		
		/*for ( int i = 0; i < tablero.length; i++ )
		{
			if ( i % 7 == 0 && i != 0 )
				System.out.println();
			
			System.out.print( tablero[i].isBorrar() + " " );
		}
		System.out.println();
		System.out.println();*/
		
		return puntos;
	}
	
	private void marcarCasillasBorrado(int posicionTablero, int tamañoColonia, int suma)
	{
		for ( int i = 0; i < tamañoColonia; i++ )
		{
			//System.out.println("-------------------" + (posicionTablero + (suma * i)));

			tablero[(posicionTablero + (suma * i))].setBorrar(true);
		}
	}
	
	private int getPuntuacion(int tamañoColonia)
	{
		if ( tamañoColonia == Puntuacion.COLONIA_3.getTamaño() )
			return Puntuacion.COLONIA_3.getPuntos();
		
		if ( tamañoColonia == Puntuacion.COLONIA_4.getTamaño() )
			return Puntuacion.COLONIA_4.getPuntos();
		
		if ( tamañoColonia == Puntuacion.COLONIA_5.getTamaño() )
			return Puntuacion.COLONIA_5.getPuntos();
		
		if ( tamañoColonia == Puntuacion.COLONIA_6.getTamaño() )
			return Puntuacion.COLONIA_6.getPuntos();
		
		if ( tamañoColonia == Puntuacion.COLONIA_7.getTamaño() )
			return Puntuacion.COLONIA_7.getPuntos();
		
		return 0;
	}
	
	/**
	 * Calcula el número de invasores iguales en la misma fila
	 * 
	 * @param invasor
	 * @return
	 */
	private int calcularTamañoColoniaFila(Invasor invasor, int posicionTablero)
	{
		// Cuando entra en el método siempre hay 2 invasores iguales
		int tamañoColonia = 2;
		int i = 1;
		
		// Mientras coincida el invasor y siga en la misma fila
		while ( tablero[posicionTablero + i].getInvasor() != null && tablero[posicionTablero + i].getInvasor().equals(invasor) && (posicionTablero + i) % 7 != 0 )
		{
			tamañoColonia++;
			i++;
		}
			
		return tamañoColonia;
	}
	
	/**
	 * Calcula el número de invasores iguales en la misma fila
	 * 
	 * @param invasor
	 * @return
	 */
	private int calcularTamañoColoniaColumna(Invasor invasor, int posicionTablero)
	{
		// Cuando entra en el método siempre hay 2 invasores iguales
		int tamañoColonia = 2;
		int i = COLUMNAS;
		
		// Mientras coincida el invasor y siga en la misma fila
		while ((posicionTablero + i) < this.getDimensionTablero() && tablero[posicionTablero + i].getInvasor() != null
				&& tablero[posicionTablero + i].getInvasor().equals(invasor)) 
		{
			tamañoColonia++;
			i += COLUMNAS;
		}
			
		return tamañoColonia;
	}
}
