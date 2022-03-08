package ru.gb.veber.homework_6_notes.java;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;

import java.util.List;

import ru.gb.veber.homework_6_notes.R;
import ru.gb.veber.homework_6_notes.notes.CardNote;
import ru.gb.veber.homework_6_notes.notes.CardNoteSourse;
import ru.gb.veber.homework_6_notes.notes.CardNoteSourseImpl;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        setTitle("RecyclerView");

        if(savedInstanceState==null)
        {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new MainFragment(),"tagg").commit();
        }
    }
}