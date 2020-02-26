///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Main Class File:  (PokemonGo.java)
// File:             (PokemonTrainer.java)
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

/**
 * This class represents a player. It contains functions associated with player actions.
 * Each player has a Pokedex which also acts as the player's inventory of caught Pokemon.
 * The player can capture a Pokemon after an encounter, see (but fail to capture) a Pokemon
 * during an encounter, and transfer a captured Pokemon to the Professor. Functions implemented
 * here rely heavily on the underlying Pokedex abstraction.
 */
public class PokemonTrainer {
  
  private String name;  
  private Pokedex pokedex;

  /**
   * Create a new PokemonTrainer (that is, a new player with his or her own progress in the game)
   *
   * @param name the name of the player; used to save the player's progress
   * @param pokedex the player's Pokedex. May be new or loaded from a previously saved game.
   */
  public PokemonTrainer(String name, Pokedex pokedex) {
    this.name = name;
    this.pokedex = pokedex;
  }
      
  /**
   * Getter methods
   */
  public String getName() {
    return name;
  }
  public Pokedex getPokedex() {
    return this.pokedex;
  }

  /**
   * Add Pokemon to player's inventory
   *
   * @param wildPokemon the Pokemon that the player captured
   */
  public void capturePokemon(Pokemon wildPokemon) {
    pokedex.addNewPokemon(wildPokemon);
  }
  
  /**
   * Add Pokemon to player's pokedex, but not inventory (Pokemon has been identified, 
   * but not captured).
   *
   * @param wildPokemon the Pokemon that the player encoutered
   */
  public void seePokemon(Pokemon wildPokemon) {
    try {
      pokedex.findSeenSpeciesData(wildPokemon.getSpecies());
      // then it has been seen before, do nothing
    } catch (PokedexException e) {
      // then Pokemon has not been seen, add it
      PokemonSpecies pokemonSpecies = new PokemonSpecies(wildPokemon.getPokedexNumber(), wildPokemon.getSpecies(), 0);
      pokedex.addNewSpecies(pokemonSpecies);
    }
  }
  
  /**
   * Transfer a Pokemon to the Professor; return the transferred Pokemon
   *
   * @param transferPokemonName the species name of the Pokemon to be transferred
   * @param transferPokemonCp the combatPower of the Pokemon to be transferred
   * @return the Pokemon that was transferred
   * @throws PokedexException if there is no species with the @transferPokemonName@ in the
   * Pokedex or if there are no Pokemon with the @transferPokemonCp@ in the player's inventory
   */
  public Pokemon transferPokemon(String transferPokemonName, int transferPokemonCp) throws PokedexException {
    Pokemon pokemon = pokedex.transferPokemon(transferPokemonName, transferPokemonCp);
    return pokemon;
  } 
}
