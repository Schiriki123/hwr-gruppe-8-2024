package hwr.oop.group8.chess.cli

class HelpCommand : CliCommand {
  override fun matches(args: List<String>): Boolean =
    args.isEmpty() || args.contains("help")

  override fun handle(args: List<String>) {
    println(
      """
      Usage: chess <command> [options]
      
      Available commands:
        new game <id> - Create a new game with the given ID.
        show game <id> - Print the current state of the game with the given ID.
        make move <id> <start> <end> - Make a move in the game with the given ID.
        list games - List all saved games.
        delete game <id> - Delete the game with the given ID.
        help - Show this help message.
      """.trimIndent(),
    )
  }
}
