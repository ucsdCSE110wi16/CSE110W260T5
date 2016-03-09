package net.atlassian.teammyrec.writersbloc;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import java.util.jar.Attributes;

/**
 * Created by giga on 2/24/16.
 */

public class DrawLine extends View{
    Paint paint = new Paint();
    int xf;
    int xt;
    int yf;
    int yt;
    public DrawLine(Context context , int x0 , int x1, int y0, int y1){
        super(context);
        xf = x0;
        xt = x1;
        yf = y0;
        yt = y1;
    }
    @Override
    public void onDraw(Canvas canvas)
    {
        canvas.drawLine(xf,yf,xt,yt,paint);
    }
    public void update( int x0, int x1, int y0, int y1)
    {
        xf = x0;
        xt = x1;
        yf = y0;
        yt = y1;
    }
}
