package com.wp.linechartpaint;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;

import com.wp.linechartpaint.utils.AxisUtils;
import com.wp.linechartpaint.utils.DateTimeUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>描述：(这里用一句话描述这个类的作用)</p>
 * 作者： wp<br>
 * 日期： 2019/5/22 9:35<br>
 * 版本： v2.0<br>
 */
public class IBreathDayLineChartNew extends View {

    private Paint mLineXPaint;//X轴画笔
    private Paint mXTextPaint;//X轴字体画笔
    private Paint mYTextPaint;//X轴字体画笔
    private Paint mLineChartPaint;//折线画笔
    private Paint mPointPaint;//最大最小点画笔
    private Paint mShadePointPaint;//最大最小阴影画笔

    private String mChartTitle;//标题
    private String mChartNormalRange;//正常范围
    private Context mContext;

    private String[] xAisx = new String[]{"2016-01-26 22:00:00", "2016-01-26 24:00:00", "2016-01-26 02:00:00", "2016-01-26 04:00:00", "2016-01-26 06:00:00", "2016-01-26 08:00:00", "2016-01-26 10:00:00"};
    private String[] yAisx = new String[]{"100", "90", "80", "70", "60"};

    private String[] xAisxValue;
    private String[] yAisxValue;
    private String mStartTime;
    private String mEndTime;
    private List<SleepChartModel> mSleepChartModels;
    private int mTimeRange;

    private int screenWidth;//屏宽
    private int screenHeight;//屏高
    private int mChartWidth;//控件宽度
    private int mChartHeight;//控件高度
    private int mTopY;//上边距
    private int mBottomY;//下边距
    private int mLeftX;//左边距
    private int mRightX; //右边距
    private int mLineHeight;//线高
    private int mXYTextSize;//x、y轴字体大小
    private int mTitleTextSize;//标题字体大小
    private int mMiniOrMaxTextSize;//最大最小值字体大小
    private int yEqualSpacing;//y轴等间距
    private float xTemp;//x轴平均间距
    private int maxWarValue = -1;
    private int minWarValue = -1;

    public IBreathDayLineChartNew(Context context) {
        this(context, null);
    }

    public IBreathDayLineChartNew(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public IBreathDayLineChartNew(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(displayMetrics);
        screenWidth = displayMetrics.widthPixels;
        screenHeight = displayMetrics.heightPixels;

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.IBreathDayLineChart);
        mChartTitle = typedArray.getString(R.styleable.IBreathDayLineChart_chart_title);
        mChartNormalRange = typedArray.getString(R.styleable.IBreathDayLineChart_chart_normal_range);
        String yAisxStr = typedArray.getString(R.styleable.IBreathDayLineChart_chart_aisx_y);
        if (!TextUtils.isEmpty(yAisxStr)) {
            yAisx = yAisxStr.split(",");
        }
        typedArray.recycle();
        init();
    }

    private void init() {
        mLineHeight = dip2px(mContext, 1);
        mTopY = dip2px(mContext, 25);
        mBottomY = dip2px(mContext, 40);
        mLeftX = dip2px(mContext, 41);
        mRightX = dip2px(mContext, 17);
        yEqualSpacing = dip2px(mContext, 41);

        mXYTextSize = sp2px(mContext, 12);
        mTitleTextSize = sp2px(mContext, 18);
        mMiniOrMaxTextSize = sp2px(mContext, 14);

        mLineXPaint = new Paint();
        mLineXPaint.setAntiAlias(true);
        mLineXPaint.setStrokeWidth(mLineHeight);
        mLineXPaint.setColor(Color.parseColor("#C6C6C6"));

        mXTextPaint = new Paint();
        mXTextPaint.setColor(Color.parseColor("#716790"));
        mXTextPaint.setTextSize(mXYTextSize);

        mYTextPaint = new Paint();
        mYTextPaint.setColor(Color.parseColor("#5E5E5E"));
        mYTextPaint.setTextSize(mXYTextSize);

        mLineChartPaint = new Paint();
        mLineChartPaint.setColor(Color.parseColor("#78D8E9"));
        mLineChartPaint.setAntiAlias(true);
        mLineChartPaint.setStrokeWidth(dip2px(mContext, 1));
        mLineChartPaint.setStyle(Paint.Style.FILL);

        mPointPaint = new Paint();
        mPointPaint.setColor(Color.parseColor("#0ABCDC"));
        mPointPaint.setStyle(Paint.Style.FILL);
        mPointPaint.setAntiAlias(true);

        mShadePointPaint = new Paint();
        mShadePointPaint.setColor(Color.parseColor("#330ABCDC"));
        mShadePointPaint.setStyle(Paint.Style.FILL);
        mShadePointPaint.setAntiAlias(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSpecSize = yEqualSpacing * (yAisx.length - 1) + mTopY + mBottomY;
        if (widthSpecMode == MeasureSpec.AT_MOST) {
            widthSpecSize = 300;
        }
        if (heightSpecMode == MeasureSpec.AT_MOST) {
            heightSpecSize = 300;
        }
        setMeasuredDimension(widthSpecSize, heightSpecSize);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mChartWidth = getWidth() - mLeftX - mRightX;
        mChartHeight = yEqualSpacing * (yAisx.length - 1);
        float yTemp = yEqualSpacing;
        xTemp = mChartWidth * 1.0f / (xAisx.length);
        //画Y轴横线和Y轴文本
        for (int i = 0; i < yAisx.length; i++) {
            canvas.drawLine(mLeftX, mTopY + yTemp * i, mLeftX + mChartWidth, mTopY + yTemp * i, mLineXPaint);
            if (Integer.parseInt(yAisx[i]) >= 100) {
                canvas.drawText(yAisx[i], dip2px(mContext, 17), mTopY + yTemp * i + dip2px(mContext, 5), mYTextPaint);
            } else {
                canvas.drawText(yAisx[i], dip2px(mContext, 22), mTopY + yTemp * i + dip2px(mContext, 5), mYTextPaint);
            }
        }
        //画X轴文本
        for (int i = 0; i < xAisx.length; i++) {
            float txtWidth = mXTextPaint.measureText(xAisx[i].substring(11, 16));
            canvas.drawText(xAisx[i].substring(11, 16), xTemp * i + mLeftX + xTemp / 2 - txtWidth / 2, mTopY + mChartHeight + dip2px(mContext, 20), mXTextPaint);
        }
        //画X轴轴标,用完隐藏
        for (int i = 0; i < xAisx.length; i++) {
            canvas.drawLine(xTemp * i + mLeftX + xTemp / 2, mTopY + mChartHeight - 5, xTemp * i + mLeftX + xTemp / 2, mTopY + mChartHeight, mLineXPaint);
        }

        //画折线
        if (mSleepChartModels != null && mSleepChartModels.size() > 0 && mStartTime != null) {
            ArrayList<MyPoint> points = value2Point(xTemp);
            if (points.size() > 0) {
                drawBroken(points, canvas, xTemp);
            }
        }

    }

    private void drawBroken(ArrayList<MyPoint> points, Canvas canvas, float xtemp) {
        float range15Minute = mLeftX + (xtemp / 2) + 15f / mTimeRange * (mChartWidth - xtemp);//15分钟对应的在X轴上的距离
        MyPoint maxPoint = points.get(0);//最大值点
        MyPoint minPoint = points.get(0);//最小值点
        for (int i = 1; i < points.size(); i++) {
            //获取最大值点
            if (points.get(i).warnValue != -1 && points.get(i).warnValue > maxPoint.warnValue) {
                maxPoint = points.get(i);
            }
            //获取最小值点
            if (points.get(i).warnValue != -1 && points.get(i).warnValue < minPoint.warnValue) {
                minPoint = points.get(i);
            }
        }
        maxWarValue = maxPoint.warnValue;
        minWarValue = minPoint.warnValue;
        //画折线
        for (int i = 0; i < points.size() - 1; i++) {
            if (points.get(i).warnValue == 0 || points.get(i + 1).warnValue == 0 || (points.get(i + 1).x - points.get(i).x) >= range15Minute) {

            } else {
                canvas.drawLine(points.get(i).x, points.get(i).y, points.get(i + 1).x, points.get(i + 1).y, mLineChartPaint);
            }
        }
        //画最大值点
        canvas.drawCircle(maxPoint.x, maxPoint.y, dip2px(mContext, 6), mShadePointPaint);
        canvas.drawCircle(maxPoint.x, maxPoint.y, dip2px(mContext, 3), mPointPaint);

        //画最小值点
        if (minPoint.warnValue > 0 && minPoint.warnValue < maxPoint.warnValue) {
            canvas.drawCircle(minPoint.x, minPoint.y, dip2px(mContext, 6), mShadePointPaint);
            canvas.drawCircle(minPoint.x, minPoint.y, dip2px(mContext, 3), mPointPaint);
        }
    }

    /**
     * 获取数值集合在图表上的位置
     */
    public ArrayList<MyPoint> value2Point(float xtemp) {
        ArrayList<MyPoint> points = new ArrayList<>();
        int totalValueY = Integer.parseInt(yAisx[0]) - Integer.parseInt(yAisx[yAisx.length - 1]);
        if (mTimeRange != 0) {
            for (int i = 0; i < mSleepChartModels.size(); i++) {
                MyPoint point = new MyPoint();
                point.x = mLeftX + (xtemp / 2) + DateTimeUtil.getMinuteDiff(mStartTime, mSleepChartModels.get(i).getKey()) * 1f / mTimeRange * (mChartWidth - xtemp);
                float y = mTopY + (Integer.parseInt(yAisx[0]) - mSleepChartModels.get(i).getValue()) * 1f / totalValueY * mChartHeight;
                point.y = y < mTopY / 2 ? mTopY / 2 : (y > mTopY + mChartHeight ? mTopY + mChartHeight : y);
                point.warnValue = mSleepChartModels.get(i).getValue();
                points.add(point);

                //如果两个数据点的间隔大于等于15分钟，则两个点不直接连接，垂直到X轴
//                if (i + 1 < xAisxValue.length && AxisUtils.getMinute(xAisxValue[i], xAisxValue[i + 1]) >= 15) {
//                    MyPoint pointX1 = new MyPoint();
//                    pointX1.x = point.x;
//                    pointX1.y = mTopY + mChartHeight;
//                    pointX1.warnValue = 0;
//                    points.add(pointX1);
//
//                    MyPoint pointX2 = new MyPoint();
//                    pointX2.x = mLeftX + xtemp / 2 + AxisUtils.getMinute(mStartTime, xAisxValue[i + 1]) * (mChartWidth - xtemp) / mTimeRange;
//                    pointX2.y = mTopY + mChartHeight;
//                    pointX2.warnValue = 0;
//                    points.add(pointX2);
//                }
            }
        }
        return points;
    }

    /**
     * 获取数值集合在图表上的位置
     *
     * @param sleepChartModels
     * @param startTime        起点时间
     * @param endTime          结束时间
     * @param timeRange        时间差
     */
    public void setChartData(List<SleepChartModel> sleepChartModels, String[] xAisx, String[] yAisx, String startTime, String endTime, int timeRange) {
        this.mSleepChartModels = sleepChartModels;
        this.mStartTime = startTime;
        this.mEndTime = endTime;
        this.mTimeRange = timeRange;
        if (xAisx.length > 0) {
            this.xAisx = xAisx;
        }
        if (yAisx.length > 0) {
            this.yAisx = yAisx;
        }
        invalidate();
    }

    /**
     * 获取最大值
     *
     * @return
     */
    public int getMaxWarValue() {
        /*if (mSleepChartModels.size() == 0){
            return -1;
        }
        ArrayList<MyPoint> points = value2Point(xTemp);
        MyPoint maxPoint = points.get(0);//最大值点
        for (int i = 1; i < points.size(); i++) {
            //获取最大值点
            if (points.get(i).warnValue != -1 && points.get(i).warnValue > maxPoint.warnValue) {
                maxPoint = points.get(i);
            }
        }
        return maxPoint.warnValue;*/
        return maxWarValue;
    }

    /**
     * 获取最小值
     *
     * @return
     */
    public int getMinWarValue() {
        /*if (mSleepChartModels.size() == 0){
            return -1;
        }
        ArrayList<MyPoint> points = value2Point(xTemp);
        MyPoint minPoint = points.get(0);//最小值点
        for (int i = 1; i < points.size(); i++) {
            //获取最小值点
            if (points.get(i).warnValue != -1 && points.get(i).warnValue < minPoint.warnValue) {
                minPoint = points.get(i);
            }
        }
        return minPoint.warnValue;*/
        return minWarValue;
    }

    public class MyPoint {
        private float x;
        private float y;
        private int warnValue;

        @Override
        public String toString() {
            return "MyPoint [x=" + x + ", y=" + y + ", value=" + warnValue + "]";
        }
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public int dip2px(Context context, float dpValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * convert sp to its equivalent px
     * <p>
     * 将sp转换为px
     */
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

}
