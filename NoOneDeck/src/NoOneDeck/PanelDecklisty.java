package NoOneDeck;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

@SuppressWarnings("serial")
public class PanelDecklisty extends JFrame {

	private JPanel contentPane;
	private JTable tabelaMojeKarty;
	public List<String> listaKart = new ArrayList<String>();
	
	@SuppressWarnings("unused")
	private String filename;
	private String dir = null;	
	private JButton openFile;
	private JButton przyciskWczytaj;
	private JLabel nazwaKarty;
	
	public PanelDecklisty() throws SQLException {
			
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setVisible(true);	
		setResizable(false);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(37, 22, 352, 154);
		contentPane.add(scrollPane);
		
		tabelaMojeKarty = new JTable();
		tabelaMojeKarty.setModel(new DefaultTableModel(new Object[][] {},	new String[] {"","Nazwa", "Wlasciciel"}) {
			public boolean isCellEditable(int row, int column) {
				return false;
				}
		});
		tabelaMojeKarty.getColumnModel().getColumn(0).setMinWidth(0);
		tabelaMojeKarty.getColumnModel().getColumn(0).setMaxWidth(0);
		tabelaMojeKarty.getColumnModel().getColumn(1).setPreferredWidth(50);
		tabelaMojeKarty.getColumnModel().getColumn(1).setMinWidth(50);
		tabelaMojeKarty.getColumnModel().getColumn(2).setPreferredWidth(50);
		tabelaMojeKarty.getColumnModel().getColumn(2).setMaxWidth(50);
		scrollPane.setViewportView(tabelaMojeKarty);
		
		openFile = new JButton("Wybierz plik");
		openFile.addActionListener(new OpenL());
		openFile.setBounds(47, 187, 89, 23);
		contentPane.add(openFile);
		
		przyciskWczytaj = new JButton("Wczytaj");
		przyciskWczytaj.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (nazwaKarty.getText() != "" && nazwaKarty.getText().substring(nazwaKarty.getText().length()-4,nazwaKarty.getText().length()).equals(".ydk")) {
						if(dir != null) {
							List<String> decklista = new ArrayList<String>();
							try {
								decklista = wczytaniePliku(dir, nazwaKarty.getText());
								List<String> listaIlosciKart = new ArrayList<String>();
								listaIlosciKart = liczenieKart(decklista);
								List<String> listaWlascicieliKart = new ArrayList<String>();
								listaWlascicieliKart = generowanieListyUserow(listaIlosciKart);
								decklista = wczytaniePliku(dir, nazwaKarty.getText());
								listaKart = matchowanieList(decklista, listaWlascicieliKart);
								wczytanieTabeli(listaKart);	
							} catch (FileNotFoundException e1) {
								e1.printStackTrace();
							} catch (SQLException e1) {
								e1.printStackTrace();
							}
						}	
					}
				else {
					JOptionPane.showMessageDialog(PanelGlowny.panelDecklisty, "Brak pliku lub niew³aœciwy plik");
				}
			}
		});
		przyciskWczytaj.setBounds(276, 187, 89, 23);
		contentPane.add(przyciskWczytaj);
		
		nazwaKarty = new JLabel("");
		nazwaKarty.setHorizontalAlignment(SwingConstants.CENTER);
		nazwaKarty.setBounds(146, 191, 120, 14);
		nazwaKarty.setVisible(false);
		contentPane.add(nazwaKarty);
		tabelaMojeKarty.changeSelection(0, 0, false, false);
	}
	
	public void wczytanieTabeli(List <String> lista) {
		DefaultTableModel model = (DefaultTableModel)tabelaMojeKarty.getModel();
		if(model.getRowCount() > 0)
			PanelTwojeKarty.czyszczenieTabeli(model);
		
		for (int i = 0; i < listaKart.size( ); i++) {		
			if(lista.get(i).equals("#created by ..."))
				continue;
			else if(lista.get(i).equals("#main"))
				model.addRow(new Object [] {"", "Main Deck", ""});
			else if(lista.get(i).equals("#extra"))
				model.addRow(new Object [] {"", "Extra Deck", ""});
			else if(lista.get(i).equals("!side"))
				model.addRow(new Object [] {"", "Side Deck", ""});
			else {
				String[] podzial = lista.get(i).split(";");
				model.addRow(new Object [] {podzial[0], podzial[3], podzial[2]});
			}
		}
	}
	
	class OpenL implements ActionListener {
	    public void actionPerformed(ActionEvent e) {
	      JFileChooser c = new JFileChooser();
	      int rVal = c.showOpenDialog(PanelDecklisty.this);
	      if (rVal == JFileChooser.APPROVE_OPTION) {
	    	nazwaKarty.setText(c.getSelectedFile().getName());
	        dir = c.getCurrentDirectory().toString();
	        nazwaKarty.setVisible(true);
	      }
	    }
	 }
	
	public static void run(JFrame frame, int width, int height) {
	    frame.setSize(width, height);
	    frame.setVisible(true);
	}
	
	private List<String> wczytaniePliku(String directory, String file) throws FileNotFoundException {
		List<String> lista = new ArrayList<String>();
		File path = new File(directory+"\\"+file);
		Scanner myReader = new Scanner(path);						
		while (myReader.hasNextLine()) {
			 lista.add(myReader.nextLine());
		}
		myReader.close();
		return lista;
	}
	
	private List<String> liczenieKart (List<String> listaWczytana){
		List<String> lista = new ArrayList<String>();
		
		for(int i= 0; i<listaWczytana.size();) {
			if(listaWczytana.size() == 0)
				break;
			String tmp = null;
			
			if(listaWczytana.get(i).equals("#created by ...") || listaWczytana.get(i).equals("#main") || listaWczytana.get(i).equals("#extra") || listaWczytana.get(i).equals("!side"))
				listaWczytana.remove(i);
			if (listaWczytana.size()>0 && listaWczytana.get(i).equals("#main"))
				continue;
			else {
				if(listaWczytana.size()>0)
					tmp = listaWczytana.get(i);
				
				int j=0, licznik = 0;
				boolean warunek = true;
				while(warunek) {
						if(listaWczytana.size()>0 && listaWczytana.get(j).equals(tmp)){
							licznik++;
							listaWczytana.remove(j);
						}
						else {
							j++;
						}
						if(j+1 > listaWczytana.size()) {
							warunek = false;
							i=0;
						}		
				}
				if(tmp != null)
					lista.add(tmp+";"+String.valueOf(licznik));	
			}
		}
		return lista;
	}
	
	private List<String> generowanieListyUserow(List<String> lista) throws SQLException {
		List<String> listaUserow = new ArrayList <String>();
		
		for(String kart : lista) {
			String[] split = kart.split(";");
			String id = split[0];
			int count = Integer.parseInt(split[1]);
			int nbCardsNeeded = count;
			List<String> ownCards =  Baza.wyszukanieKart(id, PanelGlowny.zalogowanyUzytkownik);
			getCardsFromList(listaUserow, ownCards, nbCardsNeeded);			
			if(nbCardsNeeded != 0) {
				List<String> teamCards = Baza.WyszukanieKartTeamu(id, PanelGlowny.zalogowanyUzytkownik);
				getCardsFromList(listaUserow, teamCards, nbCardsNeeded);
			}
			
			while(nbCardsNeeded != 0) {
				listaUserow.add("0;"+id+";None;"+Baza.wyciagniecieNazwy(id));
				nbCardsNeeded--;
			}
		
		}
		return listaUserow;
	}
	
	private void getCardsFromList(List<String> userList, List<String> targetList, int currentNbCardsNeeded) {
		if(!targetList.isEmpty()) {
			if(targetList.size() >= currentNbCardsNeeded) {
				userList.addAll(targetList.subList(0, currentNbCardsNeeded));
				currentNbCardsNeeded = 0;
			} else {
				userList.addAll(targetList);
				currentNbCardsNeeded =- targetList.size();
			}
		}
	}
	
	
	private List<String> matchowanieList(List<String> listaWczytana, List<String> listaOpisana){
		List<String> lista = new ArrayList<String>();	
		for(int i= 0; i<listaWczytana.size(); i++) {
			if(listaWczytana.size() == 0)
				break;
			
			if(listaWczytana.get(i).equals("#created by ...") || listaWczytana.get(i).equals("#main") || listaWczytana.get(i).equals("#extra") || listaWczytana.get(i).equals("!side")) {
				lista.add(listaWczytana.get(i));
			}
				if(listaWczytana.size()>0) {
					if(listaWczytana.get(i).equals("#created by ...") || listaWczytana.get(i).equals("#main") || listaWczytana.get(i).equals("#extra") || listaWczytana.get(i).equals("!side"))
						continue;				
					else {
						String [] podzial = null;
						List<String> siema = new ArrayList<String>();
						for(int j=0;j<listaOpisana.size(); j++) {
							podzial = listaOpisana.get(j).split(";");
							siema.add(podzial[1]);
						}
						
						int index = siema.indexOf(listaWczytana.get(i));
						lista.add(listaOpisana.get(index));	
						listaOpisana.remove(index);
						siema.remove(index);
					}				
				}
			}		
		return lista;
	}
}
