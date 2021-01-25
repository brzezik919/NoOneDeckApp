package NoOneDeck;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;

public class PanelGlowny extends JFrame {

	private static final long serialVersionUID = 1L;
	protected static String zalogowanyUzytkownik;
	protected static PanelDecklisty panelDecklisty;
	protected static PanelTwojeKarty panelTwojeKarty;
	protected static PanelWyszukiwanieKarty panelWyszukanieKarty;
	private JPanel contentPane;
	public PanelGlowny(String login) {
		zalogowanyUzytkownik = login;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 200, 260);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setVisible(true);
		setResizable(false);
		
		JButton przyciskTwojeKarty = new JButton("Twoje Karty");
		przyciskTwojeKarty.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						try {	
							panelTwojeKarty = new PanelTwojeKarty (zalogowanyUzytkownik);
							panelTwojeKarty.setTitle("NoOneApp");
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
		    }
		});
		przyciskTwojeKarty.setBounds(36, 55, 115, 23);
		contentPane.add(przyciskTwojeKarty);
		
		JButton przyciskWyszukajKarte = new JButton("Wyszukaj karte");
		przyciskWyszukajKarte.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						try {	
							panelWyszukanieKarty = new PanelWyszukiwanieKarty();
							panelWyszukanieKarty.setTitle("NoOneApp");
						}catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
			}
		});
		przyciskWyszukajKarte.setBounds(36, 89, 115, 23);
		contentPane.add(przyciskWyszukajKarte);
		
		JButton przyciskDecklista = new JButton("Decklista");
		przyciskDecklista.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				EventQueue.invokeLater(new Runnable(){
					public void run() {
						try {
							panelDecklisty = new PanelDecklisty();
							panelDecklisty.setTitle("NoOneApp");
						} catch (SQLException e) {
							e.printStackTrace();
						}
						
					}	
				});
			}
		});
		przyciskDecklista.setBounds(36, 123, 115, 23);
		contentPane.add(przyciskDecklista);
		
		JButton buttonLogout = new JButton("Wyloguj");
		buttonLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Logowanie.logOut();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		buttonLogout.setBounds(36, 157, 115, 23);
		contentPane.add(buttonLogout);
	}
}
