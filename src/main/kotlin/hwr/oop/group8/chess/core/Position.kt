package hwr.oop.group8.chess.core

data class Position(val file: Char, val rank: Int) {
  init {
    require(file in 'a'..'h') { "File must be between 'a' and 'h'" }
    require(rank in 1..8) { "Rank must be between 1 and 8" }
  }

  override fun toString(): String {
    return "$file$rank"
  }
}
