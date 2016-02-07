package net.atlassian.teammyrec.writersbloc;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class ProjectActivity extends AppCompatActivity {

    // These are here ONLY to framework the 'login'. Eventually this functionality will
    // NOT be in the ProjectActivity, but for demo purposes, this works well
    public static final String INTENT_EXTRA_PASSWORD = "ProjectActivity.PASSWORD";
    public static final String INTENT_EXTRA_USERNAME = "ProjectActivity.USERNAME";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        String toastStr = this.getIntent().getStringExtra(INTENT_EXTRA_USERNAME) +
                            " successfully logged in with password " +
                            this.getIntent().getStringExtra(INTENT_EXTRA_PASSWORD);
        Toast.makeText(this, toastStr,Toast.LENGTH_LONG).show();

        ListView list = (ListView)findViewById(R.id.projects_list_view);
        String[] strings = {};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,strings);
        list.setAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_category_activity, menu);
        return true;
    }

}
