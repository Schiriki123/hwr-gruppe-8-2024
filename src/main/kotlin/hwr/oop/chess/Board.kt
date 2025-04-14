package hwr.oop.chess

class Board() {

  val board: HashMap<Int, Rank> = HashMap<Int, Rank>();
  val ranks: Array<Rank> = Array<Rank> (8){Rank(0)}
  fun getSquare(file: Char, rank: Int): Square {

    return ranks[rank].squares[Utils.covertFileToNumber(file)]
  }

  class Rank(rankPosition: Int) {
    val squares: Array<Square> = Array<Square>(8) { Square(' ') }
  }

  class Square(val file: Char) {

  }
}

