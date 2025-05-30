package hwr.oop.group8.chess.core

enum class File(val value: Int) {
  A(0),
  B(1),
  C(2),
  D(3),
  E(4),
  F(5),
  G(6),
  H(7),
  ;

  fun right(): File = entries.getOrElse(this.value + 1) {
    throw IndexOutOfBoundsException("No right file from $this")
  }

  fun left(): File = entries.getOrElse(this.value - 1) {
    throw IndexOutOfBoundsException("No left file from $this")
  }

  companion object {
    fun fromChar(char: Char): File =
      entries.firstOrNull { it.name == char.uppercase() }
        ?: throw IllegalArgumentException("Invalid file character: $char")
  }

  override fun toString(): String = name.lowercase()
}
