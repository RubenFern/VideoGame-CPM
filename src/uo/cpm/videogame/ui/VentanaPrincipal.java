package uo.cpm.videogame.ui;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import uo.cpm.videogame.model.Colores;
import uo.cpm.videogame.service.Game;
import uo.cpm.videogame.service.Internacionalizar;

import java.awt.Toolkit;
import java.awt.Color;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.SwingConstants;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.Component;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import java.awt.FlowLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.border.LineBorder;

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
	private ProcesaResizeInterfaz procesaResizeInterfaz;
	private ProcesaPulsacionNumeroTicket procesaPulsacionNumeroTicket;
	
	private Internacionalizar internacionalizar;
	
	private int h1;
	private int h2;
	private int h3;
	private int texto;
	
	private JPanel contentPane;
	private JPanel pnPantallas;
	private JPanel pnPantallaInicio;
	private JPanel pnPantallaJuego;
	private JPanel pnPantallaPremios;
	private JPanel pnPantallaCarrito;
	private JPanel pnPantallaFinal;
	private JButton btSiguiente;
	private JMenuBar MenuBar;
	private JMenu mnAyuda;
	private JMenuItem miAcercaDe;
	private JPanel pnNorteInicio;
	private JLabel lbBienvenido;
	private JLabel lbPedirTicket;
	private JPanel pnCentroInicio;
	private JPanel pnBotonesInicio;
	private JPanel pnCampos;
	private JPanel pnImagenTicket;
	private JLabel lbImagenTicket;
	private JPanel pnTicket;
	private JPanel pnCodigo;
	private JLabel lbNumeroTicket;
	private JTextField txtNumeroTicket;
	private JLabel lbCodigoTienda;
	private JTextField txtoCodigoTienda;
	
	public VentanaPrincipal(Game game) 
	{
		setBackground(Color.DARK_GRAY);
		this.setGame(game);
		
		internacionalizar = new Internacionalizar();
		procesaCambioPantalla = new ProcesaAccionBotonCambioPantalla();
		procesaResizeInterfaz = new ProcesaResizeInterfaz();
		procesaPulsacionNumeroTicket = new ProcesaPulsacionNumeroTicket();
		
		// Textos internacionalizados
		//internacionalizar();
		
		// Asigno el tamaño por defecto de los textos
		tamañoTextos(40, 20, 16, 15);
		
		setTitle( game.getNombreTienda() );
		setIconImage(Toolkit.getDefaultToolkit().getImage(VentanaPrincipal.class.getResource( game.getIconoTienda() )));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1100, 700);
		setLocationRelativeTo(null);
		setJMenuBar(getBarraMenu());
		contentPane = new JPanel();
		contentPane.setBackground(Color.BLACK);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		contentPane.add(getPnPantallas());

		setContentPane(contentPane);
		
		// Escalono la pantalla
		addComponentListener( procesaResizeInterfaz );
	}
	
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
	
	class ProcesaResizeInterfaz extends ComponentAdapter
	{
		@Override
		public void componentResized(ComponentEvent e) 
		{
			ajustaTextos();
			ajustarImagenTicket();
		}
	}
	
	class ProcesaPulsacionNumeroTicket extends KeyAdapter
	{
		@Override
		public void keyTyped(KeyEvent e) 
		{
			char tecla = e.getKeyChar();
			
			// Si no es un dígito evito que se añada en el campo de texto
			if ( !Character.isDigit(tecla) )
				e.consume();
		}
	}
	
	private void tamañoTextos(int h1, int h2, int h3, int texto)
	{
		this.h1 = h1;
		this.h2 = h2;
		this.h3 = h3;
		this.texto = texto;
	}
	
	private void ajustaTextos()
	{
		if ( this.getWidth() < 980 )
			tamañoTextos(32, 18, 15, 15);
		else
			tamañoTextos(40, 20, 16, 15);
			
		// Aumento el margen superior cuando está en pantalla completa
		if ( this.getWidth() > 1300 && this.getHeight() > 800 )
			getLbBienvenido().setBorder(new EmptyBorder(80, 0, 0, 0));
		
		getLbBienvenido().setFont(new Font("Tahoma", Font.BOLD, h1));
		getLbPedirTicket().setFont(new Font("Tahoma", Font.PLAIN, h2));
		
		//System.out.println(this.getWidth());
	}
	
	private void ajustarImagenTicket()
	{
		// TODO
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
		String codigo = this.getTxtoCodigoTienda().getText();
		String numero = this.getTxtNumeroTicket().getText();
		
		if ( codigo.equals("") )
		{
			JOptionPane.showMessageDialog(this, internacionalizar.getTexto("error.codigoTicket"), game.getNombreTienda(), JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		if ( numero.equals("") )
		{
			JOptionPane.showMessageDialog(this, internacionalizar.getTexto("error.numeroTicket"), game.getNombreTienda(), JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		if ( game.ticketValido(codigo, Integer.parseInt( numero )) )
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
			pnPantallaInicio.setBackground(Color.DARK_GRAY);
			pnPantallaInicio.setLayout(new BorderLayout(0, 0));
			pnPantallaInicio.add(getPnNorteInicio(), BorderLayout.NORTH);
			pnPantallaInicio.add(getPnCentroInicio(), BorderLayout.CENTER);
			pnPantallaInicio.add(getPnBotonesInicio(), BorderLayout.SOUTH);
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
	
	private JButton getBtSiguiente() {
		if (btSiguiente == null) {
			btSiguiente = new JButton("");
			btSiguiente.setBackground(new Color(0, 204, 255));
			btSiguiente.setBorder(new EmptyBorder(5, 10, 5, 10));
			btSiguiente.setActionCommand(PANTALLA_JUEGO);
			btSiguiente.setFocusPainted(false); // Elimina la línea naranja			
			btSiguiente.setText( internacionalizar.getTexto("boton.siguiente") );
			
			btSiguiente.addActionListener( procesaCambioPantalla );
		}
		return btSiguiente;
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
	private JPanel getPnNorteInicio() {
		if (pnNorteInicio == null) {
			pnNorteInicio = new JPanel();
			pnNorteInicio.setLayout(new GridLayout(2, 1, 0, 0));
			pnNorteInicio.add(getLbBienvenido());
			pnNorteInicio.add(getLbPedirTicket());
		}
		return pnNorteInicio;
	}
	private JLabel getLbBienvenido() {
		if (lbBienvenido == null) {
			lbBienvenido = new JLabel("");
			lbBienvenido.setBorder(new EmptyBorder(20, 0, 0, 0));
			lbBienvenido.setHorizontalAlignment(SwingConstants.CENTER);
			lbBienvenido.setFont(new Font("Tahoma", Font.BOLD, h1));
			lbBienvenido.setText( internacionalizar.getTexto("inicio.bienvendida") );
		}
		return lbBienvenido;
	}
	private JLabel getLbPedirTicket() {
		if (lbPedirTicket == null) {
			lbPedirTicket = new JLabel("");
			lbPedirTicket.setFont(new Font("Tahoma", Font.PLAIN, h2));
			lbPedirTicket.setHorizontalAlignment(SwingConstants.CENTER);
			lbPedirTicket.setText( internacionalizar.getTexto("inicio.informacionJuego") );
		}
		return lbPedirTicket;
	}
	private JPanel getPnCentroInicio() {
		if (pnCentroInicio == null) {
			pnCentroInicio = new JPanel();
			pnCentroInicio.setAlignmentX(Component.LEFT_ALIGNMENT);
			pnCentroInicio.setLayout(new BorderLayout(0, 0));
			pnCentroInicio.add(getPnCampos());
			pnCentroInicio.add(getPnImagenTicket(), BorderLayout.SOUTH);
		}
		return pnCentroInicio;
	}
	private JPanel getPnBotonesInicio() {
		if (pnBotonesInicio == null) {
			pnBotonesInicio = new JPanel();
			pnBotonesInicio.setBorder(new EmptyBorder(10, 0, 20, 0));
			pnBotonesInicio.add( getBtSiguiente() );
		}
		return pnBotonesInicio;
	}
	
	private JPanel getPnCampos() {
		if (pnCampos == null) {
			pnCampos = new JPanel();
			pnCampos.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 15));
			pnCampos.add(getPnTicket());
			pnCampos.add(getPnCodigo());
		}
		return pnCampos;
	}
	private JPanel getPnImagenTicket() {
		if (pnImagenTicket == null) {
			pnImagenTicket = new JPanel();
			FlowLayout flowLayout = (FlowLayout) pnImagenTicket.getLayout();
			flowLayout.setVgap(20);
			pnImagenTicket.add(getLbImagenTicket());
		}
		return pnImagenTicket;
	}
	private JLabel getLbImagenTicket() {
		if (lbImagenTicket == null) {
			lbImagenTicket = new JLabel("");
			
			// Tamaño de la etiqueta
			lbImagenTicket.setBounds( new Rectangle(340, 250) );
			
			lbImagenTicket.setIcon( ajustarImagen(lbImagenTicket, "/img/ticket.png") );
		}
		return lbImagenTicket;
	}
	
	/**
	 * Escalona las imágenes según el tamaño de la etiqueta
	 * 
	 * @return La imagen escalonada
	 */
	private ImageIcon ajustarImagen(JLabel label, String path)
	{
		Image imgOriginal = new ImageIcon(VentanaPrincipal.class.getResource(path)).getImage();
		Image imgEscalada = imgOriginal.getScaledInstance(lbImagenTicket.getWidth(), lbImagenTicket.getHeight(), Image.SCALE_FAST);
		
		return new ImageIcon(imgEscalada);
	}
	
	private JPanel getPnTicket() {
		if (pnTicket == null) {
			pnTicket = new JPanel();
			pnTicket.setLayout(new GridLayout(2, 1, 0, 0));
			pnTicket.add(getLbNumeroTicket());
			pnTicket.add(getTxtNumeroTicket());
		}
		return pnTicket;
	}
	
	private JPanel getPnCodigo() {
		if (pnCodigo == null) {
			pnCodigo = new JPanel();
			pnCodigo.setLayout(new GridLayout(2, 1, 0, 0));
			pnCodigo.add(getLbCodigoTienda());
			pnCodigo.add(getTxtoCodigoTienda());
		}
		return pnCodigo;
	}
	
	private JLabel getLbNumeroTicket() {
		if (lbNumeroTicket == null) {
			lbNumeroTicket = new JLabel("");
			lbNumeroTicket.setBorder(new EmptyBorder(10, 0, 5, 30));
			lbNumeroTicket.setFont(new Font("Tahoma", Font.PLAIN, texto));
			lbNumeroTicket.setText( internacionalizar.getTexto("inicio.pedirTicket") );
		}
		return lbNumeroTicket;
	}
	
	private JTextField getTxtNumeroTicket() {
		if (txtNumeroTicket == null) {
			txtNumeroTicket = new JTextField();
			
			txtNumeroTicket.addKeyListener( procesaPulsacionNumeroTicket );
			
			txtNumeroTicket.setBounds(new Rectangle(0, 0, 300, 50));
			txtNumeroTicket.setColumns(10);
			txtNumeroTicket.setFont(new Font("Tahoma", Font.PLAIN, texto));
		}
		return txtNumeroTicket;
	}
	
	private JLabel getLbCodigoTienda() {
		if (lbCodigoTienda == null) {
			lbCodigoTienda = new JLabel("");
			lbCodigoTienda.setFont(new Font("Tahoma", Font.PLAIN, texto));
			lbCodigoTienda.setBorder(new EmptyBorder(10, 0, 5, 30));
			lbCodigoTienda.setText( internacionalizar.getTexto("inicio.pedirCodigo") );
		}
		return lbCodigoTienda;
	}
	private JTextField getTxtoCodigoTienda() {
		if (txtoCodigoTienda == null) {
			txtoCodigoTienda = new JTextField();
			txtoCodigoTienda.setFont(new Font("Tahoma", Font.PLAIN, texto));
			txtoCodigoTienda.setColumns(10);
		}
		return txtoCodigoTienda;
	}
}
