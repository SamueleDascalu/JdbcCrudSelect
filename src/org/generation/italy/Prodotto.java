package org.generation.italy;

public class Prodotto {
	private String codiceProdotto, descrizione;

	public Prodotto(String codiceProdotto, String descrizione) {
		this.codiceProdotto = codiceProdotto;
		this.descrizione = descrizione;
	}

	public String getCodiceProdotto() {
		return codiceProdotto;
	}

	public void setCodiceProdotto(String codiceProdotto) {
		this.codiceProdotto = codiceProdotto;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	@Override
	public String toString() {
		return "{" + "Codice: " + this.codiceProdotto + ", Descrizione: " + this.descrizione + "}";
	}
}
