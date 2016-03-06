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
import android.widget.Button;
//import android.widget.LinearLayout;
import android.widget.RelativeLayout;
//import android.graphics.Color;
import android.widget.Toast;

//import net.atlassian.teammyrec.writersbloc.Models.DataModels.Category;
import net.atlassian.teammyrec.writersbloc.Models.DataModels.Page;
import net.atlassian.teammyrec.writersbloc.Models.DataModels.PageInformation;
import net.atlassian.teammyrec.writersbloc.Models.DataModels.Project;
//import net.atlassian.teammyrec.writersbloc.R;

import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GraphActivity extends AppCompatActivity {


    private static final String LOG_ID = "GraphActivity.net.atlassian.teammyrec.writersbloc";
    public static final String INTENT_PROJECT_NAME = "";
    //public static final String INTENT_EXTRA_PROJECT_ABSOLUTE_DIR = "Absolute_Directory";
    public static final String PAGE_INTENT = "Page_Name";
    public static final String PAGE_PATH = "Page_Path";
    private Button cen;
    private RelativeLayout page ;
    private RelativeLayout.LayoutParams cenL ;
    private int cenX ;
    private int cenY ;

    private int createdG = 0;
    private int Radius = 200;
    private String pageName ;
    private Page mPage;
    private PageInformation mPageInformation;
    private Project mProject;

    //private Category mCategory;
    //private ArrayList<Page> mPages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        pageName = getIntent().getStringExtra(PAGE_INTENT);
        String pagePath = getIntent().getStringExtra(PAGE_PATH);
        //System.out.println("Page path: " + getIntent().getStringExtra(PAGE_PATH));
        try {
            mPageInformation = mPage.getPageInformation();
            System.out.println("Project name: " + getIntent().getStringExtra(INTENT_PROJECT_NAME));
            mProject = new Project(getIntent().getStringExtra(INTENT_PROJECT_NAME), ParseController.getCurrentUser());


            //mCategory = new Category(getIntent().getStringExtra(INTENT_EXTRA_PROJECT_ABSOLUTE_DIR));
            //mPages = mCategory.getPages();

        }catch (Exception e){
            Logger.getLogger(LOG_ID).log(Level.WARNING, "Error creating page: "+e);
            e.printStackTrace();
        }



        setContentView(R.layout.activity_graph);
        createCenter();

    }
    @Override
    protected void onStart()
    {
        super.onStart();
    }
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        if(hasFocus && createdG == 0){
            createGraph();
            createdG = 1;
        }
    }
    private void createCenter()
    {

        cen = new Button(this);
        cen.setText(pageName);
        page = (RelativeLayout)findViewById(R.id.GraphPage);
        cenL = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        //cen.setOnClickListener((View.OnClickListener) this);
        Display mdisp = getWindowManager().getDefaultDisplay();
        Point mdispSize = new Point();
        mdisp.getSize(mdispSize);
        int maxX = mdispSize.x;
        int maxY = mdispSize.y;
        Radius = mdispSize.x / 3;
        cenL.leftMargin = maxX /2 ;
        cenL.topMargin = maxY /2 ;
        cenX = cenL.leftMargin;
        cenY = cenL.topMargin;
        //cen.setBackgroundColor(0xffff99);
        page.addView(cen, cenL);




        cen.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (mPage == null) {
                    Toast.makeText(view.getContext(),
                            "mPage = NULL", Toast.LENGTH_SHORT)
                            .show();
                } else {
                    String pageName = mPage.getAbsolutePath();
                    Intent intent = new Intent(getApplicationContext(), PageActivity.class);
                    intent.putExtra(PageActivity.INTENT_PAGE_NAME, pageName);
                    String projName = getIntent().getStringExtra(INTENT_PROJECT_NAME);
                    intent.putExtra(PageActivity.INTENT_PROJECT_NAME, projName);
                    startActivity(intent);
                }

            }
        });

    }
    private void createGraph()
    {

        PriorityQueue<Page> pa ;
        if( mProject == null)
        {
            //System.out.println("mProject NULL");
            return ;
        }
        //System.out.println("mProject" + mProject);
        pa = mProject.getAllPages();
        PriorityQueue<Pair<Integer, Page>> phrases =
                PhraseLinker.findPhrases(mPageInformation.getText(), pa,mPage.toString());


        final ArrayList<String> pagePat = new ArrayList<>();
        final ArrayList<String> projectName = new ArrayList<>();
        final ArrayList<String> pageN = new ArrayList<>();
        //System.out.printf("Cen Set%d\n",phrases.size());
        for(Pair<Integer, Page> p : phrases) {
            final String pagePatT = p.second.getAbsolutePath();
            final String prjNameT = getIntent().getStringExtra(INTENT_PROJECT_NAME);
            final String pageNa = p.second.toString() ;

            pagePat.add(pagePatT);
            projectName.add(prjNameT);
            pageN.add(pageNa);

        }


        double pie = 3.141592653589793;

        double XPosition ;
        double YPosition ;

        double angle = (2 * pie) / phrases.size() ;

        double currentA = pie/2 ;
        cenL.leftMargin = cenL.leftMargin - cen.getWidth()/2 ;
        cenL.topMargin = cenL.topMargin - cen.getHeight()/2 ;
        cenX = cenL.leftMargin ;
        cenY = cenL.topMargin ;
        cen.setLayoutParams(cenL);
        RelativeLayout page = (RelativeLayout)findViewById(R.id.GraphPage);
        Button[] buttonArray = new Button[phrases.size()] ;
        for( int x = 0; x < phrases.size() ; x++ ) {
            buttonArray[x] = new Button(this);
            buttonArray[x].setText(pageN.get(x));
            RelativeLayout.LayoutParams buttonLayout = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT);
            YPosition = Math.sin(currentA)*Radius ;
            XPosition = Math.cos(currentA)*Radius ;
            buttonLayout.leftMargin = cenX + (int)XPosition ;
            buttonLayout.topMargin = cenY + (int)YPosition ;
            currentA = currentA + angle ;
            buttonArray[x].setLayoutParams(buttonLayout);

            page.addView(buttonArray[x], buttonLayout);

            final String pagePatF = pagePat.get(x);
            final String projectNameF = projectName.get(x);
            buttonArray[x].setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {


                    Intent intent = new Intent(getApplicationContext(), PageActivity.class);

                    intent.putExtra(PageActivity.INTENT_PAGE_NAME, pagePatF);
                    intent.putExtra(PageActivity.INTENT_PROJECT_NAME, projectNameF);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    getBaseContext().startActivity(intent);

                }
            });
        }
    }
}
