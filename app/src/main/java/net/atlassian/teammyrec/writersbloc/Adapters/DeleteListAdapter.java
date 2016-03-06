package net.atlassian.teammyrec.writersbloc.Adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import net.atlassian.teammyrec.writersbloc.R;

import java.util.List;


public class DeleteListAdapter<T> extends ArrayAdapter {

    private List<T> list;
    private final int resID;

    public DeleteListAdapter(Context context, int resID, List<T> list){
        super(context, resID, list);
        this.list = list;
        this.resID = resID;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if(v == null){
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(resID, null);
        }

        T resource = list.get(position);

        if(resource instanceof CategoryListAdapter.CategoryListViewModel){
            CategoryListAdapter.CategoryListViewModel model = (CategoryListAdapter.CategoryListViewModel) resource;
            View myView = v.findViewById(R.id.trash_icon);
            if(myView != null)
                myView.setBackgroundColor(model.getColor());
        }

        return v;

    }

}


