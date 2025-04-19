package com.katyshevtseva.kikiorgmobile.utils;

import android.view.View;
import android.view.Window;

import com.katyshevtseva.kikiorgmobile.core.model.Task;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public abstract class GeneralUtil {

    public static boolean isEmpty(String s) {
        return s == null || s.trim().equals("");
    }

    public static void setImmersiveStickyMode(Window window) {
        View decorView = window.getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        decorView.setSystemUiVisibility(uiOptions);
    }

    public static boolean taskFilter(Task task, String s) {
        s = s == null ? null : s.toLowerCase();
        return isEmpty(s) || (task.getTitle().toLowerCase().contains(s) || task.getDesc().toLowerCase().contains(s));
    }

    public static String getLoppedDateListString(List<Date> dates) {
        boolean dateListIsTooBig = dates.size() > 5;
        List<Date> loppedList = dateListIsTooBig ? dates.subList(0, 5) : new ArrayList<>(dates);

        StringBuilder stringBuilder = new StringBuilder();
        for (Date date : loppedList) {
            stringBuilder.append(DateUtils.getDateStringWithWeekDay(date)).append("\n");
        }

        if (dateListIsTooBig)
            stringBuilder.append("...");
        return stringBuilder.toString();
    }
}
