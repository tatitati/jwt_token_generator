import net.liftweb.json._
import net.liftweb.json.JsonDSL._
import java.util.Base64
import java.nio.charset.StandardCharsets
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec
import java.net.URLEncoder


object Jwt extends App {

  def header(): String = {
      compactRender(
        ("alg" -> "HS256") ~ ("typ" -> "JWT")
      )
  }

  def payload(): String = {
    compactRender(
      ("sub" -> "1234567890") ~
      ("name" -> "John Doe") ~
      ("admin" -> true)
    )
  }

  def signature(): String = {
    hmacSha256(
      encode64(header()) + "." + encode64(payload()),
      "mykey"
    )
  }

  def encode64(value: String) = {
    Base64.getUrlEncoder.encodeToString(value.getBytes(StandardCharsets.UTF_8))
  }

  def hmacSha256(value: String, secret: String): String = {
    val sha256_HMAC: Mac = Mac.getInstance("HmacSHA256")

    sha256_HMAC.init(
      new SecretKeySpec(
        secret.getBytes("UTF-8"),
        "HmacSHA256"
      )
    )

    val mac = sha256_HMAC.doFinal(value.getBytes())
    Base64.getUrlEncoder.encodeToString(mac)
  }

  val token = encode64(header()) +
    "." +
    encode64(payload()) +
    "." +
    signature()


  println("\n\n")
  println(token)
  println("\n\n")
}