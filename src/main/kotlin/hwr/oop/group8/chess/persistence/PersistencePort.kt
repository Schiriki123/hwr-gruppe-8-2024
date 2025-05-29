package hwr.oop.group8.chess.persistence

import hwr.oop.group8.chess.core.Game

interface PersistencePort {
  fun saveGame(game: Game, updateExistingGame: Boolean)
  fun loadAllGames(): List<Game>
  fun loadGame(id: Int): Game = loadAllGames().find { it.id == id }
    ?: throw CouldNotLoadGameException("Could not load game with id $id")
}
