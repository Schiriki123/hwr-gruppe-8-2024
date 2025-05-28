package hwr.oop.group8.chess.core

data class Move(
  val from: Position,
  val to: Position,
  val specialMove: List<Move> = listOf(),
  val promotionChar: Char? = null,
) {
  // Exclude promotionChar from equals
  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false

    other as Move

    if (from != other.from) return false
    if (to != other.to) return false
    if (specialMove != other.specialMove) return false

    return true
  }

  override fun hashCode(): Int {
    var result = promotionChar?.hashCode() ?: 0
    result = 31 * result + from.hashCode()
    result = 31 * result + to.hashCode()
    result = 31 * result + specialMove.hashCode()
    return result
  }
}
