package com.katyshevtseva.kikiorgmobile.view;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.katysh.kikiorgmobile.R;
import com.katyshevtseva.kikiorgmobile.core.backup.JsonBackupService;
import com.katyshevtseva.kikiorgmobile.utils.GeneralUtil;
import com.katyshevtseva.kikiorgmobile.utils.knobs.NoArgKnob;

public class RestoreDialog extends DialogFragment {
    private final NoArgKnob activityUpdateKnob;

    public RestoreDialog(NoArgKnob activityUpdateKnob) {
        this.activityUpdateKnob = activityUpdateKnob;
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_restore, null);
        EditText editText = v.findViewById(R.id.edit_text);
        Button restoreButton = v.findViewById(R.id.restore_button);

        restoreButton.setOnClickListener(view -> {
            String string = editText.getText().toString();
            if (!GeneralUtil.isEmpty(string)) {
                try {
                    JsonBackupService.INSTANCE.restore(string);
                    Toast.makeText(getContext(), "Restored", Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    showAlertDialog();
                } finally {
                    dismiss();
                }

            }
        });
        return v;
    }

    private void showAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext()); // 'this' — контекст активности
        builder.setTitle("Ашипка!"); // задаём заголовок
        builder.setMessage("Ашипка!"); // задаём текст сообщения
        builder.setPositiveButton("ОК", (dialog, which) -> {
            dialog.dismiss(); // закрыть диалог при нажатии
        });
        AlertDialog dialog = builder.create(); // создаём диалог
        dialog.show(); // показываем диалог
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        activityUpdateKnob.execute();
        GeneralUtil.setImmersiveStickyMode(getActivity().getWindow());
    }
}
