package uo.cpm.videogame.model;

public class Casilla 
{
	private String imagen;
	private int numero;
	
	public Casilla(String imagen, int numero)
	{
		this.imagen = imagen;
		this.numero = numero;
	}

	public String getImagen() {
		return imagen;
	}

	public void setImagen(String imagen) {
		this.imagen = imagen;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}
	
	
}
