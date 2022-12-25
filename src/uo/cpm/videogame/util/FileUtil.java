package uo.cpm.videogame.util;

import java.io.*;
import java.util.List;

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
}
