package ru.gb.veber.homework_6_notes.java;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ru.gb.veber.homework_6_notes.R;
import ru.gb.veber.homework_6_notes.notes.CardNote;
import ru.gb.veber.homework_6_notes.notes.CardNoteSourse;
import ru.gb.veber.homework_6_notes.notes.CardNoteSourseImpl;
import ru.gb.veber.homework_6_notes.recycler.AdapterNote;

public class MainFragment extends Fragment implements AdapterNote.OnNoteClickListner {


    private static final String TAG = "MainFragment";
    private CardNoteSourse sourse = CardNoteSourseImpl.getInstance() ;
    List<CardNote> notes =sourse.getAll();

    AdapterNote adapters;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        RecyclerView list= view.findViewById(R.id.list);
        adapters = new AdapterNote();
        adapters.SetNote(sourse.getAll());
        adapters.setOnNoteCliclListner(this);
        list.setHasFixedSize(true);
        list.setAdapter(adapters);
        DividerItemDecoration itemDecoration = new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL);
        itemDecoration.setDrawable(getResources().getDrawable(R.drawable.separator, null));
        list.addItemDecoration(itemDecoration);

//        if (savedInstanceState != null) {
//            currentn_not = savedInstanceState.getInt(CURRENT_CARD_NOTE,0);
//            Log.d(TAG, String.valueOf(currentn_not));
//        }
    }

    @Override
    public void onNoteClick(CardNote note) {


//        currentn_not=note.getId();
//        Log.d(TAG, " note = [" + note + "]");
//        Log.d(TAG, " currentn_not = [" + sourse.read(currentn_not) + "]");
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
        {
            requireActivity().
                    getSupportFragmentManager().
                    beginTransaction().setCustomAnimations(R.anim.slide_in,R.anim.fage_out).
                    replace(R.id.edit_fragment_container,EditNoteFragment.newInstance(note)).addToBackStack(null).commit();
          //  Log.d(TAG, " note = [" + note + "]");
         //   Log.d(TAG, " currentn_not = [" + sourse.read(currentn_not) + "]");
        }
        else
        {
            requireActivity().
                    getSupportFragmentManager().
                    beginTransaction().setCustomAnimations(R.anim.slide_in,R.anim.fage_out).
                    replace(R.id.fragment_container,EditNoteFragment.newInstance(note)).addToBackStack(null).commit();
        }
    }



    public void startButtonPressed(String message) {
        adapters.SetNote(sourse.getAll());
    }
}
