package code

import akka.actor.ActorSystem
import cats.implicits._
import redis.RedisClient
import scala.concurrent._
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global

/** An extremely simple async Redis client
  * that operates on the default thread pool.
  */
object Redis {
  private implicit val system = akka.actor.ActorSystem()

  private val client = RedisClient()

  private val keyPrefix = "taglessdemo:"

  def prepopulate(data: Map[String, String]): Unit = {
    val result = data.toList
      .traverse { case (k, v) => client.set(keyPrefix + k, v) }
      .map(_ => ())

    Await.result(result, Duration.Inf)
  }

  def get(key: String): Future[Option[String]] =
    client.get[String](keyPrefix + key)

  def close(): Unit =
    system.terminate()
}
