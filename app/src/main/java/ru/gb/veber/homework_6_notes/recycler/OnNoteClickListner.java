package ru.gb.veber.homework_6_notes.recycler;

import ru.gb.veber.homework_6_notes.notes.CardNote;

public interface OnNoteClickListner {
    void onNoteClick(CardNote note);
    void onLondNoteClick(CardNote note,int position);
    void onColorNoteClick(CardNote note, int adapterPosition);
}
