package com.katyshevtseva.kikiorgmobile.core;

import com.katyshevtseva.kikiorgmobile.core.model.IrregularTask;
import com.katyshevtseva.kikiorgmobile.core.model.RegularTask;
import com.katyshevtseva.kikiorgmobile.utils.DateUtils;

import java.util.Date;

public class SimpleBackupService {
    public static SimpleBackupService INSTANCE;
    private final KomDao komDao;

    public static void init(KomDao komDao) {
        INSTANCE = new SimpleBackupService(komDao);
    }

    private SimpleBackupService(KomDao komDao) {
        this.komDao = komDao;
    }

    public String getBackup() {
        StringBuilder stringBuilder = new StringBuilder("*** START *** ")
                .append(DateUtils.getDateString(new Date()))
                .append(" ***")
                .append("\n\n *** REGULAR TASKS ***");


        for (RegularTask regularTask : komDao.getAllRegularTasks()) {
            stringBuilder.append("\n").append(regularTask.getBackupString());
        }

        stringBuilder.append("\n\n*** IRREGULAR TASKS ***");
        for (IrregularTask irregularTask : komDao.getAllIrregularTasks()) {
            stringBuilder.append("\n").append(irregularTask.getBackupString());
        }

        stringBuilder.append("\n\n--- END ---");
        return stringBuilder.toString();
    }
}
