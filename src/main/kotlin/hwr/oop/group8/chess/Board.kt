package hwr.oop.group8.chess

import hwr.oop.group8.chess.piece.Piece

class Board(fenData: FENData): BoardInspector {
  private val map = HashMap<Position, Square>()
  val turn: Color
  val castle: String
  val halfmoveClock: Int
  val fullmoveClock: Int

  init { // Creation of Board in Map based on FEN String
    for (rank in 1..8) {
      var counter = 'a'
      fenData.getRank(rank).forEach { fileChar ->
        if (fileChar.isDigit()) {
          repeat(fileChar.digitToInt()) {
            map.put(Position(counter, 9 - rank), Square(null))
            counter++
          }
        } else {
          map.put(
            Position(counter, 9 - rank),
            Square(FENData.createPieceOnBoard(fileChar, this))
          )
          counter++
        }
      }
    }
    check(map.size == 64) { "Board must have exactly 64 squares." }

    turn = fenData.getTurn()
    castle = fenData.castle
    halfmoveClock = fenData.halfmoveClock
    fullmoveClock = fenData.fullmoveClock
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
    require(piece.color != toSquare.getPiece()?.color)
    { "Cannot move to a square occupied by the same color" }

    check(piece.getValidMoveDestinations().contains(move.to))
    { "Invalid move for piece ${piece::class.simpleName} from ${move.from} to ${move.to}" }

    toSquare.setPiece(piece)
    fromSquare.setPiece(null)
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
}
