package com.wp.linechartpaint;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;


import java.util.ArrayList;

/**
 * Created by honcho on 2016/8/31.
 * 睡眠图表日报告页折线图
 */
public class SleepDataReportView extends View {
    private Context context;
    private int screenWidth;
    private int screenHeight;
    private int width;
    private int height;
    private int contentWidth;
    private int contentHeight;
    private int topY;
    private int buttomY;
    private int leftX;
    private int rightX;


    private Paint textPaint;
    private Paint chartLinePaint;
    private Paint dataLinePaint;
    private int defaultText = Color.parseColor("#716790");
    private int chartLineColor = Color.parseColor("#3e316b");//图表XY轴颜色
    private int dataLineColor = Color.parseColor("#41f0bc");//数据折线颜色

    private String[] xAisx = new String[]{"2016-01-26 22:00:00", "2016-01-26 00:00:00", "2016-01-26 02:00:00", "2016-01-26 04:00:00", "2016-01-26 06:00:00", "2016-01-26 08:00:00", "2016-01-26 10:00:00"};
    private String[] yAisx = new String[]{"30", "20", "10", "0"};

    private String[] xAisxValue;
    private String[] yAisxValue;
    private String startTime;
    private String endTime;

    private int topValue = 25;
    private int bottomValue = 5;
    private boolean isRange = true;


    public SleepDataReportView(Context context) {
        this(context, null);
    }

    public SleepDataReportView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SleepDataReportView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        screenWidth = outMetrics.widthPixels;
        screenHeight = outMetrics.heightPixels;
        init();

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthModle = View.MeasureSpec.getMode(widthMeasureSpec);
        int heightModle = View.MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = View.MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = View.MeasureSpec.getSize(heightMeasureSpec);

        if (widthModle == View.MeasureSpec.EXACTLY) {
            width = widthSize;
        } else {
            width = screenWidth;
        }
        if (heightModle == View.MeasureSpec.EXACTLY) {
            height = heightSize;
        } else {
            height = screenWidth * 5 / 8;
        }
        contentWidth = width - leftX - rightX;
        contentHeight = height - topY - buttomY;

        setMeasuredDimension(width, height);
    }


    private void init() {
        chartLinePaint = new Paint();
        chartLinePaint.setAntiAlias(true);
        chartLinePaint.setStyle(Paint.Style.STROKE);
        chartLinePaint.setColor(chartLineColor);

        dataLinePaint = new Paint();
        dataLinePaint.setAntiAlias(true);
        dataLinePaint.setStyle(Paint.Style.STROKE);
        dataLinePaint.setColor(dataLineColor);
        dataLinePaint.setStrokeWidth(dip2px(context, 1));

        textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setTextSize(dip2px(context, 12));
        textPaint.setStrokeWidth(dip2px(context, 1));
        textPaint.setTextAlign(Paint.Align.CENTER);

        topY = dip2px(context, 16);
        buttomY = dip2px(context, 30);
        leftX = dip2px(context, 26);
        rightX = dip2px(context, 16);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        chartLinePaint.setStrokeWidth(dip2px(context, 1));
        textPaint.setColor(defaultText);
        float xtemp = contentWidth * 1.0f / (xAisx.length - 1);
        float ytemp = contentHeight * 1.0f / 3;
        float xHight = dip2px(context, 4);//X轴坐标高度
        // 画y轴横线和文本
        for (int i = 0; i < yAisx.length - 1; i++) {
            canvas.drawLine(leftX, topY + i * ytemp, leftX + contentWidth, topY + i * ytemp, chartLinePaint);
            canvas.drawText(yAisx[i], leftX / 2, topY + i * ytemp + dip2px(context, 3), textPaint);
        }
        //画y轴
        canvas.drawLine(leftX, 0, leftX, topY + contentHeight, chartLinePaint);

        //画折线
//        if (xAisxValue != null && xAisxValue.length > 0 && yAisxValue != null && yAisxValue.length > 0
//                && !TextUtils.isEmpty(startTime) && !TextUtils.isEmpty(endTime)) {
//            ArrayList<MyPoint> points = value2Point(xAisx, yAisx, xAisxValue, yAisxValue, startTime, endTime);
//            if (points.size() > 0) {
//                MyPoint maxPoint = points.get(0);
//                MyPoint minPoint = points.get(0);
//                for (int i = 0; i < points.size(); i++) {
//                    if (points.get(i).warnValue > 0) {
//                        if (points.get(i).warnValue > maxPoint.warnValue) {
//                            maxPoint = points.get(i);
//                        }
//                        if (points.get(i).warnValue < minPoint.warnValue || minPoint.warnValue == 0) {
//                            minPoint = points.get(i);
//                        }
//                    }
//                }
//                drawBroken(points, canvas);
//                if (isRange) {
//                    textPaint.setColor(Color.parseColor("#e95d5d"));
//                    //画最大值点
//                    canvas.drawCircle(maxPoint.x, maxPoint.y, dip2px(context, 2), textPaint);
//                    canvas.drawText(maxPoint.warnValue + "", maxPoint.x, maxPoint.y - dip2px(context, 4), textPaint);
//                    //画最小值点
//                    if (minPoint.warnValue > 0 && minPoint.warnValue < maxPoint.warnValue) {
//                        canvas.drawCircle(minPoint.x, minPoint.y, dip2px(context, 2), textPaint);
//                        canvas.drawText(minPoint.warnValue + "", minPoint.x, minPoint.y + dip2px(context, 12), textPaint);
//                    }
//                }
//
//            }
//        }

        //画x轴
        canvas.drawLine(leftX, topY + contentHeight, leftX + contentWidth, topY + contentHeight, chartLinePaint);
        // 画x轴坐标
        for (float i = 0; i < xAisx.length; i++) {
            chartLinePaint.setStrokeWidth(dip2px(context, 1.5f));
            canvas.drawLine(leftX + i * xtemp, topY + contentHeight - xHight, leftX + i * xtemp, topY + contentHeight, chartLinePaint);
        }
        // 画x轴本
        textPaint.setColor(defaultText);
        for (int i = 0; i < xAisx.length; i++) {
            canvas.drawText(xAisx[i].substring(11, 16), leftX + i * xtemp, topY + contentHeight + buttomY / 2, textPaint);
        }
    }

    /**
     * 设置图表数据源，且重画图表
     *
     * @param xAisx      x轴坐标集合
     * @param yAisx      y轴坐标集合
     * @param xAisxValue x轴坐标值集合
     * @param yAisxValue y轴坐标值集合
     */
    public void setData(String[] xAisx, String[] yAisx, String[] xAisxValue, String[] yAisxValue, String startTime, String endTime, int topValue, int bottomValue) {
        this.yAisx = yAisx;
        this.xAisx = xAisx;
        this.xAisxValue = xAisxValue;
        this.yAisxValue = yAisxValue;
        if (xAisx.length > 1) {
            this.startTime = xAisx[0];
            this.endTime = xAisx[xAisx.length - 1];
        } else {
            this.startTime = startTime;
            this.endTime = endTime;
        }
        this.topValue = topValue;
        this.bottomValue = bottomValue;
        invalidate();
    }

    /**
     * 设置图表数据源，且重画图表
     *
     * @param xAisx       x轴坐标集合
     * @param yAisx       y轴坐标集合
     * @param xAisxValue  x轴坐标值集合
     * @param yAisxValue  y轴坐标值集合
     * @param startTime   开始时间
     * @param endTime     结束时间
     * @param topValue    范围最大值
     * @param bottomValue 范围最小值
     * @param isRandge    是否画范围
     */
    public void setData(String[] xAisx, String[] yAisx, String[] xAisxValue, String[] yAisxValue, String startTime, String endTime, int topValue, int bottomValue, boolean isRandge) {
        setData(xAisx, yAisx, xAisxValue, yAisxValue, startTime, endTime, topValue, bottomValue);
        this.isRange = isRandge;
        invalidate();
    }

    private void drawBroken(ArrayList<MyPoint> myPointArrayList, Canvas canvas) {
        MyPoint startpoint = myPointArrayList.get(0);
        Path path = new Path();
        path.moveTo(startpoint.x, startpoint.y);
        for (int i = 1; i < myPointArrayList.size(); i++) {
            MyPoint nextp = myPointArrayList.get(i);
            path.lineTo(nextp.x, nextp.y);
        }
        canvas.drawPath(path, dataLinePaint);
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public int dip2px(Context context, float dpValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }


    /**
     * 获取数值集合在图表上的位置
     */
    /*public ArrayList<MyPoint> value2Point(String[] xAisx, String[] yAisx, String[] xAisxValue, String[] yAisxValue, String startTime, String endTime) {
        ArrayList<MyPoint> points = new ArrayList<MyPoint>();
        int allTime = AxisUtils.getMinute(startTime, endTime);
        if (allTime != 0) {
            for (int i = 0; i < xAisxValue.length; i++) {
                MyPoint point = new MyPoint();
                point.x = leftX + AxisUtils.getMinute(startTime, xAisxValue[i]) * contentWidth / allTime;
                int y = topY + contentHeight - Integer.parseInt(yAisxValue[i]) * contentHeight / Integer.parseInt(yAisx[0]);
                point.y = y < topY ? topY : y;
                point.warnValue = Integer.parseInt(yAisxValue[i]);
                points.add(point);

                //如果两个数据点的间隔大于等于15分钟，则两个点不直接连接，垂直到X轴
                if (i + 1 < xAisxValue.length && AxisUtils.getMinute(xAisxValue[i], xAisxValue[i + 1]) >= 15) {
                    MyPoint pointX1 = new MyPoint();
                    pointX1.x = point.x;
                    pointX1.y = topY + contentHeight;
                    pointX1.warnValue = 0;
                    points.add(pointX1);

                    MyPoint pointX2 = new MyPoint();
                    pointX2.x = leftX + AxisUtils.getMinute(startTime, xAisxValue[i + 1]) * contentWidth / allTime;
                    pointX2.y = topY + contentHeight;
                    pointX2.warnValue = 0;
                    points.add(pointX2);
                }
            }
        }
        return points;
    }*/

    public class MyPoint {
        public int x;
        public int y;
        public int warnValue;

        @Override
        public String toString() {
            return "MyPoint [x=" + x + ", y=" + y + ", value=" + warnValue + "]";
        }
    }
}
