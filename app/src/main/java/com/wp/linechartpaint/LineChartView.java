package com.wp.linechartpaint;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import java.util.ArrayList;

/**
 * <p>描述：(这里用一句话描述这个类的作用)</p>
 * 作者： wp<br>
 * 日期： 2019/2/18 14:14<br>
 * 版本： v2.0<br>
 */
public class LineChartView extends View {

    private Context context;
    private Paint chartLinePaint;
    private Paint textPaint;
    private Paint pointPaint;
    private Paint lastPointPaint;
    private int lineColor = Color.parseColor("#71235D");
    private int textColor = Color.parseColor("#333333");
    private int screenWidth;
    private int screenHeight;
    private int width;
    private int height;
    private int contentWidth;
    private int contentHeight;
    private int topY;
    private int bottomY;
    private int leftX;
    private int rightX;

    private String[] xAisx = new String[]{"1日", "2日", "3日", "4日", "5日", "6日", "昨天"};
    private String[] yAisx = new String[]{"75", "50", "25", "0"};

    private String[] xAisxValues;
    private String[] yAisxValues;

    public LineChartView(Context context) {
        this(context, null);
    }

    public LineChartView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LineChartView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(displayMetrics);
        screenWidth = displayMetrics.widthPixels;
        screenHeight = displayMetrics.heightPixels;
        init();
    }

    private void init() {
        chartLinePaint = new Paint();
        chartLinePaint.setAntiAlias(true);//设置抗锯齿
        chartLinePaint.setColor(lineColor);
        chartLinePaint.setStyle(Paint.Style.STROKE);


        textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setColor(textColor);
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setTextSize(32);
        textPaint.setTextAlign(Paint.Align.CENTER);

        pointPaint = new Paint();
        pointPaint.setAntiAlias(true);
        pointPaint.setColor(Color.parseColor("#769718"));
        pointPaint.setStyle(Paint.Style.STROKE);
        pointPaint.setStrokeWidth(dip2px(context, 1));

        lastPointPaint = new Paint();
        lastPointPaint.setAntiAlias(true);
        lastPointPaint.setColor(Color.parseColor("#769718"));
        lastPointPaint.setStyle(Paint.Style.FILL);

        topY = dip2px(context, 16);
        bottomY = dip2px(context, 30);
        leftX = dip2px(context, 26);
        rightX = dip2px(context, 16);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = View.MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = View.MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = View.MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = View.MeasureSpec.getSize(heightMeasureSpec);
        if (widthMode == View.MeasureSpec.EXACTLY) {
            width = widthSize;
        } else {
            width = screenWidth;
        }

        if (heightMode == View.MeasureSpec.EXACTLY) {
            height = heightSize;
        } else {
            height = screenHeight * 5 / 8;
        }

        contentWidth = width - leftX - rightX;
        contentHeight = height - topY - bottomY;
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        chartLinePaint.setStrokeWidth(dip2px(context, 1));
        float xtemp = contentWidth * 1.0f / (xAisx.length - 1);//水平分成几等分
        float ytemp = contentHeight * 1.0f / (yAisx.length - 1);//竖直方向分成几等分
        //画Y轴横线和文本
        for (int i = 0; i <= (yAisx.length - 1); i++) {
            canvas.drawLine(leftX, topY + ytemp * i, leftX + contentWidth, topY + ytemp * i, chartLinePaint);
            canvas.drawText(yAisx[i], leftX / 2, topY + i * ytemp + dip2px(context, 3), textPaint);
        }
        //画Y轴
        canvas.drawLine(leftX, 0, leftX, topY + contentHeight, chartLinePaint);
        //画X轴
//        canvas.drawLine(leftX, topY + contentHeight, leftX + contentWidth, topY + contentHeight, chartLinePaint);
        //画X轴坐标
        for (int i = 0; i <= xAisx.length - 1; i++) {
            canvas.drawLine(leftX + xtemp * i, topY + contentHeight, leftX + xtemp * i, topY + contentHeight - dip2px(context, 5), chartLinePaint);
        }
        //画X轴文本
        textPaint.setColor(textColor);
        for (int i = 0; i <= xAisx.length - 1; i++) {
            canvas.drawText(xAisx[i], leftX + i * xtemp, topY + contentHeight + bottomY / 2, textPaint);
        }
        if (xAisxValues != null && xAisxValues.length > 0 && yAisxValues != null && yAisxValues.length > 0) {
            ArrayList<MyPoint> myPoints = values2Point(xAisx, yAisx, xAisxValues, yAisxValues);
            if (myPoints.size() > 0) {
                //描点
                for (int i = 0; i < myPoints.size(); i++) {
                    if (i == (myPoints.size() - 1)) {
                        canvas.drawCircle(myPoints.get(i).x, myPoints.get(i).y, dip2px(context, 2), lastPointPaint);
                    } else {
                        canvas.drawCircle(myPoints.get(i).x, myPoints.get(i).y, dip2px(context, 2), pointPaint);
                    }
                }
                //画折线
                for (int i = 0; i < myPoints.size() - 1; i++) {
                    canvas.drawLine(myPoints.get(i).x, myPoints.get(i).y, myPoints.get(i + 1).x, myPoints.get(i + 1).y, chartLinePaint);
                }
            }
        }

    }

    /**
     * xAisx X轴
     * yAisx Y轴
     * xAisxValues x轴坐标点
     * yAisxValues x轴坐标点
     */
    private ArrayList<MyPoint> values2Point(String[] xAisx, String[] yAisx, String[] xAisxValues, String[] yAisxValues) {
        ArrayList<MyPoint> myPoints = new ArrayList<>();
        int xtemp = contentWidth / (xAisx.length - 1);//水平分成几等分
        int ytemp = contentHeight / (yAisx.length - 1);//竖直方向分成几等分
        if (xAisxValues.length > 0) {
            for (int i = 0; i <= xAisxValues.length - 1; i++) {
                MyPoint myPoint = new MyPoint();
                myPoint.x = leftX + i * xtemp;
                int y = topY + contentHeight - Integer.parseInt(yAisxValues[i]) * contentHeight / Integer.parseInt(yAisx[0]);
                myPoint.y = y;
                myPoints.add(myPoint);
            }
        }
        return myPoints;
    }

    public void setData(String[] xAisx, String[] yAisx, String[] xAisxValues, String[] yAisxValues) {
        this.xAisx = xAisx;
        this.yAisx = yAisx;
        this.xAisxValues = xAisxValues;
        this.yAisxValues = yAisxValues;
        invalidate();
    }

    public class MyPoint {
        private int x;
        private int y;
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public int dip2px(Context context, float dpValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
