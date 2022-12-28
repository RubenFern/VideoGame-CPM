package uo.cpm.videogame.ui;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import uo.cpm.videogame.service.Game;
import uo.cpm.videogame.service.Internacionalizar;

import java.awt.Toolkit;
import java.awt.Color;
import java.awt.Image;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;


public class VentanaPrincipal extends JFrame 
{
	private static final long serialVersionUID = 1L;
	
	public static final String PANTALLA_INICIO = "pantallaInicio";
	public static final String PANTALLA_JUEGO = "pantallaJuego";
	public static final String PANTALLA_PREMIOS = "pantallaPremios";
	public static final String PANTALLA_CARRITO = "pantallaCarrito";
	public static final String PANTALLA_FINAL = "pantallaFinal";
	
	private int h1;
	private int h2;
	private int h3;
	private int texto;
	
	private Game game;
	private VentanaInicio pnPantallaInicio;
	private VentanaJuego pnPantallaJuego;
	
	private Internacionalizar internacionalizar;
	
	private JPanel contentPane;
	private JPanel pnPantallas;
	private JPanel pnPantallaPremios;
	private JPanel pnPantallaCarrito;
	private JPanel pnPantallaFinal;
	private JMenuBar MenuBar;
	private JMenu mnAyuda;
	private JMenuItem miAcercaDe;
	
	public VentanaPrincipal(Game game) 
	{
		setBackground(Color.DARK_GRAY);
		this.setGame(game);
		
		internacionalizar = new Internacionalizar();
		
		// Pantallas
		pnPantallaInicio = new VentanaInicio(this);
		pnPantallaJuego = new VentanaJuego(this);
		
		setTitle( game.getNombreTienda() );
		setIconImage(Toolkit.getDefaultToolkit().getImage(VentanaPrincipal.class.getResource( game.getIconoTienda() )));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1100, 800);
		setLocationRelativeTo(null);
		setJMenuBar(getBarraMenu());
		contentPane = new JPanel();
		contentPane.setBackground(Color.BLACK);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		contentPane.add(getPnPantallas());

		setContentPane(contentPane);
	}
	
	public Internacionalizar getInternacionalizar() {
		return internacionalizar;
	}

	public Game getGame() {
		return game;
	}
	
	public void setGame(Game game) {
		this.game = game;
	}
	
	public int getH1() {
		return h1;
	}

	public int getH2() {
		return h2;
	}

	public int getH3()
	{
		return h3;
	}
	
	public int getTexto() {
		return texto;
	}

	protected void mostrarPantallaInicio() 
	{
		( (CardLayout) getPnPantallas().getLayout()).show(getPnPantallas(), PANTALLA_INICIO);
	}
	
	protected void mostrarPantallaJuego() 
	{
		( (CardLayout) getPnPantallas().getLayout()).show(getPnPantallas(), PANTALLA_JUEGO);
	}
	
	protected void mostrarPantallaPremios() 
	{
		( (CardLayout) getPnPantallas().getLayout()).show(getPnPantallas(), PANTALLA_PREMIOS);
	}
	
	protected void mostrarPantallaCarrito() 
	{
		( (CardLayout) getPnPantallas().getLayout()).show(getPnPantallas(), PANTALLA_CARRITO);
	}
	
	protected void mostrarPantallaFinal() 
	{
		( (CardLayout) getPnPantallas().getLayout()).show(getPnPantallas(), PANTALLA_FINAL);
	}
	
	protected void tamañoTextos(int h1, int h2, int h3, int texto)
	{
		this.h1 = h1;
		this.h2 = h2;
		this.h3 = h3;
		this.texto = texto;
	}
	
	/**
	 * Escalona las imágenes según el tamaño de la etiqueta
	 * 
	 * @return La imagen escalonada
	 */
	protected ImageIcon ajustarImagen(JLabel label, String path)
	{
		Image imgOriginal = new ImageIcon(VentanaPrincipal.class.getResource(path)).getImage();
		Image imgEscalada = imgOriginal.getScaledInstance(label.getWidth(), label.getHeight(), Image.SCALE_REPLICATE);
		
		return new ImageIcon(imgEscalada);
	}
	
	private JPanel getPnPantallas() 
	{
		if (pnPantallas == null)
		{
			pnPantallas = new JPanel();
			pnPantallas.setLayout(new CardLayout(0, 0));
			pnPantallas.add(pnPantallaInicio, PANTALLA_INICIO);
			pnPantallas.add(pnPantallaJuego, PANTALLA_JUEGO);
			pnPantallas.add(getPnPantallaPremios(), PANTALLA_PREMIOS);
			pnPantallas.add(getPnPantallaCarrito(), PANTALLA_CARRITO);
			pnPantallas.add(getPnPantallaFinal(), PANTALLA_FINAL);
		}
		
		return pnPantallas;
	}
	
	private JPanel getPnPantallaPremios() {
		if (pnPantallaPremios == null) {
			pnPantallaPremios = new JPanel();
			pnPantallaPremios.setBackground(Color.GREEN);
		}
		return pnPantallaPremios;
	}
	
	private JPanel getPnPantallaCarrito() {
		if (pnPantallaCarrito == null) {
			pnPantallaCarrito = new JPanel();
			pnPantallaCarrito.setBackground(Color.MAGENTA);
		}
		return pnPantallaCarrito;
	}
	
	private JPanel getPnPantallaFinal() {
		if (pnPantallaFinal == null) {
			pnPantallaFinal = new JPanel();
			pnPantallaFinal.setBackground(Color.PINK);
		}
		return pnPantallaFinal;
	}
	
	private JMenuBar getBarraMenu() {
		if (MenuBar == null) {
			MenuBar = new JMenuBar();
			MenuBar.add(getMnAyuda());
		}
		return MenuBar;
	}
	private JMenu getMnAyuda() {
		if (mnAyuda == null) {
			mnAyuda = new JMenu("");
			mnAyuda.add(getMiAcercaDe());
			mnAyuda.setText( internacionalizar.getTexto("menu.ayuda") );
		}
		return mnAyuda;
	}
	private JMenuItem getMiAcercaDe() {
		if (miAcercaDe == null) {
			miAcercaDe = new JMenuItem("");
			miAcercaDe.setText( internacionalizar.getTexto("menu.acercade") );
		}
		return miAcercaDe;
	}
}
