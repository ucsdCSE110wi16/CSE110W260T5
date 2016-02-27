package net.atlassian.teammyrec.writersbloc;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class GridActivity extends AppCompatActivity //implements View.OnClickListener
{


    private GridLayout g;
    private final int colC = 4;
    private final int maxSize = 80;
    private final int rowC = 20 ;
    private GridCustomView[] pV;//paininmyassView

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid);
        //getMaxsize/update rowC here;
        setUpGrid();
    }

    protected void setUpGrid() {
        g = (GridLayout) findViewById(R.id.grid);
        g.setColumnCount(colC);
        g.setRowCount(rowC);
        //maxSize = rowC * colC ;
        fillIn();
    }

    protected void fillIn() {

        String[] t = new String[maxSize];
        for (int x = 0; x < maxSize; x++) {
            t[x] = "None"+x;
        }
        pV = new GridCustomView[maxSize];

        for (int y = 0; y < rowC; y++) {
            for (int x = 0; x < colC; x++) {

                GridCustomView tempView = new GridCustomView(this, x, y, t[x + y * colC]);
                //tempView.setOnClickListener(this);

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

                for (int y = 0; y < rowC; y++)
                {
                    for (int x = 0; x < colC; x++)
                    {

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
    /*
    @Override
    public void OnClick(View view)
    {

    }
    */
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
    }

}
