package hwr.oop.group8.chess

import hwr.oop.group8.chess.persistence.FENData
import hwr.oop.group8.chess.piece.King
import hwr.oop.group8.chess.piece.Piece

class Board(fenData: FENData) : BoardInspector {
  private val map = HashMap<Position, Square>()
  var turn: Color
  val castle: String
  val enPassant: String
  val halfmoveClock: Int
  val fullmoveClock: Int

  init { // Creation of Board in Map based on FEN String
    for (rank in 1..8) {
      var fileCounter = 'a'
      fenData.getRank(rank).forEach { fileChar ->
        if (fileChar.isDigit()) {
          repeat(fileChar.digitToInt()) {
            map.put(Position(fileCounter, rank), Square(null))
            fileCounter++
          }
        } else {
          map.put(
            Position(fileCounter, rank),
            Square(FENData.createPieceOnBoard(fileChar, this))
          )
          fileCounter++
        }
      }
    }
    check(map.size == 64) { "Board must have exactly 64 squares." }

    turn = fenData.getTurn()
    castle = fenData.castle
    enPassant = fenData.enPassant
    halfmoveClock = fenData.halfmoveClock
    fullmoveClock = fenData.fullmoveClock
    check(!isCheckmate()) {
      "Game is over, checkmate!"
    }
  }

  fun getSquare(position: Position): Square {
    return map.getValue(position)
  }

  override fun getPieceAt(position: Position): Piece? {
    return getSquare(position).getPiece()
  }

  override fun findPositionOfPiece(piece: Piece): Position {
    return map.filterValues { it.getPiece() === piece }.keys.first()
  }

  fun makeMove(move: Move) {
    val fromSquare = getSquare(move.from)
    val toSquare = getSquare(move.to)
    val piece = fromSquare.getPiece()

    checkNotNull(piece)
    check(piece.color == turn)
    { "It's not your turn" }

    require(piece.color != toSquare.getPiece()?.color)
    { "Cannot move to a square occupied by the same color" }

    check(piece.getValidMoveDestinations().contains(move.to))
    { "Invalid move for piece ${piece::class.simpleName} from ${move.from} to ${move.to}" }

    check(isCheck(move))
    { "Move would put player in check" }

    toSquare.setPiece(piece)
    fromSquare.setPiece(null)
    turn = turn.invert()
  }

  private fun isCheckmate(): Boolean {
    val allPiecesCurrentPlayer: Set<Piece> =
      map.values.mapNotNull { it.getPiece() }.filter { it.color == turn }
        .toSet()
    allPiecesCurrentPlayer.forEach { piece ->
      val possibleMoves: Set<Position> = piece.getValidMoveDestinations()
      val startPosition = findPositionOfPiece(piece)
      // Check if any of the possible moves would put the player in check
      possibleMoves.forEach { destination ->
        if (
          isCheck(
            Move(
              startPosition,
              destination
            )
          )
        ) {
          // If any move is valid and does not put the player in check, return false
          return false
        }
      }
    }
    // If no valid moves are found, return true
    return true
  }

  private fun isCheck(move: Move): Boolean {
    // Simulate the move
    val fromSquare = getSquare(move.from)
    val toSquare = getSquare(move.to)
    val movedPiece = fromSquare.getPiece()
    val pieceOnTargetSquare = toSquare.getPiece()
    toSquare.setPiece(movedPiece)
    fromSquare.setPiece(null)

    // Check if the move puts the player in check
    val allPieces: Set<Piece> = map.values.mapNotNull { it.getPiece() }.toSet()
    val possibleMovesOfOpponent: Set<Position> = allPieces
      .filter { it.color != turn }
      .flatMap { it.getValidMoveDestinations() }
      .toSet()
    val kingPosition: Position = allPieces.filter { it.color == turn }
      .first { it is King }
      .let { findPositionOfPiece(it) }

    // Restore original board state
    fromSquare.setPiece(movedPiece)
    toSquare.setPiece(pieceOnTargetSquare)

    // Check if the king is in check
    return !possibleMovesOfOpponent.contains(kingPosition)
  }

  fun getCapturedPieces(): String {
    val whitePieces = StringBuilder()
    whitePieces.append("RNBQKBNR")
    whitePieces.append("PPPPPPPP")
    val blackPieces = StringBuilder()
    blackPieces.append("rnbqkbnr")
    blackPieces.append("pppppppp")
    for (square in map.values) {
      square.getPiece()?.let { piece ->
        if (piece.color == Color.WHITE) {
          whitePieces.deleteAt(whitePieces.indexOf(piece.getChar()))
        } else {
          blackPieces.deleteAt(blackPieces.indexOf(piece.getChar()))
        }
      }
    }
    return "White's captures: $blackPieces\nBlack's captures: $whitePieces"
  }

  fun generateFENBoardString(): String {
    val builder = StringBuilder()
    var lastPiece = 0
    for (rank in 8 downTo 1) {
      for (file in 'a'..'h') {
        val piece = map.getValue(Position(file, rank)).getPiece()
        if (piece != null) {
          if (lastPiece != 0) {
            builder.append(lastPiece)
          }
          builder.append(piece.getChar())
          lastPiece = 0
        } else {
          lastPiece++
        }
      }
      if (lastPiece != 0) builder.append(lastPiece); lastPiece = 0
      builder.append('/')
    }
    return builder.toString().dropLast(1)
  }

  fun getFENData(): FENData {
    return FENData(
      generateFENBoardString(),
      if (turn == Color.WHITE) 'w' else 'b',
      castle,
      enPassant,
      halfmoveClock,
      fullmoveClock
    )
  }

  fun printBoard() {
    val builder = StringBuilder()
    for (rank in 8 downTo 1) {
      for (file in 'a'..'h') {
        val piece = map.getValue(Position(file, rank)).getPiece()
        builder.append(piece?.getChar() ?: '.')
      }
      builder.append("\n")
    }
    println(builder.toString().trim())
  }
}
