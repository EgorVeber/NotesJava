package ru.gb.veber.homework_6_notes.java;

import static ru.gb.veber.homework_6_notes.java.MainActivity.MainFragmentTag;

import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import ru.gb.veber.homework_6_notes.R;
import ru.gb.veber.homework_6_notes.notes.CardNote;
import ru.gb.veber.homework_6_notes.notes.CardNoteSourse;
import ru.gb.veber.homework_6_notes.notes.CardNoteSourseImpl;
import ru.gb.veber.homework_6_notes.recycler.AdapterNote;

public class EditNoteFragment extends Fragment implements View.OnClickListener {

    //Ключ для аргументов фрагмента
    private static final String CardNoteKey = "CardNoteKey";

    CardNote note;
    Button button;
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
        button = view.findViewById(R.id.save_edit_note);
        edit_country = view.findViewById(R.id.edit_country);
        edit_capital = view.findViewById(R.id.edit_capital);
        edit_population = view.findViewById(R.id.edit_population);
        button.setOnClickListener(this);
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
        if(note!=null)
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
