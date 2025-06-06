package hwr.oop.group8.chess.persistence

import hwr.oop.group8.chess.core.Game

interface PersistencePort { // Split to different interfaces if needed
  fun saveGame(game: Game, updateExistingGame: Boolean)
  fun deleteGame(id: Int)
  fun loadAllGames(): List<Game>
  fun loadGame(id: Int): Game = loadAllGames().find { it.id == id }
    ?: throw CouldNotLoadGameException("Could not load game with id $id")
}
