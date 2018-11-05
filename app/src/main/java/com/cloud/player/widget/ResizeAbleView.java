package com.cloud.player.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.cloud.player.R;

/**
 * 根据原始的宽高信息,结合新的比例信息
 *
 * @author jian
 */
public class ResizeAbleView extends FrameLayout {
    private float whRate = 0f;
    public static final int RESIZE_HORIZONTAL = 0;
    public static final int RESIZE_VERTICAL = 1;

    @IntDef({RESIZE_HORIZONTAL, RESIZE_VERTICAL})
    public @interface ResizeOrientation {
    }

    @ResizeOrientation
    private int resizeOrientation = RESIZE_HORIZONTAL;

    public ResizeAbleView(@NonNull Context context) {
        super(context);
    }

    public ResizeAbleView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initParams(context, attrs, 0, 0);
    }

    public ResizeAbleView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initParams(context, attrs, defStyleAttr, 0);
    }

    public ResizeAbleView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initParams(context, attrs, defStyleAttr, defStyleRes);
    }

    private void initParams(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        final TypedArray a = context.obtainStyledAttributes(
                attrs, R.styleable.ResizeAbleView, defStyleAttr, defStyleRes);

        resizeOrientation = a.getInt(R.styleable.ResizeAbleView_resizeOrientation, -1);

        a.recycle();
    }

    public void setResizeOrientation(@ResizeOrientation int orientation) {
        this.resizeOrientation = orientation;
    }

    /**
     * scaleOrientation属性决定哪个方向可以被调整
     */
    public void setWidthHeightRate(float whRate) {
        final float ignoreValue = 0f;
        if (whRate <= ignoreValue) {
            return;
        }
        this.whRate = whRate;

        // 比例相同时,无需调整
        try {
            if (whRate == getMeasuredWidth() / (float) getMeasuredHeight()) {
                return;
            }
        } catch (Exception ignore) {
            return;
        }

        final ViewGroup.LayoutParams layoutParams = getLayoutParams();
        if (resizeOrientation == RESIZE_VERTICAL) {
            // 高度可变
            int desiredHeight = (int) (getMeasuredWidth() / whRate);
            layoutParams.height = Math.max(desiredHeight, getSuggestedMinimumHeight());
        } else {
            // 宽度可变
            int desiredWidth = (int) (getMeasuredHeight() * whRate);
            layoutParams.width = Math.max(desiredWidth, getSuggestedMinimumWidth());
        }
        setLayoutParams(layoutParams);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        final float ignoreValue = 0f;
        if (whRate > ignoreValue) {
            if (resizeOrientation == RESIZE_VERTICAL) {
                // 高度可变
                int w = MeasureSpec.getSize(widthMeasureSpec);
                int desiredHeight = (int) (w / whRate);
                heightMeasureSpec = MeasureSpec.makeMeasureSpec(desiredHeight, MeasureSpec.getMode(heightMeasureSpec));
            } else {
                // 宽度可变
                int h = getMeasuredHeight();
                int desiredWidth = (int) (h * whRate);
                widthMeasureSpec = MeasureSpec.makeMeasureSpec(desiredWidth, MeasureSpec.getMode(widthMeasureSpec));
            }
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}