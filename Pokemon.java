///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Main Class File:  (PokemonGo.java)
// File:             (Pokemon.java)
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

/**
 * This class represents a Pokemon
 */
public class Pokemon {
  private int pokedexNumber;
  private String species;
  private int combatPower;

  /**
   * Create a new Pokemon
   * @param pokedexNumber the number 1 to 151 (or higher for later generations of Pokemon) 
   * associated with a Pokemon species
   * @param species the name of the species of this Pokemon
   * @param combatPower the strength of this Pokemon for fighting battles (implementedd
   * in a future version of this application)
   */
  public Pokemon(int pokedexNumber, String species, int combatPower) {
    this.pokedexNumber = pokedexNumber;
    this.species = species;
    this.combatPower = combatPower;
  }

  /**
   * Getter methods
   */
  public int getPokedexNumber() {
    return this.pokedexNumber;
  }
  public String getSpecies() {
    return this.species;
  }
  public int getCombatPower() {
    return this.combatPower;
  }

  /**
   * A Pokemon within a PokemonSpecies can be simply represented by its combatPower.
   * This function is used to save the player's game into a save file.
   *
   * @return String the combatPower of the Pokemon
   */
  public String toString() {
    return Integer.toString(combatPower);
  }
}
