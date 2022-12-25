package uo.cpm.videogame.model;

public class Tienda 
{
	private String codigo;
	private String nombre;
	private String icono;
	
	public Tienda(String codigo, String nombre)
	{
		this.codigo = codigo;
		this.nombre = nombre;
		this.icono = "/img/icono.png";
	}
	
	public Tienda()
	{
		this("", "");
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getIcono() {
		return icono;
	}

	public void setIcono(String icono) {
		this.icono = icono;
	}

}
