package com.katyshevtseva.kikiorgmobile.core.backup;

import com.katyshevtseva.kikiorgmobile.core.model.IrregularTask;
import com.katyshevtseva.kikiorgmobile.core.model.RegularTask;

import java.util.List;

public class BackupDto {
    private List<RegularTask> regularTasks;
    private List<IrregularTask> irregularTasks;

    public BackupDto() {
    }

    public BackupDto(List<RegularTask> regularTasks, List<IrregularTask> irregularTasks) {
        this.regularTasks = regularTasks;
        this.irregularTasks = irregularTasks;
    }

    public List<RegularTask> getRegularTasks() {
        return regularTasks;
    }

    public void setRegularTasks(List<RegularTask> regularTasks) {
        this.regularTasks = regularTasks;
    }

    public List<IrregularTask> getIrregularTasks() {
        return irregularTasks;
    }

    public void setIrregularTasks(List<IrregularTask> irregularTasks) {
        this.irregularTasks = irregularTasks;
    }
}
