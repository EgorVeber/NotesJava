package ru.gb.veber.homework_6_notes.java;

import static ru.gb.veber.homework_6_notes.java.MainActivity.MainFragmentTag;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import ru.gb.veber.homework_6_notes.R;
import ru.gb.veber.homework_6_notes.notes.CardNote;



public class EditNoteFragment extends Fragment implements View.OnClickListener {

    //Ключ для аргументов фрагмента
    private static final String CardNoteKey = "CardNoteKey";
    private static final String TAG = "EditNoteFragment";
    private static final String MENU_ITEM = "MENU_ITEM";

   private CardNote note;
   private Button save_button;
   private EditText edit_country;
   private EditText edit_capital;
   private EditText edit_population;
   private Boolean check_saveInstance_menu=false;

    ActionBar actionBar;

    public static EditNoteFragment newInstance(CardNote note)
    {
        //Фабричный метод с аргументами
        EditNoteFragment fragment = new EditNoteFragment();
        // Передача параметра через бандл
        Bundle args = new Bundle();
        args.putSerializable(CardNoteKey,note);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Очищаем стек тк при перевороче экрана воссаздает всеь стек, в даннм случии один лишний будет
        if (savedInstanceState != null)
        {
            requireActivity().getSupportFragmentManager().popBackStack();
            check_saveInstance_menu= savedInstanceState.getBoolean(MENU_ITEM);//Нужно чтобы не рисовать меню занова если переворачиваем
        }
    }
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putBoolean(MENU_ITEM, true);
        super.onSaveInstanceState(outState);
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_edit_note,container,false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        init(view);
        if(getArguments()!=null)
        {
            note=(CardNote)getArguments().getSerializable(CardNoteKey);
            SetText();
        }
    }
    private void init(View view) {
        setHasOptionsMenu(true);
        //В onCreate с помощью setHasOptionsMenu включаем режим вывода элементов фрагмента в ActionBar.
        //Эта строчка говорит о том, что у фрагмента должен быть доступ к меню Активити.
        actionBar = ((AppCompatActivity)requireActivity()).getSupportActionBar();
        if (actionBar != null)
            actionBar.setSubtitle("CardNote");

        save_button = view.findViewById(R.id.save_edit_note);
        save_button.setOnClickListener(this);
        edit_country = view.findViewById(R.id.edit_country);
        edit_capital = view.findViewById(R.id.edit_capital);
        edit_population = view.findViewById(R.id.edit_population);
    }
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {

        Log.d(TAG, "onCreateOptionsMenu() ");
        if(!check_saveInstance_menu)
        {
            //скрываем всегда, надуваем новое меню только если порт
            MenuItem item = menu.findItem(R.id.add_item_toolbar_main);//Скрываем новый
            MenuItem item2 = menu.findItem(R.id.sort_reverse_toolbar_main);//Скрываем новый
            MenuItem item3 = menu.findItem(R.id.sort_name_toolbar_main);//Скрываем новый
            MenuItem item4 = menu.findItem(R.id.sort_id_toolbar_main);//Скрываем новый
            MenuItem item5 = menu.findItem(R.id.search_toolbar_main);//Скрываем новый
            if(!isLandscape())
            {
                inflater.inflate(R.menu.menu_toolbar_edit_fragment, menu);//добавляем назад
                if (item != null) {
                    item.setVisible(false);
                    item2.setVisible(false);
                    item3.setVisible(false);
                    item4.setVisible(false);
                    item5.setVisible(false);
                }
            }
            else{
                item5.setVisible(false);
            }
        }
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId()==R.id.back_memu_item_edit_note_fargment)
        {
            requireActivity().getSupportFragmentManager().popBackStack();
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onClick(View view)
    {
        //Вся логика у нас в MainFragmetn будем обновлять дынные через его метод.
        MainFragment fragment= (MainFragment)requireActivity().getSupportFragmentManager().findFragmentByTag(MainFragmentTag);
        Log.d(TAG, "onClick()");
        if(getArguments()!=null)
        {
            Log.d(TAG, "fragment_arg!=null");
        if(note!=null&&fragment!=null)
        {
            if (isLandscape())
            {
           //     fragment.addSourseAdapter(new CardNote(edit_country.getText().toString(),edit_capital.getText().toString(),edit_population.getText().toString()));
            }
            else
            {
                requireActivity().getSupportFragmentManager().popBackStack();
            }
            SetNote();
            //Можно конечно обновить replace фрагментов. Ну так наверное по лучше.Не знаю правда можно так или нет.
             fragment.updateSourseAdapter(note);
        }
        }
        else
        {
            Log.d(TAG, "fragment_arg=null");
            fragment.addSourseAdapter(new CardNote(edit_country.getText().toString(),edit_capital.getText().toString(),edit_population.getText().toString()));
           if(!isLandscape())
           {
               requireActivity().getSupportFragmentManager().popBackStack();
           }
        }
    }
    public void SetNote()
    {
        note.setCountry(edit_country.getText().toString());
        note.setCapital(edit_capital.getText().toString());
        note.setPopulation(edit_population.getText().toString());
    }
    public void SetText()
    {
        if(note!=null)
        {
            edit_country.setText(note.getCountry());
            edit_capital.setText(note.getCapital());
            edit_population.setText(note.getPopulation());
        }
    }
    public boolean isLandscape() {
        return getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_LANDSCAPE;
    }
    @Override
    public void onDetach() {
        if (actionBar != null) {
            actionBar.setSubtitle("ListNotes");
        }
        super.onDetach();
    }
}
