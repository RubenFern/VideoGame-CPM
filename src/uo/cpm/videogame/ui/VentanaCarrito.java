package uo.cpm.videogame.ui;

import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import uo.cpm.videogame.model.Premio;
import uo.cpm.videogame.service.Game;
import uo.cpm.videogame.service.GestionPremios;
import javax.swing.border.LineBorder;
import java.awt.Color;

public class VentanaCarrito extends JPanel 
{
	private static final long serialVersionUID = 1L;
	
	public final static String TODO = "todo";
	public final static String ACCESORIOS = "accesorios";
	public final static String CONSOLAS = "consolas";
	public final static String VIDEOJUEGOS = "videojuegos";

	private VentanaPrincipal vp;
	private Game game;
	private GestionPremios gestionPremios;
	
	private ProcesaAccionFiltroCategorias pAFC;
	private ProcesaBotonAtras pBA;
	private ProcesaConfirmarPremios pCP;
	private ProcesaResizeInterfaz pRI;
	
	ButtonGroup groupFiltro;
	
	private int anchoVentana;
	private String filtroActivo;
	
	private JPanel pnNorte;
	private JPanel pnCentro;
	private JPanel pnAtras;
	private JPanel pnPuntos;
	private JPanel pnConfirmar;
	private JButton btAtras;
	private JLabel lbPuntos;
	private JTextField txtPuntos;
	private JButton btConfirmar;
	private JLabel lbPremioSeleccionados;
	private JPanel pnFiltro;
	private JPanel pnFiltro2;
	private JPanel pnLabelFiltro;
	private JPanel pnCategorias;
	private JLabel lbFiltrar;
	private JLabel lbCategorias;
	private JRadioButton rdTodo;
	private JRadioButton rdAccesorios;
	private JRadioButton rdConsolas;
	private JRadioButton rdVideojuegos;
	private JScrollPane scPremios;
	private JPanel pnPremios;
	
	public VentanaCarrito(VentanaPrincipal vp) 
	{
		this.vp = vp;
		this.game = vp.getGame();
		this.gestionPremios = vp.getGestionPremios();
		this.pAFC = new ProcesaAccionFiltroCategorias();
		this.pBA = new ProcesaBotonAtras();
		this.pCP = new ProcesaConfirmarPremios();
		this.pRI = new ProcesaResizeInterfaz();
		
		groupFiltro = new ButtonGroup();
				
		setLayout(new BorderLayout(0, 0));
		add(getPnNorte(), BorderLayout.NORTH);
		add(getPnCentro(), BorderLayout.CENTER);
		
		addComponentListener(pRI);
	}
	
	public void inicializar()
	{
		this.getRdTodo().setSelected(true);
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
	
	class ProcesaBotonAtras implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			vp.mostrarPantallaPremios();
		}
	}
	
	class ProcesaConfirmarPremios implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			vp.mostrarPantallaFinal();
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

	private void redimensionarPremios()
	{
		// Si está disminuyendo la pantalla redimensiono los premios
		if ( this.getWidth() < anchoVentana )
			mostrarPremios();
		
		anchoVentana = this.getWidth();
	}
	
	private void filtrar(String accion)
	{
		switch (accion) 
		{
			case TODO: 
			{
				mostrarPremios(TODO);
				break;
			}
			case ACCESORIOS: 
			{
				mostrarPremios(ACCESORIOS);
				break;
			}
			case CONSOLAS: 
			{
				mostrarPremios(CONSOLAS);
				break;
			}
			case VIDEOJUEGOS: 
			{
				mostrarPremios(VIDEOJUEGOS);
				break;
			}
		}
	}

	private JPanel getPnNorte() {
		if (pnNorte == null) {
			pnNorte = new JPanel();
			pnNorte.setBorder(new EmptyBorder(20, 0, 0, 0));
			pnNorte.setLayout(new GridLayout(0, 3, 0, 0));
			pnNorte.add(getPnAtras());
			pnNorte.add(getPnPuntos());
			pnNorte.add(getPnConfirmar());
		}
		return pnNorte;
	}
	private JPanel getPnCentro() {
		if (pnCentro == null) {
			pnCentro = new JPanel();
			pnCentro.setLayout(new BorderLayout(0, 0));
			pnCentro.add(getLbPremioSeleccionados(), BorderLayout.NORTH);
			pnCentro.add(getPnFiltro(), BorderLayout.WEST);
			pnCentro.add(getScPremios(), BorderLayout.CENTER);
		}
		return pnCentro;
	}
	
	private JPanel getPnAtras() {
		if (pnAtras == null) {
			pnAtras = new JPanel();
			pnAtras.add(getBtAtras());
		}
		return pnAtras;
	}
	
	private JPanel getPnPuntos() {
		if (pnPuntos == null) {
			pnPuntos = new JPanel();
			pnPuntos.add(getLbPuntos());
			pnPuntos.add(getTxtPuntos());
		}
		return pnPuntos;
	}
	private JPanel getPnConfirmar() {
		if (pnConfirmar == null) {
			pnConfirmar = new JPanel();
			pnConfirmar.add(getBtConfirmar());
		}
		return pnConfirmar;
	}
	
	public JButton getBtAtras() {
		if (btAtras == null) {
			btAtras = new JButton("");
			btAtras.setFont(new Font("Tahoma", Font.BOLD, vp.getH3()));
			btAtras.setText(vp.getInternacionalizar().getTexto("boton.atras"));
			
			btAtras.addActionListener( pBA );
		}
		return btAtras;
	}
	
	public JLabel getLbPuntos() {
		if (lbPuntos == null) {
			lbPuntos = new JLabel("");
			lbPuntos.setFont(new Font("Tahoma", Font.BOLD, vp.getH3()));
			lbPuntos.setHorizontalAlignment(SwingConstants.CENTER);
			lbPuntos.setText(vp.getInternacionalizar().getTexto("premios.puntos"));
		}
		return lbPuntos;
	}
	
	public JTextField getTxtPuntos() {
		if (txtPuntos == null) {
			txtPuntos = new JTextField();
			txtPuntos.setEditable(false);
			txtPuntos.setFont(new Font("Tahoma", Font.BOLD, vp.getH3()));
			txtPuntos.setHorizontalAlignment(SwingConstants.CENTER);
			txtPuntos.setText( String.format("%d", game.getPuntos()) );
			txtPuntos.setColumns(10);
		}
		return txtPuntos;
	}
	
	public JButton getBtConfirmar() {
		if (btConfirmar == null) {
			btConfirmar = new JButton("");
			btConfirmar.setFont(new Font("Tahoma", Font.BOLD, vp.getH3()));
			btConfirmar.setText( vp.getInternacionalizar().getTexto("boton.confirmar") );
			
			btConfirmar.addActionListener( pCP );
		}
		return btConfirmar;
	}
	public JLabel getLbPremioSeleccionados() {
		if (lbPremioSeleccionados == null) {
			lbPremioSeleccionados = new JLabel("");
			lbPremioSeleccionados.setText( vp.getInternacionalizar().getTexto("carrito.titulo") );
			lbPremioSeleccionados.setFont(new Font("Tahoma", Font.BOLD, vp.getH1()));
			lbPremioSeleccionados.setHorizontalAlignment(SwingConstants.CENTER);
		}
		return lbPremioSeleccionados;
	}
	private JPanel getPnFiltro() {
		if (pnFiltro == null) {
			pnFiltro = new JPanel();
			pnFiltro.add(getPnFiltro2());
		}
		return pnFiltro;
	}
	private JPanel getPnFiltro2() {
		if (pnFiltro2 == null) {
			pnFiltro2 = new JPanel();
			pnFiltro2.setBorder(new LineBorder(new Color(0, 0, 0)));
			pnFiltro2.setLayout(new BorderLayout(0, 0));
			pnFiltro2.add(getPnLabelFiltro(), BorderLayout.NORTH);
			pnFiltro2.add(getPnCategorias());
		}
		return pnFiltro2;
	}
	
	
	private JPanel getPnLabelFiltro() {
		if (pnLabelFiltro == null) {
			pnLabelFiltro = new JPanel();
			pnLabelFiltro.setBorder(new EmptyBorder(5, 5, 5, 5));
			pnLabelFiltro.setLayout(new GridLayout(2, 1, 0, 0));
			pnLabelFiltro.add(getLbFiltrar());
			pnLabelFiltro.add(getLbCategorias());
		}
		return pnLabelFiltro;
	}
	
	public JLabel getLbFiltrar() {
		if (lbFiltrar == null) {
			lbFiltrar = new JLabel("");
			lbFiltrar.setText( vp.getInternacionalizar().getTexto("premios.filtrar") );
			lbFiltrar.setFont(new Font("Tahoma", Font.BOLD, vp.getH2()));
		}
		return lbFiltrar;
	}
	
	public JLabel getLbCategorias() {
		if (lbCategorias == null) {
			lbCategorias = new JLabel("");
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
			
			pnCategorias.add(getRdTodo());
			pnCategorias.add(getRdAccesorios());
			pnCategorias.add(getRdConsolas());
			pnCategorias.add(getRdVideojuegos());
			
			// Creo un grupo para seleccionar s�lo uno a la vez
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
			rdTodo.setSelected(true);
			rdTodo.setText( vp.getInternacionalizar().getTexto("premios.todo") );
			rdTodo.setActionCommand(TODO);
			
			rdTodo.addActionListener(pAFC);
		}
		return rdTodo;
	}
	public JRadioButton getRdAccesorios() {
		if (rdAccesorios == null) {
			rdAccesorios = new JRadioButton("");
			rdAccesorios.setText( vp.getInternacionalizar().getTexto("premios.accesorios") );
			
			rdAccesorios.setActionCommand(ACCESORIOS);
			
			rdAccesorios.addActionListener(pAFC);
		}
		return rdAccesorios;
	}
	public JRadioButton getRdConsolas() {
		if (rdConsolas == null) {
			rdConsolas = new JRadioButton("");
			rdConsolas.setText( vp.getInternacionalizar().getTexto("premios.consolas") );
			
			rdConsolas.setActionCommand(CONSOLAS);
			
			rdConsolas.addActionListener(pAFC);
		}
		return rdConsolas;
	}
	public JRadioButton getRdVideojuegos() {
		if (rdVideojuegos == null) {
			rdVideojuegos = new JRadioButton("");
			rdVideojuegos.setText( vp.getInternacionalizar().getTexto("premios.videojuegos") );
			
			rdVideojuegos.setActionCommand(VIDEOJUEGOS);
			
			rdVideojuegos.addActionListener(pAFC);
		}
		return rdVideojuegos;
	}
	
	private JScrollPane getScPremios() {
		if (scPremios == null) {
			scPremios = new JScrollPane();
			scPremios.setBorder(null);
			scPremios.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
			scPremios.setViewportView(getPnPremios());
		}
		return scPremios;
	}
	
	public JPanel getPnPremios() {
		if (pnPremios == null) {
			pnPremios = new JPanel();
			pnPremios.setBorder(new EmptyBorder(10, 10, 10, 10));
			pnPremios.setLayout(new GridLayout(0, 1, 0, 0));
			
			mostrarPremios(TODO);
		}
		return pnPremios;
	}
	
	public void mostrarPremios(String filtro)
	{
		filtroActivo = filtro;
		
		this.getPnPremios().removeAll();
		
		Premio[] listaPremios = this.getListaPremios(filtro);
		
		for ( int i = 0; i < listaPremios.length; i++ )
			this.getPnPremios().add( new PanelPremioCarrito(vp, listaPremios[i], gestionPremios) );
		
		// Mantengo el formato del grid
		if ( listaPremios.length > 0 && listaPremios.length < 3 )
		{
			this.getPnPremios().add( new PanelPremioCarrito(vp, listaPremios[0], gestionPremios) ).setVisible(false);
			this.getPnPremios().add( new PanelPremioCarrito(vp, listaPremios[0], gestionPremios) ).setVisible(false);
		}
		
		this.getPnPremios().repaint();
		this.getPnPremios().validate();
	}
	
	public void mostrarPremios()
	{
		mostrarPremios(filtroActivo);
	}
	
	private Premio[] getListaPremios(String filtro)
	{
		if ( filtro.equals(TODO) )
			return gestionPremios.getCarrito();
		
		if ( filtro.equals(ACCESORIOS) )
			return gestionPremios.getAccesoriosCarrito();
		
		if ( filtro.equals(CONSOLAS) )
			return gestionPremios.getConsolasCarrito();
		
		if ( filtro.equals(VIDEOJUEGOS) )
			return gestionPremios.getVideojuegosCarrito();
		
		return null;
	}
	
}
