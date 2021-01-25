package NoOneDeck;

import java.awt.EventQueue;

public class Logowanie {
	protected static PanelLogowania frame;
	
	public static void logIn() throws Exception {
		
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main.baza.polacz();
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
			PanelLogowania.menu.dispose();
			logIn();
	}
}