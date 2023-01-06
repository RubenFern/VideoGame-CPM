package uo.cpm.videogame.ui;

import javax.swing.JPanel;
import javax.swing.JTextArea;

import uo.cpm.videogame.model.Premio;
import uo.cpm.videogame.service.Game;
import uo.cpm.videogame.service.GestionPremios;

import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import java.awt.Color;

import javax.swing.JButton;
import javax.swing.BoxLayout;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.BorderLayout;
import javax.swing.SwingConstants;

public class PanelPremioCarrito extends JPanel 
{
	private static final long serialVersionUID = 1L;
	
	private VentanaPrincipal vp;
	private Premio p;
	private GestionPremios gestionPremios;
	private Game game;
	
	private ProcesaEliminarPremio pEP;
	
	private JPanel pnImagen;
	private JPanel pnPremio;
	private JLabel lbImagen;
	private JPanel pnNorte;
	private JLabel lbDenominacion;
	private JTextArea lbDescripcion;
	private JPanel pnInferior;
	private JLabel lbPuntos;
	private JButton btEliminar;
	private JLabel lbNumeroUnidades;
	private JPanel pnBtEliminar;
	
	public PanelPremioCarrito(VentanaPrincipal vp, Premio p, GestionPremios gestionPremios)
	{
		setBounds(new Rectangle(0, 0, 500, 200));
		this.vp = vp;
		this.p = p;
		this.gestionPremios = gestionPremios;
		this.game = vp.getGame();
		this.pEP = new ProcesaEliminarPremio();
		
		setBorder(new LineBorder( Color.black ));
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		add(getPnImagen());
		add(getPnPremio());
	}
	
	class ProcesaEliminarPremio implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			eliminarPremio();
		}
	}
	
	private void eliminarPremio()
	{
		gestionPremios.eliminarDelCarrito(game, p);
		
		// Actualizo el n�mero de �rticulos del carrito
		vp.getPnPantallaPremios().getBtCarrito().setText( "(" + gestionPremios.getNumeroPremios() + ")" );
		
		// Actualizo los puntos de la pantalla premios y carrito
		vp.getPnPantallaPremios().getTxtPuntos().setText( String.format("%d", game.getPuntos()) );
		vp.getPnPantallaCarrito().getTxtPuntos().setText( String.format("%d", game.getPuntos()) );
		
		// Actulizo los premios disponibles si está activada la opción de mostrar sólo los disponibles
		if ( vp.getPnPantallaPremios().getCbRegalosDisponibles().isSelected() )
			vp.getPnPantallaPremios().mostrarPremios(true);
		
		// Si no hay premios en el carrito vuelvo a la pantalla de premios
		if ( gestionPremios.getCarrito().length == 0 )
			vp.mostrarPantallaPremios();
		else		
			vp.getPnPantallaCarrito().mostrarPremios( VentanaCarrito.TODO );
	}

	private JPanel getPnImagen() {
		if (pnImagen == null) {
			pnImagen = new JPanel();
			pnImagen.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
			pnImagen.add(getLbImagen());
		}
		return pnImagen;
	}
	private JPanel getPnPremio() {
		if (pnPremio == null) {
			pnPremio = new JPanel();
			pnPremio.setBorder(new EmptyBorder(5, 5, 5, 5));
			pnPremio.setLayout(new BorderLayout(0, 0));
			pnPremio.add(getPnNorte(), BorderLayout.NORTH);
			pnPremio.add(getLbDescripcion(), BorderLayout.CENTER);
			pnPremio.add(getPnInferior(), BorderLayout.SOUTH);
		}
		return pnPremio;
	}
	private JLabel getLbImagen() {
		if (lbImagen == null) {
			lbImagen = new JLabel("");
			lbImagen.setBorder(new EmptyBorder(5, 5, 5, 5));
			lbImagen.setBounds( new Rectangle( 150, 150 ) );
			
			lbImagen.setIcon( vp.ajustarImagen(lbImagen, p.getImagen()) );
		}
		return lbImagen;
	}
	private JPanel getPnNorte() {
		if (pnNorte == null) {
			pnNorte = new JPanel();
			pnNorte.setLayout(new GridLayout(0, 2, 0, 0));
			pnNorte.add(getLbDenominacion());
			pnNorte.add(getLbNumeroUnidades());
		}
		return pnNorte;
	}
	private JLabel getLbDenominacion() {
		if (lbDenominacion == null) {
			lbDenominacion = new JLabel("");
			lbDenominacion.setHorizontalAlignment(SwingConstants.CENTER);
			lbDenominacion.setBorder(new EmptyBorder(5, 0, 5, 0));
			lbDenominacion.setText( p.getDenominacion() );
			lbDenominacion.setFont(new Font("Tahoma", Font.BOLD, vp.getTexto()));
		}
		return lbDenominacion;
	}
	private JTextArea getLbDescripcion() {
		if (lbDescripcion == null) {
			lbDescripcion = new JTextArea("");
			lbDescripcion.setLineWrap(true);
			lbDescripcion.setEditable(false);
			lbDescripcion.setBorder(new EmptyBorder(5, 0, 5, 0));
			lbDescripcion.setText( p.getDescripcion() );
		}
		return lbDescripcion;
	}
	private JPanel getPnInferior() {
		if (pnInferior == null) {
			pnInferior = new JPanel();
			pnInferior.setBorder(new EmptyBorder(5, 0, 5, 0));
			pnInferior.setLayout(new GridLayout(0, 2, 0, 0));
			pnInferior.add(getLbPuntos());
			pnInferior.add(getPnBtEliminar());
		}
		return pnInferior;
	}
	private JLabel getLbPuntos() {
		if (lbPuntos == null) {
			lbPuntos = new JLabel("");
			lbPuntos.setFont(new Font("Tahoma", Font.BOLD, vp.getTexto()));
			lbPuntos.setText( vp.getInternacionalizar().getTexto("premios.puntos") + " " + p.getCostePuntos() );
		}
		return lbPuntos;
	}
	
	private JLabel getLbNumeroUnidades() {
		if (lbNumeroUnidades == null) {
			lbNumeroUnidades = new JLabel("");
			lbNumeroUnidades.setHorizontalAlignment(SwingConstants.CENTER);
			lbNumeroUnidades.setFont(new Font("Tahoma", Font.PLAIN, vp.getH3()));
			
			String textoUnidades = ( p.getUnidades() > 1 ) 
					? vp.getInternacionalizar().getTexto("carrito.unidades") 
					: vp.getInternacionalizar().getTexto("carrito.unidad");
			
			lbNumeroUnidades.setText( p.getUnidades() + " " + textoUnidades );
		}
		return lbNumeroUnidades;
	}
	private JPanel getPnBtEliminar() {
		if (pnBtEliminar == null) {
			pnBtEliminar = new JPanel();
			pnBtEliminar.add(getBtEliminar());
		}
		return pnBtEliminar;
	}
	
	private JButton getBtEliminar() {
		if (btEliminar == null) {
			btEliminar = new JButton();
			btEliminar.setFont(new Font("Tahoma", Font.BOLD, vp.getTexto()));
			btEliminar.setText( vp.getInternacionalizar().getTexto("boton.eliminar") );
			
			btEliminar.addActionListener(pEP);
		}
		return btEliminar;
	}
}
