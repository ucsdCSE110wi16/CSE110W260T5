package net.atlassian.teammyrec.writersbloc;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import net.atlassian.teammyrec.writersbloc.Models.DataModels.Category;
import net.atlassian.teammyrec.writersbloc.Models.DataModels.Page;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PageListActivity extends AppCompatActivity {

    public static final String INTENT_EXTRA_PROJECT_ABSOLUTE_DIR = "Absolute_Directory";
    private static final String LOG_ID = "PageListActivity.net.atlassian.teammyrec.writersbloc";

    private Category mCategory;
    private ArrayList<Page> mPages;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        try {
            mCategory = new Category(getIntent().getStringExtra(INTENT_EXTRA_PROJECT_ABSOLUTE_DIR));
            mPages = mCategory.getPages();
        }catch (Exception e){
            Logger.getLogger(LOG_ID).log(Level.WARNING, "Error on category creation: " + e);
        }



    }

}
