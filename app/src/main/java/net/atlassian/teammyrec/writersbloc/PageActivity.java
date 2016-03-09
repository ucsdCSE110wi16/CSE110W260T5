package net.atlassian.teammyrec.writersbloc;

import android.app.AlertDialog;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import java.util.*;
import android.content.*;
import android.text.*;
import android.util.*;

import net.atlassian.teammyrec.writersbloc.Models.DataModels.Page;

import java.util.PriorityQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.atlassian.teammyrec.writersbloc.Models.DataModels.*;

public class PageActivity extends AppCompatActivity implements AddCategoryFragment.OnFragmentInteractionListener{

    public static final String INTENT_PAGE_NAME = "TEST";
    private static final String LOG_ID = "PageActivity.net.atlassian.teammyrec.writersbloc";
    public static final String INTENT_PROJECT_NAME = "pn";
    public static final String INTENT_CATEGORY_NAME = "cn";

    private static boolean showOverlay = false;
    private Page mPage;
    private Category mCategory;
    private Project mProject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(!ParseController.userIsLoggedIn()) {
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
        }

        String pageName = getIntent().getStringExtra(INTENT_PAGE_NAME);
        try {
            mPage = new Page(pageName, getIntent().getStringExtra(INTENT_CATEGORY_NAME),
                    getIntent().getStringExtra(INTENT_PROJECT_NAME), ParseController.getCurrentUser());
            mCategory = mPage.getCategory();
            System.out.println("Project name: " + getIntent().getStringExtra(INTENT_PROJECT_NAME));
            mProject = new Project(getIntent().getStringExtra(INTENT_PROJECT_NAME),
                    ParseController.getCurrentUser());
        }catch (Exception e){
            Logger.getLogger(LOG_ID).log(Level.WARNING, "Error creating page: "+e);
            e.printStackTrace();
        }

        EditText eText = (EditText)findViewById(R.id.editText);

        String content = ParseController.getPageContent(mPage.toString(),
                getIntent().getStringExtra(INTENT_CATEGORY_NAME),
                getIntent().getStringExtra(INTENT_PROJECT_NAME), ParseController.getCurrentUser());
        System.out.println("content: " + content);
        eText.setText(content + " ");
        eText.setSelection(eText.length());

        PriorityQueue< Pair<Category, Page> > pages = mProject.getAllPages();

        PriorityQueue< Pair<Pair<Integer, Page>, Category> > phrases =
                PhraseLinker.findPhrases(eText.getText().toString(), pages,mPage.toString());

        System.out.println("Number of phrases: " + phrases.size());
        //Link the words in the text
        SpannableString ss = new SpannableString(eText.getText());
        ArrayList<ClickableSpan> spans = new ArrayList<>(10);
        for(Pair<Pair<Integer, Page>, Category> p : phrases) {
            final String pageNameTmp = p.first.second.toString();
            final String prjName = getIntent().getStringExtra(INTENT_PROJECT_NAME);
            final String catName = p.second.toString();

            spans.add(new ClickableSpan() {
                @Override
                public void onClick(View widget) {

                    Intent intent = new Intent(getApplicationContext(), PageActivity.class);
                    System.out.println("page we're going to: " + pageNameTmp);
                    intent.putExtra(PageActivity.INTENT_PAGE_NAME, pageNameTmp);
                    intent.putExtra(PageActivity.INTENT_PROJECT_NAME, prjName);
                    intent.putExtra(PageActivity.INTENT_CATEGORY_NAME, catName);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    getBaseContext().startActivity(intent);
                }
            });
        }
        int index = 0;
        for(Pair<Pair<Integer, Page>, Category> p : phrases) {
            ss.setSpan(spans.get(index),p.first.first,
                    p.first.first+p.first.second.toString().length(),Spanned.SPAN_EXCLUSIVE_EXCLUSIVE );
            index++;
        }


        eText.setText(ss);
        eText.setMovementMethod(LinkMovementMethod.getInstance());

        if(showOverlay)
            findViewById(R.id.frameFragmentLayout).setVisibility(View.VISIBLE);



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_add_item, menu);
        return true;
    }

    @Override
    public void onPause(){
        super.onPause();

        EditText eText = (EditText)findViewById(R.id.editText);

        try {
            ParseController.updatePage(mPage.toString(), getIntent().getStringExtra(INTENT_CATEGORY_NAME),
                    getIntent().getStringExtra(INTENT_PROJECT_NAME),
                    ParseController.getCurrentUser(),
                    eText.getText().toString());
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void createNewPage(View v){
        try{

            ArrayList<Page> allPages =  ParseController.getAllPagesForCategory(
                    getIntent().getStringExtra(INTENT_CATEGORY_NAME),
                    getIntent().getStringExtra(INTENT_PROJECT_NAME));
            for(Page pg : allPages){
                if(((EditText) findViewById(R.id.addPageName)).getText().toString().toLowerCase()
                        .equals(pg.toString().toLowerCase()))
                {
                    AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);
                    dlgAlert.setMessage("Page '" + ((EditText) findViewById(R.id.addPageName)).
                            getText().toString() + "' already exists.");
                    dlgAlert.setTitle("Error");
                    dlgAlert.setPositiveButton("OK", null);
                    dlgAlert.setCancelable(true);
                    dlgAlert.create().show();
                    return;
                }
            }

            Page page = mCategory.addPage(((EditText) findViewById(R.id.addPageName)).getText().toString());
            ((EditText) findViewById(R.id.addPageName)).setText("");
            findViewById(R.id.frameFragmentLayout).setVisibility(View.INVISIBLE);
            showOverlay = false;

            Intent intent = new Intent(this, PageActivity.class);
            intent.putExtra(INTENT_PAGE_NAME, page.toString());
            intent.putExtra(INTENT_PROJECT_NAME, mProject.toString());
            intent.putExtra(INTENT_CATEGORY_NAME, mProject.toString());
            startActivity(intent);

        }catch (Exception e){
            Logger.getLogger(LOG_ID).log(Level.WARNING, "Invalid IO error when creating page: " + e);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.CategoryMenuTitle:
                findViewById(R.id.frameFragmentLayout).setVisibility(View.VISIBLE);
                showOverlay = true;
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

    public void cancelCreatePage(View v){
        ((EditText) findViewById(R.id.addPageName)).setText("");
        findViewById(R.id.frameFragmentLayout).setVisibility(View.INVISIBLE);
        showOverlay = false;
    }

    public void onFragmentInteraction(Uri uri) {
        return;
    }


}
