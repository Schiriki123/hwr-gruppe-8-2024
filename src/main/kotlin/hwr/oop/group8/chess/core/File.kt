package hwr.oop.group8.chess.core

enum class File {
  A,
  B,
  C,
  D,
  E,
  F,
  G,
  H,
  ;

  fun right(): File = when (this) {
    A -> B
    B -> C
    C -> D
    D -> E
    E -> F
    F -> G
    G -> H
    H -> throw IndexOutOfBoundsException("No right file from $this")
  }

  fun left(): File = when (this) {
    A -> throw IndexOutOfBoundsException("No left file from $this")
    B -> A
    C -> B
    D -> C
    E -> D
    F -> E
    G -> F
    H -> G
  }

  companion object {
    fun fromChar(char: Char): File =
      entries.firstOrNull { it.name == char.uppercase() }
        ?: throw IllegalArgumentException("Invalid file character: $char")
  }

  override fun toString(): String = name.lowercase()
}
