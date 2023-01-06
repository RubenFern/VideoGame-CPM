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
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import java.awt.FlowLayout;
import javax.swing.SwingConstants;
import javax.swing.TransferHandler;
import javax.swing.JTextField;
import java.awt.Font;

import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import uo.cpm.videogame.model.Casilla;
import uo.cpm.videogame.model.Invasor;
import uo.cpm.videogame.model.MiLabel;
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
	private JPanel pnComoJugar;
	private JButton btComoJugar;
	
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
	
	// Inicializa los datos de la pantalla
	public void inicializar()
	{
		this.getTxtPuntos().setText( String.format("%d", game.getPuntos()) );
		this.getLbRonda().setText( String.format("%s %d/%d", vp.getInternacionalizar().getTexto("juego.ronda"), game.getRonda(), Reglas.RONDAS.getValor()) );
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
			
			// Posici�n del invasor que se arrastra
			int posicionInvasor = ((MiLabel) c).getId();

			// Bloqueo los dem�s movimientos para que solo pueda colocar invasores en el tablero
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
			// Si hubo un cambio en la propiedad ImageIcon significa que se arrastr� un invasor
			if ( e.getPropertyName().equals("icon") && (e.getNewValue() != null) && (e.getNewValue().getClass().toString().contains("ImageIcon") ))
			{
				// El usuario arrastr� un invasor
				if ( game.isArrastra() )
				{
					MiLabel lbPulsada = (MiLabel) e.getSource();
					lbPulsada.setTransferHandler(null); // Impido volver a colocar un invasor en esa posici�n

					// Guardo la posici�n del tablero donde se coloc� el invasor
					int posicionTablero = lbPulsada.getId();
					game.setPosicionTableroCasilla(posicionTablero);
					
					// Resto 1 movimiento en la ronda
					game.disminuirMovimientos();

					// Añado el invasor en la casilla
					game.anadirInvasorAlTablero( game.getCasilla() );
					
					// Marco el movimiento como usado
					marcarMovimientoUsado(movimiento);
					
					// Si el invasor movido es el líder lo marco
					if ( game.getCasilla().getInvasor().isLider() )
						marcarLider(game.getCasilla().getPosicionTablero());
					
					// Compruebo el estado de la ronda o si el tablero est� lleno
					if ( game.getMovimientos() == 0 )
						siguienteRonda();
					/*else if ( game.getNumeroInvasoresTablero() == game.getNumeroInvasoresTableroMaximo() )
						finalizarPartida(false);*/
					
					//game.imprimirTablero();
										
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
	 * Comprueba el n�mero de movimientos del usuario, cuado llegan a 0 pasa a la siguiente ronda
	 * y comprueba los puntos 
	 */
	private void siguienteRonda()
	{
		// Aumento la ronda
		game.aumentarRonda();
		
		int puntosGanados = game.eliminarColonias();
		
		
		// Si retornó puntos significa que hubo eliminación de colonias
		if ( puntosGanados > 0 )
		{		
			this.getPnTablero().removeAll();
			pintaTablero();
			
			this.getTxtPuntos().setText( String.format("%d", game.getPuntos() ));
		}
		
		if ( !compruebaFin(game.isPartidaFinalizada()) )
		{
			// Imprimo la ronda
			this.getLbRonda().setText( String.format("%s %d/%d", vp.getInternacionalizar().getTexto("juego.ronda"), game.getRonda(), Reglas.RONDAS.getValor() ) );
			
			// Genero nuevo invasores
			generarNuevosMovimientos();
		}
	}
	
	private boolean compruebaFin(boolean finalizada)
	{	
		// Si se ha llenado el tablero de invasores (aunque está en la ronda 10 finaliza con derrota)
		if ( game.getNumeroInvasoresTablero() == game.getNumeroInvasoresTableroMaximo() )
		{
			finalizarPartida(false);
			return true;
		}
		
		// Superó la ronda 10
		if ( finalizada || game.getRonda() > Reglas.RONDAS.getValor() )
		{
			finalizarPartida(true);
			return true;
		}
		
		return false;
	}
	
	private void generarNuevosMovimientos()
	{
		game.setMovimientos( Reglas.INVASORES_POR_RONDA.getValor() );
		
		this.getPnMovimientos().removeAll();
		pintaMovimientos();
	}
	
	private void finalizarPartida(boolean victoria)
	{		
		if (victoria) 
		{
			this.getPnTablero().validate();

			JOptionPane.showMessageDialog(this, vp.getInternacionalizar().getTexto("juego.victoria"),
					game.getNombreTienda(), JOptionPane.INFORMATION_MESSAGE,
					new ImageIcon( game.getIconoTienda() ));
			
			vp.getPnPantallaPremios().getTxtPuntos().setText( String.format("%d", game.getPuntos()) );
			vp.mostrarPantallaPremios();
		}
		else 
		{
			game.setPuntos(0);
			JOptionPane.showMessageDialog(this, vp.getInternacionalizar().getTexto("juego.derrota"),
					game.getNombreTienda(), JOptionPane.INFORMATION_MESSAGE, new ImageIcon(game.getIconoTienda()));

			// Inicializa y vuelve a la pantalla de inicio
			vp.inicializarJuego();
			
			vp.mostrarPantallaInicio();
		}
	}
	
	private JPanel getPnNorte() {
		if (pnNorte == null) {
			pnNorte = new JPanel();
			pnNorte.setBorder(new EmptyBorder(15, 0, 20, 0));
			pnNorte.setLayout(new GridLayout(0, 4, 0, 0));
			pnNorte.add(getPnSalir());
			pnNorte.add(getPnPuntos());
			pnNorte.add(getPnRonda());
			pnNorte.add(getPnComoJugar());
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
	
	public JButton getBtSalir() {
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
	
	public JLabel getLbPuntos() {
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
	
	public JLabel getLbRonda() {
		if (lbRonda == null) {
			lbRonda = new JLabel("");
			lbRonda.setFont(new Font("Tahoma", Font.BOLD, vp.getH3()));
			lbRonda.setText( String.format("%s %d/%d", vp.getInternacionalizar().getTexto("juego.ronda"), game.getRonda(), Reglas.RONDAS.getValor()) );
		}
		return lbRonda;
	}
	private JTextField getTxtPuntos() {
		if (txtPuntos == null) {
			txtPuntos = new JTextField();
			txtPuntos.setHorizontalAlignment(SwingConstants.CENTER);
			txtPuntos.setText( String.format("%d", game.getPuntos()) );
			txtPuntos.setEditable(false);
			txtPuntos.setFont(new Font("Tahoma", Font.BOLD, vp.getH3()));
			txtPuntos.setColumns(10);
		}
		return txtPuntos;
	}
	public JPanel getPnTablero() {
		if (pnTablero == null) {
			pnTablero = new JPanel();
			pnTablero.setBorder(new LineBorder(new Color(0, 0, 0)));
			pnTablero.setLayout(new GridLayout(7, 7, 0, 0));
		
			pintaTablero();
		}
		
		return pnTablero;
	}
	
	public void pintaTablero()
	{
		Casilla[] tablero = game.getTablero();
		
		for ( int i = 0; i < tablero.length; i++ )
			pnTablero.add(pintaCasilla( tablero[i].getInvasor(), game.EsPosicionValida(i), i ) );
	}
	
	public void pintaMovimientos()
	{
		Invasor[] movimientos = game.getRondaInvasores();
		
		for ( int i = 0; i < movimientos.length; i++ )
			pnMovimientos.add(pintaMovimiento(movimientos[i], i));
	}
	
	public JPanel getPnMovimientos() {
		if (pnMovimientos == null) {
			pnMovimientos = new JPanel();
			pnMovimientos.setLayout(new GridLayout(1, 5, 0, 0));
			
			pintaMovimientos();
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
		
		// Asigno el n�mero del invasor a la etiqueta
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
		if ( valida && invasor == null )
		{
			label.setIcon(  vp.ajustarImagen(label, "/img/casilla_libre.jpg") );
			label.setTransferHandler( new TransferHandler("icon") );
			label.addPropertyChangeListener(pLT);
		}
		
		// Es una posición no válida
		if ( !valida )
			label.setIcon( vp.ajustarImagen(label, "/img/casilla_invalida.jpg") );
		
		// En la posición hay un invasor
		if ( invasor != null )
		{
			label.setNumeroInvasor(invasor.getNumero()); // Asigno el número del invasor a la etiqueta
			label.setIcon( vp.ajustarImagen(label, invasor.getImagen()) ); // Asigno la imagen del invasor
			
			// Si es líder lo marco
			if ( invasor.isLider() )
				label.setBorder( new LineBorder(Color.YELLOW) );
		}		
		
		return label;
	}
	private JPanel getPnComoJugar() {
		if (pnComoJugar == null) {
			pnComoJugar = new JPanel();
			pnComoJugar.add(getBtComoJugar());
		}
		return pnComoJugar;
	}
	public JButton getBtComoJugar() {
		if (btComoJugar == null) {
			btComoJugar = new JButton("");
			btComoJugar.setFont(new Font("Tahoma", Font.BOLD, vp.getH3()));
			btComoJugar.setText( vp.getInternacionalizar().getTexto("boton.comojugar") );
		}
		return btComoJugar;
	}
}
