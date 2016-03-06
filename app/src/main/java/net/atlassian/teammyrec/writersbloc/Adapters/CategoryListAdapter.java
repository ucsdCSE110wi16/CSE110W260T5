package net.atlassian.teammyrec.writersbloc.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import net.atlassian.teammyrec.writersbloc.R;

import java.util.List;


public class CategoryListAdapter extends ArrayAdapter<CategoryListAdapter.CategoryListViewModel> {

    private List<CategoryListViewModel> list;

    public static class CategoryListViewModel{
        private String mProjectName;

        public CategoryListViewModel(){
            this(null);
        }

        public CategoryListViewModel(String name){ this.mProjectName = name; }

        public void setProjectName(String name){
            mProjectName = name;
        }

        public String getProjectName(){
            return mProjectName;
        }

        public int getColor(){
            int alpha = 150;
            int x = Math.abs((mProjectName.hashCode())) % 1025;
            int r = (int)(120 + 135*(((double)x)/1024));
            int g = (int)(66 + 74*(((double)x)/1024));
            int b = (int)(33 + 37*(((double)x)/1024));

            return Color.argb(alpha,r,g,b);
        }

    }

    public CategoryListAdapter(Context context, int resID, List<CategoryListViewModel> list){
        super(context, resID, list);
        this.list = list;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if(v == null){
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.category_list_item, null);
        }

        CategoryListViewModel model = list.get(position);

        if( model != null){

            TextView text = (TextView)v.findViewById(R.id.listItemTextID);

            if(text != null){
                text.setText(model.getProjectName());
            }
        }

        v.setBackgroundColor(model.getColor());

        return v;

    }

}
