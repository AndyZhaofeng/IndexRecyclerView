package com.zfeng.tools.indexrecyclerview.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

/**
 * Created by zhaofeng on 16/6/21.
 */
public class IndexView {

    public static final String LetterList="ABCDEFGHIJKLMNOPQRSTUVWXYZ#";
    private static final int base_integer=20;
    private static final int base_textSize=12;
    private static final int base_textShadow=40;
    private static final int base_alpha=120;
    private static final int HIDE_LETTER=1001;
    private Context context;
    private RecyclerView recyclerView;
    private float mDensity;             //当前屏幕密度除以160
    private float mScaledDensity;       //当前屏幕密度除以160（设置字体的尺寸）
    private int mAlphaRate;           //透明度
    private float shadowWidth;
    private float shadowTextSize;

    private int mRecyclerViewWidth;
    private int mRecyclerViewHeight;
    private RectF mIndexbarRect;
    private int mIndexbarWidth;
    private int mIndexbarHeight;
    private int mIndexbarMarginTop;
    private int mRecyclerViewMarginRight;
    private float initStep;
    Paint indexbarPaint;
    Paint letterPaint;
    Paint showLetterPaint;

    Handler handler=new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {

            if(msg.what==HIDE_LETTER)
            {
                /*if(mAlphaRate>0)
                {
                    mAlphaRate-=2;
                    recyclerView.invalidate();
                    handler.sendEmptyMessageDelayed(HIDE_LETTER,100);
                }else{
                    mAlphaRate=base_alpha;
                }*/
            }
            return false;
        }
    });

    public IndexView(Context context,RecyclerView recyclerView)
    {
        this.context=context;
        this.recyclerView=recyclerView;
        mDensity=context.getResources().getDisplayMetrics().density;
        mScaledDensity=context.getResources().getDisplayMetrics().scaledDensity;

        mIndexbarWidth=(int)(base_integer*mDensity);
        mIndexbarMarginTop=(int)(base_integer*mDensity);
        mAlphaRate=base_alpha;

        shadowTextSize=mScaledDensity*base_textShadow;
        shadowWidth=shadowTextSize*2;
        indexbarPaint=new Paint();
        letterPaint=new Paint();
        showLetterPaint=new Paint();

    }
    public void onDraw(Canvas canvas)
    {
        indexbarPaint.setColor(Color.BLACK);
        indexbarPaint.setAlpha(120);

        //绘制索引条
        canvas.drawRoundRect(mIndexbarRect,10,10,indexbarPaint);
        drawLetters(canvas);
    }
    public void onSizeChanged(int w, int h, int oldw, int oldh)
    {
        mRecyclerViewWidth=w;
        mRecyclerViewHeight=h;
        /**
         * left top right bottom
         */
        mIndexbarRect=new RectF(mRecyclerViewWidth-mIndexbarWidth,mIndexbarMarginTop/2,
                mRecyclerViewWidth,mRecyclerViewHeight-mIndexbarMarginTop/2);
        initStep=mIndexbarRect.height()/27;
    }
    private void drawLetters(Canvas canvas)
    {
        letterPaint.setColor(Color.WHITE);
        float textSize=(int)mScaledDensity*base_textSize;
        letterPaint.setTextSize(textSize);

        float initTop=mIndexbarRect.top+textSize;
        float initLeft=mIndexbarRect.left;
        float letterTopMargin=2*mDensity;

        for(int i=0;i<LetterList.length();++i)
        {
            Rect rect=new Rect();
            letterPaint.getTextBounds(LetterList.charAt(i)+"",0,1,rect);
            float margin=mIndexbarWidth-rect.width();
            canvas.drawText(LetterList.charAt(i)+"",initLeft+margin/2,initTop+letterTopMargin,letterPaint);

            initTop+=initStep;
        }
    }
    public void dispatchDraw(final Canvas canvas,boolean ifShowLetter,int currentLetterCount)
    {
        float left=mRecyclerViewWidth/2-shadowWidth/2;
        float top=mRecyclerViewHeight/2-shadowWidth;
        float right=mRecyclerViewWidth/2+shadowWidth/2;;
        float bottom=mRecyclerViewHeight/2;

        RectF shadow=new RectF(left,top,right,bottom);
        drawShadow(ifShowLetter,shadow,canvas);
        if(ifShowLetter)
        {
            showLetterPaint.setTextSize(shadowTextSize);
            showLetterPaint.setColor(Color.WHITE);

            Rect rect=new Rect();
            showLetterPaint.getTextBounds(IndexView.LetterList.charAt(currentLetterCount)+"",0,1,rect);
//            showLetterPaint.setAlpha((int)(255*mAlphaRate));

            String showText=LetterList.charAt(currentLetterCount)+"";
            float textWidth=showLetterPaint.measureText(showText);
            float textHeight=showLetterPaint.descent()-showLetterPaint.ascent();
            float leftText=left+shadowWidth/2-textWidth/2;
            float topText=top+shadowWidth/2+textHeight/3;
            canvas.drawText(showText,leftText,topText,showLetterPaint);

//            handler.sendEmptyMessage(IndexView.HIDE_LETTER);
            /*handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    float left=mRecyclerViewWidth/2-shadowWidth/2;
                    float top=mRecyclerViewHeight/2-shadowWidth;
                    float right=mRecyclerViewWidth/2+shadowWidth/2;;
                    float bottom=mRecyclerViewHeight/2;

                    Paint whiteShadow=new Paint();
                    whiteShadow.setColor(Color.WHITE);
                    whiteShadow.setAlpha(120);
                    RectF shadow=new RectF(left,top,right,bottom);
                    canvas.drawRoundRect(shadow,10*mDensity,10*mDensity,whiteShadow);
                    recyclerView.invalidate();

                    Log.d("IndexRecycler","postDelayed");
                }
            },2000);*/

        }
    }
    private void drawShadow(boolean colorBool,RectF shadow,Canvas canvas)
    {
        Paint blackShadow=new Paint();
        blackShadow.setColor(Color.BLACK);
        if(colorBool){
            mAlphaRate=base_alpha;
        }else{
            mAlphaRate=0;
        }
        blackShadow.setAlpha(mAlphaRate);
        canvas.drawRoundRect(shadow,10*mDensity,10*mDensity,blackShadow);
    }
    public RectF getRectIndex()
    {
        if(mIndexbarRect!=null){
            return mIndexbarRect;
        }
        return null;
    }
    public float getInitStep()
    {
        return initStep;
    }
    public float getScaledDensity()
    {
        return mScaledDensity;
    }
}
