package uo.cpm.videogame.model;

public enum Colores 
{
	BACKGROUNDS("#000000"),
	TEXTOS("#E3B359");
	
	private final String color;
	
	Colores(String color)
	{
		this.color = color;
	}
	
	public String getColor()
	{
		return color;
	}
}
