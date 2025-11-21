package ru.mirea.chirkovia.dialogspractice;

import android.app.Dialog;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class MyProgressDialogFragment extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Загрузка")
                .setMessage("Подождите...")
                .setView(new android.widget.ProgressBar(getActivity()))
                .setCancelable(false);
        return builder.create();
    }
}