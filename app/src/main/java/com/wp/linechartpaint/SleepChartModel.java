package com.wp.linechartpaint;

import android.support.annotation.NonNull;

/**
 * @author by honcho on 2017/12/8.
 *         折线图数据model
 */

public class SleepChartModel implements Comparable<SleepChartModel> {
    private String key;
    private int value;

    public SleepChartModel(String key, int value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public int compareTo(@NonNull SleepChartModel other) {
        return this.key.compareTo(other.key);
    }
}
