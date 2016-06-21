package com.zfeng.tools.indexrecyclerview.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

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
        indexView=new IndexView();
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
        indexView.onSizeChanged(w,h,oldw,oldh);
    }
}
