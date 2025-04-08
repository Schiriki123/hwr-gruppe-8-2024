package hwr.oop.chess

import io.kotest.core.spec.style.AnnotationSpec
import org.assertj.core.api.Assertions.assertThat

class ChessboardTest : AnnotationSpec() {
  @Test
  fun `Test square init`() {
    val filePosition = 'a'
    val square = Chessboard.Square(filePosition)
    assertThat(square.file).isEqualTo(filePosition)
  }

  @Test
  fun `Test square name population, should be a, b, c, d, e, f, g, h`() {
    val rank = Chessboard.Rank(1)
    val squareA = rank.squares[0]
    val squareB = rank.squares[1]
    val squareC = rank.squares[2]
    val squareD = rank.squares[3]
    val squareE = rank.squares[4]
    val squareF = rank.squares[5]
    val squareG = rank.squares[6]
    val squareH = rank.squares[7]

  }

  @Test
  fun `Test board initialization`() {
    val size = 8
  }

  @Test
  fun `Test board size`() {

  }

  @Test
  fun `Ask for square`(){
    val board = Chessboard()
    val square: Chessboard.Square = board.getSquare('a', 1)
    assertThat(square.file).isEqualTo(' ')
  }
}
