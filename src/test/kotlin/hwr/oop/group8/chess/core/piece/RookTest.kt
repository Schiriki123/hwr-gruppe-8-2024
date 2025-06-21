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

class RookTest : AnnotationSpec() {
  @Test
  fun `char representation`() {
    val boardInspector = Board(FEN("8/8/8/8/8/8/8/K7", 'w', ""))
    val whiteRook = Rook(Color.WHITE, boardInspector.boardAnalyser)
    val blackRook = Rook(Color.BLACK, boardInspector.boardAnalyser)
    assertThat(whiteRook.fenRepresentation()).isEqualTo('R')
    assertThat(blackRook.fenRepresentation()).isEqualTo('r')
    assertThat(whiteRook.getType()).isEqualTo(PieceType.ROOK)
  }

  @Test
  fun `Rook movement on empty board from a8 to a2`() {
    val board = Board(FEN("R7/8/8/8/8/8/8/K7", 'w', ""))
    val singleMove =
      SingleMove(Position(File.A, Rank.EIGHT), Position(File.A, Rank.TWO))
    board.makeMove(singleMove)

    assertThat(FEN.generateFENBoardString(board)).isEqualTo("8/8/8/8/8/8/R7/K7")
  }

  @Test
  fun `invalid rook movement`() {
    val board = Board(FEN("R7/8/8/8/8/8/8/K7", 'w', ""))
    val singleMove =
      SingleMove(Position(File.A, Rank.EIGHT), Position(File.B, Rank.TWO))
    assertThatThrownBy { board.makeMove(singleMove) }
      .hasMessageContaining("Invalid move for piece Rook from a8 to b2")
  }

  @Test
  fun `Rook movement with path blocked by pawn`() {
    val board = Board(FEN("R7/8/8/8/8/8/P7/1K6"))
    val singleMove =
      SingleMove(Position(File.A, Rank.EIGHT), Position(File.A, Rank.ONE))
    assertThatThrownBy { board.makeMove(singleMove) }
      .hasMessageContaining("Invalid move for piece Rook from a8 to a1")
  }

  @Test
  fun `capture piece with rook`() {
    val board = Board(FEN("R7/8/8/8/8/8/p7/K7", 'w', ""))
    val singleMove =
      SingleMove(Position(File.A, Rank.EIGHT), Position(File.A, Rank.TWO))
    board.makeMove(singleMove)

    assertThat(FEN.generateFENBoardString(board)).isEqualTo("8/8/8/8/8/8/R7/K7")
  }
}
