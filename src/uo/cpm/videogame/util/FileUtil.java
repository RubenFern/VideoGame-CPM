package uo.cpm.videogame.util;

import java.io.*;
import java.util.List;

import uo.cpm.videogame.model.Categoria;
import uo.cpm.videogame.model.Entrega;
import uo.cpm.videogame.model.Premio;
import uo.cpm.videogame.model.Ticket;
import uo.cpm.videogame.model.Tienda;

public abstract class FileUtil 
{

	public static void cargarDatosTienda(String nombreFichero, Tienda tienda) 
	{
		String linea;
		String[] datos = null;

		try {
			BufferedReader fichero = new BufferedReader(new FileReader(nombreFichero));
			
			if (fichero.ready()) 
			{
				linea = fichero.readLine();
				datos = linea.split("@");
				
				tienda.setCodigo( datos[0] );
				tienda.setNombre( datos[1] );
			}
			
			fichero.close();
		} 
		catch (FileNotFoundException fnfe) 
		{ 
			System.out.println("El archivo no se ha encontrado."); 
		} 
		catch (IOException ioe) 
		{ 
			new RuntimeException("Error de entrada/salida."); 
		}
	}
	
	public static void cargarDatosTickets(String nombreFichero, List<Ticket> tickets)
	{
		String linea;
		String[] datos = null;

		try {
			BufferedReader fichero = new BufferedReader(new FileReader(nombreFichero));
			
			while (fichero.ready()) 
			{
				linea = fichero.readLine();
				datos = linea.split("@");
				
				tickets.add( new Ticket(datos[0], Integer.parseInt( datos[1] ), Double.parseDouble( datos[2] )) );
			}
			
			fichero.close();
		} 
		catch (FileNotFoundException fnfe) 
		{ 
			System.out.println("El archivo no se ha encontrado."); 
		} 
		catch (IOException ioe) 
		{ 
			new RuntimeException("Error de entrada/salida."); 
		}
	}
	
	public static void cargarPremios(String nombreFichero, List<Premio> premios)
	{
		String linea;
		String[] datos = null;

		try {
			BufferedReader fichero = new BufferedReader(new FileReader(nombreFichero));
			
			while (fichero.ready()) 
			{
				linea = fichero.readLine();
				datos = linea.split("@");
				
				premios.add( new Premio( datos[0], datos[1], datos[2], getCategoria(datos[3]), Integer.parseInt(datos[4]) ));
			}
			
			fichero.close();
		} 
		catch (FileNotFoundException fnfe) 
		{ 
			System.out.println("El archivo no se ha encontrado."); 
		} 
		catch (IOException ioe) 
		{ 
			new RuntimeException("Error de entrada/salida."); 
		}
	}
	
	private static Categoria getCategoria(String c)
	{
		if ( c.equals("Accesorios") )
			return Categoria.ACCESORIOS;
		
		if ( c.equals("Consolas") )
			return Categoria.CONSOLAS;
		
		if ( c.equals("Videojuegos") )
			return Categoria.VIDEOJUEGOS;
			
		return null;
	}
	
	public static void anadirEntrega(String fichero, Entrega entrega)
	{
		try {
			BufferedWriter f = new BufferedWriter(new FileWriter(fichero, true) );
			
			f.write( entrega.toString() + "\n" );
			f.close();
		}

		catch (FileNotFoundException fnfe) {
			System.out.println("El archivo no se ha podido guardar");
		} catch (IOException ioe) {
			new RuntimeException("Error de entrada/salida");
		}
	}	
	
}
