package hwr.oop.group8.chess.core.piece

import hwr.oop.group8.chess.core.Board
import hwr.oop.group8.chess.core.Color
import hwr.oop.group8.chess.core.File
import hwr.oop.group8.chess.core.Position
import hwr.oop.group8.chess.core.Rank
import hwr.oop.group8.chess.core.move.CastleMove
import hwr.oop.group8.chess.core.move.SingleMove
import hwr.oop.group8.chess.persistence.FEN
import io.kotest.core.spec.style.AnnotationSpec
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy

class KingTest : AnnotationSpec() {
  @Test
  fun `Char representation for King`() {
    val boardInspector = Board.factory(FEN("8/8/8/8/8/8/8/K7", 'w', ""))
    val whiteKing = King(Color.WHITE, boardInspector.analyser)
    val blackKing = King(Color.BLACK, boardInspector.analyser)
    assertThat(whiteKing.fenRepresentation()).isEqualTo('K')
    assertThat(blackKing.fenRepresentation()).isEqualTo('k')
    assertThat(whiteKing.getType()).isEqualTo(PieceType.KING)
  }

  @Test
  fun `King movement block path`() {
    val board = Board.factory(FEN("8/B7/K7/8/8/8/8/8", 'w', ""))
    val singleMove =
      SingleMove(Position(File.A, Rank.SIX), Position(File.A, Rank.SEVEN))

    assertThatThrownBy { board.makeMove(singleMove) }
    assertThat(FEN.generateFENBoardString(board)).isEqualTo("8/B7/K7/8/8/8/8/8")
  }

  @Test
  fun `King movement on empty board left`() {
    val board = Board.factory(FEN("8/8/8/3K4/8/8/8/8", 'w', ""))
    val singleMove =
      SingleMove(Position(File.D, Rank.FIVE), Position(File.C, Rank.FIVE))
    board.makeMove(singleMove)
    assertThat(FEN.generateFENBoardString(board)).isEqualTo("8/8/8/2K5/8/8/8/8")
  }

  @Test
  fun `King movement on empty board right`() {
    val board = Board.factory(FEN("8/8/8/3K4/8/8/8/8", 'w', ""))
    val singleMove =
      SingleMove(Position(File.D, Rank.FIVE), Position(File.E, Rank.FIVE))
    board.makeMove(singleMove)
    assertThat(FEN.generateFENBoardString(board)).isEqualTo("8/8/8/4K3/8/8/8/8")
  }

  @Test
  fun `King movement on empty board down`() {
    val board = Board.factory(FEN("8/8/8/3K4/8/8/8/8", 'w', ""))
    val singleMove =
      SingleMove(Position(File.D, Rank.FIVE), Position(File.D, Rank.FOUR))
    board.makeMove(singleMove)
    assertThat(FEN.generateFENBoardString(board)).isEqualTo("8/8/8/8/3K4/8/8/8")
  }

  @Test
  fun `King movement on empty board up`() {
    val board = Board.factory(FEN("8/8/8/3K4/8/8/8/8", 'w', ""))
    val singleMove =
      SingleMove(Position(File.D, Rank.FIVE), Position(File.D, Rank.SIX))
    board.makeMove(singleMove)
    assertThat(FEN.generateFENBoardString(board)).isEqualTo("8/8/3K4/8/8/8/8/8")
  }

  @Test
  fun `King movement on empty board top right`() {
    val board = Board.factory(FEN("8/8/8/3K4/8/8/8/8", 'w', ""))
    val singleMove =
      SingleMove(Position(File.D, Rank.FIVE), Position(File.E, Rank.SIX))
    board.makeMove(singleMove)
    assertThat(FEN.generateFENBoardString(board)).isEqualTo("8/8/4K3/8/8/8/8/8")
  }

  @Test
  fun `King movement on empty board top left`() {
    val board = Board.factory(FEN("8/8/8/3K4/8/8/8/8", 'w', ""))
    val singleMove =
      SingleMove(Position(File.D, Rank.FIVE), Position(File.C, Rank.SIX))
    board.makeMove(singleMove)
    assertThat(FEN.generateFENBoardString(board)).isEqualTo("8/8/2K5/8/8/8/8/8")
  }

  @Test
  fun `King movement on empty board bottom left`() {
    val board = Board.factory(FEN("8/8/8/3K4/8/8/8/8", 'w', ""))
    val singleMove =
      SingleMove(Position(File.D, Rank.FIVE), Position(File.C, Rank.FOUR))
    board.makeMove(singleMove)
    assertThat(FEN.generateFENBoardString(board)).isEqualTo("8/8/8/8/2K5/8/8/8")
  }

  @Test
  fun `King movement on empty board bottom right`() {
    val board = Board.factory(FEN("8/8/2K5/8/8/8/8/8", 'w', ""))
    val singleMove =
      SingleMove(Position(File.C, Rank.SIX), Position(File.D, Rank.FIVE))
    board.makeMove(singleMove)
    assertThat(FEN.generateFENBoardString(board)).isEqualTo("8/8/8/3K4/8/8/8/8")
  }

  @Test
  fun `Invalid double move, expecting exception`() {
    val board = Board.factory(FEN("8/8/K7/8/8/8/8/8", 'w', ""))
    val singleMove =
      SingleMove(Position(File.A, Rank.SIX), Position(File.A, Rank.FOUR))
    assertThatThrownBy { board.makeMove(singleMove) }
    assertThat(FEN.generateFENBoardString(board)).isEqualTo("8/8/K7/8/8/8/8/8")
  }

  @Test
  fun `King movement to capture`() {
    val board = Board.factory(FEN("8/p7/1K6/8/8/8/8/8", 'w', ""))
    val singleMove =
      SingleMove(Position(File.B, Rank.SIX), Position(File.A, Rank.SEVEN))
    board.makeMove(singleMove)
    assertThat(FEN.generateFENBoardString(board)).isEqualTo("8/K7/8/8/8/8/8/8")
  }

  @Test
  fun `Movement set generation for black king`() {
    val board = Board.factory(FEN("r3k2r/8/8/8/8/8/1K6/8", 'b'))
    val startPosition = Position(File.E, Rank.EIGHT)
    val king = board.analyser.getPieceAt(startPosition) as King
    val possibleMoves = king.getValidMove()

    assertThat(possibleMoves).containsExactlyInAnyOrder(
      SingleMove(startPosition, Position(File.D, Rank.EIGHT)),
      SingleMove(startPosition, Position(File.D, Rank.SEVEN)),
      SingleMove(startPosition, Position(File.F, Rank.EIGHT)),
      SingleMove(startPosition, Position(File.F, Rank.SEVEN)),
      SingleMove(startPosition, Position(File.E, Rank.SEVEN)),
      CastleMove(king, false),
      CastleMove(king, true),
    )
  }
}
