package com.katyshevtseva.kikiorgmobile.core.backup;

import com.google.gson.Gson;
import com.katyshevtseva.kikiorgmobile.core.KomDao;
import com.katyshevtseva.kikiorgmobile.core.model.IrregularTask;
import com.katyshevtseva.kikiorgmobile.core.model.RegularTask;

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

    public void restore(String json) throws Exception {
        clearDb();

        Gson gson = new Gson();
        BackupDto dto = gson.fromJson(json, BackupDto.class);

        for (IrregularTask it : dto.getIrregularTasks()) {
            komDao.saveNew(it);
        }
        for (RegularTask rt : dto.getRegularTasks()) {
            komDao.saveNew(rt);
        }
    }

    private void clearDb() {
        for (IrregularTask it : komDao.getAllIrregularTasks()) {
            komDao.delete(it);
        }
        for (RegularTask rt : komDao.getAllRegularTasks()) {
            komDao.delete(rt);
        }
    }
}
