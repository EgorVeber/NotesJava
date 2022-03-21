package ru.gb.veber.homework_6_notes.java;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import ru.gb.veber.homework_6_notes.R;

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