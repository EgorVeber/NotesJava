package ru.gb.veber.homework_6_notes.java;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

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

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    public static final String MainFragmentTag ="MainFragmentTag";
    private static final String TAG = "MainActivity";
    //SharedPreferences
    private SharedPreferences prefs;
    public static final String FILE_PROFILE = "FILE_PROFILE";
    public static final String KEY_THEME ="KeyTheme";
    public static final String KEY_PROFILE = "PROFILE_NAME";
    public static final String KEY_CHECKBOX = "KeyCheckbox";
    String getProfileName="Profile_name";

    private  MainFragment fragment;
    private  FragmentManager fragmentManager;

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    private TextView item_count;
    private TextView profile_name;

   private MenuItem item_plus;
   private MenuItem item_reverse;
   private MenuItem item_sort_id;
   private MenuItem item_sort_name;
   private MenuItem searchItem;
   private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prefs = getSharedPreferences(FILE_PROFILE, MODE_PRIVATE);
        setTheme(getThemePref());
        setContentView(R.layout.activity_main);

        init();
        showToolBar();
        if(savedInstanceState==null)
            fragmentManager.beginTransaction().replace(R.id.fragment_container,fragment,MainFragmentTag).commit();
    }
    private void init() {

        getProfileName= prefs.getString(KEY_PROFILE,getProfileName);

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
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar_main,menu);

        item_plus = menu.findItem(R.id.add_item_toolbar_main);
        item_reverse = menu.findItem(R.id.sort_reverse_toolbar_main);
        item_sort_id = menu.findItem(R.id.sort_id_toolbar_main);
        item_sort_name= menu.findItem(R.id.sort_name_toolbar_main);

        searchItem = menu.findItem(R.id.search_toolbar_main);
        searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(this);

        MenuItemCompat.setOnActionExpandListener(searchItem, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                return true;
            }
            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                hideTollbarItem(true);
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    private void hideTollbarItem(boolean flag) {
        item_plus.setVisible(flag);
        item_reverse.setVisible(flag);
        item_sort_id.setVisible(flag);
        item_sort_name.setVisible(flag);
    }
    @Override
    public boolean onQueryTextChange(String newText) {
        fragment.getAdapter().getFilter().filter(newText);
        return true;
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
            case R.id.search_toolbar_main:
                hideTollbarItem(false);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    public void showFragment(int container, Fragment fragment,boolean flag)
    {
        if(flag)
            fragmentManager.beginTransaction().setCustomAnimations(R.anim.slide_in,R.anim.fage_out).replace(container,fragment).addToBackStack(null).commit();
        else
            fragmentManager.beginTransaction().setCustomAnimations(R.anim.slide_in,R.anim.fage_out).replace(container,fragment).commit();
    }
    private void showToolBar() {
        boolean chechBox = prefs.getBoolean(KEY_CHECKBOX,false);
        if(chechBox)
            getSupportActionBar().hide();
    }
    private int getThemePref() {
        int NumTheme = prefs.getInt(KEY_THEME, 0);
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
    @Override
    public boolean onQueryTextSubmit(String query) {
        return true;
    }
}