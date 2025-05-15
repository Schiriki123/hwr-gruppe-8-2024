package hwr.oop.group8.chess.persistence

import hwr.oop.group8.chess.Game

interface LoadGameInterface {
  fun loadGame(id: Int): Game
}
