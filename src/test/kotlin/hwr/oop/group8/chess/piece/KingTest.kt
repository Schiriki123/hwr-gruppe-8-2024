package hwr.oop.group8.chess.piece

import hwr.oop.group8.chess.core.Board
import hwr.oop.group8.chess.core.Color
import hwr.oop.group8.chess.core.File
import hwr.oop.group8.chess.core.Move
import hwr.oop.group8.chess.core.Position
import hwr.oop.group8.chess.core.Rank
import hwr.oop.group8.chess.persistence.FENData
import io.kotest.core.spec.style.AnnotationSpec
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy

class KingTest : AnnotationSpec() {
  @Test
  fun `Char representation for King`() {
    val boardInspector = Board(FENData("8/8/8/8/8/8/8/K7", 'w', ""))
    val whiteKing = King(Color.WHITE, boardInspector)
    val blackKing = King(Color.BLACK, boardInspector)
    assertThat(whiteKing.getChar()).isEqualTo('K')
    assertThat(blackKing.getChar()).isEqualTo('k')
    assertThat(whiteKing.getType()).isEqualTo(PieceType.KING)
  }

  @Test
  fun `King movement block path`() {
    val board = Board(FENData("8/B7/K7/8/8/8/8/8", 'w', ""))
    val move = Move(Position(File.A, Rank.SIX), Position(File.A, Rank.SEVEN))

    assertThatThrownBy { board.makeMove(move) }
    assertThat(board.generateFENBoardString()).isEqualTo("8/B7/K7/8/8/8/8/8")
  }

  @Test
  fun `King movement on empty board left`() {
    val board = Board(FENData("8/8/8/3K4/8/8/8/8", 'w', ""))
    val move = Move(Position(File.D, Rank.FIVE), Position(File.C, Rank.FIVE))
    board.makeMove(move)
    assertThat(board.generateFENBoardString()).isEqualTo("8/8/8/2K5/8/8/8/8")
  }

  @Test
  fun `King movement on empty board right`() {
    val board = Board(FENData("8/8/8/3K4/8/8/8/8", 'w', ""))
    val move = Move(Position(File.D, Rank.FIVE), Position(File.E, Rank.FIVE))
    board.turn = Color.WHITE
    board.makeMove(move)
    assertThat(board.generateFENBoardString()).isEqualTo("8/8/8/4K3/8/8/8/8")
  }

  @Test
  fun `King movement on empty board down`() {
    val board = Board(FENData("8/8/8/3K4/8/8/8/8", 'w', ""))
    val move = Move(Position(File.D, Rank.FIVE), Position(File.D, Rank.FOUR))
    board.turn = Color.WHITE
    board.makeMove(move)
    assertThat(board.generateFENBoardString()).isEqualTo("8/8/8/8/3K4/8/8/8")
  }

  @Test
  fun `King movement on empty board up`() {
    val board = Board(FENData("8/8/8/3K4/8/8/8/8", 'w', ""))
    val move = Move(Position(File.D, Rank.FIVE), Position(File.D, Rank.SIX))
    board.turn = Color.WHITE
    board.makeMove(move)
    assertThat(board.generateFENBoardString()).isEqualTo("8/8/3K4/8/8/8/8/8")
  }

  @Test
  fun `King movement on empty board top right`() {
    val board = Board(FENData("8/8/8/3K4/8/8/8/8", 'w', ""))
    val move = Move(Position(File.D, Rank.FIVE), Position(File.E, Rank.SIX))
    board.turn = Color.WHITE
    board.makeMove(move)
    assertThat(board.generateFENBoardString()).isEqualTo("8/8/4K3/8/8/8/8/8")
  }

  @Test
  fun `King movement on empty board top left`() {
    val board = Board(FENData("8/8/8/3K4/8/8/8/8", 'w', ""))
    val move = Move(Position(File.D, Rank.FIVE), Position(File.C, Rank.SIX))
    board.turn = Color.WHITE
    board.makeMove(move)
    assertThat(board.generateFENBoardString()).isEqualTo("8/8/2K5/8/8/8/8/8")
  }

  @Test
  fun `King movement on empty board bottom left`() {
    val board = Board(FENData("8/8/8/3K4/8/8/8/8", 'w', ""))
    val move = Move(Position(File.D, Rank.FIVE), Position(File.C, Rank.FOUR))
    board.turn = Color.WHITE
    board.makeMove(move)
    assertThat(board.generateFENBoardString()).isEqualTo("8/8/8/8/2K5/8/8/8")
  }

  @Test
  fun `King movement on empty board bottom right`() {
    val board = Board(FENData("8/8/2K5/8/8/8/8/8", 'w', ""))
    val move = Move(Position(File.C, Rank.SIX), Position(File.D, Rank.FIVE))
    board.turn = Color.WHITE
    board.makeMove(move)
    assertThat(board.generateFENBoardString()).isEqualTo("8/8/8/3K4/8/8/8/8")
  }

  @Test
  fun `Invalid double move, expecting exception`() {
    val board = Board(FENData("8/8/K7/8/8/8/8/8", 'w', ""))
    val move = Move(Position(File.A, Rank.SIX), Position(File.A, Rank.FOUR))
    assertThatThrownBy { board.makeMove(move) }
    assertThat(board.generateFENBoardString()).isEqualTo("8/8/K7/8/8/8/8/8")
  }

  @Test
  fun `King movement to capture`() {
    val board = Board(FENData("8/p7/1K6/8/8/8/8/8", 'w', ""))
    val move = Move(Position(File.B, Rank.SIX), Position(File.A, Rank.SEVEN))
    board.makeMove(move)
    assertThat(board.generateFENBoardString()).isEqualTo("8/K7/8/8/8/8/8/8")
  }

  @Test
  fun `Movement set generation for black king`() {
    val board = Board(FENData("r3k2r/8/8/8/8/8/1K6/8", 'b'))
    val startPosition = Position(File.E, Rank.EIGHT)
    val king = board.getPieceAt(startPosition) as King
    val possibleMoves = king.getValidMoveDestinations()

    assertThat(possibleMoves).containsExactlyInAnyOrder(
      Move(startPosition, Position(File.D, Rank.EIGHT)),
      Move(startPosition, Position(File.D, Rank.SEVEN)),
      Move(startPosition, Position(File.F, Rank.EIGHT)),
      Move(startPosition, Position(File.F, Rank.SEVEN)),
      Move(startPosition, Position(File.E, Rank.SEVEN)),
      Move(
        startPosition,
        Position(File.C, Rank.EIGHT),
        listOf(
          Move(Position(File.A, Rank.EIGHT), Position(File.D, Rank.EIGHT)),
        ),
      ),
      Move(
        startPosition,
        Position(File.G, Rank.EIGHT),
        listOf(
          Move(Position(File.H, Rank.EIGHT), Position(File.F, Rank.EIGHT)),
        ),
      ),

    )
  }
}
