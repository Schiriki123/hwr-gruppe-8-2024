package hwr.oop.chess

import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.types.shouldBeInstanceOf
import org.assertj.core.api.Assertions.assertThat

class BoardTest : AnnotationSpec() {
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
    val size = 64
    assertThat(board.boardMap.size).isEqualTo(size)
  }

  @Test
  fun `Test getSquare method`() {
    val board = Board()
    val position = Position('a', 1)

    val testSquare = board.getSquare(position)
    assertThat(testSquare.piece).isNull()
    testSquare.shouldBeInstanceOf<Square>()
  }

  @Test
  fun `Test to add rook to a1`() {
    val board = Board()
    val position = Position('a', 1)
    val piece = Rook(true)
    board.putPiece(position, piece)

    assertThat(board.boardMap.getValue(position).piece).isEqualTo(piece)
  }

  @Test
  fun `Test piece move to empty square`() {
    val board = Board()
    val testPiece = Rook(true)
    val startPos = Position('g', 8)
    val endPos = Position('a', 1)
    val move = Move(startPos, endPos)

    board.putPiece(startPos, testPiece)
    assertThat(board.boardMap.getValue(startPos).piece).isEqualTo(testPiece)
    assertThat(board.boardMap.getValue(endPos).piece).isNull()
    assertThat(board.isItWhitesMove).isTrue
    board.makeMove(move)
    assertThat(board.boardMap.getValue(startPos).piece).isNull()
    assertThat(board.boardMap.getValue(endPos).piece).isEqualTo(testPiece)
    assertThat(board.isItWhitesMove).isFalse
  }

//  @Test
//  fun `Ask for square`(){
//    val board = Board()
//    val square: Board.Square = board.getSquare('a', 1)
//    assertThat(square.file).isEqualTo(' ')
//  }
}
