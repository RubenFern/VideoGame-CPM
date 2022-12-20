package uo.cpm.videogame.util;

import java.io.*;

import uo.cpm.videogame.model.Tienda;

public abstract class FileUtil 
{

	public static void loadFile(String nombreFichero, Tienda tienda) 
	{
		String linea;
		String[] datos = null;

		try {
			BufferedReader fichero = new BufferedReader(new FileReader(nombreFichero));
			
			while (fichero.ready()) 
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

	/*public static void saveToFile(String nombreFicheroSalida, List<Articulo> listaPedido, boolean local) {
		try {
			BufferedWriter fichero = new BufferedWriter(new FileWriter("files/" + nombreFicheroSalida + ".dat"));
			fichero.write( ( local ) ? "[Pedido para local]" : "[Pedido para llevar]" );
			String linea = listaPedido.toString();
			fichero.write(linea);
			fichero.close();
		}

		catch (FileNotFoundException fnfe) {
			System.out.println("El archivo no se ha podido guardar");
		} catch (IOException ioe) {
			new RuntimeException("Error de entrada/salida");
		}
	}*/
}
