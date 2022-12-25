package uo.cpm.videogame.model;

public class Ticket 
{
	private String codigoTienda;
	private int numero;
	private double importe;

	public Ticket(String codigoTienda, int numero, double importe) 
	{
		this.codigoTienda = codigoTienda;
		this.numero = numero;
		this.importe = importe;
	}
	
	public String getCodigoTienda() {
		return codigoTienda;
	}

	public void setCodigoTienda(String codigoTienda) {
		this.codigoTienda = codigoTienda;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public double getImporte() {
		return importe;
	}

	public void setImporte(double importe) {
		this.importe = importe;
	}

	@Override
	public String toString() {
		return "Ticket [codigoTienda=" + codigoTienda + ", numero=" + numero + ", importe=" + importe + "]";
	}
}
