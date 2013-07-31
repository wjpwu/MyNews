package com.honey.cc

import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL

import scala.collection.mutable._
import scala.xml.Elem
import scala.xml.Node

import org.scaloid.common._

import com.actionbarsherlock.app.SherlockActivity
import com.actionbarsherlock.view.Menu
import com.actionbarsherlock.view.MenuItem

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.TextView
import eu.erikw.PullToRefreshListView
import eu.erikw.PullToRefreshListView._

case class NewsViewHolder(title: TextView,source: TextView,image: ImageView)
{
  var newsId: String =_
  var newsLink: String =_
  var newsType: String =_
}
case class NewsStatus(nType: String, nPage: Int, nList: List[Node])


  
    
class Index extends MyActivity {

  var listView: PullToRefreshListView = _
  var adapter: MyAdapter = _
  var pageIndex: Int = 1
  var newsUrl: String= _

  override def onCreate(bundle: Bundle) {
    setTheme(R.style.Sherlock___Theme_Light)
    super.onCreate(bundle)
//    statusMap = new scala.collection.mutable.ListMap[String, NewsStatus]
    setContentView(R.layout.text)
    //    findView(TR.text).setText("Hello")
    listView = findView(TR.pull_to_refresh_listview)
    if(!contains("nnurl")){
      put("nnurl","http://data.3g.sina.com.cn/api/combine.php?version=5&wm=b207&cid=45&group=date&page=")
    }
    newsUrl = "http://data.3g.sina.com.cn/api/combine.php?version=5&wm=b207&cid=45&group=date&page="
    listView.setOnRefreshListener(new OnRefreshListener() {
      override def onRefresh() {
          adapter.refresh
      }
    })

    adapter = new MyAdapter();
    listView.setAdapter(adapter)

    // Request the adapter to load the data
    adapter.loadData(pageIndex)
    
    listView.setOnItemClickListener(NewsItemListener(itemsMap))
  }

  case class NewsItemListener(map: Map[String,Node]) extends android.widget.AdapterView.OnItemClickListener{
    def onItemClick(p1: AdapterView[_], p2: View, p3: Int, p4: Long) {
      val e = map.toList.apply(p3)._2
      val id = (e \\ "id").text
      val int = new Intent(Index.this, classOf[Web]) 
      int.putExtra("ids", id)
      startActivity(int)
    }
  }
  
  override def onCreateOptionsMenu(menu: Menu) = {
    //Used to put dark icons on light action bar
//    var isLight = R.style.Theme_Sherlock_Light;
	 
    val sub = menu.addSubMenu("News")
    for(url <- News.newsList) yield sub.add(0,10+News.newsList.indexOf(url) , 0, url)
    sub.getItem().setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS | MenuItem.SHOW_AS_ACTION_WITH_TEXT)
    menu.add(0,2,1,"More")
      .setIcon(R.drawable.ic_refresh_inverse)
      .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_WITH_TEXT)     
    true
  }

  override def onOptionsItemSelected(item: MenuItem) = {
    //This uses the imported MenuItem from ActionBarSherlock
    toast("Got click: " + item.getItemId())
    if(item.getItemId() - 10 > 0 && News.newsUrlList.size > item.getItemId() - 10)
    {
      newsUrl = News.newsUrlList.apply(item.getItemId - 10)
      put("nnurl",newsUrl)
      pageIndex = 1
      adapter.clean
      adapter.notifyDataSetChanged
      adapter.loadData(pageIndex)
    }else if(item.getItemId() != 0){
      adapter.loadData(pageIndex);
      toast("Got click More: " + pageIndex)
    }    
    true;
  }

  def openNewsUrl(page: Int): Elem = {
    val conn = new URL(newsUrl + page).openConnection()
    conn.connect()
    val in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
    debug(in.toString())
    scala.xml.XML.load(in)
  }
    
  def openUrl(page: Int,url: String): Elem = {
    val conn = new URL(url + page).openConnection()
    conn.connect()
    val in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
    debug(in.toString())
    scala.xml.XML.load(in)
  }

  val a = {
    <item>
      <id>344-140536-mobile-cms</id>
      <title>aaa</title>
      <source>ccc</source>
      <link>http://mobile.sina.cn/?sa=t344d140536v252</link>
      <guid>http://mobile.sina.cn/?sa=t344d140536v252</guid>
      <pubDate>Sun, 14 Jul 2013 23:38:58 GMT</pubDate>
      <comments>2-1-8539520_0_kj</comments>
      <category>news</category>
      <enclosure url="http://r3.sinaimg.cn/120914/2013/0715/14/e/37442405/auto.jpg" length="10000" type="image/jpeg" alt="û������ֻ�ֻ�������Ļ��ӣ��Ӽ����绰�ͱ��һ��ש��"/>
    </item>
  }

   private var itemsMap: scala.collection.mutable.Map[String,Node] = new scala.collection.mutable.ListMap[String,Node]()

  
  class MyAdapter extends android.widget.BaseAdapter {
    private var items: scala.collection.mutable.Seq[Node] = new scala.collection.mutable.LinkedList[Node]()
    def clean(){
//      items = new scala.collection.mutable.LinkedList[Node]()
      itemsMap.clear
    }
    
    def newsItems()={
      itemsMap.toList
    }
    
    def refresh{
        handle(openUrl(1,newsUrl),
        (news: Elem) => {
          itemsMap ++= findNews(news).map{case n =>(newsValue(n,"id"),n)}.toMap
//          items ++= itemsMap.map{case (a,b) => b}
//          items ++=findNews(news)
//          items.map{case n =>(newsValue(n,"id"),n)}.toMap
          runOnUiThread({
            debug("Running in any context")
            listView.onRefreshComplete()
            notifyDataSetChanged()
            pageIndex = 2
          })
        },
        (e: Throwable) => {
          runOnUiThread({
            debug("Running in any context")
            toast("reflash fail: " + e.getMessage())
            listView.onRefreshComplete()
          })
        })
    }
    
    def loadData(page: Int) {
      handle(openUrl(page,newsUrl),
        (news: Elem) => {
//          items ++= findNews(news)
         itemsMap ++= findNews(news).map{case n =>(newsValue(n,"id"),n)}.toMap
          runOnUiThread({
            debug("Running in any context")
            listView.onRefreshComplete()
            notifyDataSetChanged()
            pageIndex = pageIndex + 1
          })
        },
        (e: Throwable) => {
          runOnUiThread({
            debug("Running in any context")
            toast("reflash fail: " + e.getMessage())
            listView.onRefreshComplete()
          })
        })
    }

    override def getCount() = itemsMap.size
    override def getItem(position: Int) = itemsMap.toList.apply(position)._2
    override def getItemId(position: Int) = position

    override def getView(position: Int, convertView: View, parent: ViewGroup) = {
      var rowView = convertView;
      val news = getItem(position);
      val inflater = Index.this.getLayoutInflater();
      if (convertView == null) {
        rowView = inflater.inflate(R.layout.vw_newslist_item_for_toutiao, null);
        val img = rowView.findViewById(R.id.iv_newslist_item_bmp).asInstanceOf[ImageView]
        val titlev = rowView.findViewById(R.id.iv_newslist_item_titleText).asInstanceOf[TextView]
        val descv = rowView.findViewById(R.id.iv_newslist_item_desText).asInstanceOf[TextView]
        val viewHolder = NewsViewHolder(
          title = titlev,
          source = descv,
          image = img)
        rowView.setTag(viewHolder)
      }
      val holder = rowView.getTag().asInstanceOf[NewsViewHolder]
      // set holder
      holder.newsId = newsValue(news, "id")
      holder.newsLink = newsValue(news, "link")
      holder.image.setTag(imageLink(news))
      
      holder.title.setText(newsValue(news, "title"))
      holder.source.setText(newsValue(news, "source"))
      holder.image.setImageResource(0)
      ImageLoaderFactory.getImageLoader(Index.this).displayImage(holder.image.getTag().asInstanceOf[String], Index.this, holder.image)
      rowView
    }
  }
}
