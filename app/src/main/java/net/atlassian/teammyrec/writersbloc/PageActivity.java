package net.atlassian.teammyrec.writersbloc;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.*;
import java.util.*;
import android.content.*;
import android.text.*;
import android.util.*;

import net.atlassian.teammyrec.writersbloc.Models.DataModels.Page;
import net.atlassian.teammyrec.writersbloc.Models.DataModels.PageInformation;

import java.util.PriorityQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.atlassian.teammyrec.writersbloc.Models.DataModels.*;

public class PageActivity extends AppCompatActivity {

    public static final String INTENT_PAGE_NAME = "TEST";
    private static final String LOG_ID = "PageActivity.net.atlassian.teammyrec.writersbloc";
    public static final String INTENT_PROJECT_NAME = "";


    private Page mPage;
    private PageInformation mPageInformation;
    private Project mProject;

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
            System.out.println("Project name: " + getIntent().getStringExtra(INTENT_PROJECT_NAME));
            mProject = new Project(getApplicationContext(),
                    getIntent().getStringExtra(INTENT_PROJECT_NAME));
        }catch (Exception e){
            Logger.getLogger(LOG_ID).log(Level.WARNING, "Error creating page: "+e);
            e.printStackTrace();
        }

        TextView textView2 = (TextView)findViewById(R.id.textView2);
        textView2.setText(mPage.toString());

        EditText eText = (EditText)findViewById(R.id.editText);
        eText.setText(mPageInformation.getText());
        eText.setSelection(eText.length());

        PriorityQueue<String> pages = mProject.getAllPages();

        PriorityQueue< Pair<Integer, String> > phrases =
                PhraseLinker.findPhrases(mPageInformation.getText(), pages);

        System.out.println("Number of phrases: " + phrases.size());

        //Link the words in the text
        SpannableString ss = new SpannableString(eText.getText());
        ArrayList<ClickableSpan> spans = new ArrayList<>(10);
        for(Pair<Integer, String> p : phrases) {

            spans.add(new ClickableSpan() {
                @Override
                public void onClick(View widget) {
                    //do something
                    System.out.println("Clicked a link");
                }
            });
        }
        int index = 0;
        for(Pair<Integer, String> p : phrases) {
            ss.setSpan(spans.get(index),p.first,p.first+2,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE );
            index++;
        }


        eText.setText(ss);
        eText.setMovementMethod(LinkMovementMethod.getInstance());


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
