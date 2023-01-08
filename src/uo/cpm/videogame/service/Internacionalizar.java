package uo.cpm.videogame.service;

import java.util.Locale;
import java.util.ResourceBundle;

public class Internacionalizar 
{
	private ResourceBundle textos;
	private Locale localizacion;
	
	public Internacionalizar()
	{
		//Locale localizacion = Locale.getDefault(Locale.Category.FORMAT);
		
		// La localización por defecto será en España
		localizacion = new Locale("es");
		textos = ResourceBundle.getBundle("rsc/textos", localizacion);
	}
	
	public String getTexto(String s)
	{
		return textos.getString(s);
	}
	
	public Locale getLocale()
	{
		return localizacion;
	}
	
	public void setLocale(Locale localizacion)
	{
		this.localizacion = localizacion;
	}
	
	public void cambiarIdioma(Locale localizacion)
	{
		this.setLocale(localizacion);
		textos = ResourceBundle.getBundle("rsc/textos", localizacion);
	}
}
