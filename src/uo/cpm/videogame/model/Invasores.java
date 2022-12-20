package uo.cpm.videogame.model;

public enum Invasores 
{
	// Posibilidad de añadir el path de las imágenes de los invasores
	A("A", 1),
	B("B", 2),
	C("C", 3),
	D("D", 4),
	E("E", 5),
	F("F", 6);
	
	private final String imagen;
	private final int numero;
	
	Invasores(String imagen, int numero)
	{
		this.imagen = imagen;
		this.numero = numero;
	}

	public String getImagen() 
	{
		return imagen;
	}

	public int getNumero() 
	{
		return numero;
	}
}
