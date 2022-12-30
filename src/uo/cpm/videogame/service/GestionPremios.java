package uo.cpm.videogame.service;

import uo.cpm.videogame.model.Carrito;
import uo.cpm.videogame.model.Catalogo;
import uo.cpm.videogame.model.Premio;

public class GestionPremios 
{
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
	
	public void añadirPremioAlCarrito(Premio p)
	{
		carrito.añadirAlCarrito(p);
	}
	
	public void eliminarDelCarrito(Premio p)
	{
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
}
