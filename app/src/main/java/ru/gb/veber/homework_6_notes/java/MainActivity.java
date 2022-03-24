package ru.gb.veber.homework_6_notes.java;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.List;

import ru.gb.veber.homework_6_notes.R;

public class MainActivity extends AppCompatActivity {

    public static final String MainFragmentTag ="MainFragmentTag";
    public static final String EditNoteFragmentTag ="EditNoteFragmentTag";
    private static final String TAG = "MainActivityTag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initToolbar();
        //Нам нужно создать фрагмент со списком всего лишь один раз — при первом запуске. Задачу по
        //пересозданию фрагментов после поворота экрана берет на себя FragmentManager.
        if(savedInstanceState==null)
        {
            //Запускаем с тегом будет надо потом.
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new MainFragment(),MainFragmentTag).commit();
        }
    }
    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar_main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.item_1_toolbar_main:
                Log.d(TAG, "item_1_toolbar_main");
                getSupportFragmentManager().
                        beginTransaction().
                        replace(R.id.fragment_container,new EditNoteFragment(),EditNoteFragmentTag).
                        addToBackStack(null).commit();
                return true;
            case R.id.item_2_toolbar_main:

//                MainFragment fragment= (MainFragment)getSupportFragmentManager().findFragmentByTag(MainFragmentTag);
//                fragment.sortTest();
                return true;
            case R.id.item_3_toolbar_main:
                Log.d(TAG, "item_3_toolbar_main");
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {
        if(getSupportFragmentManager().getBackStackEntryCount()==0)
        {
            //TODO 9 ДЗ
            finish();
        }
        super.onBackPressed();
        //Метод getFragments Менеджера фрагментов возвращает нам список фрагментов в нашем стеке
//        for (Fragment f: fragments) {
//            Log.d(TAG, String.valueOf(f.getClass().getName()));
//        }
    }
}