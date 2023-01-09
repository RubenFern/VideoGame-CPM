package uo.cpm.videogame.model;

import java.util.ArrayList;
import java.util.List;

import uo.cpm.videogame.service.Internacionalizar;
import uo.cpm.videogame.util.FileUtil;

public class Catalogo 
{
	private List<Premio> listaPremios;
	
	public Catalogo()
	{
		listaPremios = new ArrayList<Premio>();
		
		cargarArticulos( new Internacionalizar() );
	}
	
	public Premio[] getListaPremios()
	{
		return listaPremios.toArray( new Premio[listaPremios.size()] );
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
		
		for ( Premio p : listaPremios )
			if ( p.getCategoria() == c )
				lista.add(p);
		
		return lista.toArray( new Premio[lista.size()] );
	}
	
	public void cargarArticulos(Internacionalizar i)
	{
		listaPremios.clear();
		
		FileUtil.cargarPremios(i.getTexto("archivos.premios"), listaPremios);
	}
}
