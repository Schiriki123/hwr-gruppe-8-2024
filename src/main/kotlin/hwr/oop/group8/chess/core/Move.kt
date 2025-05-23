package hwr.oop.group8.chess.core

data class Move(val from: Position, val to: Position, val specialMove: List<Move> = listOf())
