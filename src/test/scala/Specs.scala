import com.honey.cc
import org.scalatest.matchers.ShouldMatchers
import org.scalatest.FunSpec
import scala.concurrent._
import scala.util.{ Failure, Success }
import ExecutionContext.Implicits.global
import java.net.URL
import scala.xml.Elem
import com.honey.cc.Utils
import java.io.BufferedReader
import java.io.InputStreamReader
import org.scalatest.FunSuite
import com.honey.cc.News
import scala.xml.Node
import java.io.File
import java.io.FileInputStream

class Specs extends FunSpec with ShouldMatchers with News {

  def handle[R](result: => R, handler: (R) => Unit) {
    future {
      result
    } onComplete {
      case Success(t) => handler(t)
      case Failure(m) => print(m.getMessage)
    }
  }

  def openUrl(page: Int): Elem = {
    val conn = new URL(com.honey.cc.News.sinaMobileNews + page).openConnection()
    conn.connect()
    val in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
    scala.xml.XML.load(in)
  }

  def open(url: String) = {
    val conn = new URL(url).openConnection()
    conn.connect()
    val in = new java.io.BufferedReader(new java.io.InputStreamReader(conn.getInputStream(), "UTF-8"));
    scala.xml.XML.load(in)
  }

//  describe("get news list") {
//    it("should do something") {
//      var itemsMap: scala.collection.mutable.Map[String, Node] = new scala.collection.mutable.ListMap[String, Node]()
//      val news = findNews(openUrl(1))
//      itemsMap ++= news.map { case n => (newsValue(n, "id"), n) }.toMap
//      info("hello")
//      info((openUrl(1) \\ "item").text)
//    }
//  }

  describe("get news content") {
    it("should do something") {
      val url = News.newsWeb.format("184-1224631-eladies-cms")
      info(url)
      val sinaHtml = new File("/Users/Aaron/Dev/sbt_ll/fxthomas/honeyt/assets/sina3.html")
      val sinaHtml2 = new File("/Users/Aaron/Dev/sbt_ll/fxthomas/honeyt/assets/sina4.html")
      sinaHtml2.createNewFile()
      val input = new FileInputStream(sinaHtml)
      info(input.toString())
      var webTmp = Utils.convertStreamToString(input)
      info(webTmp)
      val news = open(url)

      webTmp = webTmp.replace("[title]", (news \ "channel" \ "title").text)
      webTmp = webTmp.replace("[time]", (news \ "channel" \ "pubDate").text)
      webTmp = webTmp.replace("[from]", (news \ "channel" \ "generator").text)
      webTmp = webTmp.replace("[recommend]", "")
      val imgSeq = news \ "channel" \\ "@url"
              if (imgSeq.size > 0) {
          webTmp = webTmp.replace("[content]", (news \ "channel" \ "item" \ "description").text + Utils.imgHtmlDisplay(220, 292, imgSeq.toList))
        } else webTmp = webTmp.replace("[content]", (news \ "channel" \ "item" \ "description").text)
     Utils.writeToFile(sinaHtml2,webTmp.getBytes())
      info(webTmp)
    }
  }
  
    describe("get news content image") {
    it("should do something") {
      val url = News.newsWeb.format("184-1224631-eladies-cms")

      val news = open(url)
      val imgs = news \"channel"\\ "enclosure"
//      info(imgs.toString)
      var ss = ""
      for(img <- news) yield ss + img \"@url" + img \"@size" + "a111"
      info(ss)

    }
  }
}

//class Suit extends FunSuite {
//
//  def openUrl(page: Int): Elem = {
//    val conn = new URL(com.honey.cc.News.sinaMobileNews + page).openConnection()
//    conn.connect()
//    val in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
//    scala.xml.XML.load(in)
//  }
//
//  test("authorication code") {
//    info((openUrl(1) \\ "item").text)
//  }
//}
