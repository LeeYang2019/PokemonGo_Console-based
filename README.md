<Strong>Goals</Strong>

The goals of this assignment are to:

		Develop and run multi-class Java programs.
		Use command-line arguments. (Eclipse: must set program configuration parameters.)
		Throw exceptions at the point the error is detected.
		Catch exceptions where they can be handled as required by program specifications.
		Perform console and file I/O.
		Read text file input via java.util.Scanner
		Write output to a text file via java.io.PrintWriter.
    
<Strong>Description</Strong>

For this assignment you will complete a simplified version of the video game Pokemon Go. Rather than a video game, this program is console-based. In our PokemonGo, the player (PokemonTrainer) encounters Pokemon in the wild and tries to catch them to build his or her collection. The player may transfer Pokemon to the Professor to earn candies.

Sometimes, Pokemon escape from the trainer and are not caught. A PokemonTrainer has a Pokedex (list of Pokemon) which keeps track of which Pokemon the trainer has encountered in the wild (including Pokemon that escaped). The Pokedex stores data in the form of PokemonSpecies which contains information such as the PokedexNumber and PokemonSpeciesName. If a Pokemon is caught, then candies and the Pokemon itself are also stored.

Each Pokemon in the Trainer’s inventory has a certain level of combat power which reflects its ability to win battles with other Pokemon. A player can have multiple Pokemon of the same species but with varying combat powers (or even multiple Pokemon of the same species with the same combat power).

The original game uses the same set of candies for PokemonSpecies of the same evolution path. However, we simplify the game by maintaining number of candies for each PokedexNumber. For example, in our version of PokemonGO, Bulbasaur, Ivysaur and Venusaur (members of an evolutionary path) each have their own candies rather than all of them being associated with Bulbasaur candies (as is the case in the original game). In the original game, candies can be used to evolve Pokemon, but we will not be implementing that functionality for this assignment.

The game play allows the player to search for Pokemon (encounters), display the Pokemon in their Pokedex (encountered Pokemon), display the Pokemon in their inventory (capturedPokemon), transfer captured Pokemon, and quit.

At the end of game play, the player’s progress is saved. This allows the player to resume playing at a later time.

Also, a few sample runs will be given. This is to help you get started fast. But be careful, you may not change the design or interface of this program. If you were to do so, grading tests will fail and you will lose a few or many points.
