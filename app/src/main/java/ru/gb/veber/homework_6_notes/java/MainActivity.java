package ru.gb.veber.homework_6_notes.java;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import ru.gb.veber.homework_6_notes.R;

public class MainActivity extends AppCompatActivity {

    //SharedPreferences
    public static final String ThemeFile ="ThemeFile";
    public static final String KeyTheme="KeyTheme";
    public static final String FILE_PROFILE = "FILE_PROFILE";
    public static final String PROFILE_NAME = "PROFILE_NAME";
    private SharedPreferences prefs;
    String getProfileName="Profile_name";

    //Новое
    public static final String MainFragmentTag ="MainFragmentTag";

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    private  MainFragment fragment;
    private  FragmentManager fragmentManager;

    private TextView item_count;
    private TextView profile_name;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar_main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //TODO можно убрать
        setTheme(getThemePref());
        setContentView(R.layout.activity_main);

        prefs = getSharedPreferences(FILE_PROFILE, MODE_PRIVATE);
        getProfileName= prefs.getString(PROFILE_NAME,getProfileName);

        init();
        if(savedInstanceState==null)
            fragmentManager.beginTransaction().replace(R.id.fragment_container,fragment,MainFragmentTag).commit();
    }
    private void init() {

        fragmentManager= getSupportFragmentManager();
        fragment= new MainFragment();
        toolbar = findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        if(!isLandscape())
        {
            initDraver();//Пока только для PORT

            //Обновляем кол-во заметок и профиль
            View header = navigationView.getHeaderView(0);

            item_count = header.findViewById(R.id.item_count);
            item_count.setText(""+fragment.getItemCount());

            profile_name= header.findViewById(R.id.profile_name);
            profile_name.setText(getProfileName);
        }
    }
    private void initDraver()
    {
        //DrawerLayout + гамбургер и привязка к toolbar
        drawerLayout= findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView= findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
           return metodSetNavigationItemSelectedListener(id);
        });
    }
    //Меню Драйвера
    public boolean metodSetNavigationItemSelectedListener (int id)
    {
        // getSupportActionBar().hide();
        switch (id) {
            case R.id.main_item_draver:
                for (int i=0;i<fragmentManager.getBackStackEntryCount();i++)
                    fragmentManager.popBackStack();//Очищаем все в стеке
                break;
            case R.id.settings_drawer_exit:
                showFragment(R.id.fragment_container,new SettingFragment(),true);
            break;
            case R.id.exit_item_draver:
                    finish();
              break;
        }
        drawerLayout.close();
        return false;
    }
    public void showFragment(int container, Fragment fragment,boolean flag)
    {
        if(flag)
            fragmentManager.beginTransaction().replace(container,fragment).addToBackStack(null).commit();
        else
            fragmentManager.beginTransaction().replace(container,fragment).commit();
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        fragment= (MainFragment)fragmentManager.findFragmentByTag(MainFragmentTag);
        switch (item.getItemId())
        {
            case R.id.add_item_toolbar_main:
                if(isLandscape())
                    showFragment(R.id.edit_fragment_container,new EditNoteFragment(),true);
                else
                    showFragment(R.id.fragment_container,new EditNoteFragment(),true);
                return true;
            case R.id.sort_reverse_toolbar_main:
                fragment.sortReverse();
                return toastMessage("Сортировка в обратном порядке");
            case R.id.sort_name_toolbar_main:
                fragment.sortName();
                return toastMessage("Сортировка по названию заметки");
            case R.id.sort_id_toolbar_main:
                fragment.sortId();
                return toastMessage("Изначальный список");
        }
        return super.onOptionsItemSelected(item);
    }
    private int getThemePref() {
        prefs = getSharedPreferences(ThemeFile, MODE_PRIVATE);
        int NumTheme = prefs.getInt(KeyTheme, 0);
        switch(NumTheme){
            case 0: return R.style.Theme_Homework_6_notes;
            case 1: return R.style.Theme_Homework_6_notes_Test;
            default: return R.style.Theme_Homework_6_notes;
        }
    }
    public boolean isLandscape() {
        return getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_LANDSCAPE;
    }
    public boolean toastMessage(String s)
    {
        Toast.makeText(this,s,Toast.LENGTH_SHORT).show();
        return true;
    }
    @Override
    public void onBackPressed() {
        if(fragmentManager.getBackStackEntryCount()==0)
            finish(); //TODO 9 ДЗ
        super.onBackPressed();
    }
}