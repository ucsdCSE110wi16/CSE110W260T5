package net.atlassian.teammyrec.writersbloc;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import net.atlassian.teammyrec.writersbloc.Models.DataModels.Category;
import net.atlassian.teammyrec.writersbloc.Models.DataModels.Project;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CategoryActivity extends AppCompatActivity implements AddCategoryFragment.OnFragmentInteractionListener{

    public static final String INTENT_EXTRA_PROJECT_PATH = "INTENT_EXTRA_FOLDERPATH";
    private static final String LOG_ID = "CategoryActivity.net.atlassian.teammyrec.writersbloc";
    public static final String INTENT_EXTRA_PROJECT_NAME = "";

    private Project mCurrentProject;
    private ArrayList<Category> mCategories;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        Toolbar toolbar = (Toolbar) findViewById(R.id.category_toolbar);
        setSupportActionBar(toolbar);
        String fileName = getIntent().getStringExtra(INTENT_EXTRA_PROJECT_PATH);
        try {
            mCurrentProject = new Project(fileName);
            mCategories = mCurrentProject.getCategories();
            if(mCategories.size() == 0 ){
                // Create default categories
                mCategories.add(new Category(fileName, "Character"));
                mCategories.add(new Category(fileName, "Location"));
                mCategories.add(new Category(fileName, "Event"));
                mCategories.add(new Category(fileName, "Object"));
                mCategories.add(new Category(fileName, "Other"));

            }
        }catch (Exception e){
            Logger.getLogger(LOG_ID, "Unhandled IO Exception when opening categories");
        }

        ArrayAdapter<Category> categoryArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, mCategories);
        ListView list = ((ListView)findViewById(R.id.category_list_view));
        list.setAdapter(categoryArrayAdapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), PageListActivity.class);
                intent.putExtra(PageListActivity.INTENT_EXTRA_PROJECT_ABSOLUTE_DIR, mCategories.get(position).getAbsolutePath());
                intent.putExtra(PageListActivity.INTENT_EXTRA_PROJECT_NAME, getIntent().getStringExtra(INTENT_EXTRA_PROJECT_NAME));
                startActivity(intent);
            }
        });

        ImageButton imageButton = (ImageButton)findViewById(R.id.graphButton);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toGraph(v);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_add_item, menu);
        return true;
    }

    public void createCategory(View v) {
        try {
            Category category = mCurrentProject.createCategory(((EditText) findViewById(R.id.addProjectName)).getText().toString());
            mCategories.add(category);
        }catch (Exception e){
            Logger.getLogger(LOG_ID).log(Level.WARNING,"IO Error when creating category: "+ e);
        }

        ((EditText) findViewById(R.id.addProjectName)).setText("");
        findViewById(R.id.frameFragmentLayout).setVisibility(View.INVISIBLE);

        ArrayAdapter<Category> categoryArrayAdapter = new ArrayAdapter<Category>(this, android.R.layout.simple_list_item_1, mCategories);
        ((ListView)findViewById(R.id.category_list_view)).setAdapter(categoryArrayAdapter);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.CategoryMenuTitle:
                findViewById(R.id.frameFragmentLayout).setVisibility(View.VISIBLE);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void cancelCreateCategory(View v){
        findViewById(R.id.frameFragmentLayout).setVisibility(View.INVISIBLE);
    }

    public void toGraph(View v) {
        Intent intent = new Intent(this, GridActivity.class);
        this.startActivity(intent);
    }



    @Override
    public void onFragmentInteraction(Uri uri) {
        return;
    }
}
