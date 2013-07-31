package com.honey.cc

import org.scaloid.common.SActivity
import com.actionbarsherlock.app.SherlockActivity
import android.preference.PreferenceManager
import scala.concurrent._
import scala.util.{ Failure, Success }
import ExecutionContext.Implicits.global
import org.scaloid.common._
import scala.xml.Node
import scala.xml.Elem
import java.text.SimpleDateFormat
import java.util.Locale

object News {

  val mmdd = new SimpleDateFormat("MM-dd");
  val yymmddhhmm = new SimpleDateFormat("yyyy-MM-dd HH:mm");
  val eee = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z", new Locale("en"));
  val md = new SimpleDateFormat("MM-dd HH:mm");
  val e = new SimpleDateFormat("HH:mm");
  val f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
  val g = new SimpleDateFormat("yyyy-MM-dd");
  val h = new SimpleDateFormat("yyyy-MM-dd-HH:mm");
  val m = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
  val n = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z", Locale.ENGLISH)

  val newsWeb ="http://data.3g.sina.com.cn/api/t/art/index.php?graphic_mix=1&s_tag=1&id=%s&wm=b207&version=4"
  val sinaMobileNews = "http://data.3g.sina.com.cn/api/combine.php?version=5&wm=b207&cid=45&group=date&page="
  val newsUrlList = List(
    "http://data.3g.sina.com.cn/api/combine.php?version=5&wm=b207&cid=43&group=date&page=",
    "http://data.3g.sina.com.cn/api/combine.php?version=5&wm=b207&cid=44&group=date&page=",
    "http://data.3g.sina.com.cn/api/combine.php?version=5&wm=b207&cid=45&group=date&page=",
    "http://data.3g.sina.com.cn/api/combine.php?version=5&wm=b207&cid=46&group=date&page=",
    "http://data.3g.sina.com.cn/api/combine.php?version=5&wm=b207&cid=47&group=date&page=")
  val newsList = List("房产", "体育", "手机", "女性", "头条")
}

trait MyActivity extends SherlockActivity with SActivity with TypedActivity with News {
  implicit val tag = LoggerTag("com.honey.cc")

  def handle[R](result: => R, handler: (R) => Unit, handlerFail: Throwable => Unit) {
    future {
      debug("call future")
      result
    } onComplete {
      case Success(t) => handler(t)
      case Failure(m) => {
        debug(m.getMessage)
        handlerFail(m)
      }
    }
  }
  @inline def sharedPref = PreferenceManager.getDefaultSharedPreferences(this)

  @inline def put(key: String, value: Any) {
    defaultSharedPreferences.edit().putString(key, value.toString)
    defaultSharedPreferences.edit().commit()
  }

  @inline def get(key: String) = defaultSharedPreferences.getString(key, "")
  @inline def contains(key: String): Boolean = defaultSharedPreferences.contains(key)

}

trait News {
  def nodeChild(node: Node) = (node \\ "item").toList
  //  @inline def isNoNews(news: Elem) = (news \ "status").text == "0"
  def findNews(news: Elem) = (news \\ "item").toList
  def newsValue(node: Node, key: String): String = (node \ key).text
  def newsField(node: Node, key: String): String = (node \ ("@" + key)).text
  def imageLink(node: Node): String = (node \ "enclosure" \ "@url").text
}