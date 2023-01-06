package uo.cpm.videogame.model;

import uo.cpm.videogame.Main;

public class Tablero
{
	public static final int FILAS = 7;
	public static final int COLUMNAS = 7;
	
	private boolean[] posicionesValidas;
	private Casilla[] tablero;
	private Invasor[] invasores;
	private Partida partida;
	
	public Tablero()
	{			
		posicionesValidas = new boolean[ this.getDimensionTablero() ]; 
		tablero = new Casilla[ this.getDimensionTablero() ];
		partida = new Partida();
		
		// Genero el tablero que contiene las posiciones v�lidas
		generarPosicionesValidas();
		
		// Genero el array con todos los invasores disponibles
		generarInvasores();
		
		// Genero el tablero con los invasores iniciales
		generarTablero();		
	}
	
	public Casilla[] getTablero() {
		return tablero;
	}
	
	public Invasor[] getInvasores()
	{
		return invasores;
	}
	
	public Partida getPartida()
	{
		return partida;
	}
	
	public void setPartida(Partida partida) {
		this.partida = partida;
	}

	public int getNumeroInvasoresEnTablero() 
	{
		int num = 0;
		
		for ( int i = 0; i < this.getDimensionTablero(); i++ )
			if ( tablero[i].getInvasor() != null )
				num++;
		
		return num;
	}

	public void anadirInvasorAlTablero(Casilla casilla)
	{
		int numeroInvasor = casilla.getInvasor().getNumero();
		int posicionTablero = casilla.getPosicionTablero();
		
		// A�ado el invasor en el tablero y asigno su posici�n como no v�lida
		if ( posicionesValidas[posicionTablero] )
		{
			tablero[posicionTablero].setInvasor( invasores[numeroInvasor - 1] );
			posicionesValidas[posicionTablero] = false;
		}
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
	 * Establece las posiciones inv�lidas del tablero por defecto
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
	 * Devuelve el valor de la matriz de posici�n invalidas
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
	 * Crea un array con el n�mero total de invasores que participan en el juego
	 */
	private void generarInvasores()
	{
		invasores = new Invasor[Reglas.NUMERO_INVASORES.getValor()];
		
		// Cargo los invasores
		for ( int i = 0; i < invasores.length; i++ )
			invasores[i] = new Invasor( (i + 1), false );
		
		// Asigno el l�der de la invasi�n
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
	}
	
	/**
	 * Coloca un invasor en una posici�n v�lida del tablero
	 * 
	 * @param invasor Invasor que se a�ade
	 */
	private void colocarInvasor(Invasor invasor)
	{
		int posicion = Dado.posicionTablero();
		
		// Si es una posici�n inv�lida o ya tiene un invasor vuelvo a generar posici�n
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
		System.out.println("N�mero de invasores: " + this.getNumeroInvasoresEnTablero());
		
		for ( int i = 0; i < tablero.length; i++ )
		{
			if ( i % 7 == 0 && i != 0 )
				System.out.println();
			
			System.out.print( tablero[i].getInvasor() + " " );
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
			// Si hay un invasor y la casilla no se marc� para borrar compruebo si tiene colonias
			if ( tablero[i].getInvasor() != null && !tablero[i].isBorrar() )
					puntosTotales += comprobarColonia( tablero[i].getInvasor(), i );

		// Borro las colonias marcadas
		if ( puntosTotales > 0 )
			actualizarTablero();
		
		partida.setPuntos( partida.getPuntos() + puntosTotales );
		
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
			}
	}
	
	/**
	 * Cuando se encuentra en el tablero un invasor que tiene un invasor adyacente del mismo tipo se llama a esta funci�n
	 * que calcula el tama�o de la colonia y suma los puntos
	 * 
	 * @return Los puntos de una colonia de invasores
	 */
	private int comprobarColonia(Invasor invasor, int posicionTablero)
	{
		int derecha = posicionTablero + 1;
		int abajo = posicionTablero + COLUMNAS;
		
		int puntos = 0, tamanoColonia = 0;
		
		// Si tiene un invasor igual a su derecha
		if ( derecha < this.getDimensionTablero() && tablero[derecha].getInvasor() != null && tablero[derecha].getInvasor().getNumero() == invasor.getNumero() )
		{
			tamanoColonia = this.calcularTamanoColoniaFila(invasor, derecha);
			
			if ( tamanoColonia >= 3 )
			{
				marcarCasillasBorrado( posicionTablero, tamanoColonia, 1 );
				
				puntos += getPuntuacion(tamanoColonia);
			}
		}
		
		if ( abajo < this.getDimensionTablero() && tablero[abajo].getInvasor() != null && tablero[abajo].getInvasor().getNumero() == invasor.getNumero() )
		{
			tamanoColonia = this.calcularTamanoColoniaColumna(invasor, abajo);
			
			if ( tamanoColonia >= 3 )
			{
				marcarCasillasBorrado( posicionTablero, tamanoColonia, COLUMNAS );
				
				puntos += getPuntuacion(tamanoColonia);
			}
		}
		
		// Se elimina una colonia de 5 o más líderes, partida ganada
		if ( tamanoColonia >= 5 && invasor.isLider() )
		{
			puntos += Puntuacion.COLONIA_LIDERES.getPuntos();
			partida.setFinalizada(true);
		}			
		
		return puntos;
	}
	
	private void marcarCasillasBorrado(int posicionTablero, int tamanoColonia, int suma)
	{
		for ( int i = 0; i < tamanoColonia; i++ )
			tablero[(posicionTablero + (suma * i))].setBorrar(true);
	}
	
	private int getPuntuacion(int tamanoColonia)
	{
		if ( tamanoColonia == Puntuacion.COLONIA_3.getTamano() )
			return Puntuacion.COLONIA_3.getPuntos();
		
		if ( tamanoColonia == Puntuacion.COLONIA_4.getTamano() )
			return Puntuacion.COLONIA_4.getPuntos();
		
		if ( tamanoColonia == Puntuacion.COLONIA_5.getTamano() )
			return Puntuacion.COLONIA_5.getPuntos();
		
		if ( tamanoColonia == Puntuacion.COLONIA_6.getTamano() )
			return Puntuacion.COLONIA_6.getPuntos();
		
		if ( tamanoColonia == Puntuacion.COLONIA_7.getTamano() )
			return Puntuacion.COLONIA_7.getPuntos();
		
		return 0;
	}
	
	/**
	 * Calcula el n�mero de invasores iguales en la misma fila
	 * 
	 * @param invasor
	 * @return
	 */
	private int calcularTamanoColoniaFila(Invasor invasor, int posicionTablero)
	{
		// Cuando entra en el m�todo siempre hay 2 invasores iguales
		int tamanoColonia = 2;
		int i = 1;
		
		// Mientras coincida el invasor y siga en la misma fila
		while ((posicionTablero + i) < this.getDimensionTablero() && tablero[posicionTablero + i].getInvasor() != null
				&& tablero[posicionTablero + i].getInvasor().getNumero() == invasor.getNumero() && (posicionTablero + i) % 7 != 0) 
		{
			tamanoColonia++;
			i++;
		}
			
		return tamanoColonia;
	}
	
	/**
	 * Calcula el n�mero de invasores iguales en la misma fila
	 * 
	 * @param invasor
	 * @return
	 */
	private int calcularTamanoColoniaColumna(Invasor invasor, int posicionTablero)
	{
		// Cuando entra en el m�todo siempre hay 2 invasores iguales
		int tamanoColonia = 2;
		int i = COLUMNAS;
		
		// Mientras coincida el invasor y siga en la misma fila
		while ((posicionTablero + i) < this.getDimensionTablero() && tablero[posicionTablero + i].getInvasor() != null
				&& tablero[posicionTablero + i].getInvasor().getNumero() == invasor.getNumero()) 
		{
			tamanoColonia++;
			i += COLUMNAS;
		}
			
		return tamanoColonia;
	}
	
	public void reiniciarTablero()
	{
		generarTablero();
	}
	
	
	
	
	
	
	
}
