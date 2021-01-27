package NoOneDeck;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionListener;
import java.awt.Window;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class PanelDodanieKarty extends JFrame {

	private JPanel contentPane;
	private JTextField textFieldDodajKarte;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public PanelDodanieKarty() {
		setBounds(100, 100, 450, 131);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setVisible(true);
		setResizable(false);
		
		textFieldDodajKarte = new JTextField();
		textFieldDodajKarte.setBounds(10, 31, 348, 20);
		contentPane.add(textFieldDodajKarte);
		textFieldDodajKarte.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Dodaj karte");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(177, 11, 73, 14);
		contentPane.add(lblNewLabel);
		
		JComboBox dodajKarteIlosc = new JComboBox();
		dodajKarteIlosc.setModel(new DefaultComboBoxModel(new String[] {"1", "2", "3"}));
		dodajKarteIlosc.setBounds(368, 30, 56, 22);
		contentPane.add(dodajKarteIlosc);
		
		JButton przyciskDodajKarte = new JButton("Ok");
		przyciskDodajKarte.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String nazwa = textFieldDodajKarte.getText();
				String ilosc = dodajKarteIlosc.getSelectedItem().toString();
				try {
						if(Baza.sprawdzKarte(nazwa)) {
							Baza.dodajKarte(nazwa, ilosc, PanelGlowny.zalogowanyUzytkownik);
							JOptionPane.showMessageDialog(PanelTwojeKarty.panelDodanieKarty, "Karta dodana prawidlowo");
							PanelTwojeKarty.wczytanieTabeli(PanelGlowny.zalogowanyUzytkownik);
							((Window) PanelTwojeKarty.panelDodanieKarty).dispose();
						}
						else
							JOptionPane.showMessageDialog(PanelTwojeKarty.panelDodanieKarty, "Bledna nazwa");
				} catch (Exception e1) {
					e1.printStackTrace();
				} 
			}
		});
		przyciskDodajKarte.setBounds(177, 58, 89, 23);
		contentPane.add(przyciskDodajKarte);
	}
}
