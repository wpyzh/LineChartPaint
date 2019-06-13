package com.wp.linechartpaint;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.wp.linechartpaint.utils.DateTimeUtil;
import com.wp.linechartpaint.utils.TimeUtil;

import java.text.ParseException;

public class MainActivity extends AppCompatActivity {

    private LineChartViewNoXy chartView;
    private String[] yValues = new String[]{"55","25","35","25","18","22","32"};
    private String[] xAisx = new String[]{"1日", "2日", "3日", "4日", "5日", "6日", "昨天"};
    private String[] yAisx = new String[]{"75", "50", "25", "0"};

    private IBreathDayLineChart iBreathDayLineChart;
    private String[] xAisx1 = new String[]{"2019-06-11 23:22:00", "2019-06-12 00:43:05", "2019-06-12 02:43:05", "2019-06-12 03:23:05", "2019-06-12 04:43:05", "2019-06-12 06:05:05", "2019-06-12 07:23:05"};
    private String[] yAisx1 = new String[]{"120", "100", "80", "60", "40"};

    private String[] xAisxValue = new String[]{"2019-06-11 23:25:00","2019-06-11 23:32:00","2019-06-11 23:37:00", "2019-06-12 00:10:05","2019-06-12 00:15:00","2019-06-12 00:25:00","2019-06-12 00:35:00"
                                              ,"2019-06-12 00:50:00","2019-06-12 00:58:00","2019-06-12 01:10:00","2019-06-12 01:25:00","2019-06-12 01:35:00","2019-06-12 01:40:00","2019-06-12 01:55:00"
                                              ,"2019-06-12 02:10:00","2019-06-12 02:25:00","2019-06-12 02:40:00","2019-06-12 02:50:00","2019-06-12 03:00:00","2019-06-12 03:10:00","2019-06-12 03:15:00"
                                              ,"2019-06-12 03:25:00","2019-06-12 03:40:00","2019-06-12 03:55:00","2019-06-12 04:10:00","2019-06-12 04:25:00","2019-06-12 04:30:00","2019-06-12 04:35:00"
                                              ,"2019-06-12 04:50:00","2019-06-12 04:55:00","2019-06-12 05:10:00","2019-06-12 05:15:00","2019-06-12 05:25:00","2019-06-12 05:35:00","2019-06-12 05:55:00"
                                              ,"2019-06-12 06:10:00","2019-06-12 06:25:00","2019-06-12 06:35:00","2019-06-12 06:50:00","2019-06-12 07:00:00","2019-06-12 07:10:00","2019-06-12 07:20:00"};
    private String[] yAisxValue = new String[]{"90", "80", "90", "70", "65","65","74"
                                              ,"85", "70", "80", "70", "65","68","79"
                                              ,"80", "60", "69", "70", "65","84","70"
                                              ,"83", "90", "70", "70", "65","80","75"
                                              ,"75", "80", "65", "70", "65","82","84"
                                              ,"68", "88", "86", "70", "65","84","88"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        chartView = findViewById(R.id.chartView);
        chartView.setData(xAisx,yAisx,xAisx,yValues);

        iBreathDayLineChart = findViewById(R.id.breath_chart);
        try {
            String[] xAisxs = getAxixPiont(xAisxValue[0], xAisxValue[xAisxValue.length - 1], 7);
            iBreathDayLineChart.setChartData(xAisxs,yAisx1,xAisxValue,yAisxValue,xAisxs[0],xAisxs[xAisx.length-1]);
        } catch (ParseException e) {
            e.printStackTrace();
        }


    }

    private String[] getAxixPiont(String startTime, String endTime, int maxPiont) throws ParseException {
        if (maxPiont <= 0) {
            maxPiont = 6;
        }

        int minute = DateTimeUtil.getMinuteDiff(startTime, endTime);
        if (minute < maxPiont) {
            return new String[]{startTime, endTime};
        } else {
            int perMinute = minute / maxPiont + (minute % maxPiont == 0 ? 0 : 1);
            String[] xAixs = new String[maxPiont + 1];
            xAixs[0] = startTime;
            long startMillis = TimeUtil.parseMillis(startTime, "yyyy-MM-dd HH:mm:ss");

            for (int i = 1; i <= maxPiont; ++i) {
                long nextMillis = TimeUtil.modMinute(startMillis, i * perMinute);
                xAixs[i] = TimeUtil.format(nextMillis, "yyyy-MM-dd HH:mm:ss");
            }
            return xAixs;
        }
    }
}
