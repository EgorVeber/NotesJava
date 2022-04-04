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
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import ru.gb.veber.homework_6_notes.DIalog.DialogDate;
import ru.gb.veber.homework_6_notes.DIalog.ViewModelDialog;
import ru.gb.veber.homework_6_notes.R;
import ru.gb.veber.homework_6_notes.notes.CardNote;



public class EditNoteFragment extends Fragment implements View.OnClickListener {

    //Ключ для аргументов фрагмента
    private static final String CardNoteKey = "CardNoteKey";
    private static final String MENU_ITEM = "MENU_ITEM";
    private static final String TAG = "EditNoteFragment";
    private CardNote note;
    private Button save_button;
    private EditText edit_country;
    private EditText edit_capital;
    private EditText edit_population;
    private Boolean check_saveInstance_menu=false;
    private ActionBar actionBar;
    private Date data_note;
    private SimpleDateFormat format_edit = new SimpleDateFormat("dd.MM.yyyy");

    private ViewModelDialog viewModelDialog;

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

        viewModelDialog = new ViewModelProvider(requireActivity()).get(ViewModelDialog.class);
        //Подписали на данные из диалога в EditText

//        viewModelDialog.getSecondInput().observe
//                (this, s ->data_note=s);
        if (savedInstanceState != null)
        {
            check_saveInstance_menu= savedInstanceState.getBoolean(MENU_ITEM);
            if(isLandscape())
            {
                requireActivity().getSupportFragmentManager().popBackStack();
            }
        }
    }
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        check_saveInstance_menu=true;
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
            if(note!=null)
            {

                edit_country.setText(note.getName());
                edit_capital.setText(format_edit.format(note.getDateDate()));
                edit_population.setText(note.getDescription());
                data_note=note.getDateDate();
            }
        }
        else
        {
            edit_capital.setText(format_edit.format(new Date()));
            data_note=new Date();
        }
    }
    private void init(View view) {
        setHasOptionsMenu(true);
        actionBar = ((AppCompatActivity)requireActivity()).getSupportActionBar();
        if (actionBar != null)
            actionBar.setSubtitle("CardNote");
        save_button = view.findViewById(R.id.save_edit_note);
        save_button.setOnClickListener(this);
        edit_country = view.findViewById(R.id.edit_country);
        edit_capital = view.findViewById(R.id.edit_capital);
        edit_population = view.findViewById(R.id.edit_population);

        edit_capital.setOnClickListener(view1 -> dateClick());
    }
    private void dateClick() {
        viewModelDialog.DateSay(data_note);
        new  DialogDate().show(requireActivity().getSupportFragmentManager(),null);
    }
    public void UpdateEditData(Date date)
    {
        data_note=date;
        edit_capital.setText(format_edit.format(data_note));
    }
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        if(!isLandscape()&& !check_saveInstance_menu)
        {
            inflater.inflate(R.menu.menu_toolbar_edit_fragment, menu);//добавляем назад
            initMenu(menu);
        }
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.back_memu_item_edit_note_fargment)
            requireActivity().getSupportFragmentManager().popBackStack();
        return super.onOptionsItemSelected(item);
    }
    public void initMenu(Menu menu)
    {
        MenuItem item_plus = menu.findItem(R.id.add_item_toolbar_main);
        MenuItem item_reverse = menu.findItem(R.id.sort_reverse_toolbar_main);
        MenuItem item_name = menu.findItem(R.id.sort_name_toolbar_main);
        MenuItem item_id = menu.findItem(R.id.sort_id_toolbar_main);
        MenuItem item_search = menu.findItem(R.id.search_toolbar_main);
        if (item_plus != null)
        {
            item_plus.setVisible(false);
            item_reverse.setVisible(false);
            item_name.setVisible(false);
            item_id.setVisible(false);
            item_search.setVisible(false);
        }
    }
    @Override
    public void onClick(View view)
    {
        MainFragment fragment= (MainFragment)requireActivity().getSupportFragmentManager().findFragmentByTag(MainFragmentTag);
        if(getArguments()!=null)
        {
            if(note!=null && fragment!=null)
            {
                note.setName(edit_country.getText().toString());
                note.setDateDate(data_note);
                note.setDescription(edit_population.getText().toString());
                ((ActivityController)requireActivity()).actionNote(0,note);
            }
        }
        else
        {
           note= new CardNote(edit_country.getText().toString(),edit_population.getText().toString(), data_note);
            ((ActivityController)requireActivity()).actionNote(1,note);
        }
        if(!isLandscape())
            requireActivity().getSupportFragmentManager().popBackStack();
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
