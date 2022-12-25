package uo.cpm.videogame;

import java.awt.EventQueue;
import java.util.Properties;

import javax.swing.UIManager;

import com.jtattoo.plaf.hifi.HiFiLookAndFeel;

import uo.cpm.videogame.service.Game;
import uo.cpm.videogame.ui.VentanaPrincipal;

public class Main {

	/**
	 * 0 = 5 invasores aleatorios y uno de tipo l�der
	 * 1 = 5 invasores no aleatorios y todos de tipo l�der
	 * 2 = 5 invasores no aleatorios y todos de tipo no l�der
	 */
	public final static int DEBUG = 0;
	
	public static void main(String[] args) 
	{
		Game game = new Game();
		
		EventQueue.invokeLater(new Runnable() 
		{	
			public void run() {
				try {			
					Properties props = new Properties();
					props.put("logoString", "");
			    	HiFiLookAndFeel.setCurrentTheme(props);
					UIManager.setLookAndFeel("com.jtattoo.plaf.hifi.HiFiLookAndFeel");
					
					VentanaPrincipal frame = new VentanaPrincipal(game);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
