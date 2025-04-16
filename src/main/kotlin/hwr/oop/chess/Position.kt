package hwr.oop.chess

data class Position(val file: Char, val rank: Int) {
  init {
    require(file in 'a'..'h') { "File must be between 'a' and 'h'" }
    require(rank in 1..8) { "Rank must be between 1 and 8" }
  }

  companion object {
    private fun isValid(file: Char, rank: Int): Boolean {
      return file in 'a'..'h' && rank in 1..8
    }
    fun createPositionIfValid(file: Char, rank: Int): Position? {
      return if (isValid(file, rank)) {
        Position(file, rank)
      } else {
        null
      }
    }
  }
}
