package ru.gb.veber.homework_6_notes.java;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import ru.gb.veber.homework_6_notes.R;
import ru.gb.veber.homework_6_notes.notes.CardNote;
import ru.gb.veber.homework_6_notes.notes.CardNoteSourse;
import ru.gb.veber.homework_6_notes.notes.CardNoteSourseImpl;
import ru.gb.veber.homework_6_notes.recycler.AdapterNote;


public class MainFragment extends Fragment implements AdapterNote.OnNoteClickListner{


    private static final String CURRENT_CARD_NOTE = "CURRENT_CARD_NOTE";
    private CardNote current_card_note;
    private CardNoteSourse source = CardNoteSourseImpl.getInstance() ;
    private AdapterNote adapters;
    private TextView item_count;

    private FragmentManager fragmentManager;

    private void init(View view)
    {
        RecyclerView list= view.findViewById(R.id.list);
        adapters = new AdapterNote();
        adapters.SetNote(source.getAll());
        adapters.setOnNoteCliclListner(this);
        list.setHasFixedSize(true);
        list.setAdapter(adapters);
        DividerItemDecoration itemDecoration = new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL);
        itemDecoration.setDrawable(getResources().getDrawable(R.drawable.separator, null));
        list.addItemDecoration(itemDecoration);
        fragmentManager=requireActivity().getSupportFragmentManager();
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main,container,false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        init(view);
        //Берем ту ноту которую редактировали. Сохраняем при перевороте экранна редактируем при клике.Если состояние не менялось то первый элемент из данных. Если менялось то выбранный элемент из данных. Если менялосьи ничего не выбирали то тоже первый.если у нас нет данных даже не будем показывать макет
        if(isLandscape()&&source.getSize()!=0)
        {
            if (savedInstanceState != null)
                current_card_note = (CardNote) savedInstanceState.getSerializable(CURRENT_CARD_NOTE);
            else
                current_card_note = source.getAll().get(0);
            //Показываем без BackStack один раз при перевороте чтобы пересоздавался
            showFragment(R.id.edit_fragment_container,EditNoteFragment.newInstance(current_card_note),false);
            //showLandEditFragment(current_card_note,false);
        }
    }
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        if(current_card_note==null&&source.getSize()!=0)
            current_card_note = source.getAll().get(0);
        outState.putSerializable(CURRENT_CARD_NOTE, current_card_note);
        super.onSaveInstanceState(outState);
    }

    //Popum click delete
    @Override
    public void onLondNoteClick(CardNote note)
    {
        source.delete(note.getId());
        if(current_card_note==note)
        {
            if(source.getSize()!=0)
                current_card_note=source.getAll().get(0);
        }
        adapters.SetNote(source.getAll());
        updateCount();
        if(isLandscape())
            showFragment(R.id.edit_fragment_container,EditNoteFragment.newInstance(current_card_note),true);
    }
    //Click
    @Override
    public void onNoteClick(CardNote note) {
        //Сохраняем выбранную заметку
        if (isLandscape())
        {
            if(current_card_note!=note)//Не хочу чтобы текущий всегда заново показывался
                showFragment(R.id.edit_fragment_container,EditNoteFragment.newInstance(note),true);
        }
        else
            showFragment(R.id.fragment_container,EditNoteFragment.newInstance(note),true);
        current_card_note = note;
    }
    //CRUD
    public void updateSourseAdapter(CardNote note) {
        source.update(note);
        adapters.SetNote(source.getAll());
    }
    public void addSourseAdapter(CardNote note_add) {
        source.create(note_add);
        current_card_note=note_add;
        adapters.SetNote(source.getAll());
        updateCount();
        if(isLandscape())//Без анимации так надо сохранится от бесконечного добавления
            fragmentManager.beginTransaction().replace(R.id.edit_fragment_container,EditNoteFragment.newInstance(current_card_note)).addToBackStack(null).commit();
    }
    public void updateCount()
    {
        item_count = requireActivity().findViewById(R.id.item_count);
        if(item_count!=null)
        {
            item_count.setText(String.valueOf(getItemCount()));
        }
    }
    public int getItemCount()
    {
        return source.getSize();
    }

    //Сортировки
    public void sortReverse() {
        source.sortReverse();
        adapters.SetNote(source.getAll());
    }
    public void sortName() {
        source.sortName();
        adapters.SetNote(source.getAll());
    }
    public void sortId() {
        source.sortId();
        adapters.SetNote(source.getAll());
    }
    public void showFragment(int container, Fragment fragment,boolean flag)
    {
        if(flag)
            fragmentManager.beginTransaction().setCustomAnimations(R.anim.slide_in,R.anim.fage_out).replace(container,fragment).addToBackStack(null).commit();
        else
            fragmentManager.beginTransaction().setCustomAnimations(R.anim.slide_in,R.anim.fage_out).replace(container,fragment).commit();
    }
    public boolean isLandscape() {
        return getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_LANDSCAPE;
    }
    public AdapterNote getAdapter() {
        return adapters;
    }
}
