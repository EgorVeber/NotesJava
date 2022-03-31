package ru.gb.veber.homework_6_notes.java;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;

import ru.gb.veber.homework_6_notes.R;
import ru.gb.veber.homework_6_notes.DIalog.DialogDeleteNote;
import ru.gb.veber.homework_6_notes.notes.CardNote;
import ru.gb.veber.homework_6_notes.notes.CardNoteSourse;
import ru.gb.veber.homework_6_notes.notes.CardNoteSourseImpl;
import ru.gb.veber.homework_6_notes.recycler.AdapterNote;
import ru.gb.veber.homework_6_notes.recycler.OnNoteClickListner;


public class MainFragment extends Fragment implements OnNoteClickListner {

    private static final int PENDING_REQUEST_ID = 446;
    private static final String NOTES_CHANNEL_ID = "NOTES_CHANNEL_ID";

    private static final String CURRENT_CARD_NOTE = "CURRENT_CARD_NOTE";

    private RecyclerView recyclerView;
    private CardNote current_card_note;
    private CardNoteSourse source = CardNoteSourseImpl.getInstance() ;
    private AdapterNote adapters;
    private TextView item_count;

    private FragmentManager fragmentManager;
    public static final String EditNoteFragmentTag= "EditNoteFragmentTag";

    private int notify_id =0;
    private void init(View view)
    {
        recyclerView= view.findViewById(R.id.list);
        adapters = new AdapterNote(this);
        adapters.SetNote(source.getAll());
        adapters.setOnNoteCliclListner(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapters);
        DefaultItemAnimator animator = new DefaultItemAnimator();
        //animator.setAddDuration(500);
        //animator.setRemoveDuration(500);
        recyclerView.setItemAnimator(animator);
        DividerItemDecoration itemDecoration = new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL);
        itemDecoration.setDrawable(getResources().getDrawable(R.drawable.separator, null));
        recyclerView.addItemDecoration(itemDecoration);
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
        if(isLandscape()&&source.getSize()!=0)
        {
            if (savedInstanceState != null)
                current_card_note = (CardNote) savedInstanceState.getSerializable(CURRENT_CARD_NOTE);
            else
                current_card_note = source.getAll().get(0);
            showFragment(R.id.edit_fragment_container,EditNoteFragment.newInstance(current_card_note),false);
        }
    }
    public void filter(String text)
    {
        adapters.getFilter().filter(text);
    }
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        if(current_card_note==null&&source.getSize()!=0)
            current_card_note = source.getAll().get(0);

        outState.putSerializable(CURRENT_CARD_NOTE, current_card_note);
        super.onSaveInstanceState(outState);
    }
    private void showNotification(String chanel_id,String Title,String description,int icon_id,int id_notification) {
        NotificationCompat.Builder  builder= new NotificationCompat.Builder(requireActivity(),chanel_id);
        Intent intent = new Intent(requireActivity(),MainActivity.class);
        PendingIntent pendingIntent= PendingIntent.
                getActivity(requireActivity(),PENDING_REQUEST_ID,intent,PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder1 = builder.
                setContentTitle(Title).
                setSmallIcon(icon_id).
                setAutoCancel(true).
                setContentIntent(pendingIntent).
                setContentText(description);
        NotificationManagerCompat.from(requireActivity()).notify(id_notification,builder.build());
    }
    @Override
    public void onLondNoteClick(CardNote note,int position)
    {
        DialogDeleteNote.getInstance(note,position).
                show(requireActivity().getSupportFragmentManager(),null);
    }

    @Override
    public void onColorNoteClick(CardNote note, int adapterPosition) {
        
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
        String descriprion = note_add.getName() + " "+new SimpleDateFormat("dd-MM-yy").format(note_add.getDateDate())+" "+note_add.getDescription();
        source.create(note_add);
        current_card_note=note_add;
        //adapters.SetNote(source.getAll());
        updateCount();
        showNotification(NOTES_CHANNEL_ID, "Заметка добавлена",descriprion,R.drawable.ic_baseline_plus_one_24,++notify_id);
    }
    public void deleteNote(CardNote note,int pos)
    {
        Snackbar.make(requireActivity().findViewById(R.id.anchor_snack), "Вернуть заметку "+note.getName()+"?",
                Snackbar.LENGTH_SHORT)// висит пока не нажмем на другую кнопку
                .setAction("ДА", view -> back_delete(note,pos))
                .show();
        source.delete(note.getId());
        if(current_card_note==note)
        {
            if(source.getSize()!=0)
                current_card_note=source.getAll().get(0);
        }
        adapters.delete(source.getAll(),pos);
        //recyclerView.smoothScrollToPosition(source.getSize() - 1);
        updateCount();
        String descriprion = note.getName() + " "+new SimpleDateFormat("dd-MM-yy").format(note.getDateDate())+" "+note.getDescription();
        showNotification(NOTES_CHANNEL_ID, "Заметка удалена",descriprion,R.drawable.ic_baseline_delete_forever_24,++notify_id);
    }
    private void back_delete(CardNote note,int pos) {
        source.res_create(note,pos);
        current_card_note=note;
        adapters.res_delete(source.getAll(),pos);
        //recyclerView.scrollToPosition(pos);
        recyclerView.smoothScrollToPosition(pos);
        updateCount();
    }
    public void updateCount()
    {
        item_count = requireActivity().findViewById(R.id.item_count);
        if(item_count!=null)
        {
            item_count.setText(String.valueOf(getItemCount()));
        }
        if(isLandscape())//Без анимации так надо сохранится от бесконечного добавления
            fragmentManager.beginTransaction().replace(R.id.edit_fragment_container,EditNoteFragment.newInstance(current_card_note)).addToBackStack(null).commit();
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
       // EditNoteFragmentTag
        if(flag)
            fragmentManager.beginTransaction().setCustomAnimations(R.anim.slide_in,R.anim.fage_out).replace(container,fragment,EditNoteFragmentTag).addToBackStack(null).commit();
        else
            fragmentManager.beginTransaction().setCustomAnimations(R.anim.slide_in,R.anim.fage_out).replace(container,fragment,EditNoteFragmentTag).commit();
    }
    public boolean isLandscape() {
        return getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_LANDSCAPE;
    }
    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = requireActivity().getMenuInflater();
        inflater.inflate(R.menu.menu_toolbar_main, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            // TODO оставим для примера
            case R.id.add_item_toolbar_main:
                //Toast.makeText(requireContext(),"add_item_toolbar_main",Toast.LENGTH_SHORT).show();
                return true;
            case R.id.sort_id_toolbar_main:
               // Toast.makeText(requireContext(),"sort_id_toolbar_main",Toast.LENGTH_SHORT).show();
                return true;
        }
        return super.onContextItemSelected(item);
    }
}