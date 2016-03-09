package net.atlassian.teammyrec.writersbloc;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.support.v4.content.ContextCompat;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import net.atlassian.teammyrec.writersbloc.Models.DataModels.Category;

/**
 * TODO: document your custom view class.
 */
public class GridCustomView extends View {

    private int XP = 0;
    private int YP = 0;

    private String n;
    private float nWidth;
    private float nHeight;
    private GradientDrawable nDrawable;
    private TextPaint nPaint;
    private int nColor ;
    private float nD ;
    private String p;
    private Category c ;

    private OnToggledListener listen;
    private boolean dostuff;


    public interface OnToggledListener
    {
        void OnToggled(GridCustomView view, boolean stuff , String pageName , String pagePath , Category cate);
    }

    public GridCustomView(Context context, int x, int y, String name , String path, Category cate , float Dim) {
        super(context);
        XP = x;
        YP = y;
        nPaint = new TextPaint();
        setName(name);
        p = path;
        setnD(Dim);
        setColor(Color.BLACK);
        dostuff = true;
        c = cate ;
        init();

    }

    public int getXP(){ return XP ; }
    public int getYP(){ return YP ; }

    public GridCustomView(Context context) {
        super(context);
        init();
    }

    public GridCustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public GridCustomView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
    }
    private boolean clicked;
    @Override
    public boolean onTouchEvent(MotionEvent e)
    {
        super.onTouchEvent(e);
        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:


                invalidate();
                if (listen != null) {
                    listen.OnToggled(this, dostuff , n , p , c);
                }
                clicked = true;
                return true;
            case MotionEvent.ACTION_UP:
                if (clicked) {
                    clicked = false;
                    performClick();
                    return true;
                }
        }
        return false;

    }

    @Override
    public boolean performClick()
    {
        super.performClick();
        return true ;
    }

    public void setOnToggledListener( OnToggledListener liste)
    {
        listen = liste;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if( n.equals("") )
        {
            dostuff= false;
            //canvas.drawColor(ContextCompat.getColor(this.getContext(), R.color.listBackgroundColor));
        }
        else
        {


            canvas.drawColor(ContextCompat.getColor(this.getContext(), R.color.GridButton));
        }

        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int paddingRight = getPaddingRight();
        int paddingBottom = getPaddingBottom();

        int contentWidth = getWidth() - paddingLeft - paddingRight;
        int contentHeight = getHeight() - paddingTop - paddingBottom;


        canvas.drawText(n,
                paddingLeft + (contentWidth - nWidth) / 2,
                paddingTop + (contentHeight + nHeight) / 2,
                nPaint);



        /*nDrawable.draw(canvas);
        if (nDrawable != null) {
            nDrawable.setBounds(paddingLeft, paddingTop,
                    paddingLeft + contentWidth, paddingTop + contentHeight);
            nDrawable.draw(canvas);
        }*/
    }

    public String getName() {
        return n;
    }

    public void setName(String name) {
        n = name;
        invalidateTextPaintAndMeasurements();
    }


    public int getColor() {
        return nColor;
    }


    public void setColor(int Color) {
        nColor = Color;
        invalidateTextPaintAndMeasurements();
    }

    public float getDimension() {
        return nD;
    }


    public void setnD(float D) {
        nD = D;
        invalidateTextPaintAndMeasurements();
    }


    public Drawable getDrawable() {
        return nDrawable;
    }

    public void setDrawable(GradientDrawable Draw) {
        nDrawable = Draw;
    }

    private void invalidateTextPaintAndMeasurements() {
        nPaint.setTextSize(nD);
        nPaint.setColor(nColor);
        nWidth = nPaint.measureText(n);

        Paint.FontMetrics fontMetrics = nPaint.getFontMetrics();
        nHeight = fontMetrics.bottom;
    }

}