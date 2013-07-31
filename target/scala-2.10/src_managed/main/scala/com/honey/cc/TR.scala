package com.honey.cc
import _root_.android.app.{Activity, Dialog}
import _root_.android.view.View

case class TypedResource[T](id: Int)
case class TypedLayout(id: Int)

object TR {
  val pull_to_refresh_listview = TypedResource[eu.erikw.PullToRefreshListView](R.id.pull_to_refresh_listview)
  val iv_newslist_item_discuss_text = TypedResource[android.widget.TextView](R.id.iv_newslist_item_discuss_text)
  val newlist_item = TypedResource[android.widget.RelativeLayout](R.id.newlist_item)
  val iv_newslist_item_discuss_num = TypedResource[android.widget.TextView](R.id.iv_newslist_item_discuss_num)
  val iv_newslist_item_video_icon = TypedResource[android.widget.ImageView](R.id.iv_newslist_item_video_icon)
  val iv_newslist_item_srcText = TypedResource[android.widget.TextView](R.id.iv_newslist_item_srcText)
  val iv_newslist_item_subject_icon = TypedResource[android.widget.ImageView](R.id.iv_newslist_item_subject_icon)
  val textView1 = TypedResource[android.widget.TextView](R.id.textView1)
  val webLoadingLayout = TypedResource[android.widget.LinearLayout](R.id.webLoadingLayout)
  val iv_newslist_item_desText = TypedResource[android.widget.TextView](R.id.iv_newslist_item_desText)
  val loadingProcessBar = TypedResource[android.widget.ProgressBar](R.id.loadingProcessBar)
  val iv_newslist_item_titleText = TypedResource[android.widget.TextView](R.id.iv_newslist_item_titleText)
  val wWebview = TypedResource[android.webkit.WebView](R.id.wWebview)
  val iv_newslist_item_bmp = TypedResource[android.widget.ImageView](R.id.iv_newslist_item_bmp)
  val message = TypedResource[android.widget.TextView](R.id.message)
  val textview = TypedResource[android.widget.TextView](R.id.textview)
  val ll_newslist_item_bmp = TypedResource[android.widget.LinearLayout](R.id.ll_newslist_item_bmp)
 object layout {
  val list_item = TypedLayout(R.layout.list_item)
 val main = TypedLayout(R.layout.main)
 val text = TypedLayout(R.layout.text)
 val vw_newslist_item_for_toutiao = TypedLayout(R.layout.vw_newslist_item_for_toutiao)
 val web = TypedLayout(R.layout.web)
 }
}
trait TypedViewHolder {
  def findViewById( id: Int ): View
  def findView[T](tr: TypedResource[T]) = findViewById(tr.id).asInstanceOf[T]
}
trait TypedView extends View with TypedViewHolder
trait TypedActivityHolder extends TypedViewHolder
trait TypedActivity extends Activity with TypedActivityHolder
trait TypedDialog extends Dialog with TypedViewHolder
object TypedResource {
  implicit def layout2int(l: TypedLayout) = l.id
  implicit def view2typed(v: View) = new TypedViewHolder { 
    def findViewById( id: Int ) = v.findViewById( id )
  }
  implicit def activity2typed(a: Activity) = new TypedViewHolder { 
    def findViewById( id: Int ) = a.findViewById( id )
  }
  implicit def dialog2typed(d: Dialog) = new TypedViewHolder { 
    def findViewById( id: Int ) = d.findViewById( id )
  }
}
