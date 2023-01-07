package uo.cpm.videogame.ui;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import uo.cpm.videogame.model.Premio;
import uo.cpm.videogame.service.Game;
import uo.cpm.videogame.service.GestionPremios;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import java.awt.FlowLayout;
import javax.swing.JRadioButton;
import javax.swing.JSeparator;
import javax.swing.JCheckBox;
import javax.swing.border.LineBorder;
import java.awt.Color;

public class VentanaPremios extends JPanel 
{
	private static final long serialVersionUID = 1L;

	private VentanaPrincipal vp;
	private Game game;
	private GestionPremios gestionPremios;
	
	private ProcesaAccionFiltroCategorias pAFC;
	private ProcesaAccionCarrito pAC;
	private ProcesaResizeInterfaz pRI;
	private ProcesaAccionRegalosDisponibles pARD;
	
	ButtonGroup groupFiltro;
	
	private final static String TODO = "todo";
	private final static String ACCESORIOS = "accesorios";
	private final static String CONSOLAS = "consolas";
	private final static String VIDEOJUEGOS = "videojuegos";
	
	private int anchoVentana;
	private String filtroActivo;
	
	private JPanel pnNorte;
	private JPanel pnSalir;
	private JButton btSalir;
	private JPanel pnPuntos;
	private JPanel pnCarrito;
	private JButton btCarrito;
	private JLabel lbPuntos;
	private JTextField txtPuntos;
	private JPanel pnCentro;
	private JPanel pnFiltro;
	private JScrollPane scPremios;
	private JPanel pnPremios;
	private JPanel pnFiltro2;
	private JPanel pnLabelFiltro;
	private JLabel lbFiltrar;
	private JLabel lbCategorias;
	private JPanel pnCategorias;
	private JRadioButton rdTodo;
	private JRadioButton rdAccesorios;
	private JRadioButton rdConsolas;
	private JRadioButton rdVideojuegos;
	private JSeparator spFiltro;
	private JCheckBox cbRegalosDisponibles;
	
	public VentanaPremios(VentanaPrincipal vp) 
	{
		this.vp = vp;
		this.game = vp.getGame();
		this.gestionPremios = vp.getGestionPremios();
		this.pAFC = new ProcesaAccionFiltroCategorias();
		this.pAC = new ProcesaAccionCarrito();
		this.pRI = new ProcesaResizeInterfaz();
		this.pARD = new ProcesaAccionRegalosDisponibles();
		
		anchoVentana = this.getWidth();
		
		groupFiltro = new ButtonGroup();
				
		setBackground(VentanaPrincipal.BACKGROUND);
		setLayout(new BorderLayout(0, 0));
		add(getPnNorte(), BorderLayout.NORTH);
		add(getPnCentro(), BorderLayout.CENTER);
		setBounds(100, 100, 780, 840);
		
		addComponentListener(pRI);
	}
	
	public void inicializar()
	{
		this.getBtCarrito().setText( "(" + gestionPremios.getNumeroPremios() + ")" );
		
		this.getRdTodo().setSelected(true);
		this.getCbRegalosDisponibles().setSelected(false);
		
		this.mostrarPremios(TODO, false);
	}
	
	class ProcesaAccionFiltroCategorias implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			JRadioButton b = (JRadioButton) e.getSource();
			
			filtrar( b.getActionCommand() );
		}
	}
	
	class ProcesaAccionCarrito implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			mostrarCarrito();
		}
	}
	
	class ProcesaResizeInterfaz extends ComponentAdapter
	{
		@Override
		public void componentResized(ComponentEvent e) 
		{
			redimensionarPremios();
		}
	}
	
	class ProcesaAccionRegalosDisponibles implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			mostrarSoloPremiosDisponibles();
		}
	}
	
	private void mostrarSoloPremiosDisponibles()
	{
		mostrarPremios( this.getCbRegalosDisponibles().isSelected() );
	}
	
	private void redimensionarPremios()
	{
		// Si está disminuyendo la pantalla redimensiono los premios
		if ( this.getWidth() < anchoVentana )
			mostrarPremios(false);
		
		anchoVentana = this.getWidth();
	}
	
	private void mostrarCarrito()
	{
		if ( gestionPremios.getCarrito().length == 0 )
		{
			JOptionPane.showMessageDialog(this, vp.getInternacionalizar().getTexto("carrito.vacio"),
					game.getNombreTienda(), JOptionPane.WARNING_MESSAGE, new ImageIcon(game.getIconoTienda()));
			return;
		}
			
		vp.mostrarPantallaCarrito();
		vp.getPnPantallaCarrito().mostrarPremios(); // Muestro todos los artículos del carrito
		vp.getPnPantallaCarrito().getPnPremios().validate();
		vp.getPnPantallaCarrito().getTxtPuntos().setText(String.format("%d", game.getPuntos())); // Muestro los puntos actuales del usuario
	}

	private void filtrar(String accion)
	{
		switch (accion) 
		{
			case TODO: 
			{
				mostrarPremios(TODO, this.getCbRegalosDisponibles().isSelected());
				break;
			}
			case ACCESORIOS: 
			{
				mostrarPremios(ACCESORIOS, this.getCbRegalosDisponibles().isSelected());
				break;
			}
			case CONSOLAS: 
			{
				mostrarPremios(CONSOLAS, this.getCbRegalosDisponibles().isSelected());
				break;
			}
			case VIDEOJUEGOS: 
			{
				mostrarPremios(VIDEOJUEGOS, this.getCbRegalosDisponibles().isSelected());
				break;
			}
		}
	}

	public GestionPremios getGestionPremios() {
		return gestionPremios;
	}

	private JPanel getPnNorte() {
		if (pnNorte == null) {
			pnNorte = new JPanel();
			pnNorte.setBorder(new EmptyBorder(20, 0, 0, 0));
			pnNorte.setLayout(new GridLayout(0, 3, 0, 0));
			pnNorte.setBackground(VentanaPrincipal.BACKGROUND);
			pnNorte.add(getPnSalir());
			pnNorte.add(getPnPuntos());
			pnNorte.add(getPnCarrito());
		}
		return pnNorte;
	}
	
	private JPanel getPnSalir() {
		if (pnSalir == null) {
			pnSalir = new JPanel();
			pnSalir.setBackground(VentanaPrincipal.BACKGROUND);
			pnSalir.add(getBtSalir());
		}
		return pnSalir;
	}
	public JButton getBtSalir() {
		if (btSalir == null) {
			btSalir = new JButton("");
			btSalir.setFont(new Font("Tahoma", Font.BOLD, vp.getH3()));
			btSalir.setText(vp.getInternacionalizar().getTexto("boton.salir"));
			btSalir.setMnemonic( vp.getInternacionalizar().getTexto("mn.premios.salir").charAt(0) );
			btSalir.setToolTipText( vp.getInternacionalizar().getTexto("tooltip.salir") );
			btSalir.setBackground(VentanaPrincipal.BACKGROUND_BOTONES);
			
			btSalir.addActionListener( vp.getProcesaAccionSalir() );
		}
		return btSalir;
	}
	private JPanel getPnPuntos() {
		if (pnPuntos == null) {
			pnPuntos = new JPanel();
			pnPuntos.setBackground(VentanaPrincipal.BACKGROUND);
			pnPuntos.add(getLbPuntos());
			pnPuntos.add(getTxtPuntos());
		}
		return pnPuntos;
	}
	
	public JLabel getLbPuntos() {
		if (lbPuntos == null) {
			lbPuntos = new JLabel("");
			lbPuntos.setForeground(new Color(255, 255, 255));
			lbPuntos.setFont(new Font("Tahoma", Font.BOLD, vp.getH3()));
			lbPuntos.setHorizontalAlignment(SwingConstants.CENTER);
			lbPuntos.setText(vp.getInternacionalizar().getTexto("premios.puntos"));
		}
		return lbPuntos;
	}
	
	public JTextField getTxtPuntos() {
		if (txtPuntos == null) {
			txtPuntos = new JTextField();
			txtPuntos.setForeground(new Color(255, 255, 255));
			txtPuntos.setFocusable(false);
			txtPuntos.setHorizontalAlignment(SwingConstants.CENTER);
			txtPuntos.setEditable(false);
			txtPuntos.setFont(new Font("Tahoma", Font.BOLD, vp.getH3()));
			txtPuntos.setColumns(10);
			txtPuntos.setText( String.format("%d", game.getPuntos()) );
			txtPuntos.setBackground(VentanaPrincipal.BACKGROUND);
		}
		return txtPuntos;
	}
	
	private JPanel getPnCarrito() {
		if (pnCarrito == null) {
			pnCarrito = new JPanel();
			pnCarrito.setBackground(VentanaPrincipal.BACKGROUND);
			pnCarrito.add(getBtCarrito());
		}
		return pnCarrito;
	}
	public JButton getBtCarrito() {
		if (btCarrito == null) {
			btCarrito = new JButton("");
			btCarrito.setIcon( vp.ajustarImagen(25, 25, "/img/carrito.png") );
			btCarrito.setFont(new Font("Tahoma", Font.BOLD, vp.getH3()));
			btCarrito.setText( "(" + gestionPremios.getNumeroPremios() + ")" );
			btCarrito.setToolTipText( vp.getInternacionalizar().getTexto("tooltip.carrito") );
			btCarrito.setBackground(VentanaPrincipal.BACKGROUND_BOTONES);
			
			btCarrito.addActionListener(pAC);
		}
		return btCarrito;
	}
	
	private JPanel getPnCentro() {
		if (pnCentro == null) {
			pnCentro = new JPanel();
			pnCentro.setBorder(new EmptyBorder(20, 0, 0, 0));
			pnCentro.setLayout(new BorderLayout(0, 0));
			pnCentro.setBackground(VentanaPrincipal.BACKGROUND);
			pnCentro.add(getPnFiltro(), BorderLayout.WEST);
			pnCentro.add(getScPremios());
		}
		return pnCentro;
	}
	private JPanel getPnFiltro() {
		if (pnFiltro == null) {
			pnFiltro = new JPanel();
			pnFiltro.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
			pnFiltro.setBackground(VentanaPrincipal.BACKGROUND);
			pnFiltro.add(getPnFiltro2());
		}
		return pnFiltro;
	}
	private JScrollPane getScPremios() {
		if (scPremios == null) {
			scPremios = new JScrollPane();
			scPremios.setBorder(null);
			scPremios.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
			scPremios.setViewportView(getPnPremios());
			scPremios.setBackground(VentanaPrincipal.BACKGROUND);
		}
		return scPremios;
	}
	
	private JPanel getPnPremios() {
		if (pnPremios == null) {
			pnPremios = new JPanel();
			pnPremios.setBorder(new EmptyBorder(10, 10, 10, 10));
			pnPremios.setBackground(VentanaPrincipal.BACKGROUND);
			
			mostrarPremios(TODO, false);
		}
		return pnPremios;
	}
	
	private void mostrarPremios(String filtro, boolean soloDisponibles)
	{
		filtroActivo = filtro;
		
		this.getPnPremios().removeAll();
		this.getPnPremios().repaint();
		
		Premio[] listaPremios = this.getListaPremios(filtro, soloDisponibles);
		
		getPnPremios().setLayout(new GridLayout(0, 2, 10, 10));
		
		for ( int i = 0; i < listaPremios.length; i++ )
			this.getPnPremios().add( new PanelPremio(vp, listaPremios[i], gestionPremios) );
		
		// Mantengo el formato del grid
		if ( listaPremios.length > 0 && listaPremios.length < 5 )
		{
			this.getPnPremios().add( new PanelPremio(vp, listaPremios[0], gestionPremios) ).setVisible(false);
			this.getPnPremios().add( new PanelPremio(vp, listaPremios[0], gestionPremios) ).setVisible(false);
		}
		
		this.getPnPremios().validate();
	}
	
	public void mostrarPremios(boolean soloDisponibles)
	{
		mostrarPremios(filtroActivo, soloDisponibles);
	}
	
	private Premio[] getListaPremios(String filtro, boolean soloDisponibles)
	{
		if ( filtro.equals(TODO) )
			return gestionPremios.getListaPremios(soloDisponibles, game.getPuntos());
		
		if ( filtro.equals(ACCESORIOS) )
			return gestionPremios.getAccesorios(soloDisponibles, game.getPuntos());
		
		if ( filtro.equals(CONSOLAS) )
			return gestionPremios.getConsolas(soloDisponibles, game.getPuntos());
		
		if ( filtro.equals(VIDEOJUEGOS) )
			return gestionPremios.getVideojuegos(soloDisponibles, game.getPuntos());
		
		return null;
	}
	
	private JPanel getPnFiltro2() {
		if (pnFiltro2 == null) {
			pnFiltro2 = new JPanel();
			pnFiltro2.setBorder(new LineBorder(new Color(0, 0, 0)));
			pnFiltro2.setLayout(new BorderLayout(0, 0));
			pnFiltro2.setBackground(VentanaPrincipal.BACKGROUND);
			pnFiltro2.add(getPnLabelFiltro(), BorderLayout.NORTH);
			pnFiltro2.add(getPnCategorias());
		}
		return pnFiltro2;
	}
	private JPanel getPnLabelFiltro() {
		if (pnLabelFiltro == null) {
			pnLabelFiltro = new JPanel();
			pnLabelFiltro.setBorder(new EmptyBorder(5, 5, 5, 5));
			pnLabelFiltro.setLayout(new GridLayout(0, 1, 0, 0));
			pnLabelFiltro.setBackground(VentanaPrincipal.BACKGROUND);
			pnLabelFiltro.add(getLbFiltrar());
			pnLabelFiltro.add(getLbCategorias());
		}
		return pnLabelFiltro;
	}
	
	public JLabel getLbFiltrar() {
		if (lbFiltrar == null) {
			lbFiltrar = new JLabel("");
			lbFiltrar.setForeground(new Color(255, 255, 255));
			lbFiltrar.setText( vp.getInternacionalizar().getTexto("premios.filtrar") );
			lbFiltrar.setFont(new Font("Tahoma", Font.BOLD, vp.getH2()));
		}
		return lbFiltrar;
	}
	
	public JLabel getLbCategorias() {
		if (lbCategorias == null) {
			lbCategorias = new JLabel("");
			lbCategorias.setForeground(new Color(255, 255, 255));
			lbCategorias.setText( vp.getInternacionalizar().getTexto("premios.categorias") );
			lbCategorias.setFont(new Font("Tahoma", Font.BOLD, vp.getH3()));
		}
		return lbCategorias;
	}
	
	private JPanel getPnCategorias() {
		if (pnCategorias == null) {
			pnCategorias = new JPanel();
			pnCategorias.setBorder(new EmptyBorder(5, 5, 5, 5));
			pnCategorias.setLayout(new GridLayout(6, 1, 0, 0));
			pnCategorias.setBackground(VentanaPrincipal.BACKGROUND);
			
			pnCategorias.add(getRdTodo());
			pnCategorias.add(getRdAccesorios());
			pnCategorias.add(getRdConsolas());
			pnCategorias.add(getRdVideojuegos());
			pnCategorias.add(getSpFiltro());
			pnCategorias.add(getCbRegalosDisponibles());
			
			// Creo un grupo para seleccionar sólo uno a la vez
			groupFiltro.add(getRdTodo());
			groupFiltro.add(getRdAccesorios());
			groupFiltro.add(getRdConsolas());
			groupFiltro.add(getRdVideojuegos());
		}
		return pnCategorias;
	}
	public JRadioButton getRdTodo() {
		if (rdTodo == null) {
			rdTodo = new JRadioButton("");
			rdTodo.setForeground(new Color(255, 255, 255));
			rdTodo.setSelected(true);
			rdTodo.setText( vp.getInternacionalizar().getTexto("premios.todo") );
			rdTodo.setActionCommand(TODO);
			rdTodo.setMnemonic( vp.getInternacionalizar().getTexto("mn.premios.todo").charAt(0) );
			
			rdTodo.addActionListener(pAFC);
		}
		return rdTodo;
	}
	public JRadioButton getRdAccesorios() {
		if (rdAccesorios == null) {
			rdAccesorios = new JRadioButton("");
			rdAccesorios.setForeground(new Color(255, 255, 255));
			rdAccesorios.setText( vp.getInternacionalizar().getTexto("premios.accesorios") );
			rdAccesorios.setActionCommand(ACCESORIOS);
			rdAccesorios.setMnemonic( vp.getInternacionalizar().getTexto("mn.premios.accesorios").charAt(0) );
			
			rdAccesorios.addActionListener(pAFC);
		}
		return rdAccesorios;
	}
	public JRadioButton getRdConsolas() {
		if (rdConsolas == null) {
			rdConsolas = new JRadioButton("");
			rdConsolas.setForeground(new Color(255, 255, 255));
			rdConsolas.setText( vp.getInternacionalizar().getTexto("premios.consolas") );
			rdConsolas.setActionCommand(CONSOLAS);
			rdConsolas.setMnemonic( vp.getInternacionalizar().getTexto("mn.premios.consolas").charAt(0) );
			
			rdConsolas.addActionListener(pAFC);
		}
		return rdConsolas;
	}
	public JRadioButton getRdVideojuegos() {
		if (rdVideojuegos == null) {
			rdVideojuegos = new JRadioButton("");
			rdVideojuegos.setForeground(new Color(255, 255, 255));
			rdVideojuegos.setText( vp.getInternacionalizar().getTexto("premios.videojuegos") );
			rdVideojuegos.setActionCommand(VIDEOJUEGOS);
			rdVideojuegos.setMnemonic( vp.getInternacionalizar().getTexto("mn.premios.videojuegos").charAt(0) );
			
			rdVideojuegos.addActionListener(pAFC);
		}
		return rdVideojuegos;
	}
	private JSeparator getSpFiltro() {
		if (spFiltro == null) {
			spFiltro = new JSeparator();
			spFiltro.setForeground(new Color(255, 255, 255));
		}
		return spFiltro;
	}
	public JCheckBox getCbRegalosDisponibles() {
		if (cbRegalosDisponibles == null) {
			cbRegalosDisponibles = new JCheckBox("");
			cbRegalosDisponibles.setForeground(new Color(255, 255, 255));
			cbRegalosDisponibles.setText( vp.getInternacionalizar().getTexto("premios.regalosdisponibles") );
			cbRegalosDisponibles.setMnemonic( vp.getInternacionalizar().getTexto("mn.premios.regalosdisponibles").charAt(0) );
			cbRegalosDisponibles.setToolTipText( vp.getInternacionalizar().getTexto("tooltip.carrito.regalos") );
			
			cbRegalosDisponibles.addActionListener( pARD );
		}
		return cbRegalosDisponibles;
	}
}
