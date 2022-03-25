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
    CardNote note;
    Button save_button;
    Button delete_button;
    Button add_button;
    EditText edit_country;
    EditText edit_capital;
    EditText edit_population;
    Boolean check_saveInstance_menu=false;
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
        }
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        //В onCreate с помощью setHasOptionsMenu включаем режим вывода элементов фрагмента в ActionBar.
        setHasOptionsMenu(true);//Эта строчка говорит о том, что у фрагмента должен быть доступ к меню Активити.

        ActionBar actionBar = ((AppCompatActivity)requireActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setSubtitle("CardNote");
        }
        return inflater.inflate(R.layout.fragment_edit_note,container,false);
    }
    private void init(View view) {

        save_button = view.findViewById(R.id.save_edit_note);
        delete_button = view.findViewById(R.id.delete_edit_note);
        add_button = view.findViewById(R.id.add_edit_note);
        edit_country = view.findViewById(R.id.edit_country);
        edit_capital = view.findViewById(R.id.edit_capital);
        edit_population = view.findViewById(R.id.edit_population);
        save_button.setOnClickListener(this);


        if (delete_button!=null)
        {
            delete_button.setOnClickListener(view1 -> {
                deleteCardNote();
            });
            add_button.setOnClickListener(view12 -> {
                addCardNote();
            });
        }
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
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putBoolean(MENU_ITEM, true);
        super.onSaveInstanceState(outState);
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
    public void onDetach() {
        ActionBar actionBar = ((AppCompatActivity)requireActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setSubtitle("ListNotes");
        }
        super.onDetach();
    }

    private void deleteCardNote() {
       MainFragment fragment= (MainFragment)requireActivity().getSupportFragmentManager().findFragmentByTag(MainFragmentTag);
       if(note!=null)
       {
           fragment.deleteSourseAdapter(note);
       }
        requireActivity().getSupportFragmentManager().popBackStack();
    }
    private void addCardNote() {

        CardNote cardNote= new CardNote(edit_country.getText().toString(),edit_capital.getText().toString(),edit_population.getText().toString());
        MainFragment fragment= (MainFragment)requireActivity().getSupportFragmentManager().findFragmentByTag(MainFragmentTag);
        fragment.addSourseAdapter(cardNote);
        requireActivity().getSupportFragmentManager().popBackStack();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Bundle fragment_arg = getArguments();
        if(savedInstanceState!=null)
        {
            check_saveInstance_menu= savedInstanceState.getBoolean(MENU_ITEM);
        }
        init(view);
        setHasOptionsMenu(true);
        //В данном случии нулевые непускаем сюда сделали так что аргументы и заментка они у нас никогда не будут равны 0 ну оставим на всякий сулчай.
        if(fragment_arg!=null)
        {
            note=(CardNote) fragment_arg.getSerializable(CardNoteKey);
            if(note!=null) {
               edit_country.setText(note.getCountry());
               edit_capital.setText(note.getCapital());
               edit_population.setText(note.getPopulation());
           }
        }
        else
        {

        }
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
            SetText();
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
    public void SetText()
    {
        note.setCountry(edit_country.getText().toString());
        note.setCapital(edit_capital.getText().toString());
        note.setPopulation(edit_population.getText().toString());
    }
    public boolean isLandscape() {
        return getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_LANDSCAPE;
    }
}
