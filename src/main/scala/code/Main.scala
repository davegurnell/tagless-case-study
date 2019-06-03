package code

import cats.{Id, Monad}
import cats.implicits._

trait Console[F[_]] {
  def read(): F[String]
  def write(str: String): F[Unit]
}

class ScalaConsole extends Console[Id] {
  def read(): String =
    scala.Console.in.readLine

  def write(str: String): Unit =
    scala.Console.println(str)
}

trait PasswordStore[F[_]] {
  def check(username: String, password: String): F[Boolean]
}

class InMemoryPasswordStore(var passwords: Map[String, String]) extends PasswordStore[Id] {
  def check(username: String, password: String): Boolean =
    passwords.get(username).fold(false)(_ == password)
}

class Program[F[_]: Monad](console: Console[F], store: PasswordStore[F]) {
  def run(): F[Option[String]] =
    for {
      _        <- console.write("Enter your username")
      username <- console.read()
      _        <- console.write("Enter your password")
      password <- console.read()
      loginOk  <- store.check(username, password)
      result   <- if(loginOk) {
                    console.write("Hello " + username)
                  } else {
                    console.write("Username or password incorrect")
                  }
    } yield if(loginOk) Some(username) else None
}

object Main extends App {
  val console = new ScalaConsole()

  val store = new InMemoryPasswordStore(Map(
    "garfield"    -> "iheartlasagne",
    "grumpycat"   -> "nope",
    "snagglepuss" -> "murgatroyd",
  ))

  val program = new Program[Id](console, store)

  println(program.run())
}
