package code

import cats.{Id, Applicative, Monad}
import cats.implicits._

trait Console[F[_]] {
  def read(): F[String]
  def write(str: String): F[Unit]
}

class ScalaConsole[F[_]: Applicative] extends Console[F] {
  def read(): F[String] =
    scala.Console.in.readLine.pure[F]

  def write(str: String): F[Unit] =
    scala.Console.println(str).pure[F]
}

trait PasswordStore[F[_]] {
  def check(username: String, password: String): F[Boolean]
}

class InMemoryPasswordStore[F[_]: Applicative](var passwords: Map[String, String]) extends PasswordStore[F] {
  def check(username: String, password: String): F[Boolean] =
    passwords.get(username).fold(false)(_ == password).pure[F]
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
  import scala.concurrent._
  import scala.concurrent.duration._
  import scala.concurrent.ExecutionContext.Implicits.global

  val console = new ScalaConsole[Future]()

  val store = new InMemoryPasswordStore[Future](Map(
    "garfield"    -> "iheartlasagne",
    "grumpycat"   -> "nope",
    "snagglepuss" -> "murgatroyd",
  ))

  val program = new Program[Future](console, store)

  println(Await.result(program.run(), Duration.Inf))
}
