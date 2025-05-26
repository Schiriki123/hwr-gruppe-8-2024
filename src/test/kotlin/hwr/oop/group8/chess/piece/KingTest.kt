package hwr.oop.group8.chess.piece

import hwr.oop.group8.chess.core.Board
import hwr.oop.group8.chess.core.Color
import hwr.oop.group8.chess.core.Move
import hwr.oop.group8.chess.core.Position
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
  }

  @Test
  fun `King movement block path`() {
    val board = Board(FENData("8/B7/K7/8/8/8/8/8", 'w', ""))
    val move = Move(Position('a', 6), Position('a', 7))

    assertThatThrownBy { board.makeMove(move) }
    assertThat(board.generateFENBoardString()).isEqualTo("8/B7/K7/8/8/8/8/8")
  }

  @Test
  fun `King movement on empty board left`() {
    val board = Board(FENData("8/8/8/3K4/8/8/8/8", 'w', ""))
    val move = Move(Position('d', 5), Position('c', 5))
    board.makeMove(move)
    assertThat(board.generateFENBoardString()).isEqualTo("8/8/8/2K5/8/8/8/8")
  }

  @Test
  fun `King movement on empty board right`() {
    val board = Board(FENData("8/8/8/3K4/8/8/8/8", 'w', ""))
    val move = Move(Position('d', 5), Position('e', 5))
    board.turn = Color.WHITE
    board.makeMove(move)
    assertThat(board.generateFENBoardString()).isEqualTo("8/8/8/4K3/8/8/8/8")
  }

  @Test
  fun `King movement on empty board down`() {
    val board = Board(FENData("8/8/8/3K4/8/8/8/8", 'w', ""))
    val move = Move(Position('d', 5), Position('d', 4))
    board.turn = Color.WHITE
    board.makeMove(move)
    assertThat(board.generateFENBoardString()).isEqualTo("8/8/8/8/3K4/8/8/8")
  }

  @Test
  fun `King movement on empty board up`() {
    val board = Board(FENData("8/8/8/3K4/8/8/8/8", 'w', ""))
    val move = Move(Position('d', 5), Position('d', 6))
    board.turn = Color.WHITE
    board.makeMove(move)
    assertThat(board.generateFENBoardString()).isEqualTo("8/8/3K4/8/8/8/8/8")
  }

  @Test
  fun `King movement on empty board top right`() {
    val board = Board(FENData("8/8/8/3K4/8/8/8/8", 'w', ""))
    val move = Move(Position('d', 5), Position('e', 6))
    board.turn = Color.WHITE
    board.makeMove(move)
    assertThat(board.generateFENBoardString()).isEqualTo("8/8/4K3/8/8/8/8/8")
  }

  @Test
  fun `King movement on empty board top left`() {
    val board = Board(FENData("8/8/8/3K4/8/8/8/8", 'w', ""))
    val move = Move(Position('d', 5), Position('c', 6))
    board.turn = Color.WHITE
    board.makeMove(move)
    assertThat(board.generateFENBoardString()).isEqualTo("8/8/2K5/8/8/8/8/8")
  }

  @Test
  fun `King movement on empty board bottom left`() {
    val board = Board(FENData("8/8/8/3K4/8/8/8/8", 'w', ""))
    val move = Move(Position('d', 5), Position('c', 4))
    board.turn = Color.WHITE
    board.makeMove(move)
    assertThat(board.generateFENBoardString()).isEqualTo("8/8/8/8/2K5/8/8/8")
  }

  @Test
  fun `King movement on empty board bottom right`() {
    val board = Board(FENData("8/8/2K5/8/8/8/8/8", 'w', ""))
    val move = Move(Position('c', 6), Position('d', 5))
    board.turn = Color.WHITE
    board.makeMove(move)
    assertThat(board.generateFENBoardString()).isEqualTo("8/8/8/3K4/8/8/8/8")
  }

  @Test
  fun `Invalid double move, expecting exception`() {
    val board = Board(FENData("8/8/K7/8/8/8/8/8", 'w', ""))
    val move = Move(Position('a', 6), Position('a', 4))
    assertThatThrownBy { board.makeMove(move) }
    assertThat(board.generateFENBoardString()).isEqualTo("8/8/K7/8/8/8/8/8")
  }

  @Test
  fun `King movement to capture`() {
    val board = Board(FENData("8/p7/1K6/8/8/8/8/8", 'w', ""))
    val move = Move(Position('b', 6), Position('a', 7))
    board.makeMove(move)
    assertThat(board.generateFENBoardString()).isEqualTo("8/K7/8/8/8/8/8/8")
  }

  @Test
  fun `Movement set generation for black king`() {
    val board = Board(FENData("r3k2r/8/8/8/8/8/1K6/8", 'b'))
    val startPosition = Position('e', 8)
    val king = board.getPieceAt(startPosition) as King
    val possibleMoves = king.getValidMoveDestinations()

    assertThat(possibleMoves).containsExactlyInAnyOrder(
      Move(startPosition, Position('d', 8)),
      Move(startPosition, Position('d', 7)),
      Move(startPosition, Position('f', 8)),
      Move(startPosition, Position('f', 7)),
      Move(startPosition, Position('e', 7)),
      Move(
        startPosition,
        Position('c', 8),
        listOf(Move(Position('a', 8), Position('d', 8))),
      ),
      Move(
        startPosition,
        Position('g', 8),
        listOf(Move(Position('h', 8), Position('f', 8))),
      ),

    )
  }
}
