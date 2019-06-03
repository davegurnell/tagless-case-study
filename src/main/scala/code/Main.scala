package code

trait Console {
  def read(): String
  def write(str: String): Unit
}

class ScalaConsole extends Console {
  def read(): String =
    scala.Console.in.readLine

  def write(str: String): Unit =
    scala.Console.println(str)
}

trait PasswordStore {
  def check(username: String, password: String): Boolean
}

class InMemoryPasswordStore(var passwords: Map[String, String]) extends PasswordStore {
  def check(username: String, password: String): Boolean =
    passwords.get(username).fold(false)(_ == password)
}

class Program(console: Console, store: PasswordStore) {
  def run(): Option[String] = {
    console.write("Enter your username")
    val username = console.read()
    console.write("Enter your password")
    val password = console.read()
    val loginOk = store.check(username, password)
    if(loginOk) {
      console.write("Hello " + username)
      Some(username)
    } else {
      console.write("Username or password incorrect")
      None
    }
  }
}

object Main extends App {
  val console = new ScalaConsole()

  val store = new InMemoryPasswordStore(Map(
    "garfield"    -> "iheartlasagne",
    "grumpycat"   -> "nope",
    "snagglepuss" -> "murgatroyd",
  ))

  val program = new Program(console, store)

  println(program.run())
}
