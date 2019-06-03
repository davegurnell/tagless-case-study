package code

object Program {
  def run(passwords: Map[String, String]): Option[String] = {
    println("Enter your username")
    val username = readLine
    println("Enter your password")
    val password = readLine
    val loginOk = passwords.get(username).fold(false)(_ == password)
    if(loginOk) {
      println("Hello " + username)
      Some(username)
    } else {
      println("Username or password incorrect")
      None
    }
  }
}

object Main extends App {
  val passwords: Map[String, String] = Map(
    "garfield"    -> "iheartlasagne",
    "grumpycat"   -> "nope",
    "snagglepuss" -> "murgatroyd",
  )

  println(Program.run(passwords))
}
