package hwr.oop.group8.chess.core

enum class Rank(val value: Int) {
  ONE(1),
  TWO(2),
  THREE(3),
  FOUR(4),
  FIVE(5),
  SIX(6),
  SEVEN(7),
  EIGHT(8),
  ;

  fun up(): Rank = entries.getOrElse(this.value + 1) {
    throw IllegalArgumentException("No rank above $this")
  }

  fun down(): Rank = entries.getOrElse(this.value - 1) {
    throw IllegalArgumentException("No rank below $this")
  }
}
