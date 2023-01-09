package uo.cpm.videogame.model;

import java.util.ArrayList;
import java.util.List;

public class Carrito 
{
	private List<Premio> carrito;
	
	public Carrito()
	{
		carrito = new ArrayList<Premio>();
		inicializar();
	}
	
	public void inicializar()
	{
		carrito.clear();
	}
	
	public Premio[] getCarrito() {
		return carrito.toArray( new Premio[carrito.size()] );
	}
	
	/**
	 * Actualiza el carrito en caso de que se haya cambiado el idioma de la apliación
	 */
	public void actualizarCarrito(Premio[] listaPremios)
	{
		for ( int i = 0; i < carrito.size(); i++ )
		{
			for ( int j = 0; j < listaPremios.length; j++ )
			{
				if ( carrito.get(i).getCodigo().equals( listaPremios[j].getCodigo() ) )
				{
					carrito.get(i).setDenominacion( listaPremios[j].getDenominacion() );
					carrito.get(i).setDescripcion( listaPremios[j].getDescripcion() );
				}
			}			
		}	
	}
	
	public Premio[] getAccesorios()
	{
		return getFiltro( Categoria.ACCESORIOS );
	}
	
	public Premio[] getConsolas()
	{
		return getFiltro( Categoria.CONSOLAS );
	}
	
	public Premio[] getVideojuegos()
	{
		return getFiltro( Categoria.VIDEOJUEGOS );
	}
	
	private Premio[] getFiltro(Categoria c)
	{
		List<Premio> lista = new ArrayList<Premio>();
		
		for ( Premio p : carrito )
			if ( p.getCategoria() == c )
				lista.add(p);
		
		return lista.toArray( new Premio[lista.size()] );
	}

	public int getNumeroPremios() 
	{
		int numeroPremios = 0;
		
		for ( Premio p : carrito )
			numeroPremios += p.getUnidades();
		
		return numeroPremios;
	}
	
	/**
	 * Si el premio está en el carrito aumenta en 1 las unidades. Sino lo añade
	 * 
	 * @param premioAñadir Premio que se quiere añadir al carrito
	 */
	public void anadirAlCarrito(Premio premioAnadir)
	{
		for ( Premio p : carrito )
		{
			if ( p.getCodigo().equals( premioAnadir.getCodigo() ) )
			{
				p.setUnidades( p.getUnidades() + 1 );
				return;
			}
		}
			
		carrito.add(premioAnadir);
	}

	/**
	 * Si el premio tiene más de una unidad decrementa en uno las unidades, si sólo hay una unidad elimina el premio del carrito
	 * 
	 * @param premioEliminar Premio a eliminar
	 */
	public void eliminarDelCarrito(Premio premioEliminar)
	{
		for ( int i = 0; i < carrito.size(); i++ )
		{
			if ( carrito.get(i).getCodigo().equals( premioEliminar.getCodigo() ) )
			{
				if ( carrito.get(i).getUnidades() == 1 )
					carrito.remove(i);
				else
					carrito.get(i).setUnidades( carrito.get(i).getUnidades() - 1 );
			}
		}
	}
}
