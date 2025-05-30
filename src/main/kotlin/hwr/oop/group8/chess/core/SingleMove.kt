package hwr.oop.group8.chess.core

data class SingleMove(
  // TODO: interface
  val from: Position,
  val to: Position,
  val specialSingleMove: List<SingleMove> = listOf(), // TODO: naming, composite pattern
  var promotionChar: Char? = null,
) : Move {
  // Exclude promotionChar from equals
  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false

    other as SingleMove

    if (from != other.from) return false
    if (to != other.to) return false
    if (specialSingleMove != other.specialSingleMove) return false

    return true
  }

  override fun hashCode(): Int {
    var result = promotionChar?.hashCode() ?: 0
    result = 31 * result + from.hashCode()
    result = 31 * result + to.hashCode()
    result = 31 * result + specialSingleMove.hashCode()
    return result
  }

  override fun moves(): List<SingleMove> = listOf(this)
}
