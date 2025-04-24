package hwr.oop.chess

class Square(var piece: Piece?) {
  fun isEmpty(): Boolean {
    return piece == null
  }
}
