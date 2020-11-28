package com.ooo.zcenterarcnavi;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;


public class ZCenterArcNavi extends LinearLayout {

    private Context context;

    private Path path;

    private Paint paint;

    private RectF rectF;

    private int fabId;

    private float centerRadius;

    private float cornerRadius;
    private float shadowsize;

    private int backcolor;

    public ZCenterArcNavi(Context context) {
        this(context, null);
    }

    public ZCenterArcNavi(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ZCenterArcNavi(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ZCenterArcNavi);
        fabId = typedArray.getResourceId(R.styleable.ZCenterArcNavi_zn_anchor_fab, 0);
//        centerRadius = typedArray.getDimension(R.styleable.ZCenterArcNavi_center_radius,50);

        centerRadius = typedArray.getDimensionPixelSize(R.styleable.ZCenterArcNavi_zn_center_radius, (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 50, getResources().getDisplayMetrics()));

        cornerRadius =typedArray.getDimensionPixelSize(R.styleable.ZCenterArcNavi_zn_corner_radius,(int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 5, getResources().getDisplayMetrics()));

        shadowsize = typedArray.getDimensionPixelSize(R.styleable.ZCenterArcNavi_zn_shadow_length,(int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 5, getResources().getDisplayMetrics()));

        backcolor = typedArray.getColor(R.styleable.ZCenterArcNavi_zn_back_color,0xffffffff);
        typedArray.recycle();
        init();
    }


    public void setFabId(int id) {
        fabId = id;

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (fabId != 0) {
            View fab = getRootView().findViewById(fabId);
//            centerRadius = fab.getWidth() / 2 + cornerRadius;
        }
//
//        measureChildren(widthMeasureSpec,heightMeasureSpec);
    }

//    @Override
//    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
//        int count = getChildCount();
//        int currentHeight = 0;
//        for (int i = 0 ; i < count ; i++){
//            View view = getChildAt(i);
//            int height = view.getMeasuredHeight();
//            int width  = view.getMeasuredWidth();
//            view.layout(left, currentHeight, left + width, currentHeight + height);
//            currentHeight += height;
//        }
//    }


    private void init() {

        setBackgroundColor(Color.TRANSPARENT);
        paint = new Paint();
        path = new Path();
        paint.setColor(backcolor);
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);
    }



    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        path.moveTo(0, shadowsize);
        path.lineTo(getWidth() / 2 - centerRadius - cornerRadius, shadowsize);

        path.quadTo(
                getWidth() / 2 - centerRadius,
                shadowsize,
                getWidth() / 2 - centerRadius,
                cornerRadius + shadowsize
        );

        //中间凹陷的半圆
        RectF rectCenter = new RectF(
                getWidth() / 2 - centerRadius,
                shadowsize + cornerRadius - centerRadius,
                getWidth() / 2 + centerRadius,
                shadowsize + centerRadius + cornerRadius
        );

        path.arcTo(rectCenter, 180, -180, false);

        //右边转角处
        path.quadTo(
                getWidth() / 2 + centerRadius,
                shadowsize,
                getWidth() / 2 + centerRadius + cornerRadius,
                shadowsize
        );
        path.lineTo(getWidth(), shadowsize);
        path.lineTo(getWidth(), getHeight());


        path.lineTo(0, getHeight());
        path.lineTo(0, shadowsize);



        //关闭硬件加速才能有阴影效果
        setLayerType(LAYER_TYPE_SOFTWARE, paint);
        paint.setShadowLayer(shadowsize,0,0,Color.LTGRAY);

        path.close();
        canvas.drawPath(path, paint);
    }


}
