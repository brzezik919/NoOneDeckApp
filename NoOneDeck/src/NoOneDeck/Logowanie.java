package NoOneDeck;

import java.awt.EventQueue;

public class Logowanie {
	protected static PanelLogowania frame;
	
	public static void logIn() throws Exception {
		
		Main.baza.polacz();
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					
					frame = new PanelLogowania();
					frame.setTitle("NoOneApp");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public static void logOut() throws Exception {
			Baza.rozlacz();
			//((Window) PanelLogowania.menu).dispose();
			frame = new PanelLogowania();
			frame.setTitle("NoOneApp");
	}
}