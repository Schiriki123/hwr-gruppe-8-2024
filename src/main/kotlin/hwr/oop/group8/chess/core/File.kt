package hwr.oop.group8.chess.core

enum class File(val value: Int) {
  A(1),
  B(2),
  C(3),
  D(4),
  E(5),
  F(6),
  G(7),
  H(8),
  ;

  fun right(): File = entries.getOrElse(this.value + 1) {
    throw IllegalArgumentException("No file to the right of $this")
  }

  fun left(): File = entries.getOrElse(this.value - 1) {
    throw IllegalArgumentException("No file to the left of $this")
  }
}
