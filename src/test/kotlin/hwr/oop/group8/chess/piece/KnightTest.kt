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
  fun `Test char representation`() {
    val boardInspector = Board(FENData("8/8/8/8/8/8/8/K7", 'w', ""))
    val whiteKnight = Knight(Color.WHITE, boardInspector)
    val blackKnight = Knight(Color.BLACK, boardInspector)
    assertThat(whiteKnight.getChar()).isEqualTo('N')
    assertThat(blackKnight.getChar()).isEqualTo('n')
  }

  @Test
  fun `Test Knight movement`() {
    //Knight move from d4 to f5
    var board = Board(FENData("8/8/8/8/3N4/8/8/K7"))
    var move = Move(Position('d', 4), Position('f', 5))
    board.makeMove(move)

    assertThat(board.generateFENBoardString()).isEqualTo("8/8/8/5N2/8/8/8/K7")

    //Knight move from d4 to e6
    board = Board(FENData("8/8/8/8/3N4/8/8/K7"))
    move = Move(Position('d', 4), Position('e', 6))
    board.makeMove(move)

    assertThat(board.generateFENBoardString()).isEqualTo("8/8/4N3/8/8/8/8/K7")

    //Knight move from d4 to e2
    board = Board(FENData("8/8/8/8/3N4/8/8/K7"))
    move = Move(Position('d', 4), Position('e', 2))
    board.makeMove(move)

    assertThat(board.generateFENBoardString()).isEqualTo("8/8/8/8/8/8/4N3/K7")

    //Knight move from d4 to b3
    board = Board(FENData("8/8/8/8/3N4/8/8/K7"))
    move = Move(Position('d', 4), Position('b', 3))
    board.makeMove(move)

    assertThat(board.generateFENBoardString()).isEqualTo("8/8/8/8/8/1N6/8/K7")

    //Knight move from d4 to c2
    board = Board(FENData("8/8/8/8/3N4/8/8/K7"))
    move = Move(Position('d', 4), Position('c', 2))
    board.makeMove(move)

    assertThat(board.generateFENBoardString()).isEqualTo("8/8/8/8/8/8/2N5/K7")

    //Knight move from d4 to b5
    board = Board(FENData("8/8/8/8/3N4/8/8/K7"))
    move = Move(Position('d', 4), Position('b', 5))
    board.makeMove(move)

    assertThat(board.generateFENBoardString()).isEqualTo("8/8/8/1N6/8/8/8/K7")

    //Knight move from d4 to c6
    board = Board(FENData("8/8/8/8/3N4/8/8/K7"))
    move = Move(Position('d', 4), Position('c', 6))
    board.makeMove(move)

    assertThat(board.generateFENBoardString()).isEqualTo("8/8/2N5/8/8/8/8/K7")

    //Knight move from d4 to f3
    board = Board(FENData("8/8/8/8/3N4/8/8/K7"))
    move = Move(Position('d', 4), Position('f', 3))
    board.makeMove(move)

    assertThat(board.generateFENBoardString()).isEqualTo("8/8/8/8/8/5N2/8/K7")

    // Knight move from a3 to b5
    board = Board(FENData("8/8/8/8/8/N7/8/K7", 'w', ""))
    move = Move(Position('a', 3), Position('b', 5))
    board.makeMove(move)

    assertThat(board.generateFENBoardString()).isEqualTo("8/8/8/1N6/8/8/8/K7")
  }

  @Test
  fun `Test Knight capture`() {
    val board = Board(FENData("8/8/8/5n2/3N4/8/8/K7"))
    val move = Move(Position('d', 4), Position('f', 5))
    board.makeMove(move)

    assertThat(board.generateFENBoardString()).isEqualTo("8/8/8/5N2/8/8/8/K7")
  }

  @Test
  fun `Test Knight end-position blocked`() {
    val board = Board(FENData("8/8/8/5N2/3N4/8/8/K7"))
    val move = Move(Position('d', 4), Position('f', 5))

    assertThatThrownBy { board.makeMove(move) }
    assertThat(board.generateFENBoardString()).isEqualTo("8/8/8/5N2/3N4/8/8/K7")
  }

  @Test
  fun `Test Knight invalid moves`() {
    // Knight move from d4 to d2
    var board = Board(FENData("8/8/8/8/3N4/8/8/K7"))
    var move = Move(Position('d', 4), Position('d', 2))

    assertThatThrownBy { board.makeMove(move) }
    assertThat(board.generateFENBoardString()).isEqualTo("8/8/8/8/3N4/8/8/K7")

    // Knight move from d4 to e5
    board = Board(FENData("8/8/8/8/3N4/8/8/K7"))
    move = Move(Position('d', 4), Position('f', 6))

    assertThatThrownBy { board.makeMove(move) }
    assertThat(board.generateFENBoardString()).isEqualTo("8/8/8/8/3N4/8/8/K7")
  }

  @Test
  fun `Test knight movement set generation with overflow for rank & file`() {
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
  fun `Test knight movement set generation with lower overflow for rank & file`() {
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

  @Test
  fun ` Knight adds to move history`() {
    val board = Board(FENData("8/8/8/5n2/3N4/8/8/K7"))
    val knight = Knight(Color.WHITE, board)
    val move = Move(Position('d', 4), Position('f', 5))
    knight.saveMoveToHistory(move)
    assertThat(knight.moveHistory.last()).isEqualTo(move)
  }
}
