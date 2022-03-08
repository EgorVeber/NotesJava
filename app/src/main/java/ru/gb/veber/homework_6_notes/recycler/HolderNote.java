package ru.gb.veber.homework_6_notes.recycler;

import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import ru.gb.veber.homework_6_notes.R;
import ru.gb.veber.homework_6_notes.notes.CardNote;

public class HolderNote extends RecyclerView.ViewHolder {


    private TextView country;
    private TextView capital;
    private TextView population;
    private CardNote note;

    //Принимает view тоесть элемент   itemView - xml наш
    public HolderNote(@NonNull View itemView, AdapterNote.OnNoteClickListner listner) {
        super(itemView);
        //Ссылки которые в послдестви буду переопределять
        country=itemView.findViewById(R.id.country);
        capital=itemView.findViewById(R.id.capital);
        population=itemView.findViewById(R.id.population);
        itemView.setOnClickListener(view -> {
            //Теперь когда мы щелкаем можем передавать данные о ноте на которой щелкаем
            listner.onNoteClick(note);// элемент может сообщать всем на какую произощел щелчок
        });
    }
    void bind(CardNote note)
    {
        // note содержимым переопределить TextView
        this.note=note;
        country.setText(note.getCountry());
        capital.setText(note.getCapital());
        population.setText(note.getPopulation());
    }
}
