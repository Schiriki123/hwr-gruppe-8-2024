package hwr.oop.group8.chess.cli

class HelpCommand : CliCommand {
  override fun matches(args: List<String>): Boolean {
    return args.isEmpty() || args.contains("--help") || args.contains("-h")
  }

  override fun handle(args: List<String>) {
    println("Usage: chess <command> [options]")
    println()
    println("Available commands:")
    println("  new game <id> - Create a new game with the given ID.")
    println("  show game <id> - Print the current state of the game with the given ID.")
    println("  make move <id> <start> <end> - Make a move in the game with the given ID.")
    println("  list games - List all saved games.")
    println()
    println("Options:")
    println("  -h, --help - Show this help message.")
  }
}
