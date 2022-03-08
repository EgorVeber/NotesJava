package ru.gb.veber.homework_6_notes.java;

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


    private static final String CardNoteKey = "CardNoteKey";

    private static final String TAG = "EditNoteFragment";



    CardNote note;
    Button button;
    EditText edit_country;
    EditText edit_capital;
    EditText edit_population;

    public static EditNoteFragment newInstance(CardNote note)
    {
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
        if (savedInstanceState != null)
            requireActivity().getSupportFragmentManager().popBackStack();

    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_edit_note,container,false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {



        Bundle fragment_arg = getArguments();

        button = view.findViewById(R.id.save_edit_note);
        edit_country = view.findViewById(R.id.edit_country);
        edit_capital = view.findViewById(R.id.edit_capital);
        edit_population = view.findViewById(R.id.edit_population);

        button.setOnClickListener(this);


        if(fragment_arg!=null)
        {
            note=(CardNote) fragment_arg.getSerializable(CardNoteKey);

           if(note!=null) {
               Log.d(TAG, "onViewCreated note = " + note.toString());
               edit_country.setText(note.getCountry());
               edit_capital.setText(note.getCapital());
               edit_population.setText(note.getPopulation());
           }
        }
        else
        {
            Log.d(TAG, "onViewCreated() Аргументов нет");
        }
    }

    @Override
    public void onClick(View view)
    {
        MainFragment fragment= (MainFragment)requireActivity().getSupportFragmentManager().findFragmentByTag("tagg");


        if(note!=null)
        {

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
        {
            note.setCountry(edit_country.getText().toString());
            note.setCapital(edit_capital.getText().toString());
            note.setPopulation(edit_population.getText().toString());
        }
        else
        {
            note.setCountry(edit_country.getText().toString());
            note.setCapital(edit_capital.getText().toString());
            note.setPopulation(edit_population.getText().toString());
            requireActivity().getSupportFragmentManager().popBackStack();
        }
        fragment.startButtonPressed(note);
        }
    }
}
