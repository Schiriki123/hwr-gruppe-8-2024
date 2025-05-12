package hwr.oop.group8.chess.persistence

import hwr.oop.group8.chess.Board
import hwr.oop.group8.chess.piece.Bishop
import hwr.oop.group8.chess.piece.King
import hwr.oop.group8.chess.piece.Knight
import hwr.oop.group8.chess.piece.Piece
import hwr.oop.group8.chess.piece.Queen
import hwr.oop.group8.chess.piece.Rook
import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.types.shouldBeInstanceOf
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat

class FENDataTest : AnnotationSpec() {
  @Test
  fun `test getRank for default board`() {
    val fenData = FENData()

    assertThat(fenData.getRank(8)).isEqualTo("rnbqkbnr")
    assertThat(fenData.getRank(7)).isEqualTo("pppppppp")
    assertThat(fenData.getRank(6)).isEqualTo("8")
    assertThat(fenData.getRank(5)).isEqualTo("8")
    assertThat(fenData.getRank(4)).isEqualTo("8")
    assertThat(fenData.getRank(3)).isEqualTo("8")
    assertThat(fenData.getRank(2)).isEqualTo("PPPPPPPP")
    assertThat(fenData.getRank(1)).isEqualTo("RNBQKBNR")

    Assertions.assertThatThrownBy {
      fenData.getRank(9)
    }.message().isEqualTo("Rank must be between 1 and 8")
    Assertions.assertThatThrownBy {
      fenData.getRank(0)
    }.message().isEqualTo("Rank must be between 1 and 8")
  }

  @Test
  fun `test piece object creation`() {
    val board = Board(FENData("K7/8/8/8/8/8/8/8"))
    val pieceChars =
      listOf('r', 'n', 'b', 'q', 'k', 'p', 'R', 'N', 'B', 'Q', 'K', 'P')
    Assertions.assertThat(pieceChars).allSatisfy { pieceChar ->
      val piece = FENData.createPieceOnBoard(pieceChar, board)
      when (pieceChar) {
        'r', 'R' -> piece.shouldBeInstanceOf<Rook>()
        'n', 'N' -> piece.shouldBeInstanceOf<Knight>()
        'b', 'B' -> piece.shouldBeInstanceOf<Bishop>()
        'q', 'Q' -> piece.shouldBeInstanceOf<Queen>()
        'k', 'K' -> piece.shouldBeInstanceOf<King>()
        'p', 'P' -> piece.shouldBeInstanceOf<Piece>()
      }
    }
    Assertions.assertThatThrownBy {
      FENData.createPieceOnBoard('x', board)
    }.message().isEqualTo("Invalid piece character: x")
  }

  @Test
  fun `Test invalid initialization`() {
    Assertions.assertThatThrownBy { FENData("8/8/8/8/8/8/8/8") }.message()
      .isEqualTo("Board string must be 16 or higher")
    Assertions.assertThatThrownBy { FENData(turn = 'q') }
    Assertions.assertThatThrownBy { FENData(castle = "KQkb") }
    Assertions.assertThatThrownBy { FENData(halfmoveClock = -1) }
    Assertions.assertThatThrownBy { FENData(fullmoveClock = 0) }
  }
}
