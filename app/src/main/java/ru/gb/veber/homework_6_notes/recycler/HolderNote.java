package ru.gb.veber.homework_6_notes.recycler;

import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;

import ru.gb.veber.homework_6_notes.R;
import ru.gb.veber.homework_6_notes.notes.CardNote;

public class HolderNote extends RecyclerView.ViewHolder implements PopupMenu.OnMenuItemClickListener {

    private ImageView imageView;
    private TextView country;
    private TextView capital;
    private TextView population;
    private CardNote note;

    PopupMenu popupMenu;
    OnNoteClickListner listner;

    public HolderNote(@NonNull View itemView, OnNoteClickListner listner) {
        super(itemView);

        this.listner=listner;

        country=itemView.findViewById(R.id.country);
        capital=itemView.findViewById(R.id.capital);
        population=itemView.findViewById(R.id.population);
        imageView=itemView.findViewById(R.id.note_menu);

        popupMenu= new PopupMenu(itemView.getContext(),itemView, Gravity.RIGHT);
        popupMenu.inflate(R.menu.popap_menu);

        itemView.setOnClickListener(view -> {
            listner.onNoteClick(note);
        });
        imageView.setOnClickListener(view -> {
            popupMenu.show();
        });
        itemView.setOnLongClickListener(view ->{
            popupMenu.show();
            return false;
        });
        popupMenu.setOnMenuItemClickListener(this);
    }
    void bind(CardNote note)
    {
        // note содержимым переопределить TextView
        this.note=note;
        country.setText(note.getName());
        capital.setText(new SimpleDateFormat("dd-MM-yy").format(note.getDateDate()));
        population.setText(note.getDescription());

    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.delete_popam_menu:
                listner.onLondNoteClick(note,getAdapterPosition());
                return true;
        }
        return false;
    }
}
