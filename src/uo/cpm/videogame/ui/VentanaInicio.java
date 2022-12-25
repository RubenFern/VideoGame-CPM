package uo.cpm.videogame.ui;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
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
	private ProcesaAccionBotonSiguiente procesaBotonSiguiente;
	private ProcesaResizeInterfaz procesaResizeInterfaz;
	private ProcesaPulsacionNumeroTicket procesaPulsacionNumeroTicket;
	
	private int h1;
	private int h2;
	private int texto;
	
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
	private JTextField txtoCodigoTienda;
	private JButton btSiguiente;
	private JLabel lbBienvenido;
	private JLabel lbPedirTicket;
		
	public VentanaInicio(VentanaPrincipal vp) 
	{
		this.vp = vp;
		
		procesaResizeInterfaz = new ProcesaResizeInterfaz();
		procesaPulsacionNumeroTicket = new ProcesaPulsacionNumeroTicket();
		procesaBotonSiguiente = new ProcesaAccionBotonSiguiente();
		
		// Asigno el tamaño por defecto de los textos
		tamañoTextos(40, 20, 15);
		
		setBounds(100, 100, 1100, 700);
		setLayout(new BorderLayout(0, 0));
		
		add(getPnNorteInicio(), BorderLayout.NORTH);
		add(getPnCentroInicio(), BorderLayout.CENTER);
		add(getPnBotonesInicio(), BorderLayout.SOUTH);
		
		// Escalono la pantalla
		addComponentListener( procesaResizeInterfaz );
	}
	
	class ProcesaAccionBotonSiguiente implements ActionListener
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
			
			// Si no es un dígito evito que se añada en el campo de texto
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
		String codigo = this.getTxtoCodigoTienda().getText();
		String numero = this.getTxtNumeroTicket().getText();
		
		if ( codigo.equals("") )
		{
			JOptionPane.showMessageDialog(this, vp.getInternacionalizar().getTexto("error.codigoTicket"), vp.getGame().getNombreTienda(), JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		if ( numero.equals("") )
		{
			JOptionPane.showMessageDialog(this, vp.getInternacionalizar().getTexto("error.numeroTicket"), vp.getGame().getNombreTienda(), JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		// Si el ticket es válido muestro la pantalla del juego
		if ( vp.getGame().ticketValido(codigo, Integer.parseInt( numero )) )
			vp.mostrarPantallaJuego();
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
			lbNumeroTicket.setText( vp.getInternacionalizar().getTexto("inicio.pedirTicket") );
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
			lbCodigoTienda.setText( vp.getInternacionalizar().getTexto("inicio.pedirCodigo") );
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
	
	private JLabel getLbBienvenido() {
		if (lbBienvenido == null) {
			lbBienvenido = new JLabel("");
			lbBienvenido.setBorder(new EmptyBorder(20, 0, 0, 0));
			lbBienvenido.setHorizontalAlignment(SwingConstants.CENTER);
			lbBienvenido.setFont(new Font("Tahoma", Font.BOLD, h1));
			lbBienvenido.setText( vp.getInternacionalizar().getTexto("inicio.bienvendida") );
		}
		return lbBienvenido;
	}
	private JLabel getLbPedirTicket() {
		if (lbPedirTicket == null) {
			lbPedirTicket = new JLabel("");
			lbPedirTicket.setFont(new Font("Tahoma", Font.PLAIN, h2));
			lbPedirTicket.setHorizontalAlignment(SwingConstants.CENTER);
			lbPedirTicket.setText( vp.getInternacionalizar().getTexto("inicio.informacionJuego") );
		}
		return lbPedirTicket;
	}
	
	private JButton getBtSiguiente() {
		if (btSiguiente == null) {
			btSiguiente = new JButton("");
			btSiguiente.setFont(new Font("Tahoma", Font.PLAIN, texto));
			btSiguiente.setBackground(new Color(0, 204, 255));
			btSiguiente.setBorder(new EmptyBorder(5, 10, 5, 10));
			btSiguiente.setActionCommand(VentanaPrincipal.PANTALLA_JUEGO);
			btSiguiente.setFocusPainted(false); // Elimina la línea naranja			
			btSiguiente.setText( vp.getInternacionalizar().getTexto("boton.jugar") );
			
			btSiguiente.addActionListener( procesaBotonSiguiente );
		}
		return btSiguiente;
	}
	
	private void tamañoTextos(int h1, int h2, int texto)
	{
		this.h1 = h1;
		this.h2 = h2;
		this.texto = texto;
	}
	
	private void ajustaTextos()
	{
		if ( this.getWidth() < 980 )
			tamañoTextos(32, 18, 15);
		else
			tamañoTextos(40, 20, 15);
			
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
}
