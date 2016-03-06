package net.atlassian.teammyrec.writersbloc.Adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import net.atlassian.teammyrec.writersbloc.Models.DataModels.Page;
import net.atlassian.teammyrec.writersbloc.R;

import java.util.List;

/**
 * Created by jay on 3/5/16.
 */
public class PageListAdapter extends ArrayAdapter<Page>{
    private List<Page> list;

    public static class PageListViewModel{
        private String mProjectName;
        private Drawable mProjectDrawable;
        private int color;

        public PageListViewModel(String name){ mProjectName = name;}

        public void setProjectName(String name){
            mProjectName = name;
        }

        public void setProjectDrawable(Drawable drawable){
            mProjectDrawable = drawable;
        }

        public String getProjectName(){
            return mProjectName;
        }

        public Drawable getProjectDrawable(){
            return mProjectDrawable;
        }


    }

    public PageListAdapter(Context context, int resID, List<Page> list){
        super(context, resID, list);
        this.list = list;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if(v == null){
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.page_list_item, null);
        }

        Page model = list.get(position);

        if( model != null){

            TextView text = (TextView)v.findViewById(R.id.listItemTextID);

            if(text != null){
                text.setText(model.toString());
            }
        }

        return v;

    }

}
