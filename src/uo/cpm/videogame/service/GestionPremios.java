package uo.cpm.videogame.service;

import java.util.ArrayList;
import java.util.List;

import uo.cpm.videogame.model.Carrito;
import uo.cpm.videogame.model.Catalogo;
import uo.cpm.videogame.model.Entrega;
import uo.cpm.videogame.model.Premio;
import uo.cpm.videogame.util.FileUtil;

public class GestionPremios 
{
	public static final String FICHERO_ENTREGAS = "files/entregas.dat";
	
	private Catalogo catalogo;
	private Carrito carrito;
	
	public GestionPremios()
	{
		catalogo = new Catalogo();
		carrito = new Carrito();
	}
	
	public Premio[] getListaPremios(boolean soloDisponibles, int puntos)
	{
		if ( soloDisponibles )
			return getSoloPremiosDisponibles(catalogo.getListaPremios(), puntos);
			
		return catalogo.getListaPremios();
	}
	
	public Premio[] getAccesorios(boolean soloDisponibles, int puntos)
	{
		if ( soloDisponibles )
			return getSoloPremiosDisponibles(catalogo.getAccesorios(), puntos);
		
		return catalogo.getAccesorios();
	}
	
	public Premio[] getConsolas(boolean soloDisponibles, int puntos)
	{
		if ( soloDisponibles )
			return getSoloPremiosDisponibles(catalogo.getConsolas(), puntos);
			
		return catalogo.getConsolas();
	}
	
	public Premio[] getVideojuegos(boolean soloDisponibles, int puntos)
	{
		if ( soloDisponibles )
			return getSoloPremiosDisponibles(catalogo.getVideojuegos(), puntos);
		
		return catalogo.getVideojuegos();
	}
	
	public Premio[] getSoloPremiosDisponibles(Premio[] listaPremios, int puntos)
	{
		List<Premio> disponibles = new ArrayList<Premio>();
		
		for ( Premio p : listaPremios )
			if ( p.getCostePuntos() <= puntos ) // Si el premio cuesta menos o los mismos puntos
				disponibles.add(p);
		
		return disponibles.toArray( new Premio[disponibles.size()] );
	}
	
	public int getNumeroPremios() 
	{
		return carrito.getNumeroPremios();
	}
	
	public void anadirPremioAlCarrito(Game game, Premio p)
	{
		game.setPuntos( game.getPuntos() - p.getCostePuntos() );
		carrito.anadirAlCarrito(p);
	}
	
	public void eliminarDelCarrito(Game game, Premio p)
	{
		game.setPuntos( game.getPuntos() + p.getCostePuntos() );
		carrito.eliminarDelCarrito(p);
	}
	
	public Premio[] getCarrito()
	{
		return carrito.getCarrito();
	}
	
	/**
	 * Actualiza el carrito en caso de que se haya cambiado el idioma de la apliación
	 */
	public void actualizarCarrito(boolean soloDisponibles, int puntos)
	{
		carrito.actualizarCarrito( this.getListaPremios(soloDisponibles, puntos) );
	}
	
	public Premio[] getAccesoriosCarrito()
	{
		return carrito.getAccesorios();
	}
	
	public Premio[] getConsolasCarrito()
	{
		return carrito.getConsolas();
	}
	
	public Premio[] getVideojuegosCarrito()
	{
		return carrito.getVideojuegos();
	}
	
	public void entrega(String dni, String codigoTienda)
	{
		Premio[] listaPremios = this.getCarrito();
		
		String[] codigosPremios = new String[listaPremios.length];
		
		for ( int i = 0; i < listaPremios.length; i++ )
			codigosPremios[i] = listaPremios[i].getCodigo();
		
		FileUtil.anadirEntrega(FICHERO_ENTREGAS, new Entrega(dni, codigoTienda, codigosPremios));
	}
	
	public void cargarArticulos(Internacionalizar i)
	{
		catalogo.cargarArticulos(i);
	}
}
