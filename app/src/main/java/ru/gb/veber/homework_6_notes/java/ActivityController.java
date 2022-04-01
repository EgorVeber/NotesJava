package ru.gb.veber.homework_6_notes.java;

import java.util.Date;

import ru.gb.veber.homework_6_notes.notes.CardNote;

public interface ActivityController {
    void backClick();
    void delete(CardNote city,int position);
    void actionNote(int command, CardNote note);
    void dateUpdate(Date date);
}
