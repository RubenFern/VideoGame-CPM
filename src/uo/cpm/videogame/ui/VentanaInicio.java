package uo.cpm.videogame.ui;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JPanel;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.border.EmptyBorder;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.FlowLayout;

public class VentanaInicio extends JPanel 
{
	private static final long serialVersionUID = 1L;
	private JPanel pnNorteInicio;
	private JLabel lbBienvenido;
	private JLabel lbPedirTicket;
	private JPanel pnCentroInicio;
	private JPanel pnBotonesInicio;
	private JButton btSiguiente;

	public VentanaInicio() 
	{
		setBounds(100, 100, 1100, 700);
		setBackground(Color.DARK_GRAY);
		setLayout(new BorderLayout(0, 0));
		add(getPnNorteInicio(), BorderLayout.NORTH);
		add(getPnCentroInicio_1(), BorderLayout.CENTER);
		add(getPnBotonesInicio_1(), BorderLayout.SOUTH);
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
	private JLabel getLbBienvenido() {
		if (lbBienvenido == null) {
			lbBienvenido = new JLabel("\u00A1Bienvenido a nuestro nuevo videojuego Retro!");
			lbBienvenido.setHorizontalAlignment(SwingConstants.CENTER);
			lbBienvenido.setFont(new Font("Tahoma", Font.BOLD, 40));
			lbBienvenido.setBorder(new EmptyBorder(20, 0, 0, 0));
		}
		return lbBienvenido;
	}
	private JLabel getLbPedirTicket() {
		if (lbPedirTicket == null) {
			lbPedirTicket = new JLabel("Si tiene un Ticket de compra superior o igual a 20\u20AC de esta tienda \u00A1Puede jugar una partida!");
			lbPedirTicket.setHorizontalAlignment(SwingConstants.CENTER);
			lbPedirTicket.setFont(new Font("Tahoma", Font.PLAIN, 20));
		}
		return lbPedirTicket;
	}
	private JPanel getPnCentroInicio_1() {
		if (pnCentroInicio == null) {
			pnCentroInicio = new JPanel();
			pnCentroInicio.setAlignmentX(0.0f);
			pnCentroInicio.setLayout(new BorderLayout(0, 0));
		}
		return pnCentroInicio;
	}
	private JPanel getPnBotonesInicio_1() {
		if (pnBotonesInicio == null) {
			pnBotonesInicio = new JPanel();
			pnBotonesInicio.setBorder(new EmptyBorder(10, 0, 20, 0));
			pnBotonesInicio.add(getBtSiguiente_1());
		}
		return pnBotonesInicio;
	}
	private JButton getBtSiguiente_1() {
		if (btSiguiente == null) {
			btSiguiente = new JButton("Siguiente");
			btSiguiente.setFocusPainted(false);
			btSiguiente.setBorder(new EmptyBorder(5, 10, 5, 10));
			btSiguiente.setBackground(new Color(0, 204, 255));
			btSiguiente.setActionCommand("pantallaJuego");
		}
		return btSiguiente;
	}
}
