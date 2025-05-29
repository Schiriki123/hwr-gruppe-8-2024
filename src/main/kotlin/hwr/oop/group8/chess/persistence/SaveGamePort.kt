package hwr.oop.group8.chess.persistence

import hwr.oop.group8.chess.core.Game

interface SaveGamePort {
  fun saveGame(game: Game, updateExistingGame: Boolean)
}
