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
	
	ButtonGroup groupFiltro;
	
	private final static String TODO = "todo";
	private final static String ACCESORIOS = "accesorios";
	private final static String CONSOLAS = "consolas";
	private final static String VIDEOJUEGOS = "videojuegos";
	
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
		this.gestionPremios = new GestionPremios();
		this.pAFC = new ProcesaAccionFiltroCategorias();
		this.pAC = new ProcesaAccionCarrito();
		this.pRI = new ProcesaResizeInterfaz();
		
		groupFiltro = new ButtonGroup();
		
		setLayout(new BorderLayout(0, 0));
		add(getPnNorte(), BorderLayout.NORTH);
		add(getPnCentro(), BorderLayout.CENTER);
		setBounds(100, 100, 780, 840);
		
		addComponentListener(pRI);
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
			mostrarPremios(TODO);
		}
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
		vp.getPnPantallaCarrito().mostrarPremios(TODO);
		vp.getPnPantallaCarrito().getRdTodo().setSelected(true);
		vp.getPnPantallaCarrito().getPnPremios().validate();
		vp.getPnPantallaCarrito().getTxtPuntos().setText(String.format("%d", game.getPuntos()));
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

	public GestionPremios getGestionPremios() {
		return gestionPremios;
	}

	private JPanel getPnNorte() {
		if (pnNorte == null) {
			pnNorte = new JPanel();
			pnNorte.setBorder(new EmptyBorder(20, 0, 0, 0));
			pnNorte.setLayout(new GridLayout(0, 3, 0, 0));
			pnNorte.add(getPnSalir());
			pnNorte.add(getPnPuntos());
			pnNorte.add(getPnCarrito());
		}
		return pnNorte;
	}
	
	private JPanel getPnSalir() {
		if (pnSalir == null) {
			pnSalir = new JPanel();
			pnSalir.add(getBtSalir());
		}
		return pnSalir;
	}
	private JButton getBtSalir() {
		if (btSalir == null) {
			btSalir = new JButton("");
			btSalir.setFont(new Font("Tahoma", Font.BOLD, vp.getH3()));
			btSalir.setText(vp.getInternacionalizar().getTexto("boton.salir"));
			
			btSalir.addActionListener( vp.getProcesaAccionSalir() );
		}
		return btSalir;
	}
	private JPanel getPnPuntos() {
		if (pnPuntos == null) {
			pnPuntos = new JPanel();
			pnPuntos.add(getLbPuntos());
			pnPuntos.add(getTxtPuntos());
		}
		return pnPuntos;
	}
	
	private JLabel getLbPuntos() {
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
			txtPuntos.setHorizontalAlignment(SwingConstants.CENTER);
			txtPuntos.setEditable(false);
			txtPuntos.setFont(new Font("Tahoma", Font.BOLD, vp.getH3()));
			txtPuntos.setColumns(10);
			txtPuntos.setText( String.format("%d", game.getPuntos()) );
		}
		return txtPuntos;
	}
	
	private JPanel getPnCarrito() {
		if (pnCarrito == null) {
			pnCarrito = new JPanel();
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
			
			btCarrito.addActionListener(pAC);
		}
		return btCarrito;
	}
	
	private JPanel getPnCentro() {
		if (pnCentro == null) {
			pnCentro = new JPanel();
			pnCentro.setBorder(new EmptyBorder(20, 0, 0, 0));
			pnCentro.setLayout(new BorderLayout(0, 0));
			pnCentro.add(getPnFiltro(), BorderLayout.WEST);
			pnCentro.add(getScPremios());
		}
		return pnCentro;
	}
	private JPanel getPnFiltro() {
		if (pnFiltro == null) {
			pnFiltro = new JPanel();
			pnFiltro.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
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
		}
		return scPremios;
	}
	
	private JPanel getPnPremios() {
		if (pnPremios == null) {
			pnPremios = new JPanel();
			pnPremios.setBorder(new EmptyBorder(10, 10, 10, 10));
			
			mostrarPremios(TODO);
		}
		return pnPremios;
	}
	
	private void mostrarPremios(String filtro)
	{
		this.getPnPremios().removeAll();
		
		Premio[] listaPremios = this.getListaPremios(filtro);
		
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
	
	private Premio[] getListaPremios(String filtro)
	{
		if ( filtro.equals(TODO) )
			return gestionPremios.getListaPremios();
		
		if ( filtro.equals(ACCESORIOS) )
			return gestionPremios.getAccesorios();
		
		if ( filtro.equals(CONSOLAS) )
			return gestionPremios.getConsolas();
		
		if ( filtro.equals(VIDEOJUEGOS) )
			return gestionPremios.getVideojuegos();
		
		return null;
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
			pnLabelFiltro.setLayout(new GridLayout(0, 1, 0, 0));
			pnLabelFiltro.add(getLbFiltrar());
			pnLabelFiltro.add(getLbCategorias());
		}
		return pnLabelFiltro;
	}
	
	private JLabel getLbFiltrar() {
		if (lbFiltrar == null) {
			lbFiltrar = new JLabel("");
			lbFiltrar.setText( vp.getInternacionalizar().getTexto("premios.filtrar") );
			lbFiltrar.setFont(new Font("Tahoma", Font.BOLD, vp.getH2()));
		}
		return lbFiltrar;
	}
	
	private JLabel getLbCategorias() {
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
	private JRadioButton getRdTodo() {
		if (rdTodo == null) {
			rdTodo = new JRadioButton("");
			rdTodo.setSelected(true);
			rdTodo.setText( vp.getInternacionalizar().getTexto("premios.todo") );
			rdTodo.setActionCommand(TODO);
			
			rdTodo.addActionListener(pAFC);
		}
		return rdTodo;
	}
	private JRadioButton getRdAccesorios() {
		if (rdAccesorios == null) {
			rdAccesorios = new JRadioButton("");
			rdAccesorios.setText( vp.getInternacionalizar().getTexto("premios.accesorios") );
			
			rdAccesorios.setActionCommand(ACCESORIOS);
			
			rdAccesorios.addActionListener(pAFC);
		}
		return rdAccesorios;
	}
	private JRadioButton getRdConsolas() {
		if (rdConsolas == null) {
			rdConsolas = new JRadioButton("");
			rdConsolas.setText( vp.getInternacionalizar().getTexto("premios.consolas") );
			
			rdConsolas.setActionCommand(CONSOLAS);
			
			rdConsolas.addActionListener(pAFC);
		}
		return rdConsolas;
	}
	private JRadioButton getRdVideojuegos() {
		if (rdVideojuegos == null) {
			rdVideojuegos = new JRadioButton("");
			rdVideojuegos.setText( vp.getInternacionalizar().getTexto("premios.videojuegos") );
			
			rdVideojuegos.setActionCommand(VIDEOJUEGOS);
			
			rdVideojuegos.addActionListener(pAFC);
		}
		return rdVideojuegos;
	}
	private JSeparator getSpFiltro() {
		if (spFiltro == null) {
			spFiltro = new JSeparator();
		}
		return spFiltro;
	}
	private JCheckBox getCbRegalosDisponibles() {
		if (cbRegalosDisponibles == null) {
			cbRegalosDisponibles = new JCheckBox("");
			cbRegalosDisponibles.setText( vp.getInternacionalizar().getTexto("premios.regalosdisponibles") );
		}
		return cbRegalosDisponibles;
	}
}
