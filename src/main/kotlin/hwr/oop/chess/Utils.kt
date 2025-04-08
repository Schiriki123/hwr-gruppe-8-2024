package hwr.oop.chess

object Utils {

  //TODO: Löschen und durch enum ersetzen
  fun covertFileToNumber(file: Char): Int {
    return when (file) {
      'a' -> 0
      'b' -> 1
      'c' -> 2
      'd' -> 3
      'e' -> 4
      'f' -> 5
      'g' -> 6
      'h' -> 7
      else -> throw Exception()
    }
  }

  fun covertNumberToFile(fileNumber: Int): Char {
    return when(fileNumber) {
      0 -> 'a'
      1 -> 'b'
      2 -> 'c'
      3 -> 'd'
      4 -> 'e'
      5 -> 'f'
      6 -> 'g'
      7 -> 'h'
      else -> throw Exception()
    }
  }
}
