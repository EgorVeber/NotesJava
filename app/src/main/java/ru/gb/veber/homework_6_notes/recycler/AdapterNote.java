package ru.gb.veber.homework_6_notes.recycler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ru.gb.veber.homework_6_notes.R;
import ru.gb.veber.homework_6_notes.notes.CardNote;

public class AdapterNote extends RecyclerView.Adapter<HolderNote> {

    private List<CardNote> notes = new ArrayList<>();//

    @NonNull
    @Override
    public HolderNote onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context=parent.getContext();

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view= layoutInflater.inflate(R.layout.card_note_item,parent,false); // в какой элемент надуваем

        return new HolderNote(view,listner);//view нужен чтобы создать Holder который будет сожержать
    }

    @Override
    public void onBindViewHolder(@NonNull HolderNote holder, int position) {
        holder.bind(notes.get(position));
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public void SetNote(List<CardNote> notes) {
        this.notes= notes;
        notifyDataSetChanged();// Нужео перерисвать уведомление
    }
    public interface OnNoteClickListner
    {
        void onNoteClick(CardNote note);
    }
    private OnNoteClickListner listner;//Экземпляр

    public void setOnNoteCliclListner(OnNoteClickListner listner)
    {
        this.listner=listner;
    }

}
