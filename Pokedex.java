///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Main Class File:  (PokemonGo.java)
// File:             (Pokedex.java)
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

import java.util.Iterator;
import java.util.ArrayList;
import java.io.FileNotFoundException;
import java.io.File;
import java.util.Scanner;

/**
 * The Pokedex maintains the list of Pokemon species that have been encountered by the trainer.
 * It also maintains the trainer's inventory of Pokemon belonging to a particular species
 */
public class Pokedex {
	private ArrayList<PokemonSpecies> pokedex;

	/**
	 * Create a new, empty Pokedex
	 */
	public Pokedex() {
		pokedex = new ArrayList<PokemonSpecies>();
	}

	/**
	 * Load Pokedex from a file and update the Pokedex object's private pokedex.
	 * <p>
	 * This function does not provide de-duplication (if Gengar is already in your Pokedex and you loadFromFile 
	 * a Pokedex that also has a Gengar, you will have two entries of Gengar in your Pokedex).
	 * An error is thrown to prevent duplicate entries.
	 *  
	 * @param filepath the Pokedex to load; the file is expected to have lines delimited by white space with the
	 * following fields: 
	 *   <Pokedex Number> <Species Name> <Candies> [<cp1>, <cp2>, ...]
	 * for example:
	 *   94 gengar 3 2289
	 *   110 weezing 6 457 148
	 * @throws PokedexException if the pokedex has already been loaded (pokedex is not empty) with message Config.MULTIPLE_POKEDEX_EXCEPTION
	 * @throws FileNotFoundException if the file in @filepath@ cannot be found
	 */
	public void loadFromFile(String filepath) throws FileNotFoundException, PokedexException {

		if (!pokedex.isEmpty()) {
			throw new PokedexException(String.format(Config.MULTIPLE_POKEDEX_EXCEPTION, filepath));
		}
		
		//local variables
		String line = null;
		String[] tokens = null;
		String spiecesName = null;
		int pokeIndex = 0;
		int candies = 0;
		int cp = 0;

		//create a file to read the Pokemon list
		File filename = new File(filepath);

		try {
			Scanner fileReader = new Scanner(filename);

			//for each line
			while (fileReader.hasNextLine()) {

				//split line into tokens
				line = fileReader.nextLine().toLowerCase();
				tokens = line.split("\\s+");

				pokeIndex = Integer.parseInt(tokens[0].trim());
				spiecesName = tokens[1].trim();
				candies = Integer.parseInt(tokens[2]);
				
				//create Pokemon species
				PokemonSpecies species =  new PokemonSpecies(pokeIndex, 
						spiecesName, candies);
				
				//add the new species to the pokedex
				pokedex.add(species);
				
				//if there are more than 3 tokens 
				if (tokens.length > 3) {
					
					//each token after is a cp value corresponding to an 
					//additional pokemon to add to the species inventory
					for (int i = 3; i < tokens.length; i++) {
						species.loadPokemon(new Pokemon(pokeIndex, spiecesName, 
								Integer.parseInt(tokens[i])));
					}
				}
			}
			fileReader.close();

		} catch (FileNotFoundException e) {
			
		}
	}

	/**
	 * Create a record of a new Pokemon species in the Pokedex; used by PokemonTrainer when
	 * the player encounters a new Pokemon and that Pokemon escapes. This method is also
	 * used by other Pokedex methods such as addNewPokemon to make record of any 
	 * encountered Pokemon (whether that Pokemon is captured or escapes)
	 *
	 * @param species the PokemonSpecies that has been encountered
	 */
	public void addNewSpecies(PokemonSpecies species) 
	{
		this.pokedex.add(species);
	}

	/**
	 * Add a new Pokemon to the player's inventory; may add a new species to the Pokedex
	 * if the Pokemon being added has not been encountered before. Used by PokemonTrainer 
	 * when the player encounters and catches a Pokemon.
	 *
	 * @param pokemon the new Pokemon that has been caught
	 */
	public void addNewPokemon(Pokemon pokemon) {

		//local variable
		PokemonSpecies newPokemon = 
				new PokemonSpecies(pokemon.getPokedexNumber(), pokemon.getSpecies(), 0);

		//loop through each species in the pokedex
		for (int i = 0; i < pokedex.size(); i++) {

			//check to see if we have encountered the pokemon
			if (pokedex.get(i).getPokedexNumber().
					equals(pokemon.getPokedexNumber())) {

				//add pokemon to our inventory
				pokedex.get(i).addNewPokemon(pokemon);
				return;
			}
		}

		//if pokemon is not in our pokedex, add to pokedex and inventory
		newPokemon.addNewPokemon(pokemon);
		addNewSpecies(newPokemon);
	}
	/**
	 * Transfer a Pokemon to the Professor. The Professor thanks the player by providing a candy
	 * associated with the Pokemon species of the Pokemon that was transferred.
	 *
	 * @param speciesName the species of the {@link Pokemon} to transfer
	 * @param cp the combatPower of the {@link Pokemon} to transfer
	 * @return the pokemon that was tranferred
	 * @throws PokedexException when either the Pokemon species given by the species name has not
	 * yet been encountered by the player or if there is no Pokemon with the combatPower given in cp
	 */
	public Pokemon transferPokemon(String speciesName, int cp) throws PokedexException {
		
		//transfer findCaught Pokemon from the player inventory
		PokemonSpecies newPokemon;
		newPokemon = findCaughtSpeciesData(speciesName);		
		return newPokemon.transferPokemon(cp);
	}

	/**
	 * Lookup a species in the Pokedex; if it has been seen before, return its data; 
	 * otherwise, throw PokedexException.
	 *
	 * @param name the species name to lookup in the Pokedex
	 * @return the PokemonSpecies with speciesName given by name
	 * @throws PokedexException if the PokemonSpecies cannot be found (the player has not 
	 * yet encountered this species)
	 */
	public PokemonSpecies findSeenSpeciesData(String name) throws PokedexException 
	{

		boolean isFound = false;

		while (isFound == false) {

			//check pokedex to see if pokemon is in our pokedex
			for (int i = 0; i < pokedex.size(); i++) {

				//if in pokedex, return pokemon
				if (pokedex.get(i).getSpeciesName().equals(name)) {
					return pokedex.get(i);
				}
			}
			isFound = true;
		}
		//throw exception if the Pokemon has not been seen 
		throw new PokedexException(String.format(Config.UNSEEN_POKEMON, name));
	}

	/**
	 * While {@link findSeenSpeciesData} returns information for all Pokemon observed (and 
	 * caught), this function only returns species data for Pokemon that the Trainer has in 
	 * his or her inventory.
	 *
	 * @param speciesName the name of the species to find in the Pokedex
	 * @throws PokedexException [Config.UNSEEN_POKEMON] if the species has not been encountered or [Config.UNCAUGHT_POKEMON] if the player
	 * does not have any Pokemon of that species in his or her inventory
	 */
	public PokemonSpecies findCaughtSpeciesData(String speciesName) throws PokedexException {

		boolean isFound = false;

		while (isFound == false) {

			//loops to see if pokemon is in our pokedex
			for (int i = 0; i < pokedex.size(); i++) {

				//if pokemon is in our pokedex
				if (pokedex.get(i).getSpeciesName().equals(speciesName)) {

					//if pokemon is in our inventory, return pokemon
					if (!pokedex.get(i).isEmpty()) {
						return pokedex.get(i);
					} 
					else {
					//if inventory no longer has that pokemon
					throw new PokedexException(String.format(
							Config.UNCAUGHT_POKEMON, 
							pokedex.get(i).getSpeciesName()));
					}
				}
			}
			isFound = true;
		}
		//if pokemon has not been encountered
		throw new PokedexException(String.format(Config.UNSEEN_POKEMON, speciesName));
	}

	/**
	 * Create a String with the encountered Pokemon in the form:
	 * Bulbasaur
	 * Charmander
	 * ...
	 * where Pokemon species are listed in the order they have been encountered
	 *
	 * @return the String as described above
	 */
	public String seenPokemonMenu() {

		String pokemonName = "";

		//loop through each pokemon in the pokedex
		for (int i = 0; i < pokedex.size(); i++) {

			//return each pokemon name and add to the String pokemonName
			pokemonName += pokedex.get(i).getSpeciesName() + "\n";
		}
		return pokemonName;
	}

	/**
	 * While {@link seenPokemonMenu} returns a String with all Pokemon in the Pokedex (even ones that the
	 * trainer failed to capture), this function returns a String containing only Pokemon current in 
	 * the trainer's inventory
	 *
	 * @return the String as described above
	 */
	public String caughtPokemonMenu() {

		String caughtPokemonNames = "";

		//loop through each pokemon in the pokedex
		for (int i = 0; i < pokedex.size();i++) {

			//if that pokemon exists in the player inventory
			if (!pokedex.get(i).isEmpty()) {

				//return each pokemon name and add it to caughtPokemonNames
				caughtPokemonNames += pokedex.get(i).getSpeciesName() + "\n";
			}
		}
		return caughtPokemonNames;
	}

	/**
	 * Serialize Pokedex into a String for a save file. The file format is described
	 * in {@link loadFromFile}
	 * 
	 * @return the complete Pokedex in String form, ready to be written to a file
	 */
	public String toString() {

		String pokedexStats = "";

		//for each Pokemon species in the pokedex
		for (int i = 0; i < pokedex.size(); i++) {
			pokedexStats += pokedex.get(i).toString() + "\n";
		}
		
		return pokedexStats;
	}
}
