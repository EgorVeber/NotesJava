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
    private static final String TAG = "MainActivity";

    private CardNoteSourse sourse = CardNoteSourseImpl.getInstance() ;
    List<CardNote> notes =sourse.getAll();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        setTitle("RecyclerView");

        if(savedInstanceState==null)
        {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new MainFragment(),"tagg").commit();
        }
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
        {
            if(notes.size()==0)
            {
                getSupportFragmentManager().beginTransaction().replace(R.id.edit_fragment_container, new EditNoteFragment()).commit();
            }
            else
            {
                getSupportFragmentManager().beginTransaction().replace(R.id.edit_fragment_container, EditNoteFragment.newInstance(notes.get(0))).commit();
            }
        }
        else
        {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new MainFragment(),"tagg").commit();
        }
    }
}