package ru.gb.veber.homework_6_notes.DIalog;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import java.util.Calendar;
import java.util.Date;

import ru.gb.veber.homework_6_notes.R;
import ru.gb.veber.homework_6_notes.java.ActivityController;
import ru.gb.veber.homework_6_notes.notes.CardNote;

public class DialogDate extends DialogFragment {
    private static final String TAG = "DialogDate";
    private DatePicker datePicker;
    private static final String DATE_NOTE = "DATE_NOTE";
    private ViewModelDialog viewModelDialog;

    Button button;
//    Date note_temp;

//    public static DialogDate getInstance(Date date_note) {
//        DialogDate fragment = new DialogDate();
//        Bundle args = new Bundle();
//        args.putSerializable(DATE_NOTE, date_note);
//        fragment.setArguments(args);
//        return fragment;
//    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModelDialog = new ViewModelProvider(requireActivity()).get(ViewModelDialog.class);
        viewModelDialog.getDate().observe
                (this, date ->initDatePicker(date));
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.date_dialog,container,false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        button= view.findViewById(R.id.PositiveButtonDate);
        datePicker = view.findViewById(R.id.inputDate);
        button.setOnClickListener(view1 -> {
            ((ActivityController)requireActivity()).dateUpdate(getDateFromDatePicker());
            //viewModelDialog.secondSay(note_temp);//передали данные в EditText
            dismiss();
        });
    }
    private void initDatePicker(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        this.datePicker.init(calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH),
                null);
    }
    private Date getDateFromDatePicker() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, this.datePicker.getYear());
        cal.set(Calendar.MONTH, this.datePicker.getMonth());
        cal.set(Calendar.DAY_OF_MONTH, this.datePicker.getDayOfMonth());
        return cal.getTime();
    }
}
