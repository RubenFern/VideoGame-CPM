package uo.cpm.videogame.model;

import uo.cpm.videogame.service.Game;

public class Dado 
{
	public static int lanzar()
	{ 
		return ((int) (Math.random() * Game.NUMERO_INVASORES));
	}
}
