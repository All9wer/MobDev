package ru.mirea.chirkovia.dialogspractice;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import com.google.android.material.snackbar.Snackbar;
import java.util.Calendar;

public class MyDateDialogFragment extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(getActivity(), (view, year1, month1, dayOfMonth) -> {
            String date = String.format("%02d.%02d.%d", dayOfMonth, month1 + 1, year1);
            View rootView = getActivity().findViewById(android.R.id.content);
            if (rootView != null) {
                Snackbar.make(rootView, "Выбрана дата: " + date, Snackbar.LENGTH_SHORT).show();
            }
        }, year, month, day);
    }
}