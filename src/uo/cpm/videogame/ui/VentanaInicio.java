package uo.cpm.videogame.ui;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import uo.cpm.videogame.model.Ticket;
import uo.cpm.videogame.service.Game;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class VentanaInicio extends JPanel 
{
	private static final long serialVersionUID = 1L;

	private VentanaPrincipal vp;
	private ProcesaAccionBotonJugar pBJ;
	private ProcesaResizeInterfaz pRI;
	private ProcesaPulsacionNumeroTicket pPNT;
	
	private Game game;
	
	private JPanel pnNorteInicio;
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
	private JTextField txtCodigoTienda;
	private JButton btJugar;
	private JLabel lbBienvenido;
	private JLabel lbPedirTicket;
	private JPanel pnEsteInicio;
	private JButton btEspanol;
	private JButton btIngles;
	private JPanel pnEspanol;
	private JPanel pnIngles;
		
	public VentanaInicio(VentanaPrincipal vp) 
	{
		this.vp = vp;
		this.game = vp.getGame();
		
		pRI = new ProcesaResizeInterfaz();
		pPNT = new ProcesaPulsacionNumeroTicket();
		pBJ = new ProcesaAccionBotonJugar();
		
		// Asigno el tamaño por defecto de los textos
		vp.tamanoTextos(40, 20, 17, 15);
		
		setBackground(VentanaPrincipal.BACKGROUND);
		setLayout(new BorderLayout(0, 0));
		
		add(getPnNorteInicio(), BorderLayout.NORTH);
		add(getPnCentroInicio(), BorderLayout.CENTER);
		add(getPnBotonesInicio(), BorderLayout.SOUTH);
		
		// Escalono la pantalla
		addComponentListener( pRI );
	}
	
	public void inicializar()
	{
		this.getTxtNumeroTicket().setText("");
		this.getTxtCodigoTienda().setText("");
	}
	
	class ProcesaAccionBotonJugar implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			mostrarPantallaJuego();
		}
	}
	
	class ProcesaPulsacionNumeroTicket extends KeyAdapter
	{
		@Override
		public void keyTyped(KeyEvent e) 
		{
			char tecla = e.getKeyChar();
			
			// Si no es un dígito evito que se a�ada en el campo de texto
			if ( !Character.isDigit(tecla) )
				e.consume();
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
	
	private void mostrarPantallaJuego() 
	{
		String codigo = this.getTxtCodigoTienda().getText();
		String numero = this.getTxtNumeroTicket().getText();
		
		if ( codigo.equals("") )
		{
			JOptionPane.showMessageDialog(this, vp.getInternacionalizar().getTexto("error.codigoTicketVacio"),
					game.getNombreTienda(), JOptionPane.INFORMATION_MESSAGE, new ImageIcon(game.getIconoTienda()));
			return;
		}
		
		if ( numero.equals("") )
		{
			JOptionPane.showMessageDialog(this, vp.getInternacionalizar().getTexto("error.numeroTicketVacio"),
					game.getNombreTienda(), JOptionPane.INFORMATION_MESSAGE, new ImageIcon(game.getIconoTienda()));
			return;
		}
		
		// Si no es válido detengo la ejecución
		if ( !comprobarTicket(codigo, Integer.parseInt(numero)) )
			return;
			
		// Muestro los datos de la partida
		vp.getPnPantallaJuego().actualizarPartida();
		
		// Si el ticket es válido muestro la pantalla del juego	
		vp.mostrarPantallaJuego();
	}
	
	private boolean comprobarTicket(String codigo, int numero)
	{
		Ticket ticket = game.ticketValido(codigo, numero);
		
		// El ticket no existe
		if ( ticket == null )
		{
			JOptionPane.showMessageDialog(this, vp.getInternacionalizar().getTexto("error.ticketIncorrecto"), game.getNombreTienda(), JOptionPane.ERROR_MESSAGE, new ImageIcon( game.getIconoTienda() ));
			return false;
		}
		
		// Inferior a 20 euros
		if ( ticket.getImporte() < 20 )
		{
			JOptionPane.showMessageDialog(this, vp.getInternacionalizar().getTexto("error.ticketImporteInferior"), game.getNombreTienda(), JOptionPane.ERROR_MESSAGE, new ImageIcon( game.getIconoTienda() ));
			return false;
		}
		
		// Código de tienda diferente
		if ( !codigo.equals(game.getCodigoTienda()) )
		{
			JOptionPane.showMessageDialog(this, vp.getInternacionalizar().getTexto("error.codigoTiendaIncorrecto"), game.getNombreTienda(), JOptionPane.ERROR_MESSAGE, new ImageIcon( game.getIconoTienda() ));
			return false;
		}
		
		// Elimino el ticket 
		game.eliminarTicket( ticket );
		
		return true;
	}
	
	private JPanel getPnNorteInicio() {
		if (pnNorteInicio == null) {
			pnNorteInicio = new JPanel();
			pnNorteInicio.setLayout(new GridLayout(3, 1, 0, 0));
			pnNorteInicio.setBackground(VentanaPrincipal.BACKGROUND);
			pnNorteInicio.add( getPnIdioma() );
			pnNorteInicio.add(getLbBienvenido());
			pnNorteInicio.add(getLbPedirTicket());
		}
		return pnNorteInicio;
	}
	
	private JPanel getPnCentroInicio() {
		if (pnCentroInicio == null) {
			pnCentroInicio = new JPanel();
			pnCentroInicio.setAlignmentX(Component.LEFT_ALIGNMENT);
			pnCentroInicio.setLayout(new BorderLayout(0, 0));
			pnCentroInicio.setBackground(VentanaPrincipal.BACKGROUND);
			pnCentroInicio.add(getPnCampos());
			pnCentroInicio.add(getPnImagenTicket(), BorderLayout.SOUTH);
		}
		return pnCentroInicio;
	}
	
	private JPanel getPnBotonesInicio() {
		if (pnBotonesInicio == null) {
			pnBotonesInicio = new JPanel();
			pnBotonesInicio.setBorder(new EmptyBorder(10, 0, 20, 0));
			pnBotonesInicio.setBackground(VentanaPrincipal.BACKGROUND);
			pnBotonesInicio.add( getBtJugar() );
		}
		return pnBotonesInicio;
	}
	
	private JPanel getPnCampos() {
		if (pnCampos == null) {
			pnCampos = new JPanel();
			pnCampos.setLayout(new FlowLayout(FlowLayout.CENTER, 80, 15));
			pnCampos.setBackground(VentanaPrincipal.BACKGROUND);
			pnCampos.add(getPnTicket());
			pnCampos.add(getPnCodigo());
		}
		return pnCampos;
	}
	private JPanel getPnImagenTicket() {
		if (pnImagenTicket == null) {
			pnImagenTicket = new JPanel();
			pnImagenTicket.setBackground(VentanaPrincipal.BACKGROUND);
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
			
			lbImagenTicket.setIcon( vp.ajustarImagen(lbImagenTicket, "/img/ticket.png") );
		}
		return lbImagenTicket;
	}
	
	private JPanel getPnTicket() {
		if (pnTicket == null) {
			pnTicket = new JPanel();
			pnTicket.setLayout(new GridLayout(2, 1, 0, 0));
			pnTicket.setBackground(VentanaPrincipal.BACKGROUND);
			pnTicket.add(getLbNumeroTicket());
			pnTicket.add(getTxtNumeroTicket());
		}
		return pnTicket;
	}
	
	private JPanel getPnCodigo() {
		if (pnCodigo == null) {
			pnCodigo = new JPanel();
			pnCodigo.setLayout(new GridLayout(2, 1, 0, 0));
			pnCodigo.setBackground(VentanaPrincipal.BACKGROUND);
			pnCodigo.add(getLbCodigoTienda());
			pnCodigo.add(getTxtCodigoTienda());
		}
		return pnCodigo;
	}
	
	public JLabel getLbNumeroTicket() {
		if (lbNumeroTicket == null) {
			lbNumeroTicket = new JLabel("");
			lbNumeroTicket.setForeground(new Color(255, 255, 255));
			lbNumeroTicket.setLabelFor(getTxtNumeroTicket());
			lbNumeroTicket.setBorder(new EmptyBorder(10, 0, 5, 30));
			lbNumeroTicket.setFont(new Font("Tahoma", Font.PLAIN, vp.getTexto()));
			lbNumeroTicket.setText( vp.getInternacionalizar().getTexto("inicio.pedirTicket") );
			
			lbNumeroTicket.setDisplayedMnemonic( vp.getInternacionalizar().getTexto("mn.inicio.numeroticket").charAt(0) );
		}
		return lbNumeroTicket;
	}
	
	private JTextField getTxtNumeroTicket() {
		if (txtNumeroTicket == null) {
			txtNumeroTicket = new JTextField();
			txtNumeroTicket.setForeground(new Color(0, 0, 0));
			
			txtNumeroTicket.addKeyListener( pPNT );
			
			txtNumeroTicket.setBounds(new Rectangle(0, 0, 300, 50));
			txtNumeroTicket.setColumns(10);
			txtNumeroTicket.setFont(new Font("Tahoma", Font.PLAIN, vp.getTexto()));
		}
		return txtNumeroTicket;
	}
	
	public JLabel getLbCodigoTienda() {
		if (lbCodigoTienda == null) {
			lbCodigoTienda = new JLabel("");
			lbCodigoTienda.setForeground(new Color(255, 255, 255));
			lbCodigoTienda.setLabelFor(getTxtCodigoTienda());
			lbCodigoTienda.setFont(new Font("Tahoma", Font.PLAIN, vp.getTexto()));
			lbCodigoTienda.setBorder(new EmptyBorder(10, 0, 5, 30));
			lbCodigoTienda.setText( vp.getInternacionalizar().getTexto("inicio.pedirCodigo") );
			lbCodigoTienda.setDisplayedMnemonic( vp.getInternacionalizar().getTexto("mn.inicio.codigotienda").charAt(0) );
		}
		return lbCodigoTienda;
	}
	private JTextField getTxtCodigoTienda() {
		if (txtCodigoTienda == null) {
			txtCodigoTienda = new JTextField();
			txtCodigoTienda.setFont(new Font("Tahoma", Font.PLAIN, vp.getTexto()));
			txtCodigoTienda.setColumns(10);
		}
		return txtCodigoTienda;
	}
	
	public JLabel getLbBienvenido() {
		if (lbBienvenido == null) {
			lbBienvenido = new JLabel("");
			lbBienvenido.setForeground(new Color(255, 255, 255));
			lbBienvenido.setBorder(new EmptyBorder(20, 0, 0, 0));
			lbBienvenido.setHorizontalAlignment(SwingConstants.CENTER);
			lbBienvenido.setFont(new Font("Tahoma", Font.BOLD, vp.getH1()));
			lbBienvenido.setText( vp.getInternacionalizar().getTexto("inicio.bienvendida") );
		}
		return lbBienvenido;
	}
	public JLabel getLbPedirTicket() {
		if (lbPedirTicket == null) {
			lbPedirTicket = new JLabel("");
			lbPedirTicket.setForeground(new Color(255, 255, 255));
			lbPedirTicket.setFont(new Font("Tahoma", Font.PLAIN, vp.getH2()));
			lbPedirTicket.setHorizontalAlignment(SwingConstants.CENTER);
			lbPedirTicket.setText( vp.getInternacionalizar().getTexto("inicio.informacionJuego") );
		}
		return lbPedirTicket;
	}
	
	public JButton getBtJugar() {
		if (btJugar == null) {
			btJugar = new JButton("");
			btJugar.setForeground(new Color(0, 0, 0));
			btJugar.setFont(new Font("Tahoma", Font.BOLD, vp.getH3()));
			btJugar.setBackground(VentanaPrincipal.BACKGROUND_BOTONES);
			btJugar.setBorder(new EmptyBorder(5, 10, 5, 10));
			btJugar.setActionCommand(VentanaPrincipal.PANTALLA_JUEGO);			
			btJugar.setText( vp.getInternacionalizar().getTexto("boton.jugar") );
			btJugar.setMnemonic( vp.getInternacionalizar().getTexto("mn.inicio.jugar").charAt(0) );
			
			btJugar.addActionListener( pBJ );
		}
		return btJugar;
	}
	
	private void ajustaTextos()
	{
		getLbBienvenido().setFont(new Font("Tahoma", Font.BOLD, vp.getH1()));
		getLbPedirTicket().setFont(new Font("Tahoma", Font.PLAIN, vp.getH2()));
		
		vp.tamanoTextos(40, 20, 17, 15);
		getLbBienvenido().setBorder(new EmptyBorder(10, 0, 0, 0));
		
		if ( this.getWidth() < 980 )
			vp.tamanoTextos(32, 18, 16, 15);	
			
		// Aumento el margen superior cuando está en pantalla completa
		if ( this.getWidth() > 1300 && this.getHeight() > 800 )
			getLbBienvenido().setBorder(new EmptyBorder(30, 0, 0, 0));
	}
	
	private void ajustarImagenTicket()
	{
		// TODO
	}
	
	private JPanel getPnIdioma() {
		if (pnEsteInicio == null) {
			pnEsteInicio = new JPanel();
			pnEsteInicio.setBackground(VentanaPrincipal.BACKGROUND);
			pnEsteInicio.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));
			pnEsteInicio.add(getPnEspanol());
			pnEsteInicio.add(getPnIngles());
		}
		return pnEsteInicio;
	}
	
	private JPanel getPnEspanol() {
		if (pnEspanol == null) {
			pnEspanol = new JPanel();
			pnEspanol.setBackground(VentanaPrincipal.BACKGROUND);
			pnEspanol.add(getBtEspanol());
		}
		return pnEspanol;
	}
	
	public JButton getBtEspanol() {
		if (btEspanol == null) {
			btEspanol = new JButton("");
			
			btEspanol.setBounds( new Rectangle(50, 30) );
			btEspanol.setIcon( vp.ajustarImagen(btEspanol, "/img/espana.png") );
			btEspanol.setActionCommand("es");
			btEspanol.setToolTipText( vp.getInternacionalizar().getTexto("tooltip.inicio.espanol") );
			btEspanol.setContentAreaFilled(false);
			
			btEspanol.addActionListener( vp.getActionCambiarIdioma() );
		}
		return btEspanol;
	}
	
	private JPanel getPnIngles() {
		if (pnIngles == null) {
			pnIngles = new JPanel();
			pnIngles.setBackground(VentanaPrincipal.BACKGROUND);
			pnIngles.add(getBtIngles());
		}
		return pnIngles;
	}
	
	public JButton getBtIngles() {
		if (btIngles == null) {
			btIngles = new JButton("");
			
			btIngles.setBounds( new Rectangle(50, 30) );
			btIngles.setIcon( vp.ajustarImagen(btIngles, "/img/inglaterra.png") );
			btIngles.setActionCommand("en");
			btIngles.setToolTipText( vp.getInternacionalizar().getTexto("tooltip.inicio.ingles") );
			btIngles.setContentAreaFilled(false);
			
			btIngles.addActionListener( vp.getActionCambiarIdioma() );
		}
		return btIngles;
	}
}
