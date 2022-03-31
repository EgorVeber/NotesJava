package ru.gb.veber.homework_6_notes.DIalog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import ru.gb.veber.homework_6_notes.R;
import ru.gb.veber.homework_6_notes.java.ActivityController;
import ru.gb.veber.homework_6_notes.notes.CardNote;

public class DialogDeleteNote extends DialogFragment {

    public static final String NOTE= "NOTE";
    public static final String POSITION= "POSITION";

    private CardNote note;
    AlertDialog alertDialog;
    public static DialogDeleteNote getInstance(CardNote note,int position)
    {
        DialogDeleteNote dialog = new DialogDeleteNote();
        Bundle bundle= new Bundle();
        bundle.putSerializable(NOTE,note);
        bundle.putInt(POSITION,position);
        dialog.setArguments(bundle);
        return dialog;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        note= (CardNote) bundle.getSerializable(NOTE);
        int position = bundle.getInt(POSITION);
        String name="";
        String date="";

        if(note!=null)
        {
            name=note.getCountry();
            date=note.getCapital();
        }
        View dialog= LayoutInflater.from(requireContext()).inflate(R.layout.delete_dialog,null);

        TextView dialogName=dialog.findViewById(R.id.edit_country);
        TextView dialogDate=dialog.findViewById(R.id.edit_capital);
        Button PositiveButton =dialog.findViewById(R.id.PositiveButton);
        Button NegativeButton =dialog.findViewById(R.id.NegativeButton);

        PositiveButton.setOnClickListener(view -> {
            if(note!=null)
                ((ActivityController)requireActivity()).delete(note,position);
            dismiss();
        });
        NegativeButton.setOnClickListener(view -> dismiss());


        dialogName.setText(name);
        dialogDate.setText(date);


        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext(),R.style.CustomDialogTheme);
        builder.setTitle("Удалить заметку ?");
        builder.setView(dialog).setCancelable(true);
        return builder.create();
    }
}
