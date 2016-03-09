package net.atlassian.teammyrec.writersbloc;

import android.content.Intent;
import android.graphics.pdf.PdfDocument;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import net.atlassian.teammyrec.writersbloc.Models.DataModels.Category;
import net.atlassian.teammyrec.writersbloc.Models.DataModels.Project;
import net.atlassian.teammyrec.writersbloc.Models.DataModels.Page;

import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.logging.Logger;


public class GridActivity extends AppCompatActivity implements GridCustomView.OnToggledListener
//implements View.OnClickListener
{

    public static final String PROJECT_INTENT = "Project_Name";
    private static final String LOG_ID = "GridActivity.net.atlassian.teammyrec.writersbloc";
    public static final String INTENT_EXTRA_PROJECT_NAME = "pn";
    public static final String INTENT_EXTRA_CATEGORY_NAME = "cn";

    private GridLayout g;
    private int fillup ;
    private final int minColC = 5;
    private final int minRowC = 5;
    private final int colC = 2;
    private int maxSize = 0;

    //private final ArrayList<Category> listOfCate = new ArrayList<>();
    //private final PriorityQueue< Pair<Category, Page> > PAGES = new PriorityQueue<>();
    private ArrayList<String> pageNames = new ArrayList<>();
    private ArrayList<Page> pages = new ArrayList<>();

    private int rowC = 0 ;
    private GridCustomView[] pV;
    private Project mCurrentProject;
    private ArrayList<Category> mCategories;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
/*
        if(!ParseController.userIsLoggedIn()) {
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
        }
*/
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid);

        String projectName = getIntent().getStringExtra(PROJECT_INTENT);
        System.out.println("Project Name:" + projectName);
        if(projectName != null){
            try {

                mCurrentProject = new Project(getIntent().getStringExtra(INTENT_EXTRA_PROJECT_NAME),
                        ParseController.getCurrentUser());
                mCategories = mCurrentProject.getCategories();
                for(int x = 0 ; x < mCategories.size() ; x++)
                {
                    pages.addAll((mCategories.get(x)).getPages());
                }
                for(int x = 0 ; x < pages.size() ; x++)
                {
                    pageNames.add( (pages.get(x)).toString() );

                }

            }catch (Exception e){
                Logger.getLogger(LOG_ID, "Failed");
            }
        }
        maxSize = pageNames.size() ;
        //getMaxsize/update rowC here;
        setUpGrid();
    }

    protected void setUpGrid() {
        g = (GridLayout) findViewById(R.id.grid);

        g.setColumnCount(colC);
        if( maxSize % colC == 0) {
            g.setRowCount(maxSize/colC);
        }
        else
        {
            g.setRowCount((maxSize/colC) +1);
        }
        if( g.getRowCount() < minRowC)
        {
            g.setRowCount(minRowC);
        }
        rowC = g.getRowCount() ;
        fillup = rowC * colC ;
        fillIn();
    }

    protected void fillIn() {
        if( pageNames.size() == 0)
        {
            return ;
        }


        pV = new GridCustomView[fillup];


        for (int y = 0; y < rowC; y++) {
            for (int x = 0; x < colC; x++) {

                GridCustomView tempView ;
                if( x + y * colC < maxSize) {
                    tempView = new GridCustomView(this, x, y, pageNames.get(x + y * colC),
                            pages.get(x + y * colC).toString(), pages.get(x + y * colC).getCategory());
                }
                else
                {
                    tempView = new GridCustomView(this, x ,y, "",null,null);
                }
                tempView.setOnToggledListener(this);

                pV[x + y * colC] = tempView;

                g.addView(tempView);
            }
        }

        g.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {

                final int MARGIN = 5;
                int pWidth = g.getWidth();
                int pHeight = g.getHeight();
                int w = pWidth / colC;
                int h = pHeight / rowC;

                for (int y = 0; y < rowC; y++) {
                    for (int x = 0; x < colC; x++) {
                        GridLayout.LayoutParams gP =
                                (GridLayout.LayoutParams) pV[x + y * colC].getLayoutParams();
                        gP.width = w - 2 * MARGIN;
                        gP.height = h - 2 * MARGIN;
                        gP.setMargins(MARGIN, MARGIN, MARGIN, MARGIN);
                        pV[y * colC + x].setLayoutParams(gP);

                    }
                }
                //System.out.println("AfterLoop");

            }
        });

    }

    @Override
    public void OnToggled(GridCustomView view, boolean stuff , String pageName, String pagePath, Category cate)
    {
        if(stuff) {
            toGridGraph(view, pageName, pagePath,cate);
        }
    }
    public void toGridGraph(View v , String pageN, String pageP, Category cate) {
        Intent intent = new Intent(this, GraphActivity.class);
        intent.putExtra(GraphActivity.PAGE_INTENT, pageN);
        intent.putExtra(GraphActivity.PAGE_PATH, pageP);
        intent.putExtra(GraphActivity.INTENT_CATEGORY_NAME, cate.toString());
        intent.putExtra(GraphActivity.INTENT_PROJECT_NAME, getIntent().getStringExtra(INTENT_EXTRA_PROJECT_NAME));
        this.startActivity(intent);
    }


}