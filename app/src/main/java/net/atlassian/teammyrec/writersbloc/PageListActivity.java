package net.atlassian.teammyrec.writersbloc;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import net.atlassian.teammyrec.writersbloc.Adapters.DeleteListAdapter;
import net.atlassian.teammyrec.writersbloc.Adapters.FolderListAdapter;
import net.atlassian.teammyrec.writersbloc.Adapters.PageListAdapter;
import net.atlassian.teammyrec.writersbloc.Models.DataModels.Category;
import net.atlassian.teammyrec.writersbloc.Models.DataModels.Page;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import android.content.*;

public class PageListActivity extends AppCompatActivity {

    public static final String INTENT_EXTRA_PROJECT_ABSOLUTE_DIR = "Absolute_Directory";
    private static final String LOG_ID = "PageListActivity.net.atlassian.teammyrec.writersbloc";
    public static final String INTENT_EXTRA_PROJECT_NAME = "pn";
    public static final String INTENT_EXTRA_CATEGORY_NAME = "cn";

    private Category mCategory;
    private ArrayList<Page> mPages;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(!ParseController.userIsLoggedIn()) {
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
        }

        try {
            mCategory = new Category(getIntent().getStringExtra(INTENT_EXTRA_CATEGORY_NAME),
                    ParseController.getCurrentUser(), getIntent().getStringExtra(INTENT_EXTRA_PROJECT_NAME));
           mPages = mCategory.getPages();
        }catch (Exception e){
            Logger.getLogger(LOG_ID).log(Level.WARNING, "Error on category creation: " + e);
        }

        ListView list = (ListView) findViewById(R.id.page_list_view);
        FolderListAdapter<Page> adapter = new FolderListAdapter<>(this, R.layout.page_list_item, mPages);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String pageName = mPages.get(position).toString();
                Intent intent = new Intent(getApplicationContext(), PageActivity.class);
                intent.putExtra(PageActivity.INTENT_PAGE_NAME, pageName);
                String projName = getIntent().getStringExtra(INTENT_EXTRA_PROJECT_NAME);
                intent.putExtra(PageActivity.INTENT_PROJECT_NAME, projName);
                intent.putExtra(PageActivity.INTENT_CATEGORY_NAME,
                        getIntent().getStringExtra(INTENT_EXTRA_CATEGORY_NAME));
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_add_item, menu);
        return true;
    }

    public void createPage(View v){
        try{
            Page page = mCategory.addPage(((EditText) findViewById(R.id.addPageName)).getText().toString());
            ((EditText) findViewById(R.id.addPageName)).setText("");
            //ParseController.createPage(page.toString(), mCategory.toString(),
              //      getIntent().getStringExtra(INTENT_EXTRA_PROJECT_NAME), ParseController.getCurrentUser());
            //page.writePageInformation(info);
            mPages.add(page);

            updateListAdapter();

            findViewById(R.id.frameFragmentLayout).setVisibility(View.INVISIBLE);
        }catch (Exception e){
            Logger.getLogger(LOG_ID).log(Level.WARNING, "Invalid IO error when creating page: " + e);
        }
    }

    public void goToPage(View v) {
        Intent intent = new Intent(this, PageActivity.class);
        this.startActivity(intent);
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

    public void cancelCreatePage(View v){
        findViewById(R.id.frameFragmentLayout).setVisibility(View.INVISIBLE);
    }

    public void updateListAdapter(){
        ((FolderListAdapter) ((ListView)findViewById(R.id.page_list_view)).getAdapter()).notifyDataSetChanged();
    }

}
