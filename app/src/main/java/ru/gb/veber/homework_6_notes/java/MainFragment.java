package ru.gb.veber.homework_6_notes.java;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import ru.gb.veber.homework_6_notes.R;
import ru.gb.veber.homework_6_notes.notes.CardNote;
import ru.gb.veber.homework_6_notes.notes.CardNoteSourse;
import ru.gb.veber.homework_6_notes.notes.CardNoteSourseImpl;
import ru.gb.veber.homework_6_notes.recycler.AdapterNote;

public class MainFragment extends Fragment implements AdapterNote.OnNoteClickListner{

    private static final String TAG = "MainFragment";
    private CardNoteSourse sourse = CardNoteSourseImpl.getInstance() ;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        RecyclerView list= view.findViewById(R.id.list);
        AdapterNote adapters = new AdapterNote();
        adapters.SetNote(sourse.getAll());

        adapters.setOnNoteCliclListner(this);

        list.setHasFixedSize(true);
        list.setAdapter(adapters);


        DividerItemDecoration itemDecoration = new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL);
        itemDecoration.setDrawable(getResources().getDrawable(R.drawable.separator, null));
        list.addItemDecoration(itemDecoration);
    }

    @Override
    public void onNoteClick(CardNote note) {

            requireActivity().
                    getSupportFragmentManager().
                    beginTransaction().
                    replace(R.id.fragment_container,EditNoteFragment.newInstance(note)).addToBackStack(null).commit();
    }
}
