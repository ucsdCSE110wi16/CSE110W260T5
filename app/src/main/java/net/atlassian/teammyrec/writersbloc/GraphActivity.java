package net.atlassian.teammyrec.writersbloc;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Display;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import net.atlassian.teammyrec.writersbloc.R;
public class GraphActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);
        createGraph();
    }
    private void createGraph()
    {
        //update code to get value from category page later;
        String name = new String();
        int numOfConnect = 8;//number of connection
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
        //conName[8] = "Ran9";
        //conName[9] = "Ran10";
        //conName[10] = "Ran11";
        //conName[11] = "Ran12";
        name = "Cen";
        //Math and constant
        double degree = 360;
        double pie = 3.141592653589793;
        int MAX_NUM_OF_CONNECT = 12 ;
        int STRING_CHAR_LIMIT = 8;
        double XPosition = 0;
        double YPosition = 0;
        int RADIUS = 300 ;
        int cenX = 0;
        int cenY = 0;
        double angle = (2 * pie) / numOfConnect ;
        double STARTINGA = pie/2;
        double currentA = STARTINGA ;

        //Display display = getWindowManager().getDefaultDisplay();
        //int width = display.getWidth();
        //int height = display.getHeight();

        Button cen = new Button(this);
        cen.setText(name);
        RelativeLayout page = (RelativeLayout)findViewById(R.id.GraphPage);
        RelativeLayout.LayoutParams buttonLayout = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        //buttonLayout.addRule(RelativeLayout.CENTER_IN_PARENT);
        cen.setLayoutParams(buttonLayout);
        //cen.setBackgroundResource(R.drawable.circlebutton);
        //buttonLayout.leftMargin = (page.getWidth()/2) - (RelativeLayout.LayoutParams.WRAP_CONTENT)/2 ;
        //buttonLayout.topMargin = (page.getHeight()/2) - (RelativeLayout.LayoutParams.WRAP_CONTENT)/2 ;
        //Work on value later
        buttonLayout.leftMargin = 350 ;
        buttonLayout.topMargin = 600 ;
        cenX = buttonLayout.leftMargin;
        cenY = buttonLayout.topMargin;
        //cenX = cenX - (RelativeLayout.LayoutParams.WRAP_CONTENT/2) ;
        //cenY = cenY - (RelativeLayout.LayoutParams.WRAP_CONTENT/2) ;
        //YPosition = YPosition + RADIUS ;
        page.addView(cen, buttonLayout);
        //XPosition = cenX - RelativeLayout.LayoutParams.WRAP_CONTENT;
        //The other buttons
        Button[] buttonArray = new Button[numOfConnect] ;
        for( int x = 0; x < numOfConnect ; x++ ) {
            buttonArray[x] = new Button(this);
            buttonArray[x].setText(conName[x]);
            buttonLayout = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT);
            YPosition = cenY - Math.sin(currentA)*RADIUS ;
            XPosition = cenX - Math.cos(currentA)*RADIUS ;
            currentA = currentA + angle ;
            //buttonLayout.addRule(RelativeLayout.CENTER_IN_PARENT);
            buttonLayout.leftMargin = (int)XPosition;
            buttonLayout.topMargin = (int)YPosition;
            //buttonArray[x].setBackgroundResource(R.drawable.circlebutton);
            buttonArray[x].setLayoutParams(buttonLayout);
            page.addView(buttonArray[x], buttonLayout);
        }
    }
}
