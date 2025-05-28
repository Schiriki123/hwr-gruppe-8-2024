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

  fun right(): File? = entries.getOrNull(this.value + 1)

  fun left(): File? = entries.getOrNull(this.value - 1)

  companion object {
    fun fromChar(char: Char): File =
      entries.firstOrNull { it.name == char.uppercase() }
        ?: throw IllegalArgumentException("Invalid file character: $char")
  }

  override fun toString(): String = name.lowercase()
}
