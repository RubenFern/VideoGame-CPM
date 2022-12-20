package uo.cpm.videogame.ui;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import uo.cpm.videogame.service.Game;
import java.awt.Toolkit;
import java.awt.Color;
import java.awt.SystemColor;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import java.awt.CardLayout;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class VentanaPrincipal extends JFrame 
{
	private static final long serialVersionUID = 1L;
	
	private static final String PANTALLA_INICIO = "pantallaInicio";
	private static final String PANTALLA_JUEGO = "pantallaJuego";
	private static final String PANTALLA_PREMIOS = "pantallaPremios";
	private static final String PANTALLA_CARRITO = "pantallaCarrito";
	private static final String PANTALLA_FINAL = "pantallaFinal";
	
	private Game game;
	private ProcesaAccionBotonCambioPantalla procesaCambioPantalla;
	
	private JPanel contentPane;
	private JPanel pnPantallas;
	private JPanel pnPantallaInicio;
	private JPanel pnPantallaJuego;
	private JPanel pnPantallaPremios;
	private JPanel pnPantallaCarrito;
	private JPanel pnPantallaFinal;
	private JPanel pnBotones;
	private JButton btSiguiente;

	class ProcesaAccionBotonCambioPantalla implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			JButton b = (JButton) e.getSource();
			
			switch ( b.getActionCommand() )
			{
			case PANTALLA_INICIO: 
				mostrarPantallaInicio();
				break;
			
			case PANTALLA_JUEGO: 
				mostrarPantallaJuego();
				break;
			
			case PANTALLA_PREMIOS: 
				mostrarPantallaPremios();
				break;
			
			case PANTALLA_CARRITO: 
				mostrarPantallaCarrito();
				break;
			
			case PANTALLA_FINAL: 
				mostrarPantallaFinal();
				break;
			}
		}
	}
	
	public VentanaPrincipal(Game game) 
	{
		setBackground(Color.DARK_GRAY);
		this.setGame(game);
		procesaCambioPantalla = new ProcesaAccionBotonCambioPantalla();
		
		setTitle( game.getNombreTienda() );
		setIconImage(Toolkit.getDefaultToolkit().getImage(VentanaPrincipal.class.getResource( game.getIconoTienda() )));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1082, 678);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBackground(Color.BLACK);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		contentPane.add(getPnPantallas());

		setContentPane(contentPane);
		
		Locale localizacion = Locale.getDefault(Locale.Category.FORMAT);
		ResourceBundle textos = ResourceBundle.getBundle("rsc/textos", localizacion);
		
		getBtSiguiente().setText( textos.getString("boton.siguiente") );
	}
	
	public Game getGame() {
		return game;
	}
	
	public void setGame(Game game) {
		this.game = game;
	}

	private void mostrarPantallaInicio() 
	{
		( (CardLayout) getPnPantallas().getLayout()).show(getPnPantallas(), PANTALLA_INICIO);
	}
	
	private void mostrarPantallaJuego() 
	{
		( (CardLayout) getPnPantallas().getLayout()).show(getPnPantallas(), PANTALLA_JUEGO);
	}
	
	private void mostrarPantallaPremios() 
	{
		( (CardLayout) getPnPantallas().getLayout()).show(getPnPantallas(), PANTALLA_PREMIOS);
	}
	
	private void mostrarPantallaCarrito() 
	{
		( (CardLayout) getPnPantallas().getLayout()).show(getPnPantallas(), PANTALLA_CARRITO);
	}
	
	private void mostrarPantallaFinal() 
	{
		( (CardLayout) getPnPantallas().getLayout()).show(getPnPantallas(), PANTALLA_FINAL);
	}
	
	private JPanel getPnPantallas() 
	{
		if (pnPantallas == null)
		{
			pnPantallas = new JPanel();
			pnPantallas.setLayout(new CardLayout(0, 0));
			pnPantallas.add(getPnPantallaInicio(), PANTALLA_INICIO);
			pnPantallas.add(getPnPantallaJuego(), PANTALLA_JUEGO);
			pnPantallas.add(getPnPantallaPremios(), PANTALLA_PREMIOS);
			pnPantallas.add(getPnPantallaCarrito(), PANTALLA_CARRITO);
			pnPantallas.add(getPnPantallaFinal(), PANTALLA_FINAL);
		}
		
		return pnPantallas;
	}
	private JPanel getPnPantallaInicio() {
		if (pnPantallaInicio == null) {
			pnPantallaInicio = new JPanel();
			pnPantallaInicio.setBackground(Color.RED);
			pnPantallaInicio.setLayout(new BorderLayout(0, 0));
			pnPantallaInicio.add(getPnBotones(), BorderLayout.SOUTH);
		}
		return pnPantallaInicio;
	}
	private JPanel getPnPantallaJuego() {
		if (pnPantallaJuego == null) {
			pnPantallaJuego = new JPanel();
			pnPantallaJuego.setBackground(Color.YELLOW);
		}
		return pnPantallaJuego;
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
	private JPanel getPnBotones() {
		if (pnBotones == null) {
			pnBotones = new JPanel();
			pnBotones.add(getBtSiguiente());
		}
		return pnBotones;
	}
	private JButton getBtSiguiente() {
		if (btSiguiente == null) {
			btSiguiente = new JButton("");
			btSiguiente.setActionCommand(PANTALLA_JUEGO);
			
			btSiguiente.addActionListener( procesaCambioPantalla );
		}
		return btSiguiente;
	}

	
}
