package org.generation.italy;

public class Prodotto {
	private String codiceProdotto, descrizione;
	private int quantita_disponibile;
	private float prezzo;

	public Prodotto(String codiceProdotto, String descrizione, int quantita_disponibile, float prezzo) {
		this.codiceProdotto = codiceProdotto;
		this.descrizione = descrizione;
		this.quantita_disponibile=quantita_disponibile;
		this.prezzo=prezzo;
	}

	public int getQuantita_disponibile() {
		return quantita_disponibile;
	}

	public void setQuantita_disponibile(int quantita_disponibile) {
		this.quantita_disponibile = quantita_disponibile;
	}

	public float getPrezzo() {
		return prezzo;
	}

	public void setPrezzo(float prezzo) {
		this.prezzo = prezzo;
	}

	public String getCodiceProdotto() {
		return codiceProdotto;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	@Override
	public String toString() {
		return "\n{" + "Codice: " + this.codiceProdotto + ", Descrizione: " + this.descrizione + "Quantità: " + quantita_disponibile+ "Prezzo: " + prezzo+ "}\n";
	}
}
