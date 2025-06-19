package hwr.oop.group8.chess.core.piece

import hwr.oop.group8.chess.core.Board
import hwr.oop.group8.chess.core.Color
import hwr.oop.group8.chess.core.File
import hwr.oop.group8.chess.core.Position
import hwr.oop.group8.chess.core.Rank
import hwr.oop.group8.chess.core.move.SingleMove
import hwr.oop.group8.chess.persistence.FEN
import io.kotest.core.spec.style.AnnotationSpec
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy

class BishopTest : AnnotationSpec() {
  @Test
  fun `Char representation of Bishop`() {
    val boardInspector = Board(FEN("8/8/8/8/8/8/8/K7", 'w', ""))
    val whiteBishop = Bishop(Color.WHITE, boardInspector)
    val blackBishop = Bishop(Color.BLACK, boardInspector)
    assertThat(whiteBishop.getChar()).isEqualTo('B')
    assertThat(blackBishop.getChar()).isEqualTo('b')
    assertThat(whiteBishop.getType()).isEqualTo(PieceType.BISHOP)
  }

  @Test
  fun `Bishop movement on empty board`() {
    val board = Board(FEN("B7/8/8/8/8/8/8/K7", 'w', ""))
    val singleMove =
      SingleMove(Position(File.A, Rank.EIGHT), Position(File.E, Rank.FOUR))
    board.makeMove(singleMove)

    assertThat(board.generateFENBoardString()).isEqualTo("8/8/8/8/4B3/8/8/K7")
  }

  @Test
  fun `Bishop capture move set generation`() {
    val board = Board(FEN("8/8/8/2B5/8/P3p3/8/K7", castle = ""))
    val startPosition = Position(File.C, Rank.FIVE)
    val validMoveDestinationsOfBishop =
      board.getPieceAt(startPosition)!!.getValidMoveDestinations()

    assertThat(validMoveDestinationsOfBishop).containsExactlyInAnyOrder(
      SingleMove(startPosition, Position(File.B, Rank.SIX)),
      SingleMove(startPosition, Position(File.D, Rank.SIX)),
      SingleMove(startPosition, Position(File.B, Rank.FOUR)),
      SingleMove(startPosition, Position(File.D, Rank.FOUR)),
      SingleMove(startPosition, Position(File.E, Rank.THREE)),
      SingleMove(startPosition, Position(File.F, Rank.EIGHT)),
      SingleMove(startPosition, Position(File.A, Rank.SEVEN)),
      SingleMove(startPosition, Position(File.E, Rank.SEVEN)),
    )
  }

  @Test
  fun `Bishop movement with blocked path`() {
    val board = Board(FEN("B7/8/8/8/4r3/8/8/K7", 'w', ""))
    val singleMove =
      SingleMove(Position(File.A, Rank.EIGHT), Position(File.G, Rank.TWO))
    assertThatThrownBy { board.makeMove(singleMove) }
      .hasMessageContaining("Invalid move for piece Bishop from a8 to g2")
  }

  @Test
  fun `Invalid move, expection exception`() {
    val board = Board(FEN("B7/8/8/8/4r3/8/8/K7", 'w', ""))
    val singleMove =
      SingleMove(Position(File.A, Rank.EIGHT), Position(File.A, Rank.TWO))
    assertThatThrownBy { board.makeMove(singleMove) }
      .hasMessageContaining("Invalid move for piece Bishop from a8 to a2")
  }
}
