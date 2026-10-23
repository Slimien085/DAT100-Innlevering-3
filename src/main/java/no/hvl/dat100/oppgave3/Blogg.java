package no.hvl.dat100.oppgave3;

import java.lang.reflect.Array;
import java.util.Arrays;

import no.hvl.dat100.common.TODO;
import no.hvl.dat100.oppgave1.*;
import no.hvl.dat100.oppgave2.Tekst;

public class Blogg {
	private Innlegg[] innleggtabell;
	private int nesteledig = 0;

	public Blogg() {
		this.innleggtabell = new Innlegg[20];
	}

	public Blogg(int lengde) {
		this.innleggtabell = new Innlegg[lengde];
	}

	public int getAntall() {
		return nesteledig;
	}
	
	public Innlegg[] getSamling() {
		return innleggtabell;
	}
	
	public int finnInnlegg(Innlegg innlegg) {
		for (int i = 0; i < nesteledig; i++) {
			if(innleggtabell[i].erLik(innlegg))
				return i;
		}
		return -1;
	}

	public boolean finnes(Innlegg innlegg) {
		for (int i = 0; i < nesteledig; i++) {
			if(innleggtabell[i].erLik(innlegg))
				return true;
		}
		return false;
	}

	public boolean ledigPlass() {
		return nesteledig < innleggtabell.length;
	}
	
	public boolean leggTil(Innlegg innlegg) {
		if (this.finnes(innlegg)) 
			return false;
		
		if (!ledigPlass()) 
			return false;
		
	 	this.innleggtabell[this.nesteledig] = innlegg;
		this.nesteledig++;
		return true;
	}
	
	public String toString() {
		String output = nesteledig + "\n";
		for(int i = 0; i < nesteledig; i++){
			output += innleggtabell[i].toString();
		}
		return output;
	}

	// valgfrie oppgaver nedenfor
	
	public void utvid() {
		Innlegg[] oldInnlegTabell = this.innleggtabell;
		this.innleggtabell = new Innlegg[oldInnlegTabell.length * 2];
		this.nesteledig = 0;
		for (int i = 0; i < oldInnlegTabell.length; i++) {
			this.leggTil(oldInnlegTabell[i]);
		}
	}
	
	public boolean leggTilUtvid(Innlegg innlegg) {
		if(this.leggTil(innlegg))
			return true;
		
		if(this.finnes(innlegg))
			return false;

		this.utvid();
		return this.leggTil(innlegg);
	}
	
	public boolean slett(Innlegg innlegg) {
		boolean slettet = false;
		for(int i = 0; i < nesteledig; i++){
			if(slettet){
				innleggtabell[i - 1] = innleggtabell[i];
				innleggtabell[i] = null;
			}else if(innleggtabell[i].erLik(innlegg)){
				innleggtabell[i] = null;
				slettet = true;
				this.nesteledig--;
			}
		}

		return slettet;
	}
	
	public int[] search(String keyword) {
		int found = 0;
		int[] matches = new int[nesteledig];
		for(int i = 0; i < nesteledig; i++){
			if(innleggtabell[i] instanceof Tekst && ((Tekst) innleggtabell[i]).getTekst().contains(keyword)){
				found += 1;
				matches[found] = innleggtabell[i].getId();
			}
		}

		int[] shortenedMatches = Arrays.copyOf(matches, found);
		return shortenedMatches;
	}
}