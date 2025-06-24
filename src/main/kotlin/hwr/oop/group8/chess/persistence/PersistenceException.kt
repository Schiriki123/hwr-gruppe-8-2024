package hwr.oop.group8.chess.persistence

open class PersistenceException(message: String) : Exception(message)
class CouldNotDeleteGameException(message: String) :
  PersistenceException(message)

class CouldNotLoadGameException(message: String) : PersistenceException(message)
class CouldNotSaveGameException(message: String) : PersistenceException(message)
