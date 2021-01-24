package NoOneDeck;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.awt.Window;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class PanelEdycjiStanu extends JFrame {

	private JPanel contentPane;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public PanelEdycjiStanu(String idWybranejKarty, String wybranaKarta, String stanKarty) {
		setBounds(100, 100, 250, 165);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setVisible(true);
		setResizable(false);
		
		JLabel nazwaKarty = new JLabel(wybranaKarta);
		nazwaKarty.setHorizontalAlignment(SwingConstants.CENTER);
		nazwaKarty.setBounds(10, 21, 214, 14);
		contentPane.add(nazwaKarty);
		
		JComboBox comboBoxZmianaUzycia = new JComboBox();
		comboBoxZmianaUzycia.setModel(new DefaultComboBoxModel(new String[] {"Wolne", "Kupione", "W uzyciu"}));
		comboBoxZmianaUzycia.setBounds(66, 46, 100, 22);
		contentPane.add(comboBoxZmianaUzycia);
		comboBoxZmianaUzycia.setSelectedItem(stanKarty);
		
		JButton guzikEdycjaUzycia = new JButton("Ok");
		guzikEdycjaUzycia.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String stan = (String)comboBoxZmianaUzycia.getSelectedItem();
				try {
					
					Baza.zmianaStanu(stan, idWybranejKarty);
					PanelTwojeKarty.wczytanieTabeli();
					((Window) PanelTwojeKarty.panelEdycjiStanu).dispose();					
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		guzikEdycjaUzycia.setBounds(78, 79, 89, 23);
		contentPane.add(guzikEdycjaUzycia);
	}
}
