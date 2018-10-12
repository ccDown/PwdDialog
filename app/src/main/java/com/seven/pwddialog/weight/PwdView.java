package com.seven.pwddialog.weight;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorRes;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.seven.pwddialog.R;

import java.util.ArrayList;

/**
 * @author kuan
 * Created on 2018/9/27.
 * @description 密码框
 */
public class PwdView extends android.support.v7.widget.AppCompatTextView implements TextWatcher, View.OnClickListener {

    private String TAG = "PwdView";

    private Context mContext;
    /**
     * 输入的密码
     */
    private ArrayList<String> pwdList;
    /**
     * 密码的个数
     */
    private int pwdCount;
    /**
     * 密码边框颜色
     */
    @ColorRes
    private int frameColor;
    /**
     * 密码框宽度
     */
    private float frameWidth;
    /**
     * 密码填充颜色
     */
    @ColorRes
    private int pwdColor;
    /**
     * 密码填充图片
     */
    private Drawable pwdBackground;

    private int size;

    /**
     * 外面的圆角矩形
     */
    private RectF mRoundRect;
    /**
     * 圆角矩形的圆角程度
     */
    private int mRoundRadius;
    /**
     * 边框画笔
     */
    private Paint mBorderPaint;
    //掩盖点的画笔
    private Paint mDotPaint;
    //画笔颜色
    private int mDotColor;


    public PwdView(Context context) {
        super(context);
        mContext = context;
        initView(context, null);
        initEvent();
    }

    public PwdView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initView(context, attrs);
        initEvent();
    }

    public PwdView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initView(context, attrs);
        initEvent();

    }

    private void initEvent() {
        setTextWatcher(textWatcher);
        setOnClickListener(onClickListener);
    }

    @SuppressLint("ResourceAsColor")
    private void initView(Context context, @Nullable AttributeSet attrs) {

        //隐藏光标
        setCursorVisible(false);

        final float dp = getResources().getDisplayMetrics().density;
        this.setFocusable(true);
        this.setFocusableInTouchMode(true);
        if (attrs == null) {
            frameColor = context.getResources().getColor((R.color.pwdBlue));
            frameWidth = 2;
            pwdBackground = context.getResources().getDrawable(R.drawable.ic_launcher_background);
            pwdCount = 6;
            mDotColor = Color.BLACK;
            //设置字体颜色
            pwdColor = Color.WHITE;
        } else {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.PwdView);
            frameColor = typedArray.getColor(R.styleable.PwdView_frameColor, R.color.pwdBlue);
            frameWidth = typedArray.getDimension(R.styleable.PwdView_frameWidth, 3);
            pwdBackground = typedArray.getDrawable(R.styleable.PwdView_pwdBackground);
            pwdCount = typedArray.getInt(R.styleable.PwdView_pwdCount, 6);
            mDotColor = typedArray.getColor(R.styleable.PwdView_pwdDotColor, Color.BLACK);
            pwdColor = typedArray.getColor(R.styleable.PwdView_pwdTextColor,Color.WHITE);
            typedArray.recycle();
        }

        //最大输入数据长度
        setFilters(new InputFilter[]{new InputFilter.LengthFilter(pwdCount)});

        //默认30dp一格
        size = (int) (dp * 42);
        mBorderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBorderPaint.setStrokeWidth(frameWidth);
        mBorderPaint.setStyle(Paint.Style.STROKE);
        mBorderPaint.setColor(frameColor);

        mRoundRect = new RectF();
        mRoundRadius = (int) (5 * dp);

        mDotPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mDotPaint.setStrokeWidth(3);
        mDotPaint.setStyle(Paint.Style.FILL);
        mDotPaint.setColor(mDotColor);

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
                //宽度=高*数量
                w = h * pwdCount;
                size = h;
            } else {
                //两个都不知道,默认宽高
                w = size * pwdCount;
                h = size;
            }
        } else {//宽度已知
            if (h == -1) {
                //高度不知道
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

        int paddingLeft = getPaddingLeft();
        int paddingRight = getPaddingRight();
        int paddingTop = getPaddingTop();
        int paddingBottom = getPaddingBottom();

        final int width = getWidth() - paddingLeft - paddingRight;
        final int height = getHeight() - paddingTop - paddingBottom;
        //圆角矩形
        mRoundRect.set(paddingLeft, paddingTop, width + paddingLeft, height + paddingTop);
        canvas.drawRoundRect(mRoundRect, mRoundRadius, mRoundRadius, mBorderPaint);

        //格子的竖线
        mBorderPaint.setStrokeWidth(frameWidth / 2);
        for (int i = 1; i < pwdCount; i++) {
            final int x = i * size + paddingLeft;
            canvas.drawLine(x, 0, x, height + paddingTop, mBorderPaint);
        }
        //圆圈
        //半径
        int dotRadius = size / 8;
        for (int i = 0; i < pwdList.size(); i++) {
            //在格子中间开始绘制
            float x = (float) (size * (i + 0.5)) + paddingLeft;
            float y = size / 2 + paddingTop;
            canvas.drawCircle(x, y, dotRadius, mDotPaint);
        }
    }


    @Override
    protected void onFocusChanged(boolean gainFocus, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(gainFocus, direction, previouslyFocusedRect);
        if (gainFocus) {
            //调用键盘
            showOrHideKeyBoardView(true);
        } else {
            //隐藏键盘
            showOrHideKeyBoardView(false);
        }
    }

    public void showOrHideKeyBoardView(boolean isShow) {
        InputMethodManager inputMethodManager = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (isShow) {
            inputMethodManager.showSoftInput(this, 0);
        } else {
            inputMethodManager.hideSoftInputFromWindow(this.getWindowToken(), 0);
        }
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

    public String getPwd() {
        StringBuilder stringBuilder = new StringBuilder();
        for (String s : pwdList) {
            stringBuilder.append(s);
        }
        return stringBuilder.toString();
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

    //region  监听输入事件

    private TextWatcher textWatcher;

    public void setTextWatcher(TextWatcher textWatcher) {
        this.textWatcher = textWatcher;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        if (textWatcher != null) {
            textWatcher.beforeTextChanged(s, start, count, after);
        }

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (textWatcher != null) {
            textWatcher.onTextChanged(s, start, before, count);
        }

        pwdList = new ArrayList<>();
        for (int i = 0; i < s.length(); i++) {
            pwdList.add(String.valueOf(s.charAt(i)));
            invalidate();
        }

        Log.e(TAG, "input " + s.toString());

        if (s.toString().length() == pwdCount) {
            Log.e(TAG, "onTextChanged: inputDone>>>>>" + s);
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        float currentX = event.getX();
        float currentY = event.getY();
        Log.e(TAG,"currentX>>>>>>>>"+currentX);
        Log.e(TAG,"currentY>>>>>>>>"+currentY);

        return super.onTouchEvent(event);
    }

    @Override
    public void afterTextChanged(Editable s) {
        if (textWatcher != null) {
            textWatcher.afterTextChanged(s);
        }
    }

    private OnClickListener onClickListener;

    public void setPwdOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    @Override
    public void onClick(View v) {
        //点击控件弹出输入键盘
        requestFocus();
        showOrHideKeyBoardView(true);
        onClickListener.onClick(v);
    }

//endregion

}
