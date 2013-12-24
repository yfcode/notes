package org.lxndr.notes;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;


public class ItemAdapter extends ArrayAdapter<Item> {
	
	static class ViewHolder {
		protected ImageView image;
		protected TextView title;
		protected CheckBox checkbox;
	}
	
	
	private final Activity m_Context;
	
	
	public ItemAdapter(Activity context) {
		super(context, R.layout.list_item);
		m_Context = context;
	}
	
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = null;
		
		if (convertView == null) {
			LayoutInflater inflator = m_Context.getLayoutInflater();
			view = inflator.inflate(R.layout.list_item, null);
			final ViewHolder viewHolder = new ViewHolder();
			viewHolder.image = (ImageView) view.findViewById(R.id.item_image);
			viewHolder.title = (TextView) view.findViewById(R.id.item_title);
			viewHolder.checkbox = (CheckBox) view.findViewById(R.id.item_checkbox);
			view.setTag(viewHolder);
		} else {
			view = convertView;
		}
		
		Item item = getItem(position);
		ViewHolder holder = (ViewHolder) view.getTag();
		holder.title.setText(item.title);
		holder.checkbox.setChecked(item.done);
		return view;
	}
	
}
