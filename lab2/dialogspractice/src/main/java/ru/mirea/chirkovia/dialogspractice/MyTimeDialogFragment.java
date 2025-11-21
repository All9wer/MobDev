package ru.mirea.chirkovia.dialogspractice;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import android.view.View;
import java.util.Calendar;
import com.google.android.material.snackbar.Snackbar;

public class MyTimeDialogFragment extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        return new TimePickerDialog(getActivity(), (view, hourOfDay, minute1) -> {
            String time = String.format("%02d:%02d", hourOfDay, minute1);
            View rootView = getActivity().findViewById(android.R.id.content);
            if (rootView != null) {
                Snackbar.make(rootView, "Выбрано время: " + time, Snackbar.LENGTH_SHORT).show();
            }
        }, hour, minute, true);
    }
}