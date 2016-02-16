package net.atlassian.teammyrec.writersbloc;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class ProjectActivity extends AppCompatActivity implements AddProjectFragment.OnFragmentInteractionListener {

    // These are here ONLY to framework the 'login'. Eventually this functionality will
    // NOT be in the ProjectActivity, but for demo purposes, this works well
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
        String[] strings = getFilesDir().list();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,strings);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String projectName = ((AppCompatTextView)view).getText().toString();
                Intent intent = new Intent(getApplicationContext(), CategoryActivity.class);
                intent.putExtra(CategoryActivity.INTENT_EXTRA_PROJECT_PATH, getFilesDir()+"/"+projectName);
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

    public void createProject(View v){
        AddProjectFragment fragment = (AddProjectFragment)getSupportFragmentManager().findFragmentById(R.id.overlayAddProject);
        fragment.createProject(v);

        ListView list = (ListView)findViewById(R.id.projects_list_view);
        String[] strings = getFilesDir().list();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,strings);
        list.setAdapter(adapter);

        EditText edit = ((EditText)fragment.getActivity().findViewById(R.id.addProjectName));
        edit.setText("");
        findViewById(R.id.frameFragmentLayout).setVisibility(View.INVISIBLE);

        InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        mgr.hideSoftInputFromWindow(edit.getWindowToken(), 0);

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


    @Override
    public void onFragmentInteraction(Uri uri) {
        return;
    }
}
