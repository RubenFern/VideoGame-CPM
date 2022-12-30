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
	
	private void inicializar()
	{
		carrito.clear();
	}
	
	public Premio[] getCarrito() {
		return carrito.toArray( new Premio[carrito.size()] );
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
	 * Si el premio est� en el carrito aumenta en 1 las unidades. Sino lo a�ade
	 * 
	 * @param premioA�adir Premio que se quiere a�adir al carrito
	 */
	public void a�adirAlCarrito(Premio premioA�adir)
	{
		for ( Premio p : carrito )
		{
			if ( p.getCodigo().equals( premioA�adir.getCodigo() ) )
			{
				p.setUnidades( p.getUnidades() + 1 );
				return;
			}
		}
			
		carrito.add(premioA�adir);
	}

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
