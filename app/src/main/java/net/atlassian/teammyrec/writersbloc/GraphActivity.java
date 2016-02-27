package net.atlassian.teammyrec.writersbloc;
import android.graphics.Point;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.graphics.Color;
import android.widget.Toast;
import net.atlassian.teammyrec.writersbloc.R;
public class GraphActivity extends AppCompatActivity {
    //DrawLine drawLine;
    private Button cen;
    private RelativeLayout page ;
    private RelativeLayout.LayoutParams cenL ;
    private int cenX ;
    private int cenY ;
    private final int MAX_NUM_OF_CONNECT = 12 ;
    private final int STRING_CHAR_LIMIT = 8;
    private int createdG = 0;
    private int Radius = 200;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //drawLine = new DrawLine(this);
        //setContentView(drawLine);
        setContentView(R.layout.activity_graph);
        createCenter();
        //createCenter();
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
        String name = new String();
        name = "Cen";
        cen = new Button(this);
        cen.setText(name);
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
        page.addView(cen, cenL);
    }
    private void createGraph()
    {
        //ONLY CALL ONCE
        //update code to get value from category page later;
        int numOfConnect = 10;//number of connection
        String[] conName = new String [numOfConnect];//the name of the connection
        // test case remove later
        conName[0] = "Ran1";
        conName[1] = "Ran2";
        conName[2] = "Ran3";
        conName[3] = "Ran4";
        conName[4] = "Ran5";
        conName[5] = "Ran6";
        conName[6] = "Ran7";
        conName[7] = "Ran8";
        conName[8] = "Ran9";
        conName[9] = "Ran10";
        //conName[10] = "Ran11";
        //conName[11] = "Ran12";
        //Math and constant

        double pie = 3.141592653589793;
        double XPosition = 0;
        double YPosition = 0;

        double angle = (2 * pie) / numOfConnect ;
        double STARTINGA = pie/2;
        double currentA = STARTINGA ;
        cenL.leftMargin = cenL.leftMargin - cen.getWidth()/2 ;
        cenL.topMargin = cenL.topMargin - cen.getHeight()/2 ;
        cenX = cenL.leftMargin ;
        cenY = cenL.topMargin ;
        cen.setLayoutParams(cenL);
        RelativeLayout page = (RelativeLayout)findViewById(R.id.GraphPage);
        Button[] buttonArray = new Button[numOfConnect] ;
        for( int x = 0; x < numOfConnect ; x++ ) {
            buttonArray[x] = new Button(this);
            buttonArray[x].setText(conName[x]);
            RelativeLayout.LayoutParams buttonLayout = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT);
            YPosition = Math.sin(currentA)*Radius ;
            XPosition = Math.cos(currentA)*Radius ;
            buttonLayout.leftMargin = (int)cenX + (int)XPosition ;
            buttonLayout.topMargin = (int)cenY + (int)YPosition ;
            currentA = currentA + angle ;
            buttonArray[x].setLayoutParams(buttonLayout);
            page.addView(buttonArray[x], buttonLayout);
        }
    }
}
