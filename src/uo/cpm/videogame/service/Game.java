package uo.cpm.videogame.service;

import java.util.ArrayList;
import java.util.List;

import uo.cpm.videogame.Main;
import uo.cpm.videogame.model.Dado;
import uo.cpm.videogame.model.Invasor;
import uo.cpm.videogame.model.Ticket;
import uo.cpm.videogame.model.Tienda;
import uo.cpm.videogame.util.FileUtil;

public class Game 
{
	public static final int NUMERO_INVASORES = 8;
	public static final int POSICION_LIDER = 0;
	public static final int INVASORES_POR_RONDA = 5;
	public static final int RONDAS = 10;
	
	private Invasor[] invasores;
	private Tienda tienda;
	private List<Ticket> listaTickets;
	
	public Game()
	{	
		// Genero el total de invasores del juego
		generarInvasores();
		
		tienda = new Tienda();
		listaTickets = new ArrayList<Ticket>();
		
		FileUtil.cargarDatosTienda("files/config.dat", tienda);
		FileUtil.cargarDatosTickets("files/tickets.dat", listaTickets);
	}
	
	public void inicializar()
	{
		// Cargo de nuevo los tickets disponibles
		listaTickets.clear();
		FileUtil.cargarDatosTickets("files/tickets.dat", listaTickets);
	}
	
	public String getCodigoTienda()
	{
		return tienda.getCodigo();
	}
	
	public String getNombreTienda()
	{
		return tienda.getNombre();
	}
	
	public String getIconoTienda()
	{
		return tienda.getIcono();
	}
	
	/**
	 * Crea un array con el número total de invasores que participan en el juego
	 */
	public void generarInvasores()
	{
		invasores = new Invasor[NUMERO_INVASORES];
		
		// Cargo los invasores
		for ( int i = 0; i < invasores.length; i++ )
			invasores[i] = new Invasor( (i + 1), false );
		
		// Asigno el líder de la invasión
		invasores[POSICION_LIDER].setLider(true);
	}
	
	/**
	 * Genera al azar 5 invasores eligiendolos del array de invasores
	 * 
	 * @return Los invasores de cada ronda
	 */
	public Invasor[] getRondaInvasores()
	{
		Invasor[] rondaInvasores = new Invasor[INVASORES_POR_RONDA];
		
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
	
	public boolean ticketValido(String codigo, int numero)
	{
		return false;
	}
	
	
	
	
}
