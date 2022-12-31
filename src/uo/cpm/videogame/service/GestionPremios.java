package uo.cpm.videogame.service;

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
	
	public Premio[] getListaPremios()
	{
		return catalogo.getListaPremios();
	}
	
	public Premio[] getAccesorios()
	{
		return catalogo.getAccesorios();
	}
	
	public Premio[] getConsolas()
	{
		return catalogo.getConsolas();
	}
	
	public Premio[] getVideojuegos()
	{
		return catalogo.getVideojuegos();
	}
	
	public int getNumeroPremios() 
	{
		return carrito.getNumeroPremios();
	}
	
	public void añadirPremioAlCarrito(Game game, Premio p)
	{
		game.setPuntos( game.getPuntos() - p.getCostePuntos() );
		carrito.añadirAlCarrito(p);
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
		
		FileUtil.añadirEntrega(FICHERO_ENTREGAS, new Entrega(dni, codigoTienda, codigosPremios));
	}
	
}
