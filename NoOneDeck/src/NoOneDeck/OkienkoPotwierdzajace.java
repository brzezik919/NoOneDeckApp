package NoOneDeck;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.awt.Window;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class OkienkoPotwierdzajace extends JFrame {

	private JPanel contentPane;

	public OkienkoPotwierdzajace(String idWybranejKarty) {
		setBounds(100, 100, 180, 167);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setVisible(true);
		setResizable(false);
		
		JLabel potwierdzenieNapis = new JLabel("Czy napewno chcesz to zrobi\u0107?");
		potwierdzenieNapis.setBounds(10, 37, 149, 14);
		contentPane.add(potwierdzenieNapis);
		
		JButton guzikTak = new JButton("Tak");
		guzikTak.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Baza.usuniecieKarty(idWybranejKarty);
					((Window) PanelTwojeKarty.okienkoPotwierdzajace).dispose();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		guzikTak.setBounds(10, 79, 63, 23);
		contentPane.add(guzikTak);
		
		JButton guzikNie = new JButton("Nie");
		guzikNie.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				((Window) PanelTwojeKarty.okienkoPotwierdzajace).dispose();
			}
		});
		guzikNie.setBounds(91, 79, 63, 23);
		contentPane.add(guzikNie);
	}
}
