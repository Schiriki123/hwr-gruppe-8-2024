package hwr.oop.group8.chess.core

enum class Rank {
  ONE,
  TWO,
  THREE,
  FOUR,
  FIVE,
  SIX,
  SEVEN,
  EIGHT,
  ;

  fun up(): Rank = when (this) {
    ONE -> TWO
    TWO -> THREE
    THREE -> FOUR
    FOUR -> FIVE
    FIVE -> SIX
    SIX -> SEVEN
    SEVEN -> EIGHT
    EIGHT -> throw IndexOutOfBoundsException("No higher rank from $this")
  }

  fun down(): Rank = when (this) {
    ONE -> throw IndexOutOfBoundsException("No lower rank from $this")
    TWO -> ONE
    THREE -> TWO
    FOUR -> THREE
    FIVE -> FOUR
    SIX -> FIVE
    SEVEN -> SIX
    EIGHT -> SEVEN
  }

  companion object {
    fun fromInt(value: Int): Rank = when (value) {
      1 -> ONE
      2 -> TWO
      3 -> THREE
      4 -> FOUR
      5 -> FIVE
      6 -> SIX
      7 -> SEVEN
      8 -> EIGHT
      else -> throw IllegalArgumentException("Invalid rank value: $value")
    }
  }

  fun toInt(): Int = when (this) {
    ONE -> 1
    TWO -> 2
    THREE -> 3
    FOUR -> 4
    FIVE -> 5
    SIX -> 6
    SEVEN -> 7
    EIGHT -> 8
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
