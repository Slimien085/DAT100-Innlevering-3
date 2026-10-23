package no.hvl.dat100.oppgave5;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import no.hvl.dat100.common.TODO;
import no.hvl.dat100.oppgave1.*;
import no.hvl.dat100.oppgave2.*;
import no.hvl.dat100.oppgave3.*;

import javax.swing.JOptionPane;

public class LesBlogg {

	private static String TEKST = "TEKST";
	private static String BILDE = "BILDE";
    private static Scanner reader;

	public static Blogg les(String mappe, String filnavn) {
  		File file = new File(mappe + filnavn);
		try {
    		reader = new Scanner(file);
		} catch (FileNotFoundException e) {
			return null;
		} 

		String line;
		if (!reader.hasNextLine()) {
			reader.close();
			return null;
		}

		line = reader.nextLine();
		int antallInnleg = 0;
		try {
			antallInnleg = Integer.parseInt(line);
		} catch (NumberFormatException e) {
			reader.close();
			return null;
		} 

		Blogg blogg = new Blogg(antallInnleg);

		while (reader.hasNext()) {
			Innlegg innlegg = ReadInnleg();
			blogg.leggTil(innlegg);
		}

		reader.close();
		return blogg;

	}

	private static Innlegg ReadInnleg(){
		String type = reader.nextLine();
		int id = Integer.parseInt(reader.nextLine());
		String bruker = reader.nextLine();
		String dato = reader.nextLine();
		int likes = Integer.parseInt(reader.nextLine());
		if(type.equals(TEKST)) return ReadTekst(id, bruker, dato, likes);
		if(type.equals(BILDE)) return ReadBilde(id, bruker, dato, likes);
		return null;
	}

	private static Tekst ReadTekst(int id, String bruker, String dato, int likes){
		String tekst = reader.nextLine();
		return new Tekst(id, bruker, dato, likes, tekst);
	}

	private static Bilde ReadBilde(int id, String bruker, String dato, int likes){
	 	Tekst tekst = ReadTekst(id, bruker, dato, likes);
		String url = reader.nextLine();
		return new Bilde(id, bruker, dato, likes, tekst.getTekst(), url);
	}

}
