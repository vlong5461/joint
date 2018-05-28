package com.he.joint.common;

import java.util.List;
import org.xml.sax.XMLReader;


import android.content.Context;
import android.text.Editable;
import android.text.Html.TagHandler;
import android.text.Spanned;
import android.text.style.ClickableSpan;
import android.view.View;
import android.view.View.OnClickListener;


public class MyHtmlTagHandler implements TagHandler {

	private List<String> imageList;
	private Context mContext;
	private int number = 0;

	public MyHtmlTagHandler(List<String> imageList, Context mContext) {
		this.imageList = imageList;
		this.mContext = mContext;
	}

	public MyHtmlTagHandler() {

	}

	@Override
	public void handleTag(boolean opening, String tag, Editable output, XMLReader arg3) {

		// if (opening) {
		// System.out.print(">>>>" + tag);
		// }

		if (tag.toLowerCase().equals("img")) {

			if (!opening) {
				int index = output.length();
				output.setSpan(new MSpan(number), index - 1, index, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
				number++;
			}
		}
	}

	private class MSpan extends ClickableSpan implements OnClickListener {
		private int index;

		public MSpan(int index) {
			this.index = index;
		}

		@Override
		public void onClick(View widget) {
//			 MyLog.i(">>>> " + index + " total: " + imageList.size());
//			 Bundle b = new Bundle();
//			 b.putSerializable("data", (Serializable)imageList);
//			 b.putString("title", "");
//			 b.putInt("index", index);
//			 UIHelper.startActivity(mContext, ViewBigImageActivity.class, b);
		}
	}

}
