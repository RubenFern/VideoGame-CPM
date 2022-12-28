package uo.cpm.videogame.ui;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JComponent;

import java.awt.FlowLayout;
import javax.swing.SwingConstants;
import javax.swing.TransferHandler;
import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import uo.cpm.videogame.model.Invasor;
import uo.cpm.videogame.model.Reglas;
import uo.cpm.videogame.service.Game;

import java.awt.Color;

public class VentanaJuego extends JPanel 
{
	private static final long serialVersionUID = 1L;

	private Game game;
	
	private VentanaPrincipal vp;
	private ProcesaDragAndDrop pDAD;
	private ProcesaLabelTablero pLT;
	private int movimiento; // Almacena el movimiento para marcarlo como usado
	
	private JPanel pnNorte;
	private JPanel pnCentro;
	private JPanel pnSur;
	private JPanel pnMovimientos;
	private JPanel pnSalir;
	private JButton btSalir;
	private JPanel pnPuntos;
	private JLabel lbPuntos;
	private JPanel pnRonda;
	private JLabel lbRonda;
	private JTextField txtPuntos;
	private JPanel pnTablero;
	
	public VentanaJuego(VentanaPrincipal vp) 
	{
		this.vp = vp;
		this.game = vp.getGame();

		this.pDAD = new ProcesaDragAndDrop();
		this.pLT = new ProcesaLabelTablero();
		
		setLayout(new BorderLayout(0, 0));
		add(getPnNorte(), BorderLayout.NORTH);
		add(getPnCentro(), BorderLayout.CENTER);
		add(getPnSur(), BorderLayout.SOUTH);
	}
	
	class ProcesaDragAndDrop extends MouseAdapter
	{
		@Override
		public void mousePressed(MouseEvent e) 
		{
			JLabel c = (JLabel) e.getSource();
			TransferHandler handler = c.getTransferHandler();
			handler.exportAsDrag(c, e, TransferHandler.COPY);
			
			// Guardo el invasor que se quiere arrastar
			int numeroInvasor = ((MiLabel) c).getNumeroInvasor();
			
			// Posición del invasor que se arrastra
			int posicionInvasor = ((MiLabel) c).getId();

			// Bloqueo los demás movimientos para que solo pueda colocar invasores en el tablero
			bloqueoMovimientos(posicionInvasor);
			
			game.setNumeroInvasorCasilla(numeroInvasor);
			game.setArrastra(true);
		}
		
		/**
		 * Cuando sale de los movimientos vuelvo a poner el evento
		 */
		@Override
		public void mouseExited(MouseEvent e) 
		{
			desbloqueoMovimientos();
		}
	}
	
	class ProcesaLabelTablero implements PropertyChangeListener
	{
		@Override
		public void propertyChange(PropertyChangeEvent e) 
		{			
			// Si hubo un cambio en la propiedad ImageIcon significa que se arrastró un invasor
			if ( e.getPropertyName().equals("icon") && (e.getNewValue() != null) && (e.getNewValue().getClass().toString().contains("ImageIcon") ))
			{
				// El usuario arrastró un invasor
				if ( game.isArrastra() )
				{
					MiLabel lbPulsada = (MiLabel) e.getSource();
					lbPulsada.setTransferHandler(null); // Impido volver a colocar un invasor en esa posición

					// Guardo la posición del tablero donde se colocó el invasor
					int posicionTablero = lbPulsada.getId();
					game.setPosicionTableroCasilla(posicionTablero);
					
					// Añado el invasor en la casilla
					game.añadirInvasorAlTablero( game.getCasilla() );
					
					// Marco el movimiento como usado
					marcarMovimientoUsado(movimiento);
					
					// Si el invasor movido es el líder lo marco
					if ( game.getCasilla().getNumeroInvasor() == 1 )
						marcarLider(game.getCasilla().getPosicionTablero());
					
					// Compruebo el estado de la ronda
					ronda();
										
					game.setArrastra(false);
				}
			}
		}
	}
	
	private void bloqueoMovimientos(int pos)
	{
		// Guardo el movimiento seleccionado
		this.movimiento = pos;
		
		for ( int i = 0; i < this.getPnMovimientos().getComponentCount(); i++ )
			if ( i != pos )
				((MiLabel) this.getPnMovimientos().getComponent(i)).setTransferHandler(null);
	}
	
	private void desbloqueoMovimientos()
	{
		for ( int i = 0; i < this.getPnMovimientos().getComponentCount(); i++ )
		{
			MiLabel label = (MiLabel) this.getPnMovimientos().getComponent(i);
			
			// Movimiento no usado
			if ( label.isUsada() )
				label.removeMouseListener(pDAD);
			else //Movimiento usado
				label.setTransferHandler( new TransferHandler("icon") );
		}
	}
	
	private void marcarMovimientoUsado(int posicionMovimiento)
	{
		MiLabel label = (MiLabel) this.getPnMovimientos().getComponent(posicionMovimiento);
		
		label.setUsada(true);
		label.setEnabled(false);
	}
	
	private void marcarLider(int i)
	{
		((MiLabel) this.getPnTablero().getComponent(i)).setBorder( new LineBorder(Color.YELLOW) );
	}

	/**
	 * Comprueba el número de movimientos del usuario, cuado llegan a 0 pasa a la siguiente ronda
	 * y comprueba los puntos 
	 */
	private void ronda()
	{
		
	}
	
	private JPanel getPnNorte() {
		if (pnNorte == null) {
			pnNorte = new JPanel();
			pnNorte.setBorder(new EmptyBorder(15, 0, 20, 0));
			pnNorte.setLayout(new GridLayout(1, 3, 0, 0));
			pnNorte.add(getPnSalir());
			pnNorte.add(getPnPuntos());
			pnNorte.add(getPnRonda());
		}
		return pnNorte;
	}
	
	private JPanel getPnCentro() {
		if (pnCentro == null) {
			pnCentro = new JPanel();
			pnCentro.add(getPnTablero());
		}
		return pnCentro;
	}
	
	private JPanel getPnSur() {
		if (pnSur == null) {
			pnSur = new JPanel();
			pnSur.setBorder(new EmptyBorder(20, 0, 25, 0));
			pnSur.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 0));
			pnSur.add(getPnMovimientos());
		}
		return pnSur;
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
			lbPuntos.setText(vp.getInternacionalizar().getTexto("juego.puntos"));
		}
		return lbPuntos;
	}
	
	private JPanel getPnRonda() {
		if (pnRonda == null) {
			pnRonda = new JPanel();
			pnRonda.add(getLbRonda());
		}
		return pnRonda;
	}
	
	private JLabel getLbRonda() {
		if (lbRonda == null) {
			lbRonda = new JLabel("");
			lbRonda.setFont(new Font("Tahoma", Font.BOLD, vp.getH3()));
			lbRonda.setText( String.format("%s %d/%d", vp.getInternacionalizar().getTexto("juego.ronda"), game.getRonda(), Reglas.RONDAS.getValor()));
		}
		return lbRonda;
	}
	private JTextField getTxtPuntos() {
		if (txtPuntos == null) {
			txtPuntos = new JTextField();
			txtPuntos.setHorizontalAlignment(SwingConstants.CENTER);
			txtPuntos.setText("0");
			txtPuntos.setEditable(false);
			txtPuntos.setFont(new Font("Tahoma", Font.BOLD, vp.getH3()));
			txtPuntos.setColumns(10);
		}
		return txtPuntos;
	}
	private JPanel getPnTablero() {
		if (pnTablero == null) {
			pnTablero = new JPanel();
			pnTablero.setBorder(new LineBorder(new Color(0, 0, 0)));
			pnTablero.setLayout(new GridLayout(7, 7, 0, 0));
			pnTablero.setBackground(Color.BLACK);
			
			Invasor[] tablero = game.getTablero();
			
			for ( int i = 0; i < tablero.length; i++ )
				pnTablero.add(pintaCasilla( tablero[i], game.EsPosicionValida(i), i ) );	
		}
		
		return pnTablero;
	}
	
	private JPanel getPnMovimientos() {
		if (pnMovimientos == null) {
			pnMovimientos = new JPanel();
			pnMovimientos.setLayout(new GridLayout(1, 5, 0, 0));
			
			Invasor[] movimientos = game.getRondaInvasores();
						
			for ( int i = 0; i < movimientos.length; i++ )
				pnMovimientos.add(pintaMovimiento(movimientos[i], i));
		}
		return pnMovimientos;
	}
	
	private MiLabel pintaMovimiento(Invasor invasor, int i)
	{
		MiLabel label = new MiLabel("");
		
		label.setBounds( new Rectangle(70, 70) );
		label.setBorder( new LineBorder( (invasor.isLider()) ? Color.YELLOW : Color.GRAY) );
		
		// Asigno la imagen del invasor
		label.setIcon( vp.ajustarImagen(label, invasor.getImagen()) );
		
		// Asigno el número del invasor a la etiqueta
		label.setNumeroInvasor(invasor.getNumero());
		label.setId(i);
				
		// Evento arrastrar
		label.addMouseListener(pDAD);
		label.setTransferHandler( new TransferHandler("icon") );
		
		return label;
	}
	
	private MiLabel pintaCasilla(Invasor invasor, boolean valida, int i)
	{
		MiLabel label = new MiLabel("");
		
		label.setBounds( new Rectangle(70, 70) );
		label.setBorder( new LineBorder(Color.GRAY) );
		label.setId(i);
		
		// Evento arrastrar
		label.setTransferHandler( new TransferHandler("icon") );
		label.addPropertyChangeListener(pLT);
		
		// Es una posición no válida
		if ( !valida )
		{
			label.setIcon( vp.ajustarImagen(label, "/img/casilla_invalida.png") );
			label.setTransferHandler(null); // Evito que se suelte sobre una casilla inválida
		}
			
		// En la posición hay un invasor
		if ( invasor != null )
		{
			label.setNumeroInvasor(invasor.getNumero()); // Asigno el número del invasor a la etiqueta
			label.setIcon( vp.ajustarImagen(label, invasor.getImagen()) ); // Asigno la imagen del invasor
			label.setTransferHandler(null); // Evito que se suelte sobre una casilla ocupada
			
			// Si es líder lo marco
			if ( invasor.isLider() )
				label.setBorder( new LineBorder(Color.YELLOW) );
		}		
		
		return label;
	}
	
}
