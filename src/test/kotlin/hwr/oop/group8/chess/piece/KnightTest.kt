package hwr.oop.group8.chess.piece

import hwr.oop.group8.chess.core.Board
import hwr.oop.group8.chess.core.Color
import hwr.oop.group8.chess.core.Move
import hwr.oop.group8.chess.core.Position
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
  }

  @Test
  fun `Knight move from d4 to f5`() {
    val board = Board(FENData("8/8/8/8/3N4/8/8/K7"))
    val move = Move(Position('d', 4), Position('f', 5))
    board.makeMove(move)

    assertThat(board.generateFENBoardString()).isEqualTo("8/8/8/5N2/8/8/8/K7")
  }
  @Test
  fun `Knight move from d4 to e6`() {
    val board = Board(FENData("8/8/8/8/3N4/8/8/K7"))
    val move = Move(Position('d', 4), Position('e', 6))
    board.makeMove(move)

    assertThat(board.generateFENBoardString()).isEqualTo("8/8/4N3/8/8/8/8/K7")
  }
  @Test
  fun `Knight move from d4 to e2`() {
    val board = Board(FENData("8/8/8/8/3N4/8/8/K7"))
    val move = Move(Position('d', 4), Position('e', 2))
    board.makeMove(move)

    assertThat(board.generateFENBoardString()).isEqualTo("8/8/8/8/8/8/4N3/K7")
  }
  @Test
  fun `Knight move from d4 to b3`() {
    val board = Board(FENData("8/8/8/8/3N4/8/8/K7"))
    val move = Move(Position('d', 4), Position('b', 3))
    board.makeMove(move)

    assertThat(board.generateFENBoardString()).isEqualTo("8/8/8/8/8/1N6/8/K7")
  }
  @Test
  fun `Knight move from d4 to c2`() {
    val board = Board(FENData("8/8/8/8/3N4/8/8/K7"))
    val move = Move(Position('d', 4), Position('c', 2))
    board.makeMove(move)

    assertThat(board.generateFENBoardString()).isEqualTo("8/8/8/8/8/8/2N5/K7")
  }
  @Test
  fun `Knight move from d4 to b5`() {
   val board = Board(FENData("8/8/8/8/3N4/8/8/K7"))
    val move = Move(Position('d', 4), Position('b', 5))
    board.makeMove(move)

    assertThat(board.generateFENBoardString()).isEqualTo("8/8/8/1N6/8/8/8/K7")
  }
  @Test
  fun `Knight move from d4 to c6`() {
   val board = Board(FENData("8/8/8/8/3N4/8/8/K7"))
    val move = Move(Position('d', 4), Position('c', 6))
    board.makeMove(move)

    assertThat(board.generateFENBoardString()).isEqualTo("8/8/2N5/8/8/8/8/K7")
  }
    @Test
    fun `Knight move from d4 to f3`() {
      val board = Board(FENData("8/8/8/8/3N4/8/8/K7"))
      val move = Move(Position('d', 4), Position('f', 3))
      board.makeMove(move)

      assertThat(board.generateFENBoardString()).isEqualTo("8/8/8/8/8/5N2/8/K7")
    }

  @Test
  fun `Knight move from a3 to b5`(){
    val board = Board(FENData("8/8/8/8/8/N7/8/K7", 'w', ""))
    val move = Move(Position('a', 3), Position('b', 5))
    board.makeMove(move)

    assertThat(board.generateFENBoardString()).isEqualTo("8/8/8/1N6/8/8/8/K7")
  }

  @Test
  fun `Knight capture`() {
    val board = Board(FENData("8/8/8/5n2/3N4/8/8/K7"))
    val move = Move(Position('d', 4), Position('f', 5))
    board.makeMove(move)

    assertThat(board.generateFENBoardString()).isEqualTo("8/8/8/5N2/8/8/8/K7")
  }

  @Test
  fun `Knight end-position blocked`() {
    val board = Board(FENData("8/8/8/5N2/3N4/8/8/K7"))
    val move = Move(Position('d', 4), Position('f', 5))

    assertThatThrownBy { board.makeMove(move) }
    assertThat(board.generateFENBoardString()).isEqualTo("8/8/8/5N2/3N4/8/8/K7")
  }

  @Test
  fun `Knight invalid moves`() {
    // Knight move from d4 to d2
    val board = Board(FENData("8/8/8/8/3N4/8/8/K7"))
    val move = Move(Position('d', 4), Position('d', 2))

    assertThatThrownBy { board.makeMove(move) }
    assertThat(board.generateFENBoardString()).isEqualTo("8/8/8/8/3N4/8/8/K7")
  }
  @Test
  fun `Knight move from d4 to e5`() {
    val board = Board(FENData("8/8/8/8/3N4/8/8/K7"))
    val move = Move(Position('d', 4), Position('f', 6))

    assertThatThrownBy { board.makeMove(move) }
    assertThat(board.generateFENBoardString()).isEqualTo("8/8/8/8/3N4/8/8/K7")
  }

  @Test
  fun `Knight movement set generation with overflow for rank & file`() {
    val board = Board(FENData("8/6n1/8/7p/8/8/8/k7", 'b'))
    val startPosition = Position('g', 7)
    val validMoveDestinationsOfKnight =
      board.getPieceAt(startPosition)!!.getValidMoveDestinations()

    assertThat(validMoveDestinationsOfKnight).containsExactly(
      Move(startPosition, Position('e', 8)),
      Move(startPosition, Position('e', 6)),
      Move(startPosition, Position('f', 5)),
    )
  }

  @Test
  fun `Knight movement set generation with lower overflow for rank & file`() {
    val board = Board(FENData("K7/8/8/8/3p4/8/2N5/R7"))
    val startPosition = Position('c', 2)
    val validMoveDestinationOfKnight =
      board.getPieceAt(startPosition)!!.getValidMoveDestinations()

    assertThat(validMoveDestinationOfKnight).containsExactly(
      Move(startPosition, Position('e', 3)),
      Move(startPosition, Position('e', 1)),
      Move(startPosition, Position('a', 3)),
      Move(startPosition, Position('d', 4)),
      Move(startPosition, Position('b', 4)),
    )
  }
}
