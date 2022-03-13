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

    public static final String MainFragmentTag ="MainFragmentTag";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        setTitle("RecyclerView");

        //Нам нужно создать фрагмент со списком всего лишь один раз — при первом запуске. Задачу по
        //пересозданию фрагментов после поворота экрана берет на себя FragmentManager.
        if(savedInstanceState==null)
        {
            //Запускаем с тегом будет надо потом.
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new MainFragment(),MainFragmentTag).commit();
        }
    }
}