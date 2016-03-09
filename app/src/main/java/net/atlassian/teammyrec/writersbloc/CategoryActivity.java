package net.atlassian.teammyrec.writersbloc;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.parse.Parse;
import com.parse.ParseUser;

import net.atlassian.teammyrec.writersbloc.Adapters.CategoryListAdapter;
import net.atlassian.teammyrec.writersbloc.Adapters.DeleteListAdapter;
import net.atlassian.teammyrec.writersbloc.Adapters.FolderListAdapter;
import net.atlassian.teammyrec.writersbloc.Models.DataModels.Category;
import net.atlassian.teammyrec.writersbloc.Models.DataModels.Project;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CategoryActivity extends AppCompatActivity implements AddCategoryFragment.OnFragmentInteractionListener{



    private static boolean showOverlay = false;
    public static final String INTENT_EXTRA_PROJECT_PATH = "INTENT_EXTRA_FOLDERPATH";
    private static final String LOG_ID = "CategoryActivity.net.atlassian.teammyrec.writersbloc";
    public static final String INTENT_EXTRA_PROJECT_NAME = "";

    public String chosenCategory;

    private Project mCurrentProject;
    private ArrayList<Category> mCategories;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        Toolbar toolbar = (Toolbar) findViewById(R.id.category_toolbar);
        setSupportActionBar(toolbar);
        String fileName = getIntent().getStringExtra(INTENT_EXTRA_PROJECT_PATH);

        if(!ParseController.userIsLoggedIn()) {
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
        }

        try {
            mCurrentProject = new Project(getIntent().getStringExtra(INTENT_EXTRA_PROJECT_NAME),
                    ParseController.getCurrentUser());
            mCategories = mCurrentProject.getCategories();
            if(mCategories.size() == 0 ){
                // Create default categories
                mCategories.add(new Category("Character", ParseController.getCurrentUser(),
                        mCurrentProject.toString()));
                mCategories.add(new Category("Location", ParseController.getCurrentUser(),
                        mCurrentProject.toString()));
                mCategories.add(new Category("Event", ParseController.getCurrentUser(),
                        mCurrentProject.toString()));
                mCategories.add(new Category("Object", ParseController.getCurrentUser(),
                        mCurrentProject.toString()));
                mCategories.add(new Category("Other", ParseController.getCurrentUser(),
                        mCurrentProject.toString()));

            }
        }catch (Exception e){
            Logger.getLogger(LOG_ID, "Unhandled IO Exception when opening categories");
        }

        ListView list = ((ListView)findViewById(R.id.category_list_view));

        updateListAdapter();

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), PageListActivity.class);
                chosenCategory = ((TextView) view.findViewById(R.id.listItemTextID)).getText().toString();
                intent.putExtra(PageListActivity.INTENT_EXTRA_CATEGORY_NAME, chosenCategory);
                intent.putExtra(PageListActivity.INTENT_EXTRA_PROJECT_NAME,
                        getIntent().getStringExtra(INTENT_EXTRA_PROJECT_NAME));
                startActivity(intent);
            }
        });




        if(showOverlay)
            findViewById(R.id.frameFragmentLayout).setVisibility(View.VISIBLE);

        ImageButton imageButton = (ImageButton)findViewById(R.id.graphButton);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toGraph(v);
            }
        });

    }

    @Override
    public void onResume(){
        super.onResume();

        if(!ParseController.userIsLoggedIn()) {
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
            this.overridePendingTransition(0,0);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_add_folder, menu);
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
        showOverlay = false;
        findViewById(R.id.frameFragmentLayout).setVisibility(View.INVISIBLE);

        updateListAdapter();

    }

    private void updateListAdapter(){

        FolderListAdapter categoryArrayAdapter = new FolderListAdapter(this, R.layout.category_list_item, mCategories);
        ((ListView)findViewById(R.id.category_list_view)).setAdapter(categoryArrayAdapter);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.CategoryMenuTitle:
                showOverlay = true;
                findViewById(R.id.frameFragmentLayout).setVisibility(View.VISIBLE);
                return true;
            case R.id.LogoutIcon:
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                ParseController.logoutCurrentUser();
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void cancelCreateCategory(View v){
        ((EditText) findViewById(R.id.addProjectName)).setText("");
        showOverlay = false;
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
