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

class KnightTest : AnnotationSpec() {

  @Test
  fun `Char representation of Knight`() {
    val boardInspector = Board(FENData("8/8/8/8/8/8/8/K7", 'w', ""))
    val whiteKnight = Knight(Color.WHITE, boardInspector)
    val blackKnight = Knight(Color.BLACK, boardInspector)
    assertThat(whiteKnight.getChar()).isEqualTo('N')
    assertThat(blackKnight.getChar()).isEqualTo('n')
    assertThat(whiteKnight.getType()).isEqualTo(PieceType.KNIGHT)
  }

  @Test
  fun `Knight move from d4 to f5`() {
    val board = Board(FENData("8/8/8/8/3N4/8/8/K7", 'w', ""))
    val move = Move(Position(File.D, Rank.FOUR), Position(File.F, Rank.FIVE))
    board.makeMove(move)

    assertThat(board.generateFENBoardString()).isEqualTo("8/8/8/5N2/8/8/8/K7")
  }

  @Test
  fun `Knight move from d4 to e6`() {
    val board = Board(FENData("8/8/8/8/3N4/8/8/K7", 'w', ""))
    val move = Move(Position(File.D, Rank.FOUR), Position(File.E, Rank.SIX))
    board.makeMove(move)

    assertThat(board.generateFENBoardString()).isEqualTo("8/8/4N3/8/8/8/8/K7")
  }

  @Test
  fun `Knight move from d4 to e2`() {
    val board = Board(FENData("8/8/8/8/3N4/8/8/K7", 'w', ""))
    val move = Move(Position(File.D, Rank.FOUR), Position(File.E, Rank.TWO))
    board.makeMove(move)

    assertThat(board.generateFENBoardString()).isEqualTo("8/8/8/8/8/8/4N3/K7")
  }

  @Test
  fun `Knight move from d4 to b3`() {
    val board = Board(FENData("8/8/8/8/3N4/8/8/K7", 'w', ""))
    val move = Move(Position(File.D, Rank.FOUR), Position(File.B, Rank.THREE))
    board.makeMove(move)

    assertThat(board.generateFENBoardString()).isEqualTo("8/8/8/8/8/1N6/8/K7")
  }

  @Test
  fun `Knight move from d4 to c2`() {
    val board = Board(FENData("8/8/8/8/3N4/8/8/K7", 'w', ""))
    val move = Move(Position(File.D, Rank.FOUR), Position(File.C, Rank.TWO))
    board.makeMove(move)

    assertThat(board.generateFENBoardString()).isEqualTo("8/8/8/8/8/8/2N5/K7")
  }

  @Test
  fun `Knight move from d4 to b5`() {
    val board = Board(FENData("8/8/8/8/3N4/8/8/K7", 'w', ""))
    val move = Move(Position(File.D, Rank.FOUR), Position(File.B, Rank.FIVE))
    board.makeMove(move)

    assertThat(board.generateFENBoardString()).isEqualTo("8/8/8/1N6/8/8/8/K7")
  }

  @Test
  fun `Knight move from d4 to c6`() {
    val board = Board(FENData("8/8/8/8/3N4/8/8/K7", 'w', ""))
    val move = Move(Position(File.D, Rank.FOUR), Position(File.C, Rank.SIX))
    board.makeMove(move)

    assertThat(board.generateFENBoardString()).isEqualTo("8/8/2N5/8/8/8/8/K7")
  }

  @Test
  fun `Knight move from d4 to f3`() {
    val board = Board(FENData("8/8/8/8/3N4/8/8/K7", 'w', ""))
    val move = Move(Position(File.D, Rank.FOUR), Position(File.F, Rank.THREE))
    board.makeMove(move)

    assertThat(board.generateFENBoardString()).isEqualTo("8/8/8/8/8/5N2/8/K7")
  }

  @Test
  fun `Knight move from a3 to b5`() {
    val board = Board(FENData("8/8/8/8/8/N7/8/K7", 'w', ""))
    val move = Move(Position(File.A, Rank.THREE), Position(File.B, Rank.FIVE))
    board.makeMove(move)

    assertThat(board.generateFENBoardString()).isEqualTo("8/8/8/1N6/8/8/8/K7")
  }

  @Test
  fun `Knight capture`() {
    val board = Board(FENData("8/8/8/5n2/3N4/8/8/K7", 'w', ""))
    val move = Move(Position(File.D, Rank.FOUR), Position(File.F, Rank.FIVE))
    board.makeMove(move)

    assertThat(board.generateFENBoardString()).isEqualTo("8/8/8/5N2/8/8/8/K7")
  }

  @Test
  fun `Knight end-position blocked`() {
    val board = Board(FENData("8/8/8/5N2/3N4/8/8/K7", 'w', ""))
    val move = Move(Position(File.D, Rank.FOUR), Position(File.F, Rank.FIVE))

    assertThatThrownBy { board.makeMove(move) }
    assertThat(
      board.generateFENBoardString(),
    ).isEqualTo("8/8/8/5N2/3N4/8/8/K7")
  }

  @Test
  fun `Knight invalid moves`() {
    // Knight move from d4 to d2
    val board = Board(FENData("8/8/8/8/3N4/8/8/K7", 'w', ""))
    val move = Move(Position(File.D, Rank.FOUR), Position(File.D, Rank.TWO))

    assertThatThrownBy { board.makeMove(move) }
    assertThat(board.generateFENBoardString()).isEqualTo("8/8/8/8/3N4/8/8/K7")
  }

  @Test
  fun `Knight move from d4 to e5`() {
    val board = Board(FENData("8/8/8/8/3N4/8/8/K7", 'w', ""))
    val move = Move(Position(File.D, Rank.FOUR), Position(File.F, Rank.SIX))

    assertThatThrownBy { board.makeMove(move) }
    assertThat(board.generateFENBoardString()).isEqualTo("8/8/8/8/3N4/8/8/K7")
  }

  @Test
  fun `Knight movement set generation with overflow for rank & file`() {
    val board = Board(FENData("8/6n1/8/7p/8/8/8/k7", 'b', ""))
    val startPosition = Position(File.G, Rank.SEVEN)
    val validMoveDestinationsOfKnight =
      board.getPieceAt(startPosition)!!.getValidMoveDestinations()

    assertThat(validMoveDestinationsOfKnight).containsExactly(
      Move(startPosition, Position(File.E, Rank.EIGHT)),
      Move(startPosition, Position(File.E, Rank.SIX)),
      Move(startPosition, Position(File.H, Rank.FIVE)),
      Move(startPosition, Position(File.F, Rank.FIVE)),
    )
  }

  @Test
  fun `Knight movement set generation with lower overflow for rank & file`() {
    val board = Board(FENData("K7/8/8/8/3p4/8/2N5/R7", castle = ""))
    val startPosition = Position(File.C, Rank.TWO)
    val validMoveDestinationOfKnight =
      board.getPieceAt(startPosition)!!.getValidMoveDestinations()

    assertThat(validMoveDestinationOfKnight).containsExactly(
      Move(startPosition, Position(File.E, Rank.THREE)),
      Move(startPosition, Position(File.A, Rank.THREE)),
      Move(startPosition, Position(File.E, Rank.ONE)),
      Move(startPosition, Position(File.A, Rank.ONE)),
      Move(startPosition, Position(File.D, Rank.FOUR)),
      Move(startPosition, Position(File.B, Rank.FOUR)),
    )
  }
}
