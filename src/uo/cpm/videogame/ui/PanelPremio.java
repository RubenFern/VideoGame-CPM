package uo.cpm.videogame.ui;

import javax.swing.JPanel;
import javax.swing.JTextArea;

import uo.cpm.videogame.model.Premio;
import uo.cpm.videogame.service.GestionPremios;

import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.BoxLayout;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class PanelPremio extends JPanel 
{
	private static final long serialVersionUID = 1L;

	private VentanaPrincipal vp;
	private Premio p;
	private GestionPremios gestionPremios;
	
	private ProcesaAñadirPremio pAP;
	
	private JPanel pnImagen;
	private JPanel pnPremio;
	private JLabel lbImagen;
	private JLabel lbDenominacion;
	private JTextArea lbDescripcion;
	private JPanel pnInferior;
	private JLabel lbPuntos;
	private JButton btAñadir;
	
	public PanelPremio(VentanaPrincipal vp, Premio p, GestionPremios gestionPremios)
	{
		setBounds(new Rectangle(0, 0, 500, 300));
		this.vp = vp;
		this.p = p;
		this.gestionPremios = gestionPremios;
		this.pAP = new ProcesaAñadirPremio();
		
		setBorder(new LineBorder( Color.black ));
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		add(getPnImagen());
		add(getPnPremio());
	}
	
	class ProcesaAñadirPremio implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			añadirPremio();
		}
	}
	
	private void añadirPremio()
	{
		gestionPremios.añadirPremioAlCarrito(p);
		
		vp.getPnPantallaPremios().getBtCarrito().setText( "(" + gestionPremios.getNumeroPremios() + ")" );
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
			//lbImagen.setBounds( new Rectangle( this.getPnImagen().getWidth(), this.getPnImagen().getHeight() ) );
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
			pnInferior.add(getBtAñadir());
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
	private JButton getBtAñadir() {
		if (btAñadir == null) {
			btAñadir = new JButton("");
			btAñadir.setText( vp.getInternacionalizar().getTexto("boton.añadir") );
			btAñadir.setFont(new Font("Tahoma", Font.BOLD, vp.getTexto()));
			
			btAñadir.addActionListener(pAP);
		}
		return btAñadir;
	}
}
