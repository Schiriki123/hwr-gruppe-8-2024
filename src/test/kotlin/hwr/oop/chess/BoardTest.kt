package hwr.oop.chess

import io.kotest.core.spec.style.AnnotationSpec
import org.assertj.core.api.Assertions.assertThat

class BoardTest : AnnotationSpec() {
  @Test
  fun `Test square init`() {
    val filePosition = 'a'
    val rankPosition = 1
    val piece = Pawn(false)
    val square = Square(piece)
    assertThat(square.piece).isEqualTo(piece)

  }

  @Test
  fun `Test square name population, should be a, b, c, d, e, f, g, h`() {
    val position = Position('a', 1)
    assertThat(position.file).isEqualTo('a')
    assertThat(position.rank).isEqualTo(1)
  }

  // Size, Square objects, Square piece is null
  @Test
  fun `Test board initialization`() {
    val board = Board()
    val size  = 64
    assertThat(board.boardMap.size).isEqualTo(size)
  }

//  @Test
//  fun `Ask for square`(){
//    val board = Board()
//    val square: Board.Square = board.getSquare('a', 1)
//    assertThat(square.file).isEqualTo(' ')
//  }
}
