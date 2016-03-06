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
    public DrawLine(Context context){
        super(context);

    }
    @Override
    public void onDraw(Canvas canvas)
    {
        //canvas.drawLine();
    }
}
