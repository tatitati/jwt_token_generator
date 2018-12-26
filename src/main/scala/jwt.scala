import net.liftweb.json._
import net.liftweb.json.JsonDSL._
import java.util.Base64
import java.nio.charset.StandardCharsets
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec
import java.net.URLEncoder


object Jwt extends App {

  def header(): Array[Byte] = {
      compactRender(
        ("alg" -> "HS256") ~ ("typ" -> "JWT")
      ).getBytes
  }

  def payload(): Array[Byte] = {
    compactRender(
      ("sub" -> "1234567890") ~
      ("name" -> "John Doe") ~
      ("admin" -> true)
    ).getBytes
  }

  def signature(): Array[Byte] = {
    hmacSha256(
      encode64Url(header()) + "." + encode64Url(payload()),
      "mykey"
    )
  }

  def encode64Url(value: Array[Byte]) = {
    Base64.getUrlEncoder.encodeToString(value)
  }

  def hmacSha256(value: String, secret: String): Array[Byte] = {
    val sha256_HMAC: Mac = Mac.getInstance("HmacSHA256")

    sha256_HMAC.init(
      new SecretKeySpec(
        secret.getBytes("UTF-8"),
        "HmacSHA256"
      )
    )

    sha256_HMAC.doFinal(value.getBytes())
  }

  val token = encode64Url(header()) +
    "." +
    encode64Url(payload()) +
    "." +
    encode64Url(signature())


  println("\n\n")
  println(token)
  println("\n\n")
}