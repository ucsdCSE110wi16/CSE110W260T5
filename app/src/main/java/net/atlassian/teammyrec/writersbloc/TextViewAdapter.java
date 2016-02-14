package net.atlassian.teammyrec.writersbloc;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Created by williamchiu on 2/9/16.
 */
public class TextViewAdapter extends BaseAdapter {
    private Context context;
    private final String[] textViewValues;

    public TextViewAdapter(Context context, String[] textViewValues) {
        this.context = context;
        this.textViewValues = textViewValues;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View listView;

        if (convertView == null) {
            listView = new View(context);

            listView = inflater.inflate(R.layout.project_list_item, null);
            TextView textView = (TextView) listView.findViewById(R.id.grid_item_label);
            textView.setText(textViewValues[position]);
        } else {
            listView = (View) convertView;
        }

        return listView;
    }

    @Override
    public int getCount() {
        return textViewValues.length;
    }

    //Don't need bottom two methods
    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
}
