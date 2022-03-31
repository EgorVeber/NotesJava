package ru.gb.veber.homework_6_notes.recycler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ru.gb.veber.homework_6_notes.R;
import ru.gb.veber.homework_6_notes.notes.CardNote;

public class AdapterNote extends RecyclerView.Adapter<HolderNote> implements Filterable {

    private List<CardNote> notes = new ArrayList<>();//
    private List<CardNote> notesFull;
    private OnNoteClickListner listner;

    private Fragment fragment;

    public AdapterNote(Fragment fragment)
    {
            this.fragment = fragment;
    }
    @NonNull
    @Override
    public HolderNote onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context  context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.card_note_item, parent, false); // в какой элемент надуваем
        return new HolderNote(view, listner,fragment);//view нужен чтобы создать Holder который будет сожержать
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
        this.notes = notes;
        notesFull = new ArrayList<>(notes);
        notifyDataSetChanged();// Нужео перерисвать уведомление
    }
    public void delete(List<CardNote> notes, int position) {
        this.notes=notes;
        notesFull = new ArrayList<>(notes);
        notifyItemRemoved(position);
    }
    public void res_delete(List<CardNote> notes, int position) {
        this.notes=notes;
        notesFull = new ArrayList<>(notes);
        notifyItemInserted(position);
    }
    public void setOnNoteCliclListner(OnNoteClickListner listner) {
        this.listner = listner;
    }

    @Override
    public Filter getFilter() {
        return examplFilter;
    }
    private Filter examplFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<CardNote> filterList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0)
                filterList.addAll(notesFull);
            else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (CardNote note : notesFull) {
                    //startsWith
                    if (note.getName().toLowerCase().contains(filterPattern))
                        filterList.add(note);
                }
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values = filterList;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            notes.clear();
            notes.addAll((List) filterResults.values);
            notifyDataSetChanged();
        }
    };
}