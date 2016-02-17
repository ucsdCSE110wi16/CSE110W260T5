package net.atlassian.teammyrec.writersbloc;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.*;

public class PageActivity extends AppCompatActivity {

    public static final String INTENT_PAGE_NAME = "TEST";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TextView textView2 = (TextView)findViewById(R.id.textView2);
        textView2.setText(getIntent().getStringExtra(INTENT_PAGE_NAME));




    }

}
