package ru.gb.veber.homework_6_notes.hom_9;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import ru.gb.veber.homework_6_notes.R;
import ru.gb.veber.homework_6_notes.notes.CardNote;

public class DialogDeleteNote extends DialogFragment {

    public static final String CARDNOTE= "CARDNOTE";
    public static final String POSITION= "POSITION";

    private CardNote note;

    public static DialogDeleteNote getInstance(CardNote note,int position)
    {
        DialogDeleteNote dialog = new DialogDeleteNote();
        Bundle bundle= new Bundle();
        bundle.putSerializable(CARDNOTE,note);
        dialog.setArguments(bundle);
        return dialog;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        note= (CardNote) bundle.getSerializable(CARDNOTE);

        String name="";
        String date="";
        String desc="";

        if(note!=null)
        {
            name=note.getCountry();
            date=note.getCapital();
            desc=note.getPopulation();
        }
        View dialog= LayoutInflater.from(requireContext()).inflate(R.layout.delete_dialog,null);

        TextView dialogName=dialog.findViewById(R.id.edit_country);
        TextView dialogDate=dialog.findViewById(R.id.edit_capital);
        dialogName.setText(name);
        dialogDate.setText(date);


        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());

        builder.setTitle("Удалить заметку ?");
        builder.setView(dialog).setCancelable(true);
        builder.setNegativeButton("Cancel", (dialogInterface, i) -> dialogInterface.cancel());//отмена действи
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(note!=null)
                    ((DialogController)requireActivity()).delete(note);
                dialogInterface.dismiss();
            }
        });
        return builder.create();
    }
}
