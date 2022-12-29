package uo.cpm.videogame.model;

public class Invasor 
{
	private String imagen;
	private int numero;
	private boolean lider;
	
	public Invasor(int numero, boolean lider)
	{
		this.imagen = String.format("/img/invasor%d.png", numero);
		this.numero = numero;
		this.lider = lider;
	}

	public String getImagen() 
	{
		return imagen;
	}
	
	public void setImagen(String imagen) {
		this.imagen = imagen;
	}

	public int getNumero() 
	{
		return numero;
	}
	
	public void setNumero(int numero) {
		this.numero = numero;
	}

	public boolean isLider() {
		return lider;
	}
	
	public void setLider(boolean lider) {
		this.lider = lider;
	}

	@Override
	public String toString() {
		return "Invasor-" + numero;
	}	
	
}
