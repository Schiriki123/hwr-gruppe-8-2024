package hwr.oop.group8.chess

enum class Color {
  WHITE,
  BLACK;

  fun invert(): Color {
    return if (this == WHITE) BLACK else WHITE
  }
}
