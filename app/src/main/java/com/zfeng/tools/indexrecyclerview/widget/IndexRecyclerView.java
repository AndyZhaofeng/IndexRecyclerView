package com.zfeng.tools.indexrecyclerview.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import java.util.ArrayList;

/**
 * Created by zhaofeng on 16/6/21.
 */
public class IndexRecyclerView extends RecyclerView
{
    private IndexView indexView;
    private boolean ifShowLetter=false;
    private int currentLetterCount=0;
    private ChineseUtils chineseUtils;

    private ArrayList<String> lists=null;

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
        chineseUtils=new ChineseUtils();
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
//        RectF rectIndex=indexView.getRectIndex();
        int count=e.getPointerCount();
        if(count==1)
        {
            switch (e.getAction())
            {
                case MotionEvent.ACTION_DOWN:
                case MotionEvent.ACTION_MOVE:
                case MotionEvent.ACTION_UP:

                    if(checkOk(e))
                    {
                        return true;
                    }
                    break;
            }

        }
        return super.onInterceptTouchEvent(e);
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                if (checkOk(e)) {
                    float height = e.getY() - indexView.getRectIndex().top;
                    int count = (int) Math.ceil(height / indexView.getInitStep());
                    if (count > 0 && count < indexView.LetterList.length() + 1) {
                        ifShowLetter = true;
                        currentLetterCount = count - 1;
//                    Log.d("IndexRecycler","count="+count+"  letter = "+indexView.LetterList.charAt(currentLetterCount));
                        invalidate();
                        smoothScrollToLetter(count);
                        return true;
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                if(checkOk(e))
                {
                    ifShowLetter = false;
                    invalidate();
                    return true;
                }
                break;
        }

        return super.onTouchEvent(e);
    }

    private void smoothScrollToLetter(int count)
    {
        if(lists!=null&&lists.size()>0)
        {
            char letter=IndexView.LetterList.charAt(currentLetterCount);
            int thisLetter=0;
            for(int i=0;i<lists.size();++i)
            {
                String firstLetter=chineseUtils.getFirstLetter(lists.get(i)).toUpperCase();

                Log.d("IndexRecycler","firstLetter="+firstLetter+"  letter+"+letter);
                if(firstLetter.equals(letter+""))
                {
                    thisLetter=i;
                    Log.d("IndexRecycler","firstLetter="+firstLetter+"  thisLetter="+i+"  letter+"+letter);
                }
                /*if(chineseUtils.isChinese(lists.get(i).toUpperCase().charAt(0)))
                {
                }else{
                    if(lists.get(i).toUpperCase().charAt(0)==letter)
                    {
//                    Log.d("IndexRecycler","letter="+lists.get(i)+" upper="+upper+"  thisLetter="+i);
                        thisLetter=i;
                    }
                }*/
            }
            this.smoothScrollToPosition(thisLetter);
        }else{
            Log.e(this.getClass().toString(),"You must put a list in this viewgroup!");
        }
    }

    private boolean checkOk(MotionEvent e)
    {
        RectF rectIndex=indexView.getRectIndex();
        if (e.getX() > rectIndex.left
                && e.getY() > rectIndex.top && e.getY() < rectIndex.bottom) {
            return true;
        }
        return false;
    }
    @Override
    protected void dispatchDraw(Canvas canvas) {
        indexView.dispatchDraw(canvas,ifShowLetter,currentLetterCount);
        super.dispatchDraw(canvas);
    }
    public void setLists(ArrayList<String> lists)
    {
        this.lists=lists;
    }
}
