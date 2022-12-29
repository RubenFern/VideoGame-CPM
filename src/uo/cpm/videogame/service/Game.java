package uo.cpm.videogame.service;

import java.util.ArrayList;
import java.util.List;

import uo.cpm.videogame.model.Casilla;
import uo.cpm.videogame.model.Invasor;
import uo.cpm.videogame.model.Reglas;
import uo.cpm.videogame.model.Tablero;
import uo.cpm.videogame.model.Ticket;
import uo.cpm.videogame.model.Tienda;
import uo.cpm.videogame.util.FileUtil;

public class Game 
{
	private Tienda tienda;
	private Tablero tablero;
	private List<Ticket> listaTickets;
	private int ronda;
	private int puntos;
	private int movimientos;
	private Casilla casilla;
	private boolean arrastra;
	
	public Game()
	{	
		// Cargo la informaci�n de la tienda y de los tickets
		tienda = new Tienda();
		tablero = new Tablero();
		listaTickets = new ArrayList<Ticket>();
		casilla = new Casilla();		
		
		FileUtil.cargarDatosTienda("files/config.dat", tienda);
		FileUtil.cargarDatosTickets("files/tickets.dat", listaTickets);
		
		this.setRonda(1);
		this.setMovimientos( Reglas.INVASORES_POR_RONDA.getValor() );
	}
	
	public int getRonda() {
		return ronda;
	}

	public void setRonda(int ronda) {
		this.ronda = ronda;
	}
	
	public void aumentarRonda()
	{
		this.setRonda( this.getRonda() + 1 );
	}
	
	public int getPuntos() {
		return puntos;
	}

	public void setPuntos(int puntos) {
		this.puntos = puntos;
	}
	
	public int getMovimientos() {
		return movimientos;
	}

	public void setMovimientos(int movimientos) {
		this.movimientos = movimientos;
	}

	public boolean isArrastra() {
		return arrastra;
	}

	public void setArrastra(boolean arrastra) {
		this.arrastra = arrastra;
	}
	
	public Invasor getInvasor(int numeroInvasor)
	{
		return tablero.getInvasores()[numeroInvasor - 1];
	}

	public void inicializar()
	{
		// Cargo de nuevo los tickets disponibles
		listaTickets.clear();
		FileUtil.cargarDatosTickets("files/tickets.dat", listaTickets);
	}
	
	public String getCodigoTienda()
	{
		return tienda.getCodigo();
	}
	
	public String getNombreTienda()
	{
		return tienda.getNombre();
	}
	
	public String getIconoTienda()
	{
		return tienda.getIcono();
	}
	
	public Casilla[] getTablero()
	{
		return tablero.getTablero();
	}
	
	public Invasor[] getRondaInvasores()
	{
		return tablero.getRondaInvasores();
	}
	
	public Casilla getCasilla()
	{
		return casilla;
	}
	
	public void setNumeroInvasorCasilla(int numeroInvasor)
	{
		casilla.setInvasor( this.getInvasor(numeroInvasor) );
	}
	
	public void setPosicionTableroCasilla(int posicionTablero)
	{
		casilla.setPosicionTablero(posicionTablero);
	}
	
	public void a�adirInvasorAlTablero(Casilla casilla)
	{
		tablero.a�adirInvasorAlTablero(casilla);
	}
	
	/**
	 * Recorre la lista de tickets y cuando encuentra un ticket con el mismo n�mero de ticket lo devuelve
	 * 
	 * @param codigo C�digo del ticket
	 * @param numero N�mero del ticket
	 * @return Devuelve el ticket encontrado o null si no lo ha encontrado
	 */
	public Ticket ticketValido(String codigo, int numero)
	{
		for ( Ticket t : listaTickets )
			if ( t.getNumero() == numero )
				return t;
		
		return null;
	}
	
	public boolean EsPosicionValida(int posicion)
	{
		return tablero.EsPosicionValida(posicion);
	}
	
	public int getNumeroInvasoresTablero()
	{
		return tablero.getNumeroInvasoresEnTablero();
	}
	
	public int getNumeroInvasoresTableroMaximo()
	{
		// El tama�o total del tablero menos las 5 posiciones no v�lidas del tablero
		return tablero.getDimensionTablero() - Reglas.NUM_POSICIONES_NO_VALIDAS.getValor();
	}
	
	public int eliminarColonias()
	{
		return tablero.eliminarColonias();
	}
	
	public void imprimirTablero()
	{
		tablero.imprimirTablero();
	}
	
	public void imprPosicionesValidas()
	{
		tablero.imprPosicionesValidas();
	}
}
