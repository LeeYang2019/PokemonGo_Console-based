///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Main Class File:  (PokemonGo.java)
// File:             (PokemonSpecies.java)
// Semester:         (CS 367: Introduction to Data Structures) Fall 2016
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

import java.util.ArrayList;
import java.util.Iterator;

/**
 * A PokemonSpecies entry in the Pokedex. Maintains the number of candies associated
 * with the Pokemon species as well as the Trainer's inventory of Pokemon of this
 * species.
 */
public class PokemonSpecies {

	private int pokedexNumber;
	private String speciesName;
	private int candies;

	/**
	 * Maintains the list of Pokemon of this species in the Trainer's inventory.
	 */
	private ArrayList<Pokemon> caughtPokemon;

	/**
	 * Constructor suitable for a newly encountered Pokemon species during the course of the
	 * game and for loading species data from a save file.
	 *
	 * @param pokedexNumber the Pokedex Number for the species
	 * @param speciesName the name of the species
	 * @param candies the number of candies the player has obtained by catching 
	 * or transferring Pokemon of this species
	 */
	public PokemonSpecies(int pokedexNumber, String speciesName, int candies) {
		this.pokedexNumber = pokedexNumber;
		this.speciesName = speciesName;
		this.candies = candies;

		// construct caughtPokemon
		caughtPokemon = new ArrayList<Pokemon>();
	}

	/**
	 * Getter methods
	 */
	public Integer getPokedexNumber() {
		return pokedexNumber;
	}
	public String getSpeciesName() {
		return speciesName;
	}
	public int getCandies() {
		return candies;
	}

	/**
	 * Add a newly caught Pokemon to the player's inventory and
	 * increase the number of candies for this PokemonSpecies
	 *
	 * @param pokemon the newly caught Pokemon
	 */
	public void addNewPokemon(Pokemon pokemon) 
	{
		this.caughtPokemon.add(pokemon);
		addNewPokemonCandies();
	}

	/**
	 * Helper function to load Pokemon from a save file into the player's inventory for this
	 * Pokemon Species
	 *
	 * @param pokemon the pokemon to add to this species
	 */
	public void loadPokemon(Pokemon pokemon) {
		this.caughtPokemon.add(pokemon);
	}

	/**
	 * Find a Pokemon of the given combatPower in the player's inventory for this species.
	 *
	 * @param cp the combatPower of the Pokemon to find
	 * @throws PokedexException [Config.POKEMON_NOT_FOUND] if there is no Pokemon with the 
	 * given combatPower in the player's inventory.
	 * @return the first Pokemon with the provided combatPower
	 */
	public Pokemon findPokemon(int cp) throws PokedexException {

		boolean isFound = false;
		int indxPos = 0;

		while (isFound == false) {
			
			//for each pokemon in the player inventory
			for (int i = 0; i < caughtPokemon.size(); i++) {
				
				//if the cp of a pokemon in the player inventory matches the cp
				//passed in, return the pokemon with that cp
				if (caughtPokemon.get(i).getCombatPower() == cp) {
					indxPos = i;
					return caughtPokemon.get(i);
				}
			}

			isFound = true;
		}
		
		//throw an exception if the pokemon has not be found
		throw new PokedexException(String.format(Config.POKEMON_NOT_FOUND, 
				caughtPokemon.get(indxPos), cp));
	}

	/**
	 * Transfer a Pokemon with the provided combatPower from the player's inventory
	 * to the Professor. This removes the Pokemon from the player's inventory and
	 * also increases the number of candies the player has associated with this
	 * PokemonSpecies.
	 *
	 * @param cp the combatPower of the Pokemon to transfer
	 * @throws PokedexException if the player does not have a Pokemon with the given
	 * combatPower
	 * @return the transferred Pokemon
	 */
	public Pokemon transferPokemon(int cp) throws PokedexException {

		boolean isFound = false;
		int indxPos = 0;

		while (isFound == false) {
			
			//for each pokemon in the player inventory
			for (int i = 0; i < caughtPokemon.size(); i++) {
				
				//if the cp of a pokemon in the player inventory matches the cp
				//passed in, return the pokemon with that cp
				if (caughtPokemon.get(i).equals(findPokemon(cp)) && 
						caughtPokemon.get(i).getCombatPower() == cp) {
					addTransferCandies();
					return caughtPokemon.remove(indxPos);
				} 
			}
			isFound = true;
		}
		
		//throw an exception if the pokemon with cp cannot be found
		throw new PokedexException(String.format(Config.POKEMON_NOT_FOUND, 
				caughtPokemon.remove(indxPos).getSpecies(), cp));
	}

	/**
	 * Check if the player has any Pokemon of this species
	 * @return false if the player has Pokemon of this species in his or her inventory
	 * and true otherwise
	 */
	public boolean isEmpty() {
		
		//for each pokemon in the player inventory
		for (int i = 0; i < caughtPokemon.size(); i++) {
			
			//if that pokemon name matches this pokemon name, return false, 
			//else, return true
			if (caughtPokemon.get(i).getSpecies().equals(this.speciesName)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Increment candies when a new pokemon is caught
	 */
	private void addNewPokemonCandies() {
		this.candies += PokemonGO.NEW_POKEMON_CANDIES;
	}

	/**
	 * Increment candies when a pokemon is transferred to the professor
	 */
	private void addTransferCandies() {
		this.candies += PokemonGO.TRANSFER_POKEMON_CANDIES;
	}

	/**
	 * Prepare a listing of all the Pokemon of this species that are currently in the
	 * player's inventory.
	 * 
	 * @return a String of the form
	 *   <cp1> <cp2> ...
	 */
	public String caughtPokemonToString() {

		String comPow = "";
		
		//for each pokemon in the player inventory
		for (int i = 0; i < caughtPokemon.size(); i++) {
			
			//concatenate and add each pokemon cp to the string comPow
			comPow += Integer.toString(caughtPokemon.get(i).getCombatPower()) +
					" ";
		}
		return  comPow;
	}

	/**
	 * Prepare a String representing this entire PokemonSpecies. This is used to
	 * save the PokemonSpecies (one part of the Pokedex) to a file to
	 * save the player's game.
	 *
	 * @return a String of the form
	 *   <pokedexNumber> <speciesName> <candies> [<cp1>, <cp2>, ...]
	 */
	public String toString() {

		String pokeStats = "";
		
		//concatenate and add pokedexNumber, speciesName, and candies into the
		//String pokeStats
		pokeStats += this.pokedexNumber + 
				" " + this.speciesName + 
				" " + this.candies + 
				" " + caughtPokemonToString();
		
		return pokeStats;
	}
}
