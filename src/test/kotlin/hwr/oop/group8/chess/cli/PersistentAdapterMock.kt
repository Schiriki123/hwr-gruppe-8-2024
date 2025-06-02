package hwr.oop.group8.chess.cli

import hwr.oop.group8.chess.core.Game
import hwr.oop.group8.chess.persistence.CouldNotDeleteGameException
import hwr.oop.group8.chess.persistence.FEN
import hwr.oop.group8.chess.persistence.PersistencePort

class PersistentAdapterMock : PersistencePort {
  private var game: Game? = null

  fun savedGame(): Game? = game

  override fun loadGame(id: Int): Game {
    // Mock implementation
    return game ?: Game(id, FEN())
  }

  override fun saveGame(game: Game, updateExistingGame: Boolean) {
    // Mock implementation
    this.game = game
  }

  override fun deleteGame(id: Int) {
    // Mock implementation
    if (game?.id == id) {
      game = null
    } else {
      throw CouldNotDeleteGameException("Game with id $id does not exist")
    }
  }

  override fun loadAllGames(): List<Game> = listOf(
    Game(1, FEN()),
    Game(2, FEN("rnbqkbnr/pppppppp/8/8/4P3/8/PPPP1PPP/RNBQKBNR", 'b')),
  )
}
