package uo.cpm.videogame.model;

public class Entrega 
{
	private String dniUsuario;
	private String codigoTienda;
	private String[] codigosPremios;
	
	public Entrega(String dniUsuario, String codigoTienda, String[] codigosPremios) 
	{
		this.dniUsuario = dniUsuario;
		this.codigoTienda = codigoTienda;
		this.codigosPremios = codigosPremios;
	}

	public String getDniUsuario() {
		return dniUsuario;
	}

	public void setDniUsuario(String dniUsuario) {
		this.dniUsuario = dniUsuario;
	}

	public String getCodigoTienda() {
		return codigoTienda;
	}

	public void setCodigoTienda(String codigoTienda) {
		this.codigoTienda = codigoTienda;
	}

	public String[] getCodigosPremios() {
		return codigosPremios;
	}

	public void setCodigosPremios(String[] codigosPremios) {
		this.codigosPremios = codigosPremios;
	}

	@Override
	public String toString() 
	{
		String s = String.format("%s@%s", dniUsuario, codigoTienda);
		
		for ( int i = 0; i < codigosPremios.length; i++ )
			s += String.format("@%s", codigosPremios[i]);
		
		return s;
	}
	
	
	
}
