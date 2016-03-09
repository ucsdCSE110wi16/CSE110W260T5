package net.atlassian.teammyrec.writersbloc.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.ArraySwipeAdapter;

import net.atlassian.teammyrec.writersbloc.Interfaces.Deletable;
import net.atlassian.teammyrec.writersbloc.R;

import java.util.List;

/**
 * Created by jay on 3/8/16.
 */
public class FolderListAdapter<T> extends ArraySwipeAdapter {

    private List<T> mItemList;
    private LayoutInflater mInflater;
    private int layoutId;

    public FolderListAdapter(Context context, int layoutId, List<T> list){
        super(context, layoutId, list);
        this.mItemList = list;
        this.mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.layoutId = layoutId;
    }

    public View generateView(int position, ViewGroup parent){
        return mInflater.inflate(this.layoutId, null);
    }

    public View fillView(int position, View convertView){
        final SwipeLayout layout = (SwipeLayout) convertView;
        if(layout != null){
            TextView title = (TextView)layout.findViewById(R.id.listItemTextID);
            if(title != null)
                title.setText(mItemList.get(position).toString());

            ImageButton trashButton = (ImageButton)layout.findViewById(R.id.trash_button);
            if(trashButton != null){
                trashButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position = (Integer) v.getTag();

                        layout.close(true);
                        T item = mItemList.get(position);
                        // Call delete on item
                        ((Deletable)item).delete();


                        // Then delete from list
                        mItemList.remove(position);


                        notifyDataSetChanged();
                    }
                });

                trashButton.setTag(position);
            }
            layout.close(true);

        }

        return layout;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View v = convertView;

        if(v == null) {
            v = generateView(position, parent);
        }

        return fillView(position, v);

    }


    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe;
    }
}
