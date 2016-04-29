package com.xinran.testviewgroup;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * 一个MeasureSpec封装了父布局传递给子布局的布局要求，每个MeasureSpec代表了一组宽度和高度的要求。
 * 一个MeasureSpec由大小和模式组成。它有三种模式：UNSPECIFIED(未指定),父元素部队自元素施加任何束缚，
 * 子元素可以得到任意想要的大小；EXACTLY(完全)，父元素决定自元素的确切大小，子元素将被限定在给定的边界里而忽略它本身大小；
 * AT_MOST(至多)，子元素至多达到指定大小的值。
 * <p/>
 * 　　它常用的三个函数：
 * 　　1.static int getMode(int measureSpec):根据提供的测量值(格式)提取模式(上述三个模式之一)
 * 　　2.static int getSize(int measureSpec):根据提供的测量值(格式)提取大小值(这个大小也就是我们通常所说的大小)
 * 　　3.static int makeMeasureSpec(int size,int mode):根据提供的大小值和模式创建一个测量值(格式)
 * Created by qixinh on 16/4/29.
 */
public class QxCustomViewGroup extends ViewGroup {

    public QxCustomViewGroup(Context context) {
        super(context);
    }

    public QxCustomViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public QxCustomViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimensionCustom(widthMode, widthSize, heightMode, heightSize);
    }

    private void setMeasuredDimensionCustom(int widthMode, int widthSize, int heightMode, int heightSize) {

        if (widthMode == MeasureSpec.UNSPECIFIED && heightMode == MeasureSpec.UNSPECIFIED) {
            setMeasuredDimension(widthSize, heightSize);
        } else if (widthMode == MeasureSpec.UNSPECIFIED && heightMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(widthSize, 0);
        } else if (widthMode == MeasureSpec.UNSPECIFIED && heightMode == MeasureSpec.EXACTLY) {
            setMeasuredDimension(widthSize, heightSize);
        } else if (widthMode == MeasureSpec.AT_MOST && heightMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(0, 0);
        } else if (widthMode == MeasureSpec.AT_MOST && heightMode == MeasureSpec.UNSPECIFIED) {
            setMeasuredDimension(0, heightSize);
        } else if (widthMode == MeasureSpec.AT_MOST && heightMode == MeasureSpec.EXACTLY) {
            setMeasuredDimension(0, heightSize);
        } else if (widthMode == MeasureSpec.EXACTLY && heightMode == MeasureSpec.EXACTLY) {
            setMeasuredDimension(widthSize, heightSize);
        } else if (widthMode == MeasureSpec.EXACTLY && heightMode == MeasureSpec.UNSPECIFIED) {
            setMeasuredDimension(widthSize, heightSize);
        } else if (widthMode == MeasureSpec.EXACTLY && heightMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(widthSize, 0);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        int row = 0;
        int column = 0;
        int childNum = getChildCount();

        if (childNum > 0) {
            View bottomView = getChildAt(childNum - 1);
            bottomView.layout(0, height - bottomView.getMeasuredHeight(), width, height);
            for (int i = 0; i < childNum - 1; i++) {
                int childW = getChildAt(i).getMeasuredWidth();
                int childH = getChildAt(i).getMeasuredHeight();
                int childL = column * childW;
                int childR = childL + childW;
                int childT = row * childH;
                int childB = childT + childH;
                if (childB > height - bottomView.getMeasuredHeight()) {
                    column++;
                    row = 0;
                    i--;
                } else {
                    row++;
                    getChildAt(i).layout(childL, childT, childR, childB);
                }
            }


        }
    }

    /**
     * 此方法都是重写的View的方法
     *
     * @param size
     * @param measureSpec
     * @return Measure specification mode: The parent has not imposed any constraint
     * on the child. It can be whatever size it wants.
     * <p/>
     * public static final int UNSPECIFIED
     * <p/>
     * <p/>
     * Measure specification mode: The parent has determined an exact size
     * for the child. The child is going to be given those bounds regardless
     * of how big it wants to be.
     * <p/>
     * public static final int EXACTLY
     * <p/>
     * <p/>
     * Measure specification mode: The child can be as large as it wants up
     * to the specified size.
     * <p/>
     * public static final int AT_MOST
     */


    public static int resolveSize(int size, int measureSpec) {
        int result = size;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        switch (specMode) {
            case MeasureSpec.UNSPECIFIED:
                result = size;
                break;
            case MeasureSpec.AT_MOST:
                result = Math.min(size, specSize);
                break;
            case MeasureSpec.EXACTLY:
                result = specSize;
                break;
        }
        return result;
    }

    /**
     * 此方法都是重写的View的方法
     *
     * @param size
     * @param mode
     * @return
     */
    public static int makeMeasureSpec(int size, int mode) {
        return size + mode;
    }
}
