package ru.gb.veber.homework_6_notes.java;

import static ru.gb.veber.homework_6_notes.java.MainActivity.MainFragmentTag;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import ru.gb.veber.homework_6_notes.R;
import ru.gb.veber.homework_6_notes.notes.CardNote;



public class EditNoteFragment extends Fragment implements View.OnClickListener {

    //Ключ для аргументов фрагмента
    private static final String CardNoteKey = "CardNoteKey";
    private static final String TAG = "EditNoteFragment";

    CardNote note;
    Button save_button;
    Button delete_button;
    Button add_button;
    EditText edit_country;
    EditText edit_capital;
    EditText edit_population;


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
            requireActivity().getSupportFragmentManager().popBackStack();
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
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
    private void deleteCardNote() {
       MainFragment fragment= (MainFragment)requireActivity().getSupportFragmentManager().findFragmentByTag(MainFragmentTag);
       fragment.deleteSourseAdapter(note);

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
        init(view);

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
    }
    @Override
    public void onClick(View view)
    {
        //Вся логика у нас в MainFragmetn будем обновлять дынные через его метод.
        MainFragment fragment= (MainFragment)requireActivity().getSupportFragmentManager().findFragmentByTag(MainFragmentTag);

        if(note!=null&&fragment!=null)
        {
            if (isLandscape())
            {
                SetText();
            }
            else
            {
                SetText();
                requireActivity().getSupportFragmentManager().popBackStack();
            }
            //Можно конечно обновить replace фрагментов. Ну так наверное по лучше.Не знаю правда можно так или нет.
             fragment.updateSourseAdapter(note);

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
