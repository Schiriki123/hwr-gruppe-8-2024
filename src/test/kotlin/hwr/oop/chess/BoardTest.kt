package hwr.oop.chess

import io.kotest.core.spec.style.AnnotationSpec
import org.assertj.core.api.Assertions.assertThat

class BoardTest : AnnotationSpec() {
  @Test
  fun `Test square init`() {
    val filePosition = 'a'
    val rankPosition = 1
    val piece= Pawn(false)
    val square = Square(filePosition,rankPosition,piece)
    assertThat(square.file).isEqualTo(filePosition)
    assertThat(square.rank).isEqualTo(rankPosition)
    assertThat(square.piece).isEqualTo(piece)

  }

  @Test
  fun `Test square name population, should be a, b, c, d, e, f, g, h`() {
    val position =Position('a',1)

    assertThat(position.getPosition()).isEqualTo("a1")

  }

  @Test
  fun `Test board initialization`() {
    val board = Board()
    val position= Position('g',5)
    val square= board.board.get(position)
    val result = "g5"
    if (square != null) {

    }

  }


//  @Test
//  fun `Ask for square`(){
//    val board = Board()
//    val square: Board.Square = board.getSquare('a', 1)
//    assertThat(square.file).isEqualTo(' ')
//  }
}
