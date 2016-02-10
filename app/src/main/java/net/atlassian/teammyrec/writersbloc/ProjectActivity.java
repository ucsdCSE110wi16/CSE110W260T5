package net.atlassian.teammyrec.writersbloc;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class ProjectActivity extends AppCompatActivity {

    // These are here ONLY to framework the 'login'. Eventually this functionality will
    // NOT be in the ProjectActivity, but for demo purposes, this works well
    //TODO
    public static final String INTENT_EXTRA_PASSWORD = "ProjectActivity.PASSWORD";
    public static final String INTENT_EXTRA_USERNAME = "ProjectActivity.USERNAME";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project);
        Toolbar toolbar = (Toolbar) findViewById(R.id.project_toolbar);
        setSupportActionBar(toolbar);

        String toastStr = this.getIntent().getStringExtra(INTENT_EXTRA_USERNAME) +
                            " successfully logged in with password " +
                            this.getIntent().getStringExtra(INTENT_EXTRA_PASSWORD);
        Toast.makeText(this, toastStr,Toast.LENGTH_LONG).show();

        ListView list = (ListView)findViewById(R.id.projects_list_view);
        String[] strings = {"Project 1", "Project 2", "Project 3", "Project 4", "Project 5"};
        //ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,strings);
        list.setAdapter(new TextViewAdapter(this, strings));

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent categoryAct = new Intent(ProjectActivity.this, CategoryActivity.class);

                //Pass in any needed info in category activity here
                ProjectActivity.this.startActivity(categoryAct);
            }
        });


        //GridView gridView = (GridView) findViewById(R.id.projects_grid_view);
        //gridView.setAdapter(new TextViewAdapter(this, strings));

        Toast.makeText(this, "here",Toast.LENGTH_SHORT).show();

        /*
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(ProjectActivity.this, "" + position, Toast.LENGTH_SHORT).show();
            }
        });
        */

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_category_activity, menu);
        return true;
    }


}
