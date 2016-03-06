package net.atlassian.teammyrec.writersbloc;

import android.content.Intent;
import android.graphics.pdf.PdfDocument;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import net.atlassian.teammyrec.writersbloc.Models.DataModels.Project;
import net.atlassian.teammyrec.writersbloc.Models.DataModels.Page;

import java.util.ArrayList;


public class GridActivity extends AppCompatActivity implements GridCustomView.OnToggledListener
//implements View.OnClickListener
{

    public static final String PROJECT_INTENT = "Project_Name";

    private GridLayout g;
    private int fillup ;
    private final int minColC = 5;
    private final int minRowC = 5;
    private final int colC = 5;
    private int maxSize = 0;
    private final ArrayList<String> pageNames = new ArrayList<>();
    private final ArrayList<Page> pages = new ArrayList<>();
    private int rowC = 0 ;
    private GridCustomView[] pV;//paininmyassView

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid);

        String projectName = getIntent().getStringExtra(PROJECT_INTENT);
        if(projectName != null){
            try {
                Project project = new Project(this, projectName);
                pageNames.clear();
                pages.clear();
                for(Page p: project.getAllPages()){
                    pageNames.add(p.toString());
                    pages.add(p);
                }
            }catch (Exception e){}
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
                        tempView = new GridCustomView(this, x, y, pageNames.get(x + y * colC));
                    }
                    else
                    {
                        tempView = new GridCustomView(this, x ,y, "");
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

            }
        });
        /*
        Button[] buttonArray = new Button[maxSize] ;
        for( int x = 0; x < maxSize ; x++)
        {
            buttonArray[x] = new Button(this);
            buttonArray[x].setText(t[x]);
            RelativeLayout.LayoutParams buttonL = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT);

            buttonArray[x].setLayoutParams( buttonL);
            g.addView(buttonArray[x]);

        }*/
    }

    @Override
    public void OnToggled(GridCustomView view, boolean stuff , String pageName)
    {
        toGridGraph(view , pageName);
    }
    public void toGridGraph(View v , String pageName) {
        Intent intent = new Intent(this, GraphActivity.class);
        intent.putExtra(GraphActivity.PAGE_INTENT,pageName);
        this.startActivity(intent);
    }
    /*
    private String[] sortbyA(String [] i) {
        String[] b = new String[i.length];
        for (int y = 0; y < i.length; y++)
        {
            String temp = i[0];
            for (int x = 0; x < i.length; x++)
            {

            }
        }
        return i;
    }*/

}
