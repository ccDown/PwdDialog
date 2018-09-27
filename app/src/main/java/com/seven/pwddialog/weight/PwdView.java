package com.seven.pwddialog.weight;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorRes;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.seven.pwddialog.R;

import java.util.ArrayList;

/**
 * @author kuan
 * Created on 2018/9/27.
 * @description 密码框
 */
public class PwdView extends View {


    /**输入的密码*/
    private ArrayList pwdList = new ArrayList();
    /**密码的个数*/
    private int pwdCount;
    /**密码边框颜色*/
    @ColorRes
    private int frameColor;
    /**密码框宽度*/
    private float frameWidth;
    /**密码填充颜色*/
    @ColorRes
    private int pwdColor;
    /**密码填充图片*/
    private Drawable pwdBackground;

    private int size;

    /**外面的圆角矩形*/
    private RectF mRoundRect;
    /**圆角矩形的圆角程度*/
    private int mRoundRadius;
    /**边界画笔*/
    private Paint mBorderPaint;


    public PwdView(Context context) {
        super(context);
        initView(context,null);
    }

    public PwdView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context,attrs);
    }

    public PwdView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context,attrs);
    }

    @SuppressLint("ResourceAsColor")
    private void initView(Context context, @Nullable AttributeSet attrs) {
        final float dp = getResources().getDisplayMetrics().density;
        this.setFocusable(true);
        this.setFocusableInTouchMode(true);
        if (attrs == null){
            frameColor = context.getResources().getColor((R.color.pwdBlue));
            frameWidth = 3;
            pwdBackground = context.getResources().getDrawable(R.drawable.ic_launcher_background);
            pwdCount = 6;

        } else {
            TypedArray typedArray = context.obtainStyledAttributes(attrs,R.styleable.PwdView);
            frameColor = typedArray.getColor(R.styleable.PwdView_frameColor,R.color.pwdBlue);
            frameWidth = typedArray.getDimension(R.styleable.PwdView_frameWidth,3);
            pwdBackground = typedArray.getDrawable(R.styleable.PwdView_pwdBackground);
            pwdCount = typedArray.getInt(R.styleable.PwdView_pwdCount,6);

            typedArray.recycle();
        }

        size = (int) (dp * 30);//默认30dp一格
        mBorderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBorderPaint.setStrokeWidth(frameWidth);
        mBorderPaint.setStyle(Paint.Style.STROKE);
        mBorderPaint.setColor(frameColor);
        mRoundRect = new RectF();
        mRoundRadius = (int) (5 * dp);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int w = measureWidth(widthMeasureSpec);
        int h = measureHeight(heightMeasureSpec);
        int wsize = MeasureSpec.getSize(widthMeasureSpec);
        int hsize = MeasureSpec.getSize(heightMeasureSpec);
        //宽度没指定,但高度指定
        if (w == -1) {
            if (h != -1) {
                w = h * pwdCount;//宽度=高*数量
                size = h;
            } else {//两个都不知道,默认宽高
                w = size * pwdCount;
                h = size;
            }
        } else {//宽度已知
            if (h == -1) {//高度不知道
                h = w / pwdCount;
                size = h;
            }
        }
        setMeasuredDimension(Math.min(w, wsize), Math.min(h, hsize));
    }

    private int measureWidth(int widthMeasureSpec) {
        //宽度
        int wmode = MeasureSpec.getMode(widthMeasureSpec);
        int wsize = MeasureSpec.getSize(widthMeasureSpec);
        if (wmode == MeasureSpec.AT_MOST) {
            //wrap_content
            return -1;
        }
        return wsize;
    }

    private int measureHeight(int heightMeasureSpec) {
        //高度
        int hmode = MeasureSpec.getMode(heightMeasureSpec);
        int hsize = MeasureSpec.getSize(heightMeasureSpec);
        if (hmode == MeasureSpec.AT_MOST) {
            //wrap_content
            return -1;
        }
        return hsize;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        
    }

    //region  getter And setter

    public PwdView setFrameColor(int frameColor) {
        this.frameColor = frameColor;
        return this;
    }

    public PwdView setFrameWidth(float frameWidth) {
        this.frameWidth = frameWidth;
        return this;
    }

    public PwdView setPwdBackground(Drawable pwdBackground) {
        this.pwdBackground = pwdBackground;
        return this;
    }

    public PwdView setPwdColor(int pwdColor) {
        this.pwdColor = pwdColor;
        return this;
    }

    public PwdView setPwdCount(int pwdCount) {
        this.pwdCount = pwdCount;
        return this;
    }

    public PwdView setPwdList(ArrayList pwdList) {
        this.pwdList = pwdList;
        return this;
    }

    public ArrayList getPwdList() {
        return pwdList;
    }

    public Drawable getPwdBackground() {
        return pwdBackground;
    }

    public int getFrameColor() {
        return frameColor;
    }

    public float getFrameWidth() {
        return frameWidth;
    }

    public int getPwdColor() {
        return pwdColor;
    }

    public int getPwdCount() {
        return pwdCount;
    }

    //endregion

}
