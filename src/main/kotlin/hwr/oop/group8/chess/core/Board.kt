package hwr.oop.group8.chess.core

import hwr.oop.group8.chess.persistence.FENData
import hwr.oop.group8.chess.piece.King
import hwr.oop.group8.chess.piece.Piece

class Board(fenData: FENData) : BoardInspector {
  private val map = HashMap<Position, Square>()
  var turn: Color
  val enPassant: String
  var castle: String
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

  override fun isSquareEmpty(position: Position): Boolean {
    return getSquare(position).getPiece() == null
  }

  override fun findPositionOfPiece(piece: Piece): Position {
    return map.filterValues { it.getPiece() === piece }.keys.first()
  }

  fun makeMove(move: Move) {
    val fromSquare = getSquare(move.from)
    val toSquare = getSquare(move.to)
    val specialMove = move.specialMove
    val piece = fromSquare.getPiece()

    checkNotNull(piece)
    check(piece.color == turn)
    { "It's not your turn" }

    require(piece.color != toSquare.getPiece()?.color)
    { "Cannot move to a square occupied by the same color" }

    check(piece.getValidMoveDestinations().contains(move))
    { "Invalid move for piece ${piece::class.simpleName} from ${move.from} to ${move.to}" }

    check(isMoveCheck(move))
    { "Move would put player in check" }
    //apply specialmove
    if (!move.specialMove.isEmpty()) {
      val specialToSquare = getSquare(specialMove.first().to)
      val specialFromSquare = getSquare(specialMove.first().from)
      val specialMovePiece = getPieceAt(specialMove.first().from)
      specialToSquare.setPiece(specialMovePiece)
      specialFromSquare.setPiece(null)
    }
    // apply standardmove
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
      val possibleMoves: Set<Move> = piece.getValidMoveDestinations()
      // Check if any of the possible moves would put the player in check
      possibleMoves.forEach { destination ->
        if (
          isMoveCheck(
            destination
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

    val kingPosition: Position = allPieces.filter { it.color == turn }
      .first { it is King }
      .let { findPositionOfPiece(it) }

    return isPositionThreatened(turn, kingPosition)
  }

  override fun isCastlingAllowed(color: Color): Pair<Boolean, Boolean> {
    val homeRank = if (color == Color.WHITE) 1 else 8

    if (isCheck()) {
      return Pair(false, false)
    }
    val kingSide: Boolean =
      isSquareEmpty(Position('f', homeRank)) &&
          isSquareEmpty(Position('g', homeRank)) &&
          castle.contains(if (color == Color.WHITE) "K" else "k") &&
          !isPositionThreatened(color, Position('f', homeRank)) &&
          !isPositionThreatened(color, Position('g', homeRank))

    // Queen side castle
    val queenSide: Boolean =
      isSquareEmpty(Position('d', homeRank)) &&
          isSquareEmpty(Position('c', homeRank)) &&
          isSquareEmpty(Position('b', homeRank)) &&
          castle.contains(if (color == Color.WHITE) "Q" else "q") &&
          !isPositionThreatened(color, Position('d', homeRank)) &&
          !isPositionThreatened(color, Position('c', homeRank))
    return Pair(queenSide, kingSide)
  }

  override fun getCurrentTurn(): Color {
    return turn
  }

  override fun isPositionThreatened(
    currentPlayer: Color,
    position: Position,
  ): Boolean {
    val allPieces: Set<Piece> = map.values.mapNotNull { it.getPiece() }.toSet()
    val possibleMovesOfOpponent: Set<Move> = allPieces
      .filter { it.color != currentPlayer }
      .flatMap { it.getValidMoveDestinations() }
      .toSet()
    return possibleMovesOfOpponent.any { it.to == position }
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

  fun getMap(): HashMap<Position, Square> {
    return map
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
      builder.append("${System.lineSeparator()}")
    }
    println(builder.toString().trim())
  }
}
