package uo.cpm.videogame.service;

import java.util.Locale;
import java.util.ResourceBundle;

public class Internacionalizar 
{
	private ResourceBundle textos;
	
	public Internacionalizar()
	{
		Locale localizacion = Locale.getDefault(Locale.Category.FORMAT);
		textos = ResourceBundle.getBundle("rsc/textos", localizacion);
	}
	
	public String getTexto(String s)
	{
		return textos.getString(s);
	}
}
