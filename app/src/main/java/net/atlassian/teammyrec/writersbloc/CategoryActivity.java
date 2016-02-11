package net.atlassian.teammyrec.writersbloc;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class CategoryActivity extends AppCompatActivity {

    public static final String INTENT_EXTRA_USERNAME = "ProjectActivity.USERNAME";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        Toolbar toolbar = (Toolbar) findViewById(R.id.category_toolbar);
        toolbar.setTitle("Categories");
        setSupportActionBar(toolbar);

        ListView categoryList = (ListView)findViewById(R.id.category_list_view);
        String[] strings = {"Characters", "Locations", "Events", "Objects", "Other"};
        //ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,strings);
        categoryList.setAdapter(new TextViewAdapter(this, strings));

        categoryList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(CategoryActivity.this, "" + position, Toast.LENGTH_SHORT).show();
            }
        });


    }

}
