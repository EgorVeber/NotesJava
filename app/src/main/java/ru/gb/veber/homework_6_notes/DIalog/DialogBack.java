package ru.gb.veber.homework_6_notes.DIalog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import ru.gb.veber.homework_6_notes.R;
import ru.gb.veber.homework_6_notes.java.ActivityController;

public class DialogBack extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        TextView textView = new TextView(requireContext());
        textView.setText(getResources().getString(R.string.exit_dialog));
        textView.setPadding(20, 30, 20, 30);
        //textView.setGravity(Gravity.CENTER);
        textView.setTextSize(20F);
        //textView.setBackgroundColor(Color.CYAN);
        textView.setTextColor(getResources().getColor(R.color.drawer_text_color));

        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext(),R.style.CustomDialogTheme);
        builder.setTitle(getResources().getString(R.string.exit_dialog));
        builder.setNegativeButton("Cansel", (dialogInterface, i) -> dialogInterface.cancel());
        builder.setPositiveButton("OK", (dialogInterface, i) -> {
                ((ActivityController)requireContext()).backClick();
                dialogInterface.dismiss();
        });
        return builder.create();
    }
}
