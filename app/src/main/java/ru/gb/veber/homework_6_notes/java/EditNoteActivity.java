package ru.gb.veber.homework_6_notes.java;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import ru.gb.veber.homework_6_notes.R;
import ru.gb.veber.homework_6_notes.notes.CardNote;

public class EditNoteActivity extends AppCompatActivity {


    private static final String TAG = "CardNotes";
    CardNote note;
    Button button;
    EditText edit_country;
    EditText edit_capital;
    EditText edit_population;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);
        setTitle("RecyclerViewEdit");
        note=(CardNote) getIntent().getSerializableExtra(CardNote.NOTE);


        button = findViewById(R.id.save_edit_note);
        edit_country = findViewById(R.id.edit_country);
        edit_capital = findViewById(R.id.edit_capital);
        edit_population = findViewById(R.id.edit_population);

        edit_country.setText(note.getCountry());
        edit_capital.setText(note.getCapital());
        edit_population.setText(note.getPopulation());

        button.setOnClickListener(view -> {

            SaveNote();

        });
    }
    public void SaveNote()
    {
        note.setCountry(edit_country.getText().toString());
        note.setCapital(edit_capital.getText().toString());
        note.setPopulation(edit_population.getText().toString());
        Intent intent = new Intent();
        intent.putExtra(CardNote.NOTE,note);
        setResult(RESULT_OK,intent);
        finish();
    }
}