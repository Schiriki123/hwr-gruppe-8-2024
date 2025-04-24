package hwr.oop.chess

import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.types.shouldBeInstanceOf
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy

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
  fun `Test get square based on position object`() {
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
  fun `Test rook move to empty square`() {
    val board = Board()
    val testPiece = Rook(true)
    val startPos = Position('g', 8)
    val endPos = Position('g', 1)
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

  @Test
  fun `Test move exception for empty start square`() {
    val board = Board()
    val startPos = Position('a', 1)
    val endPos = Position('g', 1)
    val move = Move(startPos, endPos)

    assertThatThrownBy {
      board.makeMove(move)
    }.message().isEqualTo("Start square does not contain any piece")
  }

  @Test
  fun `Test move exception for target with ally piece`() {
    val board = Board()
    val testRook = Rook(true)
    val testPawn = Pawn(true)
    val startPos = Position('a', 1)
    val endPos = Position('a', 2)
    val move = Move(startPos, endPos)

    // Setup board
    board.putPiece(startPos, testRook)
    board.putPiece(endPos, testPawn)

    // Try to make move
    assertThatThrownBy {
      board.makeMove(move) // Try to move rook to position of pawn
    }.message().isEqualTo("Target square is occupied by ally piece")
  }

  @Test
  fun `Test move exception for moving piece from opponent`() {
    val board = Board()
    val testRook = Rook(false) // Black rook
    val startPos = Position('a', 1)
    val endPos = Position('a', 2)
    val move = Move(startPos, endPos)

    // Setup board
    board.isItWhitesMove = true
    board.putPiece(startPos, testRook)

    // Try to make move
    assertThatThrownBy {
      board.makeMove(move)
    }.message().isEqualTo("Piece belongs to opponent")
  }
}
