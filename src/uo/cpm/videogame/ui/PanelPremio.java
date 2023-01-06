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
import javax.swing.JOptionPane;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class PanelPremio extends JPanel 
{
	private static final long serialVersionUID = 1L;

	private VentanaPrincipal vp;
	private Premio p;
	private Game game;
	private GestionPremios gestionPremios;
	
	private ProcesaAnadirPremio pAP;
	
	private JPanel pnImagen;
	private JPanel pnPremio;
	private JLabel lbImagen;
	private JLabel lbDenominacion;
	private JTextArea lbDescripcion;
	private JPanel pnInferior;
	private JLabel lbPuntos;
	private JButton btAnadir;
	
	public PanelPremio(VentanaPrincipal vp, Premio p, GestionPremios gestionPremios)
	{
		setBounds(new Rectangle(0, 0, 500, 300));
		this.vp = vp;
		this.p = p;
		this.game = vp.getGame();
		this.gestionPremios = gestionPremios;
		this.pAP = new ProcesaAnadirPremio();
		
		setBorder(new LineBorder( Color.black ));
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		add(getPnImagen());
		add(getPnPremio());
	}
	
	class ProcesaAnadirPremio implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			anadirPremio();
		}
	}
	
	private void anadirPremio()
	{
		if ( game.getPuntos() < p.getCostePuntos() )
		{
			JOptionPane.showMessageDialog(vp.getPnPantallaPremios(), vp.getInternacionalizar().getTexto("error.faltapuntos"),
					game.getNombreTienda(), JOptionPane.WARNING_MESSAGE, new ImageIcon(game.getIconoTienda()));
			return;
		}
		
		gestionPremios.anadirPremioAlCarrito(game, p);
		
		vp.getPnPantallaPremios().getTxtPuntos().setText( String.format("%d", game.getPuntos()) );
		vp.getPnPantallaPremios().getBtCarrito().setText( "(" + gestionPremios.getNumeroPremios() + ")" );
		
		// Muestro de nuevo los premios si está activado el mostrar sólo los disponibles
		if ( vp.getPnPantallaPremios().getCbRegalosDisponibles().isSelected() )
			vp.getPnPantallaPremios().mostrarPremios(true);
	}

	private JPanel getPnImagen() {
		if (pnImagen == null) {
			pnImagen = new JPanel();
			pnImagen.setLayout(new BorderLayout(0, 0));
			pnImagen.add(getLbImagen());
		}
		return pnImagen;
	}
	private JPanel getPnPremio() {
		if (pnPremio == null) {
			pnPremio = new JPanel();
			pnPremio.setBorder(new EmptyBorder(5, 5, 5, 5));
			pnPremio.setLayout(new BorderLayout(0, 0));
			pnPremio.add(getLbDenominacion(), BorderLayout.NORTH);
			pnPremio.add(getLbDescripcion(), BorderLayout.CENTER);
			pnPremio.add(getPnInferior(), BorderLayout.SOUTH);
		}
		return pnPremio;
	}
	private JLabel getLbImagen() {
		if (lbImagen == null) {
			lbImagen = new JLabel("");
			lbImagen.setBorder(new EmptyBorder(5, 5, 5, 5));
			lbImagen.setBounds( new Rectangle( 100, 100 ) );
			
			lbImagen.setIcon( vp.ajustarImagen(lbImagen, p.getImagen()) );
		}
		return lbImagen;
	}
	private JLabel getLbDenominacion() {
		if (lbDenominacion == null) {
			lbDenominacion = new JLabel("");
			lbDenominacion.setBorder(new EmptyBorder(5, 0, 5, 0));
			lbDenominacion.setText( p.getDenominacion() );
			lbDenominacion.setFont(new Font("Tahoma", Font.BOLD, vp.getTexto()));
		}
		return lbDenominacion;
	}
	private JTextArea getLbDescripcion() {
		if (lbDescripcion == null) {
			lbDescripcion = new JTextArea("");
			lbDescripcion.setBorder(new EmptyBorder(5, 0, 5, 0));
			lbDescripcion.setEditable(false);
			lbDescripcion.setLineWrap(true);
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
			pnInferior.add(getBtAnadir());
		}
		return pnInferior;
	}
	
	private JLabel getLbPuntos() {
		if (lbPuntos == null) {
			lbPuntos = new JLabel("");
			lbPuntos.setText( vp.getInternacionalizar().getTexto("premios.puntos") + " " + p.getCostePuntos() );
			lbPuntos.setFont(new Font("Tahoma", Font.BOLD, vp.getTexto()));
		}
		return lbPuntos;
	}
	private JButton getBtAnadir() {
		if (btAnadir == null) {
			btAnadir = new JButton("");
			btAnadir.setText( vp.getInternacionalizar().getTexto("boton.anadir") );
			btAnadir.setFont(new Font("Tahoma", Font.BOLD, vp.getTexto()));
			
			btAnadir.addActionListener(pAP);
		}
		return btAnadir;
	}
}
