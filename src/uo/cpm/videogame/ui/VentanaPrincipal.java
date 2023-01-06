package uo.cpm.videogame.ui;

import javax.help.HelpBroker;
import javax.help.HelpSet;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.border.EmptyBorder;

import uo.cpm.videogame.model.Reglas;
import uo.cpm.videogame.service.Game;
import uo.cpm.videogame.service.GestionPremios;
import uo.cpm.videogame.service.Internacionalizar;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.URL;
import java.util.Locale;
import java.awt.Color;
import java.awt.Image;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JOptionPane;
import javax.swing.JMenuItem;
import java.awt.Dimension;
import javax.swing.JRadioButtonMenuItem;

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
	private GestionPremios gestionPremios;
	private VentanaInicio pnPantallaInicio;
	private VentanaJuego pnPantallaJuego;
	private VentanaPremios pnPantallaPremios;
	private VentanaCarrito pnPantallaCarrito;
	private VentanaFinal pnPantallaFinal;
	
	private ProcesaAccionSalir pAS;
	private ProcesaCambiarIdioma pCI;
	
	private Internacionalizar internacionalizar;
	
	private JPanel contentPane;
	private JPanel pnPantallas;
	private JMenuBar MenuBar;
	private JMenu mnAyuda;
	private JMenuItem miAyuda;
	private JSeparator separator;
	private JMenuItem miAcercaDe;
	private JMenu mnJuego;
	private JMenuItem miSalir;
	private JMenu mnOpciones;
	private JRadioButtonMenuItem miEspanol;
	private JRadioButtonMenuItem miIngles;
	
	public VentanaPrincipal(Game game, GestionPremios gestionPremios) 
	{
		setMinimumSize(new Dimension(1100, 800));
		this.setGame(game);
		this.setGestionPremios(gestionPremios);
		this.pAS = new ProcesaAccionSalir();
		this.pCI = new ProcesaCambiarIdioma();
		
		internacionalizar = new Internacionalizar();
		
		// Pantallas
		pnPantallaInicio = new VentanaInicio(this);
		pnPantallaJuego = new VentanaJuego(this);
		pnPantallaPremios = new VentanaPremios(this);
		pnPantallaCarrito = new VentanaCarrito(this);
		pnPantallaFinal = new VentanaFinal(this);
		
		setBackground(Color.DARK_GRAY);
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
		
		cargaAyuda();
		
		mostrarPantallaInicio();
	}
	
	public void cargaAyuda() 
	{
		URL hsURL;
		HelpSet hs;

		try {
			File fichero = new File("help/ayuda.hs");
			hsURL = fichero.toURI().toURL();
			hs = new HelpSet(null, hsURL);
		}
		catch (Exception e) {
			System.out.println("Ayuda no encontrada");
			return;
		}

		HelpBroker hb = hs.createHelpBroker();
		hb.initPresentation();

		hb.enableHelpKey(getRootPane(), "introduccion", hs); // Escucha el F1 desde toda la aplicaci�n
		hb.enableHelpOnButton(getMiAyuda(), "introduccion", hs); // La ayuda no funciona con action event
		hb.enableHelpOnButton( this.getPnPantallaJuego().getBtComoJugar() , "como_jugar", hs);
		
		// Ayuda sensible al contexto
		hb.enableHelp(this.getPnPantallaJuego(), "como_jugar", hs);
	}
	
	class ProcesaAccionSalir implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			salir();
		}
	}
	
	class ProcesaCambiarIdioma implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			String locale = e.getActionCommand();
			
			traducir( new Locale(locale) );
		}
	}
	
	public void traducir(Locale locale)
	{
		internacionalizar.cambiarIdioma( locale );
		
		// --- Pantalla principal ---
		this.getMnJuego().setText( internacionalizar.getTexto("menu.juego") );
		this.getMiSalir().setText( internacionalizar.getTexto("menu.salir") );
		this.getMnAyuda().setText( internacionalizar.getTexto("menu.ayuda") );
		this.getMiAyuda().setText( internacionalizar.getTexto("menu.ayuda") );
		this.getMiAcercaDe().setText( internacionalizar.getTexto("menu.acercade") );
		this.getMnOpciones().setText( internacionalizar.getTexto("menu.opciones") );
		this.getMiEspanol().setText( internacionalizar.getTexto("menu.espanol") );
		this.getMiIngles().setText( internacionalizar.getTexto("menu.ingles") );
		
		// --- Pantalla de Inicio ---
		this.getPnPantallaInicio().getLbBienvenido().setText( internacionalizar.getTexto("inicio.bienvendida") );
		this.getPnPantallaInicio().getLbNumeroTicket().setText( internacionalizar.getTexto("inicio.pedirTicket") );
		this.getPnPantallaInicio().getLbCodigoTienda().setText( internacionalizar.getTexto("inicio.pedirCodigo") );
		this.getPnPantallaInicio().getLbPedirTicket().setText( internacionalizar.getTexto("inicio.informacionJuego") );
		this.getPnPantallaInicio().getBtSiguiente().setText( internacionalizar.getTexto("boton.jugar") );		
		
		// --- Pantalla de Juego ---
		this.getPnPantallaJuego().getBtSalir().setText(internacionalizar.getTexto("boton.salir"));
		this.getPnPantallaJuego().getLbPuntos().setText(internacionalizar.getTexto("juego.puntos"));
		this.getPnPantallaJuego().getLbRonda().setText( String.format("%s %d/%d", internacionalizar.getTexto("juego.ronda"), game.getRonda(), Reglas.RONDAS.getValor()) );
		this.getPnPantallaJuego().getBtComoJugar().setText( internacionalizar.getTexto("boton.comojugar") );
		
		// --- Pantalla de Premios ---
		gestionPremios.cargarArticulos(internacionalizar); // Recargo los premios internacionalizados
		this.getPnPantallaPremios().mostrarPremios(this.getPnPantallaPremios().getCbRegalosDisponibles().isSelected());
		
		this.getPnPantallaPremios().getBtSalir().setText(internacionalizar.getTexto("boton.salir"));
		this.getPnPantallaPremios().getLbPuntos().setText(internacionalizar.getTexto("premios.puntos"));
		this.getPnPantallaPremios().getLbFiltrar().setText( internacionalizar.getTexto("premios.filtrar") );
		this.getPnPantallaPremios().getLbCategorias().setText( internacionalizar.getTexto("premios.categorias") );
		this.getPnPantallaPremios().getRdTodo().setText( internacionalizar.getTexto("premios.todo") );
		this.getPnPantallaPremios().getRdAccesorios().setText( internacionalizar.getTexto("premios.accesorios") );
		this.getPnPantallaPremios().getRdConsolas().setText( internacionalizar.getTexto("premios.consolas") );
		this.getPnPantallaPremios().getRdVideojuegos().setText( internacionalizar.getTexto("premios.videojuegos") );
		this.getPnPantallaPremios().getCbRegalosDisponibles().setText( internacionalizar.getTexto("premios.regalosdisponibles") );
		
		// --- Pantalla del Carrito ---
		gestionPremios.actualizarCarrito(this.getPnPantallaPremios().getCbRegalosDisponibles().isSelected(), game.getPuntos());
		this.getPnPantallaCarrito().mostrarPremios();
		
		this.getPnPantallaCarrito().getBtAtras().setText(internacionalizar.getTexto("boton.atras"));
		this.getPnPantallaCarrito().getLbPuntos().setText(internacionalizar.getTexto("premios.puntos"));
		this.getPnPantallaCarrito().getBtConfirmar().setText( internacionalizar.getTexto("boton.confirmar") );
		this.getPnPantallaCarrito().getLbPremioSeleccionados().setText( internacionalizar.getTexto("carrito.titulo") );
		this.getPnPantallaCarrito().getLbFiltrar().setText( internacionalizar.getTexto("premios.filtrar") );
		this.getPnPantallaCarrito().getLbCategorias().setText( internacionalizar.getTexto("premios.categorias") );
		this.getPnPantallaCarrito().getRdTodo().setText( internacionalizar.getTexto("premios.todo") );
		this.getPnPantallaCarrito().getRdAccesorios().setText( internacionalizar.getTexto("premios.accesorios") );
		this.getPnPantallaCarrito().getRdConsolas().setText( internacionalizar.getTexto("premios.consolas") );
		this.getPnPantallaCarrito().getRdVideojuegos().setText( internacionalizar.getTexto("premios.videojuegos") );
		
		// --- Pantalla Final --- 
		this.getPnPantallaFinal().getBtAtras().setText( internacionalizar.getTexto("boton.atras") );
		this.getPnPantallaFinal().getLbIntroducirDNI().setText( internacionalizar.getTexto("final.introducirdni") );
		this.getPnPantallaFinal().getBtRecogerPremios().setText( internacionalizar.getTexto("boton.recoger") );
	}
	
	public ProcesaCambiarIdioma getActionCambiarIdioma()
	{
		return pCI;
	}
	
	public VentanaInicio getPnPantallaInicio() {
		return pnPantallaInicio;
	}

	public VentanaJuego getPnPantallaJuego() {
		return pnPantallaJuego;
	}

	public VentanaPremios getPnPantallaPremios() {
		return pnPantallaPremios;
	}
	
	public VentanaCarrito getPnPantallaCarrito()
	{
		return pnPantallaCarrito;
	}
	
	public VentanaFinal getPnPantallaFinal()
	{
		return pnPantallaFinal;
	}

	public ProcesaAccionSalir getProcesaAccionSalir() {
		return pAS;
	}

	private void salir()
	{
		if ( confirmarSalir() )
		{
			this.inicializarJuego();
			
			this.mostrarPantallaInicio();
		}		
	}
	
	private boolean confirmarSalir()
	{		
		int respuesta = JOptionPane.showConfirmDialog(this, this.getInternacionalizar().getTexto("boton.mensajeSalir"));
		
		if ( respuesta == JOptionPane.YES_OPTION )
			return true;
		
		return false;
	}
	
	/**
	 * Reinicia los puntos, ronda, tablero y movimientos
	 */
	public void inicializarJuego()
	{
		game.inicializar();
		
		pnPantallaJuego.getPnTablero().removeAll();
		pnPantallaJuego.getPnMovimientos().removeAll();
		
		pnPantallaJuego.pintaTablero();
		pnPantallaJuego.pintaMovimientos();
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
	
	public GestionPremios getGestionPremios() {
		return gestionPremios;
	}

	public void setGestionPremios(GestionPremios gestionPremios) {
		this.gestionPremios = gestionPremios;
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
		this.setTitle( game.getNombreTienda() + " " + internacionalizar.getTexto("inicio.pantalla") );
		( (CardLayout) getPnPantallas().getLayout()).show(getPnPantallas(), PANTALLA_INICIO);
	}
	
	protected void mostrarPantallaJuego() 
	{
		this.setTitle( game.getNombreTienda() + " " + internacionalizar.getTexto("juego.pantalla") );
		( (CardLayout) getPnPantallas().getLayout()).show(getPnPantallas(), PANTALLA_JUEGO);
	}
	
	protected void mostrarPantallaPremios() 
	{
		this.setTitle( game.getNombreTienda() + " " + internacionalizar.getTexto("premios.pantalla") );
		( (CardLayout) getPnPantallas().getLayout()).show(getPnPantallas(), PANTALLA_PREMIOS);
	}
	
	protected void mostrarPantallaCarrito() 
	{
		this.setTitle( game.getNombreTienda() + " " + internacionalizar.getTexto("carrito.pantalla") );
		( (CardLayout) getPnPantallas().getLayout()).show(getPnPantallas(), PANTALLA_CARRITO);
	}
	
	protected void mostrarPantallaFinal() 
	{
		this.setTitle( game.getNombreTienda() + " " + internacionalizar.getTexto("final.pantalla") );
		( (CardLayout) getPnPantallas().getLayout()).show(getPnPantallas(), PANTALLA_FINAL);
	}
	
	protected void tamanoTextos(int h1, int h2, int h3, int texto)
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
	protected ImageIcon ajustarImagen(JComponent comp, String path)
	{
		Image imgOriginal = new ImageIcon(VentanaPrincipal.class.getResource(path)).getImage();
		Image imgEscalada = imgOriginal.getScaledInstance(comp.getWidth(), comp.getHeight(), Image.SCALE_FAST);
		
		return new ImageIcon(imgEscalada);
	}
	
	/*protected ImageIcon ajustarImagen(JPanel panel, String path)
	{
		Image imgOriginal = new ImageIcon(VentanaPrincipal.class.getResource(path)).getImage();
		Image imgEscalada = imgOriginal.getScaledInstance(panel.getWidth(), panel.getHeight(), Image.SCALE_FAST);
		
		return new ImageIcon(imgEscalada);
	}*/
	
	protected ImageIcon ajustarImagen(int w, int h, String path)
	{
		Image imgOriginal = new ImageIcon(VentanaPrincipal.class.getResource(path)).getImage();
		Image imgEscalada = imgOriginal.getScaledInstance(w, h, Image.SCALE_REPLICATE);
		
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
			pnPantallas.add(pnPantallaPremios, PANTALLA_PREMIOS);
			pnPantallas.add(pnPantallaCarrito, PANTALLA_CARRITO);
			pnPantallas.add(pnPantallaFinal, PANTALLA_FINAL);
		}
		
		return pnPantallas;
	}
	
	private JMenuBar getBarraMenu() {
		if (MenuBar == null) {
			MenuBar = new JMenuBar();
			MenuBar.add(getMnJuego());
			MenuBar.add(getMnOpciones());
			MenuBar.add(getMnAyuda());
		}
		return MenuBar;
	}
	
	private JMenu getMnJuego() {
		if (mnJuego == null) {
			mnJuego = new JMenu("");
			mnJuego.setText( internacionalizar.getTexto("menu.juego") );
			
			mnJuego.add(getMiSalir());
		}
		return mnJuego;
	}
	
	private JMenuItem getMiSalir() {
		if (miSalir == null) {
			miSalir = new JMenuItem("");
			miSalir.setText( internacionalizar.getTexto("menu.salir") );
			
			miSalir.addActionListener( pAS );
		}
		return miSalir;
	}
	
	private JMenu getMnAyuda() {
		if (mnAyuda == null) {
			mnAyuda = new JMenu("");
			mnAyuda.setText( internacionalizar.getTexto("menu.ayuda") );
			
			mnAyuda.add(getMiAyuda());
			mnAyuda.add(getSeparator());
			mnAyuda.add(getMiAcercaDe());
		}
		return mnAyuda;
	}
	
	public JMenuItem getMiAyuda() {
		if (miAyuda == null) {
			miAyuda = new JMenuItem("");
			miAyuda.setText( internacionalizar.getTexto("menu.ayuda") );
		}
		return miAyuda;
	}
	
	private JSeparator getSeparator()
	{
		if ( separator == null )
		{
			separator = new JSeparator();
		}
		return separator;
	}
	
	private JMenuItem getMiAcercaDe() {
		if (miAcercaDe == null) {
			miAcercaDe = new JMenuItem("");
			miAcercaDe.setText( internacionalizar.getTexto("menu.acercade") );
		}
		return miAcercaDe;
	}
	
	private JMenu getMnOpciones() {
		if (mnOpciones == null) {
			mnOpciones = new JMenu("");
			mnOpciones.setText( internacionalizar.getTexto("menu.opciones") );
			mnOpciones.add(getMiEspanol());
			mnOpciones.add(getMiIngles());
			
			ButtonGroup groupOpciones = new ButtonGroup();
			
			groupOpciones.add( getMiEspanol() );
			groupOpciones.add( getMiIngles() );
		}
		return mnOpciones;
	}
	
	private JRadioButtonMenuItem getMiEspanol() {
		if (miEspanol == null) {
			miEspanol = new JRadioButtonMenuItem("");
			miEspanol.setSelected(true);
			miEspanol.setText( internacionalizar.getTexto("menu.espanol") );
			miEspanol.setActionCommand("es");
			
			miEspanol.addActionListener( pCI );
		}
		return miEspanol;
	}
	private JRadioButtonMenuItem getMiIngles() {
		if (miIngles == null) {
			miIngles = new JRadioButtonMenuItem("");
			miIngles.setText( internacionalizar.getTexto("menu.ingles") );
			miIngles.setActionCommand("en");
			
			miIngles.addActionListener( pCI );
		}
		return miIngles;
	}
}
