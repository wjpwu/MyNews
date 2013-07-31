package com.honey.cc

import org.scaloid.common.SVerticalLayout
import android.os.Bundle
import org.scaloid.common.SWebView
import android.webkit.WebView
import android.app.Activity
import scala.xml.Node
import android.webkit.WebViewClient
import android.graphics.Bitmap
import android.webkit.SslErrorHandler
import android.net.http.SslError
import android.webkit.HttpAuthHandler
import android.view.View
import java.io.BufferedReader
import java.net.URL
import scala.xml.Elem
import java.io.InputStreamReader
import org.scaloid.common._
import com.actionbarsherlock.view.Menu
import com.actionbarsherlock.view.MenuItem

class Web extends MyActivity {

  class WebEnv(web: Web) {
    def clickAction(str: String) {
      toast("WebEnv" + str)
    }
  }

  override def onCreateOptionsMenu(menu: Menu) = {
    //Used to put dark icons on light action bar
    //    var isLight = R.style.Theme_Sherlock_Light;
    menu.add(0, 2, 1, "Home")
      .setIcon(R.drawable.icon_house_press)
      .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_WITH_TEXT)
    true
  }

  override def onOptionsItemSelected(item: MenuItem) = {
    //This uses the imported MenuItem from ActionBarSherlock
    //    startActivity[Index]
    onBackPressed()
    true;
  }
  override def onCreate(savedInstanceState: Bundle) {
    setTheme(R.style.Sherlock___Theme_Light)
    super.onCreate(savedInstanceState)
    setContentView(R.layout.web)
    val web = findView(TR.wWebview)
    val load = findView(TR.webLoadingLayout)
    web.requestFocus()
    web.setScrollBarStyle(0);
    val localWebSettings = web.getSettings();
    localWebSettings.setJavaScriptEnabled(true);
    localWebSettings.setBuiltInZoomControls(false);
    localWebSettings.setLightTouchEnabled(false);
    localWebSettings.setSupportZoom(false);
    localWebSettings.setAllowFileAccess(true);
    localWebSettings.setCacheMode(1)
    val displayPic = new WebEnv(this)
    web.addJavascriptInterface(displayPic, "javaEventPic");
    web.setSaveEnabled(false)

    val webTmpInput = this.getAssets().open("sina3.html")
    //    val webTmp = new java.io.BufferedReader(new java.io.InputStreamReader(webTmpInput, "UTF-8"))
    var webTmp = Utils.convertStreamToString(webTmpInput)
    webTmp.replaceAll("<br/>", "")
    webTmp.replaceAll(" ", "")
    toast(getIntent.getStringExtra("ids"))
    val url =
      //      "http://data.3g.sina.com.cn/api/t/art/index.php?graphic_mix=1&s_tag=1&id=184-1224477-eladies-cms&wm=b207&version=4"
      News.newsWeb.format(getIntent.getStringExtra("ids"))
    web.setVisibility(View.GONE);
    load.setVisibility(View.VISIBLE);
//<!--{IMG_1}--><br/><!--{IMG_2}--><br/><!--{IMG_3}--><br/>
    handle({
      val conn = new URL(url).openConnection()
      conn.connect()
      val in = new java.io.BufferedReader(new java.io.InputStreamReader(conn.getInputStream(), "UTF-8"));
      scala.xml.XML.load(in)
    },
      (news: Elem) => {
        val imgSeq = news \ "channel" \\ "@url"
        val imgs = news \"channel"\\ "enclosure"
        val iList = new java.util.ArrayList[java.util.HashMap[String,String]]
        for(ii <- imgs) yield
        {
         val imap = new java.util.HashMap[String,String]
         imap.put("url", (ii \"@url").text)
         imap.put("alt", (ii \"@alt").text)
         val size = (ii \"@size").text
         imap.put("width", size.substring(0, size.indexOf("x")))
         imap.put("height", size.substring(size.indexOf("x")+1))
         iList.add(imap)
        }
        webTmp = webTmp.replace("[title]", (news \ "channel" \ "title").text.replaceAll("\n", "").replaceAll(" +", ""))
        webTmp = webTmp.replace("[time]", (news \ "channel" \ "pubDate").text)
        webTmp = webTmp.replace("[from]", (news \ "channel" \ "generator").text)
        if (imgSeq.size > 0) {
          webTmp = webTmp.replace("[content]", (news \ "channel" \ "item" \ "description").text + Utils.imgHtmlDisplay(imgSeq.toList))
        } else webTmp = webTmp.replace("[content]", (news \ "channel" \ "item" \ "description").text)
        webTmp = webTmp.replace("[recommend]", "")
        runOnUiThread({
          load.setVisibility(View.GONE)
          web.setVisibility(View.VISIBLE)
          web.requestFocus()
          web.loadDataWithBaseURL(null, webTmp.replace("href=\"#\"", "href=\"javascript:void(0)\""), "text/html", "UTF-8", null)
        })
      },
      (e: Throwable) => {
        runOnUiThread({
          load.setVisibility(View.GONE)
          web.setVisibility(View.VISIBLE)
          web.requestFocus()
          toast("fail to get news")
        })
      })
  }
  //      val localStringBuilder6 = localStringBuilder5.
  //  append(String.format("<div class=\"imgplay\" style=\"width:%dpx; height:%dpx;\" ", arrayOfObject4)).
  //  append("onclick=\"window.javaEventPic.clickAction2(document.getElementById('").
  //  append(paramString1).append("').id)\" >").
  //  append("<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\"> <tr>");

  //  val imguuu = "<img src="%s"  width="310" height="220">"
  //  def imgBuild() {
  //    var imgStr = new StringBuilder
  //    val imgFor = Array[Int](292,220)
  //    imgStr.append(String.format("<div class=\"imgplay\" style=\"width:%dpx; height:%dpx;\" ", imgFor)).
  //    append("onclick=\"window.javaEventPic.clickAction2(document.getElementById('").
  //    append("").append("').id)\" >")
  //    <img src="%s"  width="292" height="220">
  //  }

  //   private void g()
  //  {
  //    LinearLayout localLinearLayout = (LinearLayout)findViewById(2131361844);
  //    p = new WebView(this);
  //    p.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
  //    p.setScrollBarStyle(0);
  //    localLinearLayout.addView(p, 0);
  //    WebSettings localWebSettings = p.getSettings();
  //    localWebSettings.setLightTouchEnabled(false);
  //    localWebSettings.setSupportZoom(false);
  //    localWebSettings.setJavaScriptEnabled(true);
  //    localWebSettings.setCacheMode(1);
  //    p.addJavascriptInterface(displayObj, "javaEventObj");
  //    p.addJavascriptInterface(displayPic, "javaEventPic");
  //    p.addJavascriptInterface(displayRecommend, "javaEventRecommend");
  //    p.setWebViewClient(new ch(this));
  //    int i1 = Build.VERSION.SDK_INT;
  //    e.f.a("mWebView android.VERSION：" + i1);
  //    s = new y(this, p, d);
  //    if (i1 < 9)
  //      p.setOnCreateContextMenuListener(new cm(this));
  //    p.setOnKeyListener(new cn(this));
  //    q = p.getScale();

  //  def webView(url: String) = {
  //    val view = getLayoutInflater.inflate(R.layout.web, null)
  //    val web = view.findViewById(R.id.wWebview).asInstanceOf[WebView]
  //    val load = view.findViewById(R.id.webLoadingLayout)
  //
  //    web.requestFocus()
  //    web.setScrollBarStyle(0);
  //    val localWebSettings = web.getSettings();
  //    localWebSettings.setJavaScriptEnabled(true);
  //    localWebSettings.setBuiltInZoomControls(true);
  //    localWebSettings.setSupportZoom(true);
  //    localWebSettings.setAllowFileAccess(true);
  //    localWebSettings.setCacheMode(1)
  //    web.setSaveEnabled(false)
  //    web.setWebViewClient(new WebViewClient() {
  //      override def onPageStarted(webview1: WebView, url: String, favicon: Bitmap) {
  //        if (load != null) {
  //          webview1.setVisibility(View.GONE);
  //          load.setVisibility(View.VISIBLE);
  //        } else webview1.setVisibility(View.GONE);
  //        super.onPageStarted(webview1, url, favicon)
  //      }
  //
  //      override def onPageFinished(webview1: WebView, url: String) {
  //        if (load != null) {
  //          load.setVisibility(View.GONE)
  //          webview1.setVisibility(View.VISIBLE)
  //          webview1.requestFocus()
  //        }
  //        super.onPageFinished(webview1, url)
  //      }
  //
  //      override def onReceivedError(webview1: WebView, errorCode: Int, description: String, failingUrl: String) {
  //        super.onReceivedError(webview1, errorCode, description, failingUrl)
  //      }
  //
  //      override def onReceivedSslError(view: WebView, handler: SslErrorHandler, error: SslError) {
  //        super.onReceivedSslError(view, handler, error)
  //      }
  //
  //      override def onReceivedHttpAuthRequest(view: WebView, handler: HttpAuthHandler, host: String, realm: String) {
  //        super.onReceivedHttpAuthRequest(view, handler, host, realm)
  //      }
  //    })
  //    web.loadUrl(url)
  //    view
  //  }

}