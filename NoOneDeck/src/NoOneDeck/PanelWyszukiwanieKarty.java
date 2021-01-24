package NoOneDeck;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;
import javax.swing.JScrollPane;

@SuppressWarnings("serial")
public class PanelWyszukiwanieKarty extends JFrame {

	private JPanel contentPane;
	private JTextField tekstWyszukwianieKarty;
	private JTable tabelaWyszukiwania;


	public PanelWyszukiwanieKarty() {
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setVisible(true);
		setResizable(false);
		
		tekstWyszukwianieKarty = new JTextField();
		tekstWyszukwianieKarty.setBounds(10, 11, 325, 20);
		contentPane.add(tekstWyszukwianieKarty);
		tekstWyszukwianieKarty.setColumns(10);
		
		JScrollPane tabelaWyszukiwanieKarty = new JScrollPane();
		tabelaWyszukiwanieKarty.setBounds(10, 56, 414, 182);
		contentPane.add(tabelaWyszukiwanieKarty);
		
		tabelaWyszukiwania = new JTable();
		tabelaWyszukiwania.setModel(new DefaultTableModel(new Object[][] {}, new String[] {"Nazwa", "Ilosc", "Wlasciciel"})
		{
			public boolean isCellEditable(int row, int column) {
				return false;
			}
				});
		tabelaWyszukiwania.getColumnModel().getColumn(0).setPreferredWidth(33);
		tabelaWyszukiwania.getColumnModel().getColumn(0).setMinWidth(33);
		tabelaWyszukiwania.getColumnModel().getColumn(1).setPreferredWidth(33);
		tabelaWyszukiwania.getColumnModel().getColumn(1).setMinWidth(33);
		tabelaWyszukiwania.getColumnModel().getColumn(2).setPreferredWidth(33);
		tabelaWyszukiwania.getColumnModel().getColumn(2).setMinWidth(33);
		tabelaWyszukiwanieKarty.setViewportView(tabelaWyszukiwania);
		
		JButton guzikWyszukiwanieKarty = new JButton("Ok");
		guzikWyszukiwanieKarty.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			
			String nazwaKartyWyszukiwanej = tekstWyszukwianieKarty.getText();
			int iloscKart = 0;
			List<String> wlasciciel = null;
			try {
				wlasciciel = Baza.wyszukanieWlascicieli(nazwaKartyWyszukiwanej);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			DefaultTableModel model = (DefaultTableModel)tabelaWyszukiwania.getModel();
			for (int i = 0; i < wlasciciel.size(); i++) {
			try {
				iloscKart = Baza.wyszukanieIlosci(wlasciciel.get(i), nazwaKartyWyszukiwanej);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			model.addRow(new Object [] {nazwaKartyWyszukiwanej, iloscKart, wlasciciel.get(i)});
			}
		}
		});
		guzikWyszukiwanieKarty.setBounds(345, 10, 85, 23);
		contentPane.add(guzikWyszukiwanieKarty);
	}
}
