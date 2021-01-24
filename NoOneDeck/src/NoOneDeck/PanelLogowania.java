package NoOneDeck;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Window;

import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;

public class PanelLogowania extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField poleLogin;
	private static JPasswordField poleHaslo;
	public static String login;

	public PanelLogowania() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 303, 188);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setVisible(true);
		setResizable(false);
		
		JLabel labelLogin = new JLabel("Login: ");
		labelLogin.setFont(new Font("Tahoma", Font.PLAIN, 18));
		labelLogin.setBounds(28, 26, 64, 36);
		contentPane.add(labelLogin);
		
		JLabel labelHaslo = new JLabel("Has\u0142o: ");
		labelHaslo.setFont(new Font("Tahoma", Font.PLAIN, 18));
		labelHaslo.setBounds(28, 65, 64, 28);
		contentPane.add(labelHaslo);
		
		poleLogin = new JTextField();
		poleLogin.setBounds(102, 37, 131, 20);
		contentPane.add(poleLogin);
		poleLogin.setColumns(10);
		
		poleHaslo = new JPasswordField();
		poleHaslo.setBounds(102, 72, 131, 20);
		contentPane.add(poleHaslo);
		poleHaslo.setColumns(10);
		
		JButton przyciskZaloguj = new JButton("Zaloguj");
		przyciskZaloguj.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
			
				String login = poleLogin.getText();
				String haslo = getPassword(poleHaslo);
					
				boolean potwierdzenie = false;
				try {
					potwierdzenie = Baza.sprawdzenieLogowania(login, haslo);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				if (potwierdzenie){
					try {
						@SuppressWarnings("unused")
						PanelGlowny menu = new PanelGlowny(login);
						((Window) Logowanie.frame).dispose();
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
				else {
					PanelLogowania frame2 = new PanelLogowania();
					
					JOptionPane.showMessageDialog(frame2, "Nieprawidlowy login lub haslo");
					poleLogin.setText("");
					poleHaslo.setText("");
				}
			}
		});
		przyciskZaloguj.setBounds(102, 105, 89, 23);
		contentPane.add(przyciskZaloguj);
	}
	
	public static String getPassword(JPasswordField nazwaPola) {
		String pass = "";
		char[] tmp = nazwaPola.getPassword();
		for(int i=0; i<tmp.length; i++) {
			pass += tmp[i];
		}
		return pass;
	}
}
