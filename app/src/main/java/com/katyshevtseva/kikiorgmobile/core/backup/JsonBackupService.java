package com.katyshevtseva.kikiorgmobile.core.backup;

import com.google.gson.Gson;
import com.katyshevtseva.kikiorgmobile.core.KomDao;

public class JsonBackupService {
    public static JsonBackupService INSTANCE;
    private final KomDao komDao;

    public static void init(KomDao komDao) {
        INSTANCE = new JsonBackupService(komDao);
    }

    private JsonBackupService(KomDao komDao) {
        this.komDao = komDao;
    }

    public String getBackup() {
        BackupDto dto = new BackupDto(komDao.getAllRegularTasks(), komDao.getAllIrregularTasks());
        Gson gson = new Gson();
        return gson.toJson(dto);
    }
}
