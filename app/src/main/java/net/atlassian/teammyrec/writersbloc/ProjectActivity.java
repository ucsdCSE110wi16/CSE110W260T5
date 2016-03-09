package net.atlassian.teammyrec.writersbloc;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import net.atlassian.teammyrec.writersbloc.Adapters.FolderListAdapter;
import net.atlassian.teammyrec.writersbloc.Adapters.ProjectListAdapter;
import net.atlassian.teammyrec.writersbloc.Models.DataModels.Project;

import com.daimajia.swipe.adapters.ArraySwipeAdapter;

import java.util.ArrayList;
import net.atlassian.teammyrec.writersbloc.Models.DataModels.*;

public class ProjectActivity extends AppCompatActivity implements AddProjectFragment.OnFragmentInteractionListener {

    // These are here ONLY to framework the 'login'. Eventually this functionality will
    public static final String INTENT_EXTRA_USERNAME = "ProjectActivity.USERNAME";
    private static boolean showOverlay = false;

    private ArrayList<Project> mProjects;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        if(!ParseController.userIsLoggedIn()) {
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project);
        Toolbar toolbar = (Toolbar) findViewById(R.id.project_toolbar);
        setSupportActionBar(toolbar);

        mProjects = ParseController.getAllProjects();
        ArrayList<Project> projects = ParseController.getAllProjects();
        System.out.println(projects.size());
        for(Project project : projects) {
            ArrayList<Category> categories = ParseController.getAllCategoriesForProject(project.toString());
            for(Category category : categories) {
                System.out.println(category);
                ArrayList<Page> pages = ParseController.getAllPagesForCategory(category.toString(), project.toString());
                for(Page page : pages) {
                    System.out.println("Page " + page.toString() + " is in category " + category + " and project " +
                            project);
                }
            }
        }

        ListView list = (ListView)findViewById(R.id.projects_list_view);

        ArrayList<ProjectListAdapter.ProjectListViewModel> models = new ArrayList<>();
        for(Project p : ParseController.getAllProjects()){
            models.add(new ProjectListAdapter.ProjectListViewModel(p.toString()));
        }
        ProjectListAdapter adapter = new ProjectListAdapter(this, R.layout.project_list_item,models);
        list.setAdapter(adapter);


        updateAdapter(mProjects);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String projectName = ((TextView) view.findViewById(R.id.listItemTextID)).getText().toString();
                Intent intent = new Intent(getApplicationContext(), CategoryActivity.class);
                intent.putExtra(CategoryActivity.INTENT_EXTRA_PROJECT_NAME, projectName);
                startActivity(intent);
            }
        });

        if(showOverlay)
            findViewById(R.id.frameFragmentLayout).setVisibility(View.VISIBLE);



        setTitle(ParseController.getCurrentUser());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_add_folder, menu);
        return true;
    }

    public void createProject(View v){
        AddProjectFragment fragment = (AddProjectFragment)getSupportFragmentManager().findFragmentById(R.id.overlayAddProject);
        Project p = fragment.createProject(v);
        if(p != null) mProjects.add(p);

        ListView list = (ListView)findViewById(R.id.projects_list_view);
        ((ArraySwipeAdapter)list.getAdapter()).notifyDataSetChanged();


        EditText edit = ((EditText)fragment.getActivity().findViewById(R.id.addProjectName));
        edit.setText("");
        findViewById(R.id.frameFragmentLayout).setVisibility(View.INVISIBLE);
        showOverlay=false;

        InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        mgr.hideSoftInputFromWindow(edit.getWindowToken(), 0);


    }

    public void cancelCreateProject(View v){
        ((EditText) findViewById(R.id.addPageName)).setText("");
        findViewById(R.id.frameFragmentLayout).setVisibility(View.INVISIBLE);
        showOverlay=false;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.CategoryMenuTitle:
                findViewById(R.id.frameFragmentLayout).setVisibility(View.VISIBLE);
                showOverlay=true;
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

    @Override
    public void onResume() {
        super.onResume();
        if(!ParseController.userIsLoggedIn()) {
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
            this.overridePendingTransition(0,0);
        }
    }


    public void updateAdapter(ArrayList<Project> mProjects) {
        ListView list = (ListView)findViewById(R.id.projects_list_view);
        FolderListAdapter adapter = new FolderListAdapter(this, R.layout.project_list_item, mProjects );
        list.setAdapter(adapter);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

        return;
    }


}
