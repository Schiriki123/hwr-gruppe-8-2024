package hwr.oop.group8.chess.core

enum class Rank(val value: Int) {
  ONE(0),
  TWO(1),
  THREE(2),
  FOUR(3),
  FIVE(4),
  SIX(5),
  SEVEN(6),
  EIGHT(7),
  ;

  fun up(): Rank = entries.getOrElse(this.value + 1) {
    throw IndexOutOfBoundsException("No upper rank from $this")
  }

  fun down(): Rank = entries.getOrElse(this.value - 1) {
    throw IndexOutOfBoundsException("No lower rank from $this")
  }

  companion object {
    fun fromInt(value: Int): Rank =
      entries.firstOrNull { it.value == value - 1 }
        ?: throw IllegalArgumentException("Invalid rank value: $value")
  }

  override fun toString(): String = when (this) {
    ONE -> "1"
    TWO -> "2"
    THREE -> "3"
    FOUR -> "4"
    FIVE -> "5"
    SIX -> "6"
    SEVEN -> "7"
    EIGHT -> "8"
  }
}
