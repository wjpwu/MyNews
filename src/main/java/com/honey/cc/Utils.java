package com.honey.cc;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import scala.xml.Node;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

/**
 * Created with IntelliJ IDEA. User: Aaron Date: 13-6-26 Time: 3:06 To change
 * this template use File | Settings | File Templates.
 */
public class Utils {

	private static String Tag = "Utils";

	public File getTempFile(Context context, String url) {
		File file = null;
		try {
			String fileName = Uri.parse(url).getLastPathSegment();
			file = File.createTempFile(fileName, null, context.getCacheDir());
		} catch (IOException e) {
			// Error while creating file
		}
		return file;
	}

	public static String imgHtmlBuilder(int height, int width,
			String imgDocumentId, String imgHttpUrl) {
		StringBuilder localStringBuilder1 = new StringBuilder();
		StringBuilder localStringBuilder2 = localStringBuilder1
				.append(" <div class=\"cont_imgbox\"><div class=\"img_box\">");
		Object[] arrayOfObject1 = new Object[2];
		arrayOfObject1[0] = Integer.valueOf(width);
		arrayOfObject1[1] = Integer.valueOf(height);
		StringBuilder localStringBuilder3 = localStringBuilder2
				.append(String
						.format("<div class=\"imglist\" style=\"width:%dpx; height:%dpx;\">",
								arrayOfObject1))
				.append("<div class=\"loading\"> <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" ");
		Object[] arrayOfObject2 = new Object[2];
		arrayOfObject2[0] = Integer.valueOf(width);
		arrayOfObject2[1] = Integer.valueOf(height);
		StringBuilder localStringBuilder4 = localStringBuilder3
				.append(String
						.format("style=\"width:%dpx; background:#000000; text-align:center;\" > <tr> <td height=\"%dpx\" align=\"center\" >",
								arrayOfObject2)).append(
						"</td></tr></table></div>");
		Object[] arrayOfObject3 = new Object[2];
		arrayOfObject3[0] = Integer.valueOf(width);
		arrayOfObject3[1] = Integer.valueOf(height);
		StringBuilder localStringBuilder5 = localStringBuilder4
				.append(String
						.format("<p class=\"imgcont\" style=\"width:%dpx; height:%dpx;\"><a href=\"#\">",
								arrayOfObject3)).append("<img id=\"")
				.append(imgDocumentId).append("\" src=\"").append("")
				.append("\" ").append("alt=\"\" ").append("/></a></p>");
		Object[] arrayOfObject4 = new Object[2];
		arrayOfObject4[0] = Integer.valueOf(width);
		arrayOfObject4[1] = Integer.valueOf(height);
		StringBuilder localStringBuilder6 = localStringBuilder5
				.append(String
						.format("<div class=\"imgplay\" style=\"width:%dpx; height:%dpx;\" ",
								arrayOfObject4))
				.append("onclick=\"window.javaEventPic.clickAction(document.getElementById('")
				.append(imgDocumentId)
				.append("').id)\" >")
				.append("<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\"> <tr>");
		Object[] arrayOfObject5 = new Object[1];
		arrayOfObject5[0] = Integer.valueOf(height);
		StringBuilder localStringBuilder7 = localStringBuilder6
				.append(String.format("<td height=\"%dpx\" align=\"center\">",
						arrayOfObject5)).append("<img src=\"")
				.append(imgHttpUrl).append("\" ");
		Object[] arrayOfObject6 = new Object[2];
		// arrayOfObject6[0] = Integer.valueOf(com.sina.news.d.r.d(q));
		// arrayOfObject6[1] = Integer.valueOf(com.sina.news.d.r.c(q));
		localStringBuilder7
				.append(String.format("width=\"%dpx\" height=\"%dpx\" ",
						arrayOfObject4)).append("alt=\"\" ").append("/>")
				.append("</td></tr></table></div>");
		localStringBuilder1.append("</div></div></div>");
		return localStringBuilder1.toString();
	}

	public static String imgHtmlDisplay(int height, int width,
			scala.collection.immutable.List<Node> urls) {
		StringBuilder localStringBuilder1 = new StringBuilder();
		localStringBuilder1
				.append(" <div class=\"cont_imgbox\"><div class=\"img_box\">");
		for (int i = 0; i < urls.size(); i++) {
			localStringBuilder1
					.append("<div class=\"imgplay\" ")
					.append("onclick=\"window.javaEventPic.clickAction('")
					.append("url:" + urls.apply(i).text())
					.append("')\" >")
					.append("<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\"> <tr>");
			localStringBuilder1.append("<td align=\"center\">")
					.append("<img src=\"").append(urls.apply(i).text())
					.append("\" ");
			// Object[] arrayOfObject6 = new Object[2];
			// arrayOfObject6[0] = Integer.valueOf(com.sina.news.d.r.d(q));
			// arrayOfObject6[1] = Integer.valueOf(com.sina.news.d.r.c(q));
			localStringBuilder1.append("width=\"100%\" height=\"100%\" ")
					.append("alt=\"\" ").append("/>")
					.append("</td></tr></table></div>");
			localStringBuilder1.append("<br>");
		}
		localStringBuilder1.append("</div></div>");
		return localStringBuilder1.toString();
	}

	public static String imgHtmlDisplay(
			scala.collection.immutable.List<Node> urls) {
		StringBuilder localStringBuilder1 = new StringBuilder();
		localStringBuilder1
				.append(" <div class=\"cont_imgbox\"><div class=\"img_box\">");
		for (int i = 0; i < urls.size(); i++) {
			localStringBuilder1
					.append("<div class=\"imgplay\" ")
					.append("onclick=\"window.javaEventPic.clickAction('")
					.append("url:" + urls.apply(i).text())
					.append("')\" >");
			localStringBuilder1.append("<img src=\"").append(urls.apply(i).text())
					.append("\" ");
			// Object[] arrayOfObject6 = new Object[2];
			// arrayOfObject6[0] = Integer.valueOf(com.sina.news.d.r.d(q));
			// arrayOfObject6[1] = Integer.valueOf(com.sina.news.d.r.c(q));
			localStringBuilder1
//			.append("width=\"100%\" height=\"100%\" ")
					.append("alt=\"\" ").append("/>")
					.append("</div><br/>");
		}
		localStringBuilder1.append("</div></div>");
		return localStringBuilder1.toString();
	}
	
	public static String javaImgHtmlDisplay(
			java.util.ArrayList<java.util.HashMap<String,String>> urls) {
		StringBuilder localStringBuilder1 = new StringBuilder();
		localStringBuilder1
				.append(" <div class=\"cont_imgbox\"><div class=\"img_box\">");
		for (int i = 0; i < urls.size(); i++) {
			localStringBuilder1
					.append("<div class=\"imgplay\" ")
					.append("onclick=\"window.javaEventPic.clickAction('")
					.append("url:" + urls.get(i).get("url"))
					.append("')\" >");
			localStringBuilder1.append("<img src=\"").append(urls.get(i).get("url"))
					.append("\" ");
			 Object[] size = new Object[2];
			 size[0] = Integer.valueOf((String)urls.get(i).get("width"));
			 size[1] = Integer.valueOf((String)urls.get(i).get("height"));
			localStringBuilder1.append(String.format("width=\"%dpx\" height=\"%dpx\" ",
					size))
					.append("alt=\"\" ").append("/>")
					.append("</div><br/>");
		}
		localStringBuilder1.append("</div></div>");
		return localStringBuilder1.toString();
	}

	public static void saveToInternal(Context context, String fileName,
			byte[] content) {
		try {
			FileOutputStream outputStream = context.openFileOutput(fileName,
					Context.MODE_PRIVATE);
			outputStream.write(content);
			outputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
			Log.e(Tag, "save to internal failed.");
		}
	}

	public static void writeToFile(File file, byte[] content) {
		try {
			FileOutputStream outputStream = new FileOutputStream(file);
			outputStream.write(content);
			outputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
			Log.e(Tag, "save to internal failed.");
		}
	}

	public static FileInputStream getFromInternal(Context context,
			String fileName) {

		FileInputStream input = null;
		try {
			input = context.openFileInput(fileName);
		} catch (FileNotFoundException e) {
			// do nothing
		}
		return input;
	}

	public static int getDrawable(Context context, String fileName) {
		return context.getResources().getIdentifier(fileName, "drawable",
				context.getPackageName());
	}

	public static void shareMsg(Context context, String activityTitle,
			String msgTitle, String msgText, String imgPath) {
		Intent intent = new Intent(Intent.ACTION_SEND);
		if (imgPath == null || imgPath.equals("")) {
			intent.setType("text/plain");
		} else {
			File f = new File(imgPath);
			if (f != null && f.exists() && f.isFile()) {
				intent.setType("image/png");
				Uri u = Uri.fromFile(f);
				intent.putExtra(Intent.EXTRA_STREAM, u);
			}
		}
		intent.putExtra(Intent.EXTRA_SUBJECT, msgTitle);
		intent.putExtra(Intent.EXTRA_TEXT, msgText);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(Intent.createChooser(intent, activityTitle));
	}

	// TODO
	public static void shareMMS(Context context, String activityTitle,
			String msgTitle, String msgText, String imgPath) {
		Intent intent = new Intent(Intent.ACTION_SEND);
		intent.setClassName("com.android.mms",
				"com.android.mms.ui.ComposeMessageActivity");
		if (imgPath == null || imgPath.equals("")) {
			intent.setType("text/plain");
		} else {
			File f = new File(imgPath);
			if (f != null && f.exists() && f.isFile()) {
				intent.setType("image/png");
				Uri u = Uri.fromFile(f);
				intent.putExtra(Intent.EXTRA_STREAM, u);
			}
		}
		intent.putExtra(Intent.EXTRA_SUBJECT, msgTitle);
		intent.putExtra(Intent.EXTRA_TEXT, msgText);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(Intent.createChooser(intent, activityTitle));
	}

	/**
	 * inputstream to string utf-8
	 * 
	 * @param is
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String convertStreamToString(InputStream is)
			throws UnsupportedEncodingException {
		BufferedInputStream bis = new BufferedInputStream(is);
		InputStreamReader inputStreamReader = new InputStreamReader(bis,
				"utf-8");
		BufferedReader br = new BufferedReader(inputStreamReader);
		StringBuilder sb = new StringBuilder();
		String line = null;
		try {
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}

}
