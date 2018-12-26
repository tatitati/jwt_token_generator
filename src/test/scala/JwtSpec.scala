import net.liftweb.json._
import net.liftweb.json.JsonDSL._
import org.scalatest.FunSuite

class JwtSpec extends FunSuite {
  implicit val formats = DefaultFormats

  test("I can create a simple json") {
    val json = ("name" -> "whatever")
    println(prettyRender(json))
  }
}