package net.atlassian.teammyrec.writersbloc;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.*;

import net.atlassian.teammyrec.writersbloc.Models.DataModels.Page;
import net.atlassian.teammyrec.writersbloc.Models.DataModels.PageInformation;

import java.util.logging.Level;
import java.util.logging.Logger;

public class PageActivity extends AppCompatActivity {

    public static final String INTENT_PAGE_NAME = "TEST";
    private static final String LOG_ID = "PageActivity.net.atlassian.teammyrec.writersbloc";


    private Page mPage;
    private PageInformation mPageInformation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        String pageName = getIntent().getStringExtra(INTENT_PAGE_NAME);
        try {
            mPage = new Page(pageName);
            mPageInformation = mPage.getPageInformation();
        }catch (Exception e){
            Logger.getLogger(LOG_ID).log(Level.WARNING, "Error creating page: "+e);
            e.printStackTrace();
        }

        TextView textView2 = (TextView)findViewById(R.id.textView2);
        textView2.setText(mPage.toString());

        EditText eText = (EditText)findViewById(R.id.editText);
        eText.setText(mPageInformation.getText());
        eText.setSelection(eText.length());
    }

    @Override
    public void onPause(){
        super.onPause();

        EditText eText = (EditText)findViewById(R.id.editText);
        mPageInformation.setText(eText.getText().toString());

        try {
            mPage.writePageInformation(mPageInformation);
        }catch (Exception e){
            Logger.getLogger(LOG_ID).log(Level.WARNING, "Page Information not written");
        }

    }

}
