package com.katyshevtseva.kikiorgmobile.view;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.katysh.kikiorgmobile.R;
import com.katyshevtseva.kikiorgmobile.core.IrregularTaskService;
import com.katyshevtseva.kikiorgmobile.core.backup.JsonBackupService;
import com.katyshevtseva.kikiorgmobile.core.KomDao;
import com.katyshevtseva.kikiorgmobile.core.LogService;
import com.katyshevtseva.kikiorgmobile.core.OptionalTaskService;
import com.katyshevtseva.kikiorgmobile.core.RegularTaskService;
import com.katyshevtseva.kikiorgmobile.core.Service;
import com.katyshevtseva.kikiorgmobile.core.SimpleBackupService;
import com.katyshevtseva.kikiorgmobile.db.KomDaoImpl;
import com.katyshevtseva.kikiorgmobile.utils.DateUtils;
import com.katyshevtseva.kikiorgmobile.utils.DateUtils.TimeUnit;
import com.katyshevtseva.kikiorgmobile.view.utils.KomActivity;

import java.util.Date;

public class MainActivity extends KomActivity {
    private Date date;
    private TextView dateView;
    private final MainTaskListFragment mainTaskListFragment = new MainTaskListFragment(this::fragmentUpdateListener);
    private TextView alarmTextView;
    private boolean prevDateIsAvailable;

    public MainActivity() {
        setOnStart(this::updateTaskPane);
        setImmersiveStickyMode(true);
        setOnLeftSwipe(this::previousDate);
        setOnRightSwipe(this::nextDate);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        KomDao komDao = new KomDaoImpl(this);
        Service.init(komDao);
        RegularTaskService.init(komDao);
        IrregularTaskService.init(komDao);
        LogService.init(komDao);
        OptionalTaskService.init(komDao);
        SimpleBackupService.init(komDao);
        JsonBackupService.init(komDao);
        alarmTextView = findViewById(R.id.alarm_text_view);

        date = new Date();
        dateView = findViewById(R.id.main_date_text_view);
        findViewById(R.id.admin_button).setOnClickListener(view ->
                startActivity(new Intent(getApplicationContext(), AdminActivity.class)));
        dateView.setOnClickListener(view -> openDatePicker());

        findViewById(R.id.add_irt_button).setOnClickListener(view1 ->
                startActivity(IrtEditActivity.newIntent(this, null)));

        getSupportFragmentManager().beginTransaction().add(R.id.task_list_container, mainTaskListFragment).commit();

        updateTaskPane();
    }

    private void updateTaskPane() {
        dateView.setText(DateUtils.getDateStringWithWeekDay(date));
        mainTaskListFragment.setDate(date);
        setDateViewStyle();
        updateAlarmBanner();
        updatePrevDateIsAvailable();
    }

    private void fragmentUpdateListener() {
        updateAlarmBanner();
        updatePrevDateIsAvailable();
    }

    private void updatePrevDateIsAvailable(){
        Date earliestTaskDate = Service.INSTANCE.getEarliestTaskDate();
        if(earliestTaskDate!=null){
            prevDateIsAvailable = DateUtils.beforeIgnoreTime(earliestTaskDate, date);
        }else {
            prevDateIsAvailable = false;
        }
    }

    private void setDateViewStyle() {
        if (DateUtils.equalsIgnoreTime(date, new Date())) {
            dateView.setTextColor(ContextCompat.getColor(this, R.color.colorAccent));
            dateView.setTypeface(null, Typeface.BOLD);
        } else {
            dateView.setTextColor(ContextCompat.getColor(this, R.color.black));
            dateView.setTypeface(null, Typeface.NORMAL);
        }
    }

    public void updateAlarmBanner() {
        if (DateUtils.equalsIgnoreTime(date, new Date()) && Service.INSTANCE.overdueTasksExist()) {
            alarmTextView.setVisibility(View.VISIBLE);
        } else {
            alarmTextView.setVisibility(View.GONE);
        }
    }

    public void openDatePicker() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this);
        datePickerDialog.setOnDateSetListener((datePicker, year, month, day) -> {
            date = DateUtils.parse(year, month + 1, day);
            updateTaskPane();
        });
        datePickerDialog.show();
    }

    private void previousDate() {
        if (prevDateIsAvailable) {
            date = DateUtils.shiftDate(date, TimeUnit.DAY, -1);
            updateTaskPane();
        }
    }

    private void nextDate() {
        date = DateUtils.shiftDate(date, TimeUnit.DAY, 1);
        updateTaskPane();
    }
}
