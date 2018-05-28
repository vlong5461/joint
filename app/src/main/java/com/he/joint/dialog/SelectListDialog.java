package com.he.joint.dialog;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.he.joint.R;
import com.he.joint.adapter.DialogSelectListAdapter;

import java.util.List;

public class SelectListDialog extends BaseDialog{

	private static int default_width = -2;
	private static int default_height = -2;

	private Context mContext;
	private TextView title,tvBottom;
	private ListView listView;
	private DialogSelectListAdapter adapter;
	public ListOKListener listener;
	
	public SelectListDialog(Context context) {
		super(context, default_width, default_height, R.layout.dialog_list_view, R.style.DialogStyle2,
				Gravity.CENTER);
		this.setCancelable(true);
		this.mContext=context;
		initView();
	}
	
	private void initView() {
		title= (TextView) findViewById(R.id.title);
		listView = (ListView) findViewById(R.id.listView);
		tvBottom = (TextView) findViewById(R.id.tvBottom);
	
	}

	@Override
	public void onDetachedFromWindow() {
		super.onDetachedFromWindow();
	}
	
	public void setTitle(String text){
		title.setText(text);
	}
	public void setBotomText(String text){
		tvBottom.setText(text);
	}
	public void setListData(final List<String> ls, String age){
		if(adapter==null){
			adapter =new DialogSelectListAdapter(mContext,ls);
			listView.setAdapter(adapter);
		}else{
			adapter.setDataList(ls);
			adapter.notifyDataSetChanged();
		}
		int position=0;
		for(int i=0;i<ls.size();i++){
			if(age.equals(ls.get(i).toString())){
				position = i;
				break;
			}
		}
		listView.setSelection(position);
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				if (listener != null) {
					listener.onOK(ls.get(position).toString());
				}
				SelectListDialog.this.dismiss();
			}
		});
	}
	
	public interface ListOKListener {
		public void onOK(String text);
	}

}
