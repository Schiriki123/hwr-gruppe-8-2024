package hwr.oop.group8.chess

import hwr.oop.group8.chess.piece.King
import hwr.oop.group8.chess.piece.Piece

class Board(fenData: FENData) : BoardInspector {
  private val map = HashMap<Position, Square>()
  var turn: Color
  var castle: String
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

    check(isMoveCheck(move))
    { "Move would put player in check" }

    // Check for castle move and update rook position
    val homeRank = if (piece.color == Color.WHITE) 1 else 8
    if (move == Move(
        Position('e', homeRank),
        Position('g', homeRank)
      ) && isCastlingAllowed(
        turn
      ).second
    ) {
      val rook = getPieceAt(Position('h', homeRank))
      map.getValue(Position('f', homeRank)).setPiece(rook)
      map.getValue(Position('h', homeRank)).setPiece(null)
    } else if (move == Move(
        Position('e', homeRank),
        Position('c', homeRank)
      ) && isCastlingAllowed(turn).second
    ) {
      val rook = getPieceAt(Position('a', homeRank))
      map.getValue(Position('d', homeRank)).setPiece(rook)
      map.getValue(Position('a', homeRank)).setPiece(null)
    }

    toSquare.setPiece(piece)
    fromSquare.setPiece(null)
    updateCastlingPermission()
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
          isMoveCheck(
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

  private fun isMoveCheck(move: Move): Boolean {
    // Simulate the move
    val fromSquare = getSquare(move.from)
    val toSquare = getSquare(move.to)
    val movedPiece = fromSquare.getPiece()
    val pieceOnTargetSquare = toSquare.getPiece()
    toSquare.setPiece(movedPiece)
    fromSquare.setPiece(null)

    // Check if the move puts the player in check
    val doesMovePutInCheck = isCheck()

    // Restore original board state
    fromSquare.setPiece(movedPiece)
    toSquare.setPiece(pieceOnTargetSquare)

    return !doesMovePutInCheck
  }

  private fun isCheck(): Boolean {

    val allPieces: Set<Piece> = map.values.mapNotNull { it.getPiece() }.toSet()
    val possibleMovesOfOpponent: Set<Position> = allPieces
      .filter { it.color != turn }
      .flatMap { it.getValidMoveDestinations() }
      .toSet()
    val kingPosition: Position = allPieces.filter { it.color == turn }
      .first { it is King }
      .let { findPositionOfPiece(it) }

    return possibleMovesOfOpponent.contains(kingPosition)
  }

  override fun isCastlingAllowed(color: Color): Pair<Boolean, Boolean> {
    val homeRank = if (color == Color.WHITE) 1 else 8
    val kingPosition = Position('e', homeRank)
    val rookPositionKingSide = Position('h', homeRank)
    val rookPositionQueenSide = Position('a', homeRank)

    if (isCheck()) {
      return Pair(false, false)
    }
    // King side castle
    val kingSide: Boolean =
      getPieceAt(Position('f', homeRank)) == null &&
          getPieceAt(Position('g', homeRank)) == null &&
          castle.contains(if (color == Color.WHITE) "K" else "k") &&
          isMoveCheck(Move(kingPosition, Position('f', homeRank))) &&
          isMoveCheck(Move(kingPosition, Position('g', homeRank)))

    // Queen side castle
    val queenSide: Boolean =
      getPieceAt(Position('d', homeRank)) == null &&
          getPieceAt(Position('c', homeRank)) == null &&
          getPieceAt(Position('b', homeRank)) == null &&
          castle.contains(if (color == Color.WHITE) "Q" else "q") &&
          isMoveCheck(Move(kingPosition, Position('d', homeRank))) &&
          isMoveCheck(Move(kingPosition, Position('c', homeRank)))

    return Pair(queenSide, kingSide)
  }

  override fun getCurrentTurn(): Color {
    return turn
  }

  private fun updateCastlingPermission() {
    if (castle.isEmpty()) {
      return
    }
    val homeRank = if (turn == Color.WHITE) 1 else 8
    val kingPosition = getPieceAt(Position('e', homeRank))
    val rookPositionKingSide = getPieceAt(Position('h', homeRank))
    val rookPositionQueenSide = getPieceAt(Position('a', homeRank))
    val kingChar = if (turn == Color.WHITE) "K" else "k"
    val queenChar = if (turn == Color.WHITE) "Q" else "q"

    if (kingPosition == null || kingPosition.color != turn) {
      castle = castle.replace(kingChar, "")
      castle = castle.replace(queenChar, "")
    }
    if (rookPositionKingSide == null || rookPositionKingSide.color != turn) {
      castle = castle.replace(kingChar, "")
    }
    if (rookPositionQueenSide == null || rookPositionQueenSide.color != turn) {
      castle = castle.replace(queenChar, "")
    }
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
