///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Main Class File:  (PokemonGo.java)
// File:             (PokemonDB.java)
// Semester:         (Introduction to Data Structures) Fall 2016
//
// Author:           (Nhialee Yang nyang5@wisc.edu)
// CS Login:         (nhialee)
// Lecturer's Name:  (Alexander Brooks)
// Lab Section:      (N/A)
//
//////////////////// PAIR PROGRAMMERS COMPLETE THIS SECTION ////////////////////
//
// Pair Partner:     (name of your pair programming partner)
// Email:            (email address of your programming partner)
// CS Login:         (partner's login name)
// Lecturer's Name:  (name of your partner's lecturer)
// Lab Section:      (your partner's lab section number)
//
//////////////////// STUDENTS WHO GET HELP FROM OTHER THAN THEIR PARTNER //////
//                   fully acknowledge and credit all sources of help,
//                   other than Instructors and TAs.
//
// Persons:          Identify persons by name, relationship to you, and email.
//                   Describe in detail the the ideas and help they provided.
//
// Online sources:   avoid web searches to solve your problems, but if you do
//                   search, be sure to include Web URLs and description of 
//                   of any information you find.
//////////////////////////// 80 columns wide //////////////////////////////////

import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * This class provides functions for interacting with the set of Pokemon that the player 
 * will encounter during the course of the game.
 */
public class PokemonDB {

	public static final int NUM_POKEMON = 151;
	public static final int WILD_CANDIES = 3;
	private static ArrayList<String> pokemonSpeciesNames;

	/**
	 * Create a PokemonDB by parsing the Pokemon file in @path@
	 *
	 * @param path the file path that contains the Pokemon used for this game
	 */
	public PokemonDB(String path) throws FileNotFoundException {
		pokemonSpeciesNames = new ArrayList<String>();
		File infile = new File(path);
		loadFile(infile);
	}

	/**
	 * Parse Pokemon file with format 
	 *   <PokedexNumber> <PokemonName>\n
	 * File lines must be ordered from PokedexNumber and start from 1
	 *
	 * @param infile the file with all the Pokemon that will be used for this game
	 */
	public void loadFile(File infile) throws FileNotFoundException {
		Scanner sc = new Scanner(infile);
		while(sc.hasNextLine()) {
			String line = sc.nextLine();
			String[] tokens = line.split("\\s+");
			String name = tokens[1];
			name = name.toLowerCase(); // to make sure all cases are acceptable
			pokemonSpeciesNames.add(name);
		}
		sc.close();
	}

	/**
	 * Generate a Pokedex Number to (1) provide a Pokemon for an encounter and (2)
	 * provide a set of alternative options for the Pokedex Number guessing game to
	 * capture the encountered Pokemon
	 *
	 * @return the Pokedex Number generated
	 */
	public int generatePokedexNumber() {
		int pokedexNumber = Config.POKEDEX_NUMBER_GENERATOR.nextInt(PokemonDB.NUM_POKEMON-1)+1; // adjustments for origin 0/1
		return pokedexNumber;
	}

	/**
	 * Lookup a Pokemon species name using its Pokedex Number (the index+1 of the 
	 * string in pokemonSpeciesNames which matches the parameter pokedexNumber)
	 *
	 * @param pokedexNumber find the Pokemon species name associated with this number
	 * @throws PokedexException thrown if the pokedexNumber is not in the range 1 to 
	 *   NUM_POKEMON (inclusive)
	 */
	public String lookupSpeciesName(int pokedexNumber) throws PokedexException {
		
		//throw exception is the pokedexNumber guessed is out of range
		if (pokedexNumber < 1 || pokedexNumber > NUM_POKEMON) {
			throw new PokedexException(String.format(Config.INVALID_POKEDEX_NUMBER, pokedexNumber));
		}
		return pokemonSpeciesNames.get(pokedexNumber-1);
	}

	/**
	 * Lookup a Pokedex Number using its species name
	 * 
	 * @param speciesName the name of the Pokemon species to find the Pokedex Number for
	 * @throws PokedexException if the speciesName is not in the database
	 */
	public int lookupPokedexNumber(String speciesName) throws PokedexException {
		int rv = 0;
		Iterator<String> it = pokemonSpeciesNames.iterator();
		int i = 1;
		while(it.hasNext()) {
			String name = it.next();
			if(speciesName.equals(name)) {
				rv = i;
			}
			i += 1;
		}
		if(rv == 0) {
			throw new PokedexException(String.format(Config.INVALID_POKEMON_SPECIES, speciesName));
		}
		return rv;
	}
}
