package org.generation.italy;

/**
* Classe per operazioni CRUD (Create/Read/Update/Delete) sul database, invio di comandi SQL
 * 
 * @author Angelo Pasquarelli
 *
 */

import java.sql.Connection; //classe istanziata per mantenere il riferimento alla connessione stabilita verso il database 
import java.sql.DriverManager; //classe di riferimento per l'uso del JDBC driver installato come dipendenza nel pom.xml di Maven										
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException; //imposta classe SqlException per gestione errori nella comunicazione col database
import java.util.ArrayList;
import java.util.List;

public class JdbcCrudSelect {

	public static void main(String[] args) { // metodo di accesso all'applicazione da parte della Java Virtual Machine
												// (vedi slide architettura Java)

		// Connessione al database
		String databaseName = "magazzino"; // nome del database a cui connettersi
		String dbmsUserName = "root"; // nome utente configurato nel dbms
		String dbmsPassword = ""; // password utente configurato nel dbms
		String jdbcUrl = "jdbc:mariadb://localhost:3306/" + databaseName;

		try { // prova ad eseguire le istruzioni interne al blocco try-catch

			/****************************************************************************/
			/* CONNESSIONE AL DATABASE */
			/****************************************************************************/
			Connection jdbcConnectionToDatabase =
					DriverManager.getConnection(jdbcUrl, dbmsUserName, dbmsPassword);

			System.out.println("Connessione al database magazzino riuscita!");

			/****************************************************************************/
			/* ESECUZIONE DELLA QUERY DI SELECT */
			/****************************************************************************/

			List<Prodotto> prodotti = querySelectTV(jdbcConnectionToDatabase);
			
			for (Prodotto televisore: prodotti) {
				if (televisore != null) {
					System.out.println("Dati del prodotto => " + televisore.toString());
				} else {
					System.out.println("Il prodotto ricercato non e presente!!!");
				}
			}
			
			/****************************************************************************/
			/* ESECUZIONE DELLA QUERY DI INSERT */
			/****************************************************************************/

			Prodotto prodotto = new Prodotto("TVLGC200", "Televisore LG C200", 10, 249.90f);

			queryInsertTV(jdbcConnectionToDatabase, prodotto);

			/****************************************************************************/
			/* ESECUZIONE DELLA QUERY DI SELECT AGGIORNATA*/
			/****************************************************************************/
			
			prodotti = querySelectTV(jdbcConnectionToDatabase);

			for (Prodotto televisore : prodotti) {
				if (televisore != null) {
					System.out.println("Dati del prodotto => " + televisore.toString());
				} else {
					System.out.println("Il cliente ricercato non e presente!!!");
				}
			}

		} catch (SQLException e) { // errore di tipo classe SQLException
			// TODO Auto-generated catch block
			e.printStackTrace(); // stampa la pila (stack) degli errori, dal pi� recente al meno recente
		}

	}

	/*
	 * METODO PER LA QUERY DI SELECT DEI PRODOTTI DI TIPO TELEVISORE
	 * */
	static ArrayList<Prodotto> querySelectTV(Connection connection) throws SQLException{
		String selectFromClienteByCodiceProdotto = // imposta il testo del comando SQL da eseguire
				" SELECT codice_prodotto, descrizione, quantita_disponibile, prezzo  "
			  + "   FROM prodotto                       " 
			  + "  WHERE prodotto.codice_prodotto LIKE ? ";
		
		String parametroCodiceProdotto = "TV%";
		PreparedStatement preparedStatement = // predispone JDBC per l'invio al database del comando SQL
				connection.prepareStatement(selectFromClienteByCodiceProdotto);
		preparedStatement.setString(1, parametroCodiceProdotto);
		
		ResultSet rsSelect = preparedStatement.executeQuery();
		
		ArrayList<Prodotto> prodotti = new ArrayList<Prodotto>();
		
		while (rsSelect.next()) { // fino a che ci sono risutalti da leggere

			String codProdotto = rsSelect.getString("codice_prodotto"); // lettura del valore del campo
																		// codice_fiscale
			if (rsSelect.wasNull()) {
				codProdotto = "";
			}

			String descrizione // lettura del valore del campo 'nominativo'
					= rsSelect.getString("descrizione");
			if (rsSelect.wasNull()) {
				descrizione = "";
			}

			int quantita_disponibile = rsSelect.getInt("quantita_disponibile");
			if (rsSelect.wasNull()) {
				quantita_disponibile = 0;
			}

			float prezzo = rsSelect.getFloat("prezzo");
			if (rsSelect.wasNull()) {
				prezzo = 0;
			}

			prodotti.add(new Prodotto(codProdotto, descrizione, quantita_disponibile, prezzo));
		}
		
		return prodotti;	
	}
	
	static void queryInsertTV(Connection connection, Prodotto prodotto) throws SQLException {
		String clienteToInsert = "INSERT INTO prodotto (codice_prodotto, descrizione, quantita_disponibile, prezzo) "
				+ "	VALUES (?,?,?,?)";
		
		PreparedStatement preparedStatementInsert = connection.prepareStatement(clienteToInsert);

		preparedStatementInsert.setString(1, prodotto.getCodiceProdotto());
		preparedStatementInsert.setString(2, prodotto.getDescrizione());
		preparedStatementInsert.setInt(3, prodotto.getQuantita_disponibile());
		preparedStatementInsert.setFloat(4, prodotto.getPrezzo());

		preparedStatementInsert.executeUpdate();

		System.out.println("Prodotto inserito: => " + prodotto.toString());
	}
	
}
