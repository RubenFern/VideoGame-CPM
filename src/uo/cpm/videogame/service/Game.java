package uo.cpm.videogame.service;

import java.util.ArrayList;
import java.util.List;

import uo.cpm.videogame.model.Casilla;
import uo.cpm.videogame.model.Invasores;
import uo.cpm.videogame.model.Tienda;
import uo.cpm.videogame.util.FileUtil;

public class Game 
{
	private static final int NUMERO_INVASORES = 10;
	
	private List<Invasores> invasores;
	private Casilla[] casillas;
	private Tienda tienda;
	
	public Game()
	{
		invasores = new ArrayList<Invasores>();
		casillas = new Casilla[NUMERO_INVASORES];
		tienda = new Tienda();
		
		// Recorro todos los invasores
		for ( Invasores c : Invasores.values() )
			invasores.add(c);
		
		FileUtil.loadFile("files/config.dat", tienda);
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
}
