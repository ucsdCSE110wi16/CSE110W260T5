package net.atlassian.teammyrec.writersbloc;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;


public class ProjectListAdapter extends ArrayAdapter<ProjectListAdapter.ProjectListViewModel> {

    private List<ProjectListAdapter.ProjectListViewModel> list;

    public static class ProjectListViewModel{
        private String mProjectName;
        private Drawable mProjectDrawable;
        private int color;

        public ProjectListViewModel(){
            this(null, null);
        }

        public ProjectListViewModel(String name){ this(name, null);}

        public ProjectListViewModel(String name, Drawable drawable){
            mProjectName = name;
            mProjectDrawable = drawable;
        }

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

    public ProjectListAdapter(Context context, int resID, List<ProjectListViewModel> list){
        super(context, resID, list);
        this.list = list;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if(v == null){
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.project_list_item, null);
        }

        ProjectListViewModel model = list.get(position);

        if( model != null){

            TextView text = (TextView)v.findViewById(R.id.listItemTextID);

            if(text != null){
                text.setText(model.getProjectName());
            }
        }

        return v;

    }

}
