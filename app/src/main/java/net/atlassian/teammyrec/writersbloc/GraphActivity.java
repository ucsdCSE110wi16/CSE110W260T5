package net.atlassian.teammyrec.writersbloc;
import android.content.Intent;
import android.graphics.Point;
//import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
//import android.support.design.widget.AppBarLayout;
//import android.support.design.widget.FloatingActionButton;
//import android.support.design.widget.Snackbar;
//import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
//import android.support.v7.widget.Toolbar;
//import android.text.style.ClickableSpan;
//import android.util.Log;
import android.util.Pair;
import android.view.Display;
import android.view.View;
//import android.widget.AbsListView;
import android.view.ViewTreeObserver;
import android.widget.Button;
//import android.widget.LinearLayout;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.RelativeLayout;
//import android.graphics.Color;
import android.widget.Toast;

//import net.atlassian.teammyrec.writersbloc.Models.DataModels.Category;
import com.parse.Parse;

import net.atlassian.teammyrec.writersbloc.Models.DataModels.Category;
import net.atlassian.teammyrec.writersbloc.Models.DataModels.Page;
import net.atlassian.teammyrec.writersbloc.Models.DataModels.Project;

import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GraphActivity extends AppCompatActivity {


    private static final String LOG_ID = "GraphActivity.net.atlassian.teammyrec.writersbloc";
    public static final String INTENT_PROJECT_NAME = "Proj_Name";
    public static final String INTENT_CATEGORY_NAME = "Cate_Name";

    public static final String PAGE_INTENT = "Page_Name";
    public static final String PAGE_PATH = "Page_Path";
    private Button cen;
    private RelativeLayout page;
    private RelativeLayout.LayoutParams cenL;
    private int cenX;
    private int cenY;

    private int createdB = 0;
    private int createdG = 0;
    private int Radius = 200;
    private String pageName;
    private Page mPage;
    private Project mProject;

    private int createB = 0;

    private int cenXC;
    private int cenYC;

    //private Category mCategory;
    //private ArrayList<Page> mPages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        pageName = getIntent().getStringExtra(PAGE_INTENT);
        //String pagePath = getIntent().getStringExtra(PAGE_PATH);

        try {
            mPage = new Page(pageName, getIntent().getStringExtra(INTENT_CATEGORY_NAME),
                    getIntent().getStringExtra(INTENT_PROJECT_NAME), ParseController.getCurrentUser());
            System.out.println("Project name: " + getIntent().getStringExtra(INTENT_PROJECT_NAME));
            mProject = new Project(getIntent().getStringExtra(INTENT_PROJECT_NAME),
                    ParseController.getCurrentUser());
            //mCategory = mPage.getCategory();
        } catch (Exception e) {
            Logger.getLogger(LOG_ID).log(Level.WARNING, "Error creating page: " + e);
            e.printStackTrace();
        }


        setContentView(R.layout.activity_graph);
        createCenter();


    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    //@Override
    /*public void onWindowFocusChanged(boolean hasFocus) {
        if (hasFocus && createdG == 0) {
            createGraph();
            createdG = 1;
        }
    }*/

    private void createCenter() {

        cen = new Button(this);
        cen.setText(pageName);
        page = (RelativeLayout) findViewById(R.id.GraphPage);

        cenL = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        Display mdisp = getWindowManager().getDefaultDisplay();
        Point mdispSize = new Point();
        mdisp.getSize(mdispSize);
        int maxX = mdispSize.x;
        int maxY = mdispSize.y;

        if( mdispSize.x /3 < mdispSize.y/3 )
        {
            Radius = mdispSize.x / 3;
        }
        else
        {
            Radius = mdispSize.y / 5;
        }

        cenL.leftMargin = maxX / 2;
        cenL.topMargin = maxY / 2;
        cenX = cenL.leftMargin;
        cenY = cenL.topMargin;
        page.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if( createdG == 0 ) {
                    cenL.leftMargin = cenL.leftMargin - cen.getWidth() / 2;
                    cenL.topMargin = cenL.topMargin - cen.getHeight() / 2;
                    cen.setLayoutParams(cenL);
                    createdG = 1 ;


                    createGraph();


                }
                //page.addView(cen, cenL);
                //System.out.println("AfterLoop");

            }
        });
        page.addView(cen, cenL);


        cen.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (mPage == null) {
                    Toast.makeText(view.getContext(),
                            "mPage = NULL", Toast.LENGTH_SHORT)
                            .show();
                } else {

                    System.out.println("Cen" + mPage.toString() + pageName + getIntent().getStringExtra(INTENT_PROJECT_NAME)
                            + mPage.getCategory().toString() );
                    String pageName = mPage.toString();
                    Intent intent = new Intent(getApplicationContext(), PageActivity.class);
                    intent.putExtra(PageActivity.INTENT_PAGE_NAME, pageName);
                    String projName = getIntent().getStringExtra(INTENT_PROJECT_NAME);
                    intent.putExtra(PageActivity.INTENT_PROJECT_NAME, projName);
                    intent.putExtra(PageActivity.INTENT_CATEGORY_NAME,
                            mPage.getCategory().toString());
                    startActivity(intent);


                }

            }
        });

    }



    private void createGraph() {



        if (mProject == null) {
            //System.out.println("mProject NULL");
            return;
        }

        PriorityQueue<Pair<Category, Page>> pages = mProject.getAllPages();
        System.out.println("Side" + pageName + mPage.getCategory().toString() +
                getIntent().getStringExtra(INTENT_PROJECT_NAME) + mPage.toString());
        String body =  ParseController.getPageBody(pageName, mPage.getCategory().toString(),
                getIntent().getStringExtra(INTENT_PROJECT_NAME), ParseController.getCurrentUser());
        // EditText eText = (EditText)findViewById(R.id.editText);


        PriorityQueue< Pair<Pair<Integer, Page>, Category> > phrases =
                PhraseLinker.findPhrases(body, pages, mPage.toString());
        final ArrayList<String> pageNameTmp  = new ArrayList<>();
        final ArrayList<String> prjName = new ArrayList<>();
        final ArrayList<String> catName = new ArrayList<>();
        for(Pair<Pair<Integer, Page>, Category> p : phrases) {
            pageNameTmp.add(p.first.second.toString());
            prjName.add(getIntent().getStringExtra(INTENT_PROJECT_NAME));
            catName.add(p.second.toString());
        }


        double pie = 3.141592653589793;

        double XPosition;
        double YPosition;

        double angle = (2 * pie) / phrases.size() ;

        double currentA = pie/2 ;

        cenX = cenL.leftMargin ;
        cenY = cenL.topMargin ;
        cen.setLayoutParams(cenL);
        RelativeLayout page = (RelativeLayout)findViewById(R.id.GraphPage);
        Button[] buttonArray = new Button[phrases.size()] ;
        for( int x = 0; x < phrases.size() ; x++ ) {
            buttonArray[x] = new Button(this);
            buttonArray[x].setText(pageNameTmp.get(x));
            RelativeLayout.LayoutParams buttonLayout = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT);
            YPosition = Math.sin(currentA)*Radius ;
            XPosition = Math.cos(currentA)*Radius ;
            buttonLayout.leftMargin = cenX + (int)XPosition ;
            buttonLayout.topMargin = cenY + (int)YPosition ;
            currentA = currentA + angle ;

            buttonArray[x].setLayoutParams(buttonLayout);

            page.addView(buttonArray[x], buttonLayout);
            final String pageNameTmpT = pageNameTmp.get(x);
            final String prjNameT = prjName.get(x);
            final String catNameT = catName.get(x);
            buttonArray[x].setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {

                    Intent intent = new Intent(getApplicationContext(), PageActivity.class);
                    System.out.println("page we're going to: " + pageNameTmpT);
                    intent.putExtra(PageActivity.INTENT_PAGE_NAME, pageNameTmpT);
                    intent.putExtra(PageActivity.INTENT_PROJECT_NAME, prjNameT);
                    intent.putExtra(PageActivity.INTENT_CATEGORY_NAME, catNameT);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    getBaseContext().startActivity(intent);
                }
            });
        }
        final Button[] buttonA = buttonArray ;
        final int pSize = phrases.size() ;
        page.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if( createdB == 0 ) {
                    for ( int x = 0 ; x < pSize ; x++) {
                        RelativeLayout.LayoutParams buttonLayout = (RelativeLayout.LayoutParams)buttonA[x].getLayoutParams();
                        buttonLayout.leftMargin = buttonLayout.leftMargin - buttonA[x].getWidth() / 2
                                + cen.getWidth()/2;
                        buttonLayout.topMargin = buttonLayout.topMargin - buttonA[x].getHeight() / 2
                                + cen.getHeight()/2;
                        buttonA[x].setLayoutParams(buttonLayout);
                    }

                    createdB = 1 ;


                }
                //page.addView(cen, cenL);
                //System.out.println("AfterLoop");

            }
        });
        //page.addView(cen, cenL);


    }
}