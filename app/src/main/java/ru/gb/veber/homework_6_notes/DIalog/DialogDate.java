package ru.gb.veber.homework_6_notes.DIalog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
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

import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import ru.gb.veber.homework_6_notes.R;
import ru.gb.veber.homework_6_notes.java.ActivityController;
import ru.gb.veber.homework_6_notes.notes.CardNote;

public class DialogDate extends DialogFragment {
    private static final String TAG = "DialogDate";
    DatePicker datePicker;
    private static final String CARD_NOTE = "CARD_NOTE";
    private ViewModelDialog viewModelDialog;

    Button button;
    CardNote note;

//    public static DialogDate getInstance(CardNote note) {
//        DialogDate fragment = new DialogDate();
//        Bundle args = new Bundle();
//        args.putSerializable(CARD_NOTE, note);
//        fragment.setArguments(args);
//        return fragment;
//    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModelDialog = new ViewModelProvider(requireActivity()).get(ViewModelDialog.class);
        viewModelDialog.getDate().observe
                (this, s -> initDatePicker(s.getDateDate(),s));
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.date_dialog,container,false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        if(getArguments()!=null)
        {
            note=(CardNote)getArguments().getSerializable(CARD_NOTE);
            Log.d(TAG, note.toString());
        }
        button= view.findViewById(R.id.PositiveButtonDate);
        datePicker = view.findViewById(R.id.inputDate);
        //initDatePicker(note.getDateDate());

        button.setOnClickListener(view1 -> {
            note.setDateDate(getDateFromDatePicker());
            //((ActivityController)requireActivity()).dateUpdate(note);
            viewModelDialog.secondSay(note);//передали данные в EditText
            dismiss();

        });
    }
    private void initDatePicker(Date date,CardNote notes) {
        note=notes;
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
