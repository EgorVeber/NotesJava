package ru.gb.veber.homework_6_notes.notes;

import java.util.List;

public interface CardNoteSourse  {

    int create(CardNote note);
    int res_create(CardNote notem,int pos);
    CardNote read(int id);
    void update (CardNote note);
    void delete (int id);
    List<CardNote> getAll();
    int getSize();
    void sortReverse  ();
    void sortName ();
    void sortId ();

}
