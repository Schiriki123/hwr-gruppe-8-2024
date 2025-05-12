package hwr.oop.group8.chess.cli

interface CliCommand {
  fun matches(args: List<String>): Boolean
  fun handle(args: List<String>)
}
