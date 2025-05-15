package hwr.oop.group8.chess.persistence

import hwr.oop.group8.chess.core.Game

interface LoadAllGamesInterface {
  fun loadAllGames(): List<Game>
}
