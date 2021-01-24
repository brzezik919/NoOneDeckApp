package NoOneDeck;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Baza {
	
	String baza;
    String nazwa;
    String haslo;
    String MySQLURL = "jdbc:mysql://localhost:3306/";
    static Connection con = null;
    
    public Baza(String baza, String nazwa, String haslo) {
    	this.baza = baza;
    	this.nazwa = nazwa;
    	this.haslo = haslo;
    	this.MySQLURL += baza;
    }
    
    public void polacz() throws Exception {
    	con = DriverManager.getConnection(MySQLURL, nazwa, haslo);
    	if (con != null) {
    		System.out.println("Database connection is successful !!!!");
    	}
    }
    
    public static boolean sprawdzenieLogowania(String user, String pass) throws Exception{
    	boolean wynik;
    	String p = null;
    	Statement stmt = con.createStatement();    	
    	ResultSet rs = stmt.executeQuery("select haslo from user where login =\""+user+"\"");
    	while(rs.next()) {
    		p = rs.getString("haslo");
    	}
    	if (p != null && p.equals(pass))
    		wynik = true;
    	else
    		wynik = false;
    	stmt.close();
    	return wynik;
    }

    public static void rozlacz() throws Exception {
    	con.close();
    	System.out.println("Rozlaczono");
    }

	public static void dodajKarte(String nazwaDodawanejKarty, String ilosc, String login) throws SQLException {
		Statement stmt = con.createStatement();
		
		String idNazwa = null;
		String idUser = null;
		ResultSet rs = stmt.executeQuery("select idNumer from numer where nazwa =\""+nazwaDodawanejKarty+"\"");
		while(rs.next()) {
			idNazwa = rs.getString("idNumer");
    	}
		
		rs = stmt.executeQuery("select idUser from user where login =\""+login+"\"");
		while(rs.next()) {
			idUser = rs.getString("idUser");
    	}
		
		for(int i = 1; i <= Integer.parseInt(ilosc); i++) {
			stmt.execute("insert into karta (idUser, idNazwa) values('"+idUser+"', '"+idNazwa+"')");
		} 	
    	stmt.close();
	}

	public static boolean sprawdzKarte(String nazwaKartySprawdzanej) throws SQLException {
		boolean wynik;
    	String p = null;
    	Statement stmt = con.createStatement();    	
    	ResultSet rs = stmt.executeQuery("select nazwa from numer where nazwa =\""+nazwaKartySprawdzanej+"\"");
    	while(rs.next()) {
    		p = rs.getString("nazwa");
    	}
    	if (p != null)
    		wynik = true;
    	else
    		wynik = false;
    	stmt.close();
    	return wynik;	
	}

	public static List<String> wyszukanieWlascicieli(String nazwaKartyWyszukiwanej) throws SQLException {
		Statement stmt = con.createStatement();
		List<String> wynik = new ArrayList<String>();
		
		//Poprawa wyszukiwania po czesci nazwy np 'Lord'
		//ResultSet rs = stmt.executeQuery("select DISTINCT login from user inner join karta on user.idUser = karta.idUser inner join numer on karta.idNazwa = numer.idNumer where numer.nazwa LIKE '%"+nazwaKartyWyszukiwanej+"%'");
		ResultSet rs = stmt.executeQuery("select DISTINCT login from user inner join karta on user.idUser = karta.idUser inner join numer on karta.idNazwa = numer.idNumer where numer.nazwa LIKE '"+nazwaKartyWyszukiwanej+"'");
		while(true) {
			if(rs.next()) {
				wynik.add(rs.getString("login"));
			}
			else {
				break;
			}
		}	
		stmt.close();
		return wynik;
	}

	public static int wyszukanieIlosci(String wlasciciel, String nazwa) throws SQLException {
		Statement stmt = con.createStatement();
		String wynik = null;
		ResultSet rs = stmt.executeQuery("select COUNT(idKarta) from karta inner join user on karta.idUser = user.idUser inner join numer on karta.idNazwa = numer.idNumer where (user.login ='"+wlasciciel+"' and numer.nazwa ='"+nazwa+"')");
		while(true) {
			if(rs.next()) {
				wynik = rs.getString("COUNT(idKarta)");
			}
			else {
				break;
			}
		}
		stmt.close();
		return Integer.parseInt(wynik);
	}

	public static List<String> wczytanieKart(String user) throws SQLException {
		Statement stmt = con.createStatement();
		List<String> wynik = new ArrayList<String>();
		ResultSet rs = stmt.executeQuery("select idKarta, nazwa, stan from numer inner join karta on numer.idNumer = karta.idNazwa inner join user on karta.idUser = user.idUser where login = '"+user+"'");
		while(true) {
			if(rs.next()) {
				wynik.add(rs.getString("idKarta"));
				wynik.add(rs.getString("nazwa"));
				wynik.add(rs.getString("stan"));
			}
			else {
				break;
			}
		}
		stmt.close();
		return wynik;
	}

	public static void zmianaStanu(String stan, String idWybranejKarty) throws SQLException {
		Statement stmt = con.createStatement();
		stmt.execute("update karta set stan = '"+stan+"' where idKarta = '"+idWybranejKarty+"'");
    	stmt.close();
	}

	public static void usuniecieKarty(String idWybranejKarty) throws SQLException {
		Statement stmt = con.createStatement();
		stmt.execute("delete from karta where idKarta = '"+idWybranejKarty+"'");
    	stmt.close();
	}

	public static List<String> wyszukanieKart(String id, String zalogowanyUzytkownik) throws SQLException {	
		Statement stmt = con.createStatement();
		List<String> wynik = new ArrayList<String>();
		ResultSet rs = stmt.executeQuery("select idKarta, idNazwa, nazwa from karta inner join user on karta.idUser = user.idUser inner join numer on karta.idNazwa = numer.idNumer where (user.login =\""+zalogowanyUzytkownik+"\" and idNazwa ='"+id+"')");
		while(true) {
			if(rs.next()) {
				wynik.add(rs.getString("idKarta")+";"+rs.getString("idNazwa")+";"+zalogowanyUzytkownik+";"+rs.getString("nazwa"));
			}
			else {
				break;
			}
		}	
		stmt.close();
		return wynik;
	}

	public static List<String> WyszukanieKartTeamu(String id, String zalogowanyUzytkownik) throws SQLException {
		Statement stmt = con.createStatement();
		List<String> wynik = new ArrayList<String>();
		ResultSet rs = stmt.executeQuery("select idKarta, idNazwa, login, nazwa from karta inner join user on karta.idUser = user.idUser inner join numer on karta.idNazwa = numer.idNumer  where (idNazwa ='"+id+"' and stan = 'Wolne' and not user.login ='"+zalogowanyUzytkownik+"')");
		while(true) {
			if(rs.next()) {
				wynik.add(rs.getString("idKarta")+";"+rs.getString("idNazwa")+";"+rs.getString("login")+";"+rs.getString("nazwa"));
			}
			else {
				break;
			}
		}	
		stmt.close();
		return wynik;
	}
	
	public static String wyciagniecieNazwy(String id) throws SQLException {
		Statement stmt = con.createStatement();
		String nazwa = null;
		ResultSet rs = stmt.executeQuery("select nazwa from numer where idNumer = '"+id+"'");
		while(true) {
			if(rs.next()) {
				nazwa = rs.getString("nazwa");
			}
			else {
				break;
			}
		}	
		stmt.close();
		return nazwa;
	}
}
