package NoOneDeck;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class PanelTwojeKarty extends JFrame {

	protected static PanelEdycjiStanu panelEdycjiStanu;
	protected static PanelDodanieKarty panelDodanieKarty;
	protected static OkienkoPotwierdzajace okienkoPotwierdzajace;
	private JPanel contentPane;
	private static JTable tabelaMojeKarty;
	public static List<String> listaKart = new ArrayList<String>();
	
	
	public PanelTwojeKarty(String zalogowanyUzytkownik) throws SQLException {
				
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setVisible(true);
		setResizable(false);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setEnabled(false);
		scrollPane.setBounds(37, 22, 352, 154);
		contentPane.add(scrollPane);
		
		tabelaMojeKarty = new JTable();
		tabelaMojeKarty.setModel(new DefaultTableModel(new Object[][] {},	new String[] {"","Nazwa", ""}){
			public boolean isCellEditable(int row, int column) {
				return false;
				}
			});
		tabelaMojeKarty.getColumnModel().getColumn(0).setMinWidth(0);
		tabelaMojeKarty.getColumnModel().getColumn(0).setMaxWidth(0);
		tabelaMojeKarty.getColumnModel().getColumn(1).setPreferredWidth(50);
		tabelaMojeKarty.getColumnModel().getColumn(1).setMinWidth(50);
		tabelaMojeKarty.getColumnModel().getColumn(2).setMinWidth(50);
		tabelaMojeKarty.getColumnModel().getColumn(2).setMaxWidth(50);
		scrollPane.setViewportView(tabelaMojeKarty);
		wczytanieTabeli(zalogowanyUzytkownik);
		tabelaMojeKarty.changeSelection(0, 0, false, false);
		JButton guzikDodajKarte = new JButton("Dodaj Karte");
		guzikDodajKarte.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						try {	
							panelDodanieKarty = new PanelDodanieKarty();
							panelDodanieKarty.setTitle("NoOneApp");
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
			}
		});
		guzikDodajKarte.setBounds(58, 204, 89, 23);
		contentPane.add(guzikDodajKarte);
		
		JButton guzikEdytajKarte = new JButton("Zmie\u0144 Stan");
		guzikEdytajKarte.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						try {
							String idWybranejKarty = tabelaMojeKarty.getModel().getValueAt(tabelaMojeKarty.getSelectedRow(),0).toString();
							String wybranaKarta = tabelaMojeKarty.getModel().getValueAt(tabelaMojeKarty.getSelectedRow(),1).toString();
							String stan = tabelaMojeKarty.getModel().getValueAt(tabelaMojeKarty.getSelectedRow(),2).toString();
							panelEdycjiStanu = new PanelEdycjiStanu(idWybranejKarty, wybranaKarta, stan);
							panelEdycjiStanu.setTitle("NoOneApp");
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
			}
		});
		guzikEdytajKarte.setBounds(157, 204, 89, 23);
		contentPane.add(guzikEdytajKarte);	
		
		JButton usunKarte = new JButton("Usu\u0144 Karte");
		usunKarte.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String idWybranejKarty = tabelaMojeKarty.getModel().getValueAt(tabelaMojeKarty.getSelectedRow(),0).toString();
				okienkoPotwierdzajace = new OkienkoPotwierdzajace(idWybranejKarty);
				if(okienkoPotwierdzajace == null)
					try {
						wczytanieTabeli(zalogowanyUzytkownik);
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
			}
		});
		usunKarte.setBounds(256, 204, 89, 23);
		contentPane.add(usunKarte);
	}
	
	public static void wczytanieTabeli(String user) throws SQLException {
		DefaultTableModel model = (DefaultTableModel)tabelaMojeKarty.getModel();
		if(model.getRowCount() > 0)
			czyszczenieTabeli(model);
		listaKart = Baza.wczytanieKart(user);
		for (int i = 0; i < listaKart.size( )- 2; i = i+3) {
			model.addRow(new Object [] {listaKart.get(i), listaKart.get(i+1), listaKart.get(i+2)});
		}
	}
	
	public static void czyszczenieTabeli(DefaultTableModel table) {
		int count = table.getRowCount();
		for(int i = count-1; i >= 0; i--)
			table.removeRow(i);
	}
}
