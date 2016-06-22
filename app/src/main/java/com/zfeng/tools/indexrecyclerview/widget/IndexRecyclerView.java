package com.zfeng.tools.indexrecyclerview.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

/**
 * Created by zhaofeng on 16/6/21.
 */
public class IndexRecyclerView extends RecyclerView
{
    private IndexView indexView;

    public IndexRecyclerView(Context context) {
        super(context);
        init();
    }

    public IndexRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public IndexRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init()
    {
        indexView=new IndexView(getContext(),this);
    }

    @Override
    public void onDraw(Canvas c) {
        if(indexView!=null)
        {
            indexView.onDraw(c);
        }

        super.onDraw(c);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if(indexView!=null)
        {
            indexView.onSizeChanged(w,h,oldw,oldh);
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        RectF rectIndex=indexView.getRectIndex();
        int count=e.getPointerCount();
        if(count==1)
        {
            if(e.getX()>rectIndex.left&&e.getX()<rectIndex.right
                    &&e.getY()>rectIndex.top&&e.getY()<rectIndex.bottom)
            {
                return true;
            }
        }
        return super.onInterceptTouchEvent(e);
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        float height=e.getY()-indexView.getRectIndex().top;
        int count=(int)Math.ceil(height/indexView.getInitStep());
        if(count>0&&count<indexView.LetterList.length())
        {
            Log.d("IndexRecycler","count="+count+"  letter = "+indexView.LetterList.charAt(count-1));
            return true;
        }
        return super.onTouchEvent(e);
    }
}
