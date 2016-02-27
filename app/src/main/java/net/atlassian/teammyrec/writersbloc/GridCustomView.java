package net.atlassian.teammyrec.writersbloc;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

/**
 * TODO: document your custom view class.
 */
public class GridCustomView extends View {

    private int XP = 0;
    private int YP = 0;

    private String n;
    private float nWidth;
    private float nHeight;
    private Drawable nDrawable;
    private TextPaint nPaint;
    private int nColor = Color.BLACK;
    private float nD = 50;

    public GridCustomView(Context context, int x, int y, String name) {
        super(context);
        XP = x;
        YP = y;
        nPaint = new TextPaint();
        setName(name);
        setnD(30);
        setColor(Color.BLACK);
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

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(0x3B5998);


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


        if (nDrawable != null) {
            nDrawable.setBounds(paddingLeft, paddingTop,
                    paddingLeft + contentWidth, paddingTop + contentHeight);
            nDrawable.draw(canvas);
        }
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

    public void setDrawable(Drawable Draw) {
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