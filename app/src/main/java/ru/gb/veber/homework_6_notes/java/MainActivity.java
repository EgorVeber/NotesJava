package ru.gb.veber.homework_6_notes.java;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import ru.gb.veber.homework_6_notes.R;

public class MainActivity extends AppCompatActivity {

    public static final String MainFragmentTag ="MainFragmentTag";
    public static final String EditNoteFragmentTag ="EditNoteFragmentTag";
    private static final String TAG = "MainActivityTag";

    private Toolbar toolbar;
    private  MainFragment fragment;
    private TextView item_count;
    private TextView profile_name;

    public static final String ThemeFile ="ThemeFile";
    public static final String KeyTheme="KeyTheme";

    public static final String FILE_PROFILE = "FILE_PROFILE";
    public static final String PROFILE_NAME = "PROFILE_NAME";
    private SharedPreferences prefs;
    String getProfileName="Profile_name";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTheme(getThemePref());
        setContentView(R.layout.activity_main);
        fragment= new MainFragment();



        prefs = getSharedPreferences(FILE_PROFILE, MODE_PRIVATE);
        getProfileName= prefs.getString(PROFILE_NAME,"");
        Log.d(TAG, getProfileName);

        initToolbar();

        //Нам нужно создать фрагмент со списком всего лишь один раз — при первом запуске. Задачу по
        //пересозданию фрагментов после поворота экрана берет на себя FragmentManager.
        if(savedInstanceState==null)
        {
            //Запускаем с тегом будет надо потом.

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment,MainFragmentTag).commit();
        }




    }

    private int getThemePref() {

        SharedPreferences sharedPref = getSharedPreferences(ThemeFile, MODE_PRIVATE);
        int NumTheme = sharedPref.getInt(KeyTheme, 0);

        switch(NumTheme){
            case 0:
                return R.style.Theme_Homework_6_notes;
            case 1:
                return R.style.Theme_Homework_6_notes_Test;
            default:
                return R.style.Theme_Homework_6_notes;
        }
    }

    private void initToolbar() {
        toolbar = findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);

        if(!isLandscape())
        {
            initDraver();
        }
    }


    private void initDraver()
    {

        DrawerLayout drawerLayout= findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView view = findViewById(R.id.navigation_view);
        view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch (id) {
                    case R.id.main_item_draver:
                        for (int i=0;i<getSupportFragmentManager().getBackStackEntryCount();i++) {
                            getSupportFragmentManager().popBackStack();
                        }
                        drawerLayout.close();
                        return true;
                    case R.id.settings_drawer_exit:
                        getSupportFragmentManager().
                                beginTransaction().
                                replace(R.id.fragment_container,new SettingFragment()).
                                addToBackStack(null).commit();
                        drawerLayout.close();
                       // getSupportActionBar().hide();
                        return true;
                    case R.id.exit_item_draver:
                        finish();
                        return true;
                }
                return false;
            }
        });

        View header = view.getHeaderView(0);
        item_count = (TextView) header.findViewById(R.id.item_count);
        item_count.setText(""+fragment.getItemCount());
        profile_name=(TextView) header.findViewById(R.id.profile_name);
        profile_name.setText(getProfileName);

        //prefs.edit().putString(NUM1, calState.getNum1()).commit();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar_main,menu);
        return super.onCreateOptionsMenu(menu);
    }




    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {


        fragment= (MainFragment)getSupportFragmentManager().findFragmentByTag(MainFragmentTag);
        switch (item.getItemId())
        {
            case R.id.item_1_toolbar_main:
                Log.d(TAG, "item_1_toolbar_main");
                if(isLandscape())
                {
                    getSupportFragmentManager().
                            beginTransaction().
                            replace(R.id.edit_fragment_container,new EditNoteFragment(),EditNoteFragmentTag).
                            addToBackStack(null).commit();
                }
                else
                {
                    getSupportFragmentManager().
                            beginTransaction().
                            replace(R.id.fragment_container,new EditNoteFragment(),EditNoteFragmentTag).
                            addToBackStack(null).commit();
                }
                return true;
            case R.id.sort_reverse_toolbar_main:
                fragment.sortReverse();
                Toast.makeText(this,"Сортировка в обратном порядке",Toast.LENGTH_SHORT).show();
                return true;
            case R.id.sort_name_toolbar_main:
                fragment.sortName();
                Toast.makeText(this,"Сортировка по названию заметки",Toast.LENGTH_SHORT).show();
                return true;
            case R.id.sort_id_toolbar_main:
                fragment.sortId();
                Toast.makeText(this,"Изначальный список",Toast.LENGTH_SHORT).show();
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

    public boolean isLandscape() {
        return getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_LANDSCAPE;
    }
}