package uo.cpm.videogame.model;

public class Premio 
{
	private String codigo;
	private String denominacion;
	private String descripcion;
	private Categoria categoria;
	private int costePuntos;
	private String imagen;
	private int unidades;
	
	public Premio(String codigo, String denominacion, String descripcion, Categoria categoria, int costePuntos, int unidades) 
	{
		this.codigo = codigo;
		this.denominacion = denominacion;
		this.descripcion = descripcion;
		this.categoria = categoria;
		this.costePuntos = costePuntos;
		this.imagen = "/img/" + codigo + ".png";
		this.unidades = unidades;
	}
	
	public Premio(String codigo, String denominacion, String descripcion, Categoria categoria, int costePuntos) 
	{
		this(codigo, denominacion, descripcion, categoria, costePuntos, 1);
	}
	
	public String getCodigo() {
		return codigo;
	}
	
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	
	public String getDenominacion() {
		return denominacion;
	}
	
	public void setDenominacion(String denominacion) {
		this.denominacion = denominacion;
	}
	
	public String getDescripcion() {
		return descripcion;
	}
	
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	public Categoria getCategoria() {
		return categoria;
	}
	
	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}
	
	public int getCostePuntos() {
		return costePuntos;
	}
	
	public void setCostePuntos(int costePuntos) {
		this.costePuntos = costePuntos;
	}

	public String getImagen() {
		return imagen;
	}

	public void setImagen(String imagen) {
		this.imagen = imagen;
	}

	public int getUnidades() {
		return unidades;
	}

	public void setUnidades(int unidades) {
		this.unidades = unidades;
	}

	@Override
	public String toString() {
		return "Premio [codigo=" + codigo + ", denominacion=" + denominacion + ", categoria=" + categoria
				+ ", costePuntos=" + costePuntos + ", imagen=" + imagen + ", unidades=" + unidades + "]";
	}

	

	
}
