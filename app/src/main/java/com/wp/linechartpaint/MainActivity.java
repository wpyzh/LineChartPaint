package com.wp.linechartpaint;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private LineChartViewNoXy chartView;
    private String[] yValues = new String[]{"55","25","35","25","18","22","32"};
    private String[] xAisx = new String[]{"1日", "2日", "3日", "4日", "5日", "6日", "昨天"};
    private String[] yAisx = new String[]{"75", "50", "25", "0"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        chartView = findViewById(R.id.chartView);
        chartView.setData(xAisx,yAisx,xAisx,yValues);
    }
}
