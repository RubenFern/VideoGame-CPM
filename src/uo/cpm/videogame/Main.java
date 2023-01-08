package uo.cpm.videogame;

import java.awt.EventQueue;
import java.util.Properties;

import javax.swing.UIManager;

import com.jtattoo.plaf.hifi.HiFiLookAndFeel;

import uo.cpm.videogame.service.Game;
import uo.cpm.videogame.service.GestionPremios;
import uo.cpm.videogame.ui.VentanaPrincipal;

public class Main 
{
	/**
	 * 0 = 5 invasores aleatorios y uno de tipo líder
	 * 1 = 5 invasores no aleatorios y todos de tipo líder
	 * 2 = 5 invasores no aleatorios y todos de tipo no líder
	 */
	public final static int DEBUG = 0;
	
	public static void main(String[] args) 
	{
		Game game = new Game();
		GestionPremios gestionPremios = new GestionPremios();
		
		EventQueue.invokeLater(new Runnable() 
		{	
			public void run() {
				try {			
					Properties props = new Properties();
					props.put("logoString", "");
			    	HiFiLookAndFeel.setCurrentTheme(props);
					UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
					
					VentanaPrincipal frame = new VentanaPrincipal(game, gestionPremios);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
