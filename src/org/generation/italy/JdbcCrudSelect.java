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
			Connection jdbcConnectionToDatabase = // esegue la connessione al dbms con riferimento al database, se
													// fallisce genera eccezzione SQLException (effettuare il debugging
													// per verificarlo)
					DriverManager.getConnection(jdbcUrl, dbmsUserName, dbmsPassword);

			System.out.println("Connessione al database magazzino riuscita!"); // visualizza messaggio per avvenuta
																				// connessione al database

			/****************************************************************************/
			/* ESECUZIONE DELLA QUERY DI SELECT */
			/****************************************************************************/

			String selectFromClienteByCodiceProdotto = // imposta il testo del comando SQL da eseguire
							  " SELECT codice_prodotto, descrizione, quantita_disponibile, prezzo  "
							+ "   FROM prodotto                       "
							+ "  WHERE prodotto.codice_prodotto LIKE 'TV%' ";

			//String parametroCodiceProdotto = "'TV%'"; // imposta il valore del parametro codice fiscale

			PreparedStatement preparedStatement = // predispone JDBC per l'invio al database del comando SQL
					jdbcConnectionToDatabase.prepareStatement(selectFromClienteByCodiceProdotto);
			//preparedStatement.setString(1, parametroCodiceProdotto); // imposta il valore del parametro di ricerca
																		// codice
																		// fiscale (parametro String)

			ResultSet rsSelect = preparedStatement.executeQuery(); // esegue la query di SELECT e si predisone a leggere
																	// i risutlati presenti in memoria nel DBMS

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

				prodotti.add(new Prodotto(codProdotto, descrizione, quantita_disponibile, prezzo)); // istanzia un oggetto di tipo classe Cliente
																									// inizializzandolo con i valori letti dal record
			}
			
			for(Prodotto prodotto:prodotti) {
				if (prodotto != null) {
					System.out.println("Dati del prodotto => "+prodotto.toString());
				} else {
					System.out.println("Il cliente ricercato non e presente!!!");
				}
			}

		} catch (SQLException e) { // errore di tipo classe SQLException
			// TODO Auto-generated catch block
			e.printStackTrace(); // stampa la pila (stack) degli errori, dal pi� recente al meno recente
		}

	}

}
