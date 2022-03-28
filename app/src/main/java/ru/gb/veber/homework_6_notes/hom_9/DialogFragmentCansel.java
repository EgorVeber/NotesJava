package ru.gb.veber.homework_6_notes.hom_9;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class DialogFragmentCansel extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Уже уходите?");
        builder.setNegativeButton("Cansel", (dialogInterface, i) -> dialogInterface.cancel());
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                    ((DialogController)requireContext()).backClick();
                    dialogInterface.dismiss();
            }
        });
        return builder.create();
    }
}
