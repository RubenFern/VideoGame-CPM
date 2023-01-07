package uo.cpm.videogame.ui;

import javax.swing.JPanel;
import java.awt.BorderLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import uo.cpm.videogame.service.Game;
import uo.cpm.videogame.service.GestionPremios;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;

public class VentanaFinal extends JPanel 
{
	private static final long serialVersionUID = 1L;
	
	private VentanaPrincipal vp;
	private Game game;
	private GestionPremios gestionPremios;
	
	private ProcesaBotonAtras pBA;
	private ProcesaConfirmarPremios pCP;
	
	private JPanel pnNorte;
	private JPanel pnCentro;
	private JPanel pnAtras;
	private JButton btAtras;
	private JPanel pnLabel;
	private JLabel lbIntroducirDNI;
	private JPanel pnDNI;
	private JTextField txtDNI;
	private JPanel pnRecogerPremios;
	private JButton btRecogerPremios;
	
	public VentanaFinal(VentanaPrincipal vp) 
	{
		this.vp = vp;
		this.game = vp.getGame();
		this.gestionPremios = vp.getGestionPremios();
		this.pBA = new ProcesaBotonAtras();
		this.pCP = new ProcesaConfirmarPremios();
				
		setBackground(VentanaPrincipal.BACKGROUND);
		setLayout(new BorderLayout(0, 0));
		add(getPnNorte(), BorderLayout.NORTH);
		add(getPnCentro(), BorderLayout.CENTER);
	}
	
	public void inicializar()
	{
		this.getTxtDNI().setText("");
	}
	
	class ProcesaBotonAtras implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			vp.mostrarPantallaCarrito();
		}
	}
	
	class ProcesaConfirmarPremios implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			confirmarPremios();
		}
	}
	
	private void confirmarPremios()
	{
		String DNI = this.getTxtDNI().getText();

		if ( DNI.equals("") )
		{
			JOptionPane.showMessageDialog(this, vp.getInternacionalizar().getTexto("error.DNIvacio"),
					game.getNombreTienda(), JOptionPane.ERROR_MESSAGE, new ImageIcon(game.getIconoTienda()));
			return;
		}
		
		entrega(DNI);
	}
	
	private void entrega(String dni)
	{
		if ( confirmarEntrega() )
		{
			gestionPremios.entrega(dni, game.getCodigoTienda());
			
			// Inicializa toda la aplicación
			vp.inicializarAplicacion();
			
			JOptionPane.showMessageDialog(this, vp.getInternacionalizar().getTexto("final.entrega"),
					game.getNombreTienda(), JOptionPane.INFORMATION_MESSAGE, new ImageIcon(game.getIconoTienda()));
		}		
	}
	
	private boolean confirmarEntrega()
	{		
		int respuesta = JOptionPane.showConfirmDialog(this, vp.getInternacionalizar().getTexto("final.preguntaentrega"));
		
		if ( respuesta == JOptionPane.YES_OPTION )
			return true;
		
		return false;
	}

	private JPanel getPnNorte() {
		if (pnNorte == null) {
			pnNorte = new JPanel();
			pnNorte.setLayout(new GridLayout(0, 3, 0, 0));
			pnNorte.setBackground(VentanaPrincipal.BACKGROUND);
			pnNorte.add(getPnAtras());
		}
		return pnNorte;
	}
	private JPanel getPnCentro() {
		if (pnCentro == null) {
			pnCentro = new JPanel();
			pnCentro.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
			pnCentro.setBackground(VentanaPrincipal.BACKGROUND);
			pnCentro.add(getPnLabel());
		}
		return pnCentro;
	}
	private JPanel getPnAtras() {
		if (pnAtras == null) {
			pnAtras = new JPanel();
			pnAtras.setBorder(new EmptyBorder(20, 0, 0, 0));
			pnAtras.setBackground(VentanaPrincipal.BACKGROUND);
			pnAtras.add(getBtAtras());
		}
		return pnAtras;
	}
	public JButton getBtAtras() {
		if (btAtras == null) {
			btAtras = new JButton("");
			btAtras.setFont(new Font("Tahoma", Font.BOLD, vp.getH3()));
			btAtras.setText( vp.getInternacionalizar().getTexto("boton.atras") );
			btAtras.setMnemonic( vp.getInternacionalizar().getTexto("mn.final.atras").charAt(0) );
			btAtras.setBackground(VentanaPrincipal.BACKGROUND_BOTONES);
			
			btAtras.addActionListener( pBA );
		}
		return btAtras;
	}
	private JPanel getPnLabel() {
		if (pnLabel == null) {
			pnLabel = new JPanel();
			pnLabel.setBorder(new EmptyBorder(100, 0, 0, 0));
			pnLabel.setLayout(new GridLayout(3, 1, 0, 0));
			pnLabel.setBackground(VentanaPrincipal.BACKGROUND);
			pnLabel.add(getLbIntroducirDNI());
			pnLabel.add(getPnDNI());
			pnLabel.add(getPnRecogerPremios());
		}
		return pnLabel;
	}
	public JLabel getLbIntroducirDNI() {
		if (lbIntroducirDNI == null) {
			lbIntroducirDNI = new JLabel("");
			lbIntroducirDNI.setForeground(new Color(255, 255, 255));
			lbIntroducirDNI.setBorder(new EmptyBorder(30, 25, 20, 25));
			lbIntroducirDNI.setHorizontalAlignment(SwingConstants.CENTER);
			lbIntroducirDNI.setText( vp.getInternacionalizar().getTexto("final.introducirdni") );
			lbIntroducirDNI.setFont(new Font("Tahoma", Font.BOLD, vp.getH1() - 2));
		}
		return lbIntroducirDNI;
	}
	private JPanel getPnDNI() {
		if (pnDNI == null) {
			pnDNI = new JPanel();
			pnDNI.setBorder(new EmptyBorder(10, 0, 0, 0));
			pnDNI.setLayout(new GridLayout(3, 1, 0, 0));
			pnDNI.setBackground(VentanaPrincipal.BACKGROUND);
			pnDNI.add(getTxtDNI());
		}
		return pnDNI;
	}
	private JTextField getTxtDNI() {
		if (txtDNI == null) {
			txtDNI = new JTextField();
			txtDNI.setColumns(10);
			txtDNI.setFont(new Font("Tahoma", Font.BOLD, vp.getTexto()));
		}
		return txtDNI;
	}
	private JPanel getPnRecogerPremios() {
		if (pnRecogerPremios == null) {
			pnRecogerPremios = new JPanel();
			pnRecogerPremios.add(getBtRecogerPremios());
			pnRecogerPremios.setBackground(VentanaPrincipal.BACKGROUND);
		}
		return pnRecogerPremios;
	}
	public JButton getBtRecogerPremios() {
		if (btRecogerPremios == null) {
			btRecogerPremios = new JButton("");
			btRecogerPremios.setText( vp.getInternacionalizar().getTexto("boton.recoger") );
			btRecogerPremios.setFont(new Font("Tahoma", Font.BOLD, vp.getH3()));
			btRecogerPremios.setMnemonic( vp.getInternacionalizar().getTexto("mn.final.recoger").charAt(0) );
			btRecogerPremios.setBackground(VentanaPrincipal.BACKGROUND_BOTONES);
			
			btRecogerPremios.addActionListener( pCP );
		}
		return btRecogerPremios;
	}
}
