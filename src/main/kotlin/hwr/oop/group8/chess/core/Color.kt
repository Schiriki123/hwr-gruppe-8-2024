package hwr.oop.group8.chess.core

enum class Color {
  WHITE(),
  BLACK(),
  ;

  fun invert(): Color = if (this == WHITE) BLACK else WHITE
}
